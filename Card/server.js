require('dotenv').config();
const express = require('express');
const http = require('http');
const cors = require('cors');
const mongoose = require('mongoose');
const session = require('express-session');
const Keycloak = require('keycloak-connect');
const { Eureka } = require('eureka-js-client');
const axios = require('axios');
const app = express();
const server = http.createServer(app);
const PORT = process.env.PORT || 5000;


// =====================
// Session & Keycloak Setup
// =====================
const memoryStore = new session.MemoryStore();
app.use(session({
  secret: process.env.SESSION_SECRET || 'your-strong-secret-here',
  resave: false,
  saveUninitialized: false,
  store: memoryStore,
  cookie: { secure: false, httpOnly: true, maxAge: 24 * 60 * 60 * 1000 }
}));

const keycloakConfig = {
  realm: process.env.KEYCLOAK_REALM || 'libary-system',
  'auth-server-url': process.env.KEYCLOAK_AUTH_URL || 'http://localhost:8082/',
  resource: process.env.KEYCLOAK_CLIENT_ID || 'admin-client',
  'public-client': false,
  'confidential-port': 0,
  'ssl-required': 'external',
  credentials: { secret: process.env.KEYCLOAK_CLIENT_SECRET }
};

const keycloak = new Keycloak({ store: memoryStore }, keycloakConfig);

// ================
// Middlewares
// ================
app.use(cors({
  origin: process.env.CORS_ORIGINS ? process.env.CORS_ORIGINS.split(',') : ['http://localhost:8086'],
  methods: ['GET', 'POST', 'PUT', 'DELETE', 'OPTIONS'],
  allowedHeaders: ['Content-Type', 'Authorization'],
  exposedHeaders: ['Authorization'],
  credentials: true
}));
app.use(express.json());
app.use(express.urlencoded({ extended: true }));

// Health & Info endpoints (public)
app.get('/health', (req, res) => {
  res.status(200).json({ status: 'UP', mongo: mongoose.connection.readyState === 1 ? 'UP' : 'DOWN' });
});
app.get('/info', (req, res) => {
  res.json({ service: 'Card Service', version: '1.0.0', environment: process.env.NODE_ENV || 'development' });
});

// Keycloak middleware (protect routes below)
app.use(keycloak.middleware({ logout: '/logout', admin: '/' }));

// =================
// Eureka Client Setup
// =================
const eurekaClient = new Eureka({
  instance: {
    app: 'CARD-SERVICE',
    instanceId: `card-service:${PORT}`,
    hostName: 'localhost',
    ipAddr: '127.0.0.1',
    statusPageUrl: `http://localhost:${PORT}/info`, 
    healthCheckUrl: `http://localhost:${PORT}/health`, 
    port: { '$': PORT, '@enabled': true },
    vipAddress: 'card-service',
    dataCenterInfo: { '@class': 'com.netflix.appinfo.InstanceInfo$DefaultDataCenterInfo', name: 'MyOwn' }
  },
  eureka: {
    host: process.env.EUREKA_HOST || 'localhost',
    port: process.env.EUREKA_PORT || 8761,
    servicePath: '/eureka/apps/',
    maxRetries: 5,
    requestRetryDelay: 2000
  }
});

// =================
// Route Registration
// =================
// Pass keycloak instance to routes for route-level protection
require('./routes/routes')(app, keycloak);

// ================
// Database Connection & Eureka Registration
// ================
mongoose.connect(process.env.MONGO_URI, { useNewUrlParser: true, useUnifiedTopology: true })
  .then(() => {
    console.log('✅ Connected to MongoDB');
    eurekaClient.start(error => {
      if (error) {
        console.error('❌ Eureka registration error:', error);
        process.exit(1);
      }
      console.log('✅ Registered with Eureka');
    });
  })
  .catch(err => {
    console.error('❌ MongoDB connection error:', err);
    process.exit(1);
  });

// ================
// Error Handling Middleware
// ================
app.use((err, req, res, next) => {
  console.error('Unhandled Error:', err);
  res.status(err.status || 500).json({ error: err.message || 'Internal Server Error' });
});

// ================
// Start HTTP Server
// ================
server.listen(PORT, () => {
  console.log(`🚀 Server running at http://localhost:${PORT}`);
});
