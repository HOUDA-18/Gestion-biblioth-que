const express = require('express');
const http = require('http');
const cors = require('cors');
const bodyParser = require('body-parser');
const mongoose = require('mongoose');
const { createProxyMiddleware } = require('http-proxy-middleware');
const { Eureka } = require('eureka-js-client');

const app = express();
const server = http.createServer(app);
const PORT = process.env.PORT || 5000;

// Middlewares
app.use(cors({
  origin: ['http://localhost:8086', 'http://apigateway:8086'],
  methods: ['GET', 'POST', 'PUT', 'DELETE', 'OPTIONS'],
  allowedHeaders: ['Content-Type', 'Authorization'], // Authorization autorisé
  credentials: true // Si vous utilisez des cookies
}));
app.use(bodyParser.json({ limit: '50mb' }));
app.use(express.urlencoded({ extended: true }));
app.use(express.json());

// MongoDB URI
const mongoURI = process.env.MONGO_URI || require('./config/db.json').mongo.uri;

// Eureka Configuration (enregistré comme "CARD-SERVICE")
const eurekaClient = new Eureka({
  instance: {
    app: 'CARD-SERVICE',
    instanceId: `card-service:${PORT}`,
    hostName: 'card-service',
    ipAddr: '0.0.0.0',
    statusPageUrl: `http://card-service:${PORT}/info`,
    healthCheckUrl: `http://card-service:${PORT}/health`,
    port: { '$': PORT, '@enabled': true },
    vipAddress: 'card-service',
    dataCenterInfo: {
      '@class': 'com.netflix.appinfo.InstanceInfo$DefaultDataCenterInfo',
      name: 'MyOwn'
    }
  },
  eureka: {
    host: 'eureka',
    port: 8761,
    servicePath: '/eureka/apps/',
    maxRetries: 5,
    requestRetryDelay: 2000
  }
});

// Connexion MongoDB
mongoose.connect(mongoURI)
  .then(() => {
    console.log('✅ Connecté à MongoDB');

    // Démarrer Eureka après connexion Mongo
    eurekaClient.start((error) => {
      if (error) {
        console.error('❌ Erreur Eureka:', error);
        process.exit(1);
      }
      console.log('✅ Enregistré sur Eureka');
    });
  })
  .catch(err => {
    console.error('❌ Erreur MongoDB:', err);
    process.exit(1);
  });

// Routes principales de l'app
require('./routes/routes')(app);

// Endpoints pour Eureka
app.get('/health', (req, res) => {
  res.status(200).json({ 
    status: 'UP',
    components: {
      mongo: mongoose.connection.readyState === 1 ? 'UP' : 'DOWN'
    }
  });
});

app.get('/info', (req, res) => {
  res.json({
    service: 'Card Service',
    version: '1.0.0',
    status: 'RUNNING',
    environment: process.env.NODE_ENV || 'development'
  });
});

// Exemple de proxy vers un autre service via Eureka (non obligatoire)
app.use('/gateway/another-service', async (req, res, next) => {
  const instances = eurekaClient.getInstancesByAppId('ANOTHER-SERVICE');
  if (instances && instances.length > 0) {
    const instance = instances[0];
    const target = `http://${instance.hostName}:${instance.port.$}`;
    return createProxyMiddleware({ target, changeOrigin: true })(req, res, next);
  } else {
    res.status(500).send('ANOTHER-SERVICE indisponible');
  }
});

// Gestion arrêt propre
const gracefulShutdown = () => {
  eurekaClient.stop(() => {
    console.log('🔴 Deregistered from Eureka');
    server.close(() => {
      console.log('🔴 Server stopped');
      process.exit(0);
    });
  });
};
process.on('SIGINT', gracefulShutdown);
process.on('SIGTERM', gracefulShutdown);

// Lancer serveur
server.listen(PORT, () => {
  console.log('====================================');
  console.log(`🚀 Server running on port ${PORT}`);
  console.log('====================================');
});

// Logs Eureka
eurekaClient.on('started', () => console.log('🟡 Eureka registration started'));
eurekaClient.on('registered', () => console.log('🟢 Eureka registration completed'));
eurekaClient.on('renewed', () => console.log('🟠 Eureka lease renewed'));
eurekaClient.on('deregistered', () => console.log('🔴 Eureka deregistration completed'));
