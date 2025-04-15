module.exports= app =>{

const cardController = require("../controllers/CardController")
const {CardSchema}=require("../models/Card")
var router = require("express").Router();
const validate = require('../middelwares/validate')




//card routes

router.get('/cards/getById/:id', cardController.getById)

router.get('/cards/allCards', cardController.getAllCards)

router.post('/cards/addCard',validate(CardSchema), cardController.addCard)

router.put('/cards/updateCard/:id',validate(CardSchema), cardController.updateCard)

router.delete('/cards/deleteCard/:id', cardController.deleteCard)

router.get('/cards/cardStats', cardController.cardStats)

router.put('/cards/cardStatus/:id', cardController.toggleCardStatus)





app.use('/api', router);
  
}