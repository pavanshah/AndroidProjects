var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var UserTimeline = new Schema({
	screenName : String,
	following : [String],
	friends : [String],
	posts : [{postText : String, postImages : [String]}]
})

module.exports = mongoose.model("UserTimeline",UserTimeline);
