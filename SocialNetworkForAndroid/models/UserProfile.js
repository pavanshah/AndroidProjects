var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var UserProfile = new Schema({
	screenName : String,
	firstName: String,
	lastName : String,
	email: String,	
	password : String,
	profilePic : String,
	address : String,
	profession : String,
	aboutMe : String,
	interests : String,
	visibility : String, // friendsOnly, public
	verificationCode: String,
	status : String,  //  unverified, verified
	notification : String,

	following : [String],
	friends : [String],
	pendingRequests : [String],
	sentRequests : [String],
	messages : [
		{
			senderScreenName : String, 
			receiverScreenName : String, 
			subject : String, 
			message : String, 
			timeStamp:{type: Date, default: Date.now }
		}
		],
	posts : [{postText : String, 
		postImages : [String],
		timeStamp:{type: Date, default: Date.now }
		}]
})

module.exports = mongoose.model("UserProfile",UserProfile);
