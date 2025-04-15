const {Card, CardSchema} = require("../models/Card");



exports.getAllCards=async (req,res)=>{
    try {
        const cards= await Card.find();
        res.status(200).json(cards)
       } catch (error) {
        res.status(400).json({error: error.message})
       }
}

exports.getById=async (req,res)=>{
    try {
        const card= await Card.findOne({_id: req.params.id});
        res.status(200).json(card)
       } catch (error) {
        res.status(400).json({error: error.message})
       }
}

exports.addCard=async (req,res)=>{
    try {
        const card= new Card({
            "issueDate": new Date(),
            "isActive":true,
            "isDesactive":false,
            "expiryDate":req.body.expiryDate
        })
        
        
        const savedCard = await Card.create(card)
        res.status(200).json(savedCard)
       } catch (error) {
        res.status(400).json({error: error.message})
       }
}

exports.updateCard=async (req,res)=>{

    try {
        
        const card= {
            issueDate:req.body.issueDate,
            expiryDate: req.body.expiryDate
        };
        const updatedCard =await Card.findByIdAndUpdate({_id: req.params.id}, {$set: card} )
        res.status(200).json(updatedCard);
       } catch (error) {
        res.status(400).json({error: error.message})
       }

}

exports.deleteCard=async (req,res)=>{
    try {
        const {id} = req.params;
        await Card.findByIdAndDelete(id);
        res.send("deleted successfully")
       } catch (error) {
        res.status(400).json({error: error.message})
       }
}



exports.cardStats = async (req, res) => {
    try {
      const total = await Card.countDocuments();
      const expired = await Card.countDocuments({ expiryDate: { $lt: new Date() } });
      const active = await Card.countDocuments({ isActive: true });
      const soonExpiring = await Card.countDocuments({
        expiryDate: {
          $gt: new Date(),
          $lt: new Date(Date.now() + 7* 24 * 60 * 60 * 1000), // expires in the next 7 days
        },
      });
  
      res.json({ total, expired, active, soonExpiring });
    } catch (error) {
      res.status(500).json({ error: error.message });
    }
  };




exports.toggleCardStatus = async (req, res) => {
  try {
    const { id } = req.params; // Extract card ID from URL params

    // Find the card by its ID
    const card = await Card.findById(id);

    if (!card) {
      return res.status(404).json({ error: "Card not found" });
    }

    // Check if the card is currently active, if yes, deactivate it
    if (card.isActive) {
      card.isActive = false;   // Set isActive to false
      card.isDesactive = true; // Set isDesactive to true
    } else {
      return res.status(400).json({ error: "Card is already deactivated" });
    }

    // Save the updated card to the database
    await card.save();

    res.json({ message: "Card status has been successfully deactivated.", card });
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
};


  