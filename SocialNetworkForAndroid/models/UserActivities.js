var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var UserActivities = new Schema({
	screenName : String,
	following : [String],
	friends : [String],
	pendingRequests : [String],
	sentRequests : [String],
	messages : [{senderAddress : String, receiverAddress : String, subject : String, message : String }]
})

module.exports = mongoose.model("UserActivities",UserActivities);
