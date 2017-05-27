var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var Message = new Schema({

	from : String,
	to : String,
	messageList : [
	{
		subject : String,
		message : String,
		timeStamp : {type: Date, default: Date.now }
	}],
	status : String //read || unread
})

module.exports = mongoose.model("Message",Message);
