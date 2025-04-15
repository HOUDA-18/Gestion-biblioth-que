const client = new Eureka({
    instance: {
      app: process.env.EUREKA_INSTANCE_APPNAME,
      instanceId: `${process.env.EUREKA_INSTANCE_HOSTNAME}:${process.env.EUREKA_INSTANCE_NONSECUREPORT}`,
      hostName: process.env.EUREKA_INSTANCE_HOSTNAME,
      ipAddr: '0.0.0.0',
      statusPageUrl: `http://${process.env.EUREKA_INSTANCE_HOSTNAME}:${process.env.EUREKA_INSTANCE_NONSECUREPORT}/info`,
      healthCheckUrl: `http://${process.env.EUREKA_INSTANCE_HOSTNAME}:${process.env.EUREKA_INSTANCE_NONSECUREPORT}/health`,
      port: {
        '$': parseInt(process.env.EUREKA_INSTANCE_NONSECUREPORT),
        '@enabled': true
      }
    },
    eureka: {
      host: 'eureka',
      port: 8761,
      servicePath: '/eureka/apps/',
      maxRetries: 10,
      requestRetryDelay: 3000
    }
  });