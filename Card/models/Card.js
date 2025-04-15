const mongoose = require('mongoose')
const yup = require('yup')

const card = new mongoose.Schema({
    issueDate: {type: Date, required: true},

    isActive: {
        type: Boolean,
        default: true,
      },
      
      isDesactive: {
        type: Boolean,
        default: false,
      },
      

    expiryDate: {type: Date, required: true}
    

})

const Card = mongoose.model('card', card)

const CardSchema = yup.object({
    body:
        yup.object({

            expiryDate: yup.date().min(new Date(), "Expiry date must be in the future").required("expiryDate name is required")
        })
})


module.exports = {Card, CardSchema}
