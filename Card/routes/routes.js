module.exports = (app, keycloak) => {
    const router = require('express').Router();
    const cardController = require('../controllers/CardController');
    const { CardSchema } = require('../models/Card');
    const validate = require('../middelwares/validate');
  
    // Protection globale : toutes les routes sous /api nécessitent un token valide
    router.use(keycloak.protect());
  
    // GET Card by ID
    router.get('/cards/getById/:id',
      cardController.getById
    );
  
    // GET All Cards
    router.get('/cards/allCards',
      cardController.getAllCards
    );
  
    // POST Add Card
    router.post('/cards/addCard',
      validate(CardSchema),
      cardController.addCard
    );
  
    // PUT Update Card
    router.put('/cards/updateCard/:id',
      validate(CardSchema),
      cardController.updateCard
    );
  
    // DELETE Card
    router.delete('/cards/deleteCard/:id',
      cardController.deleteCard
    );
    router.get('/cards/welcome',cardController.getWelcomeMessage);
  
    // GET Card Statistics
    router.get('/cards/cardStats',
      cardController.cardStats
    );
  
    // Toggle Card Status
    router.put('/cards/cardStatus/:id',
      cardController.toggleCardStatus
    );
  
    // Gestion des erreurs de validation
    router.use((err, req, res, next) => {
      if (err.name === 'ValidationError') {
        return res.status(400).json({ error: err.message });
      }
      next(err);
    });
  
    // Monte le routeur sous /api
    app.use('/api', router);
  
    // Gestion 404 pour toutes les autres routes
    app.use('*', (req, res) => {
      res.status(404).json({
        error: 'Endpoint non trouvé',
        message: `La route ${req.originalUrl} n'existe pas`
      });
    });
  };