var mongo = require("./mongo");
var mongoose = require('mongoose');
var Users = require('../models/UserProfile');


exports.getProfile = function(req,res){
	var email = req.body.data.email;

	Users.findOne({"email":email}, {password:0}, function(err, result){
		if(err){
			res.status(400).json({"status":400,"result":err});
		}
		else
		{
			if(result)
			{
				res.status(200).json({"status":200,"result":result});
			}
			else
			{
				res.status(400).json({"status":400,"result":"User doesn't exist!"});
			}
		}
	});
}


exports.updateProfile = function(req,res){
	
	var screenName = req.body.data.screenName;
	var firstName = req.body.data.firstName;
	var lastName = req.body.data.lastName;
	var email= req.body.data.email;	
	var profilePic = req.body.data.profilePic;
	var address = req.body.data.address;
	var profession = req.body.data.profession;
	var aboutMe = req.body.data.aboutMe;
	var interests = req.body.data.interests;

	Users.findOne({"screenName":screenName}, function(err,result){
		if(err){
			res.status(400).json({"status":400,"result":err});
		}
		else{
			if(result){
				if(!(result.email == email)){
					res.status(200).json({"status":400, "result":"Screen Name already taken"});	
				}				
			else{
				Users.findOneAndUpdate({"email":email}, {$set:{screenName:screenName, firstName:firstName, lastName:lastName, profilePic:profilePic, address:address, profession:profession, aboutMe:aboutMe, interests:interests}}, function(err, result){
					if(err){
						res.status(400).json({"status":400,"result":err});
					}
					else{
						if(result){
							res.status(200).json({"status":200,"result":"Profile Updated!"});
						}else
						{
							res.status(400).json({"status":400,"result":"User not present!"});
						}
					}
				});
			}
			}
			else{
				Users.findOneAndUpdate({"email":email}, {$set:{screenName:screenName, firstName:firstName, lastName:lastName, profilePic:profilePic, address:address, profession:profession, aboutMe:aboutMe, interests:interests}}, function(err, result){
					if(err){
						res.status(400).json({"status":400,"result":err});
					}
					else{
						if(result){
							res.status(200).json({"status":200,"result":"Profile Updated!"});
						}else
						{
							res.status(400).json({"status":400,"result":"User not present!"});
						}
					}
				});
			}
		}
	});

	
}

exports.setVisibilityLevel = function(req,res){
	var email = req.body.data.email;
	var visibilityLevel = req.body.data.visibilityLevel;

	Users.findOneAndUpdate({"email":email}, {$set:{visibility:visibilityLevel}}, function(err,result){
		if(err){
			res.status(400).json({"status":400,"result":err});
		}
		else
		{
			if(result){
				res.status(200).json({"status":200,"result":"Visibility status changed!"});
			}
			else
			{
				res.status(400).json({"status":400,"result":"User doesn't exist!"});
			}
			
		}
	});
}

exports.setNotificationOption = function(req,res){

	var email = req.body.data.email;
	var notification = req.body.data.notification;

	Users.findOneAndUpdate({"email":email}, {$set:{notification:notification}}, function(err,result) {
		if(err){
			res.status(400).json({"status":400,"result":err});
		}
		else
		{
			if(result){
				res.status(200).json({"status":200,"result":"Notification settings changed!"});
			}
			else
			{
				res.status(400).json({"status":400,"result":"User not present!"});	
			}
		}
	});

}

exports.browseProfile = function(req,res){
	var email = req.body.data.email;
	var friends = [];
	Users.findOne({"email":email}, function(err,result){
		if(err){
			res.status(400).json({"status":400,"result":err});
		}
		else{
			console.log("ssfdf"+result);
			if(result){
				friends = result.friends;
				Users.find({$or: [{email:  {$in: friends}}, {visibility:"public"}]}, function(err, result){
					if(err){
						res.status(400).json({"status":400,"result":err});				
					}
					else{
						res.status(200).json({"status":200,"result":result});
					}
				});
			}
		}
	});


	
}

exports.browseProfileWithoutFollowingProfiles = function(req,res){
	var email = req.body.data.email;
	var friends = [];
	var following = [];
	Users.findOne({"email":email}, function(err,result){
		if(err){
			res.status(400).json({"status":400,"result":err});
		}
		else{
			console.log("ssfdf"+result);
			if(result){
				friends = result.friends;
				following = result.following;
				Users.find({$and: [{email:  {$nin: friends}}, {visibility:"public"}, {email:  {$nin: following}}]}, function(err, result){
					if(err){
						res.status(400).json({"status":400,"result":err});				
					}
					else{
						res.status(200).json({"status":200,"result":result});
					}
				});
			}
		}
	});


	
}


exports.updateProfilePic = function(req,res){
	var email = req.body.data.email;
	var profilePic = req.body.data.profilePic;

	Users.findOneAndUpdate({"email":email}, {$set:{profilePic:profilePic}}, function(err,result){
		if(err){
			res.status(400).json({"status":400, "result":err});
		}else{
			res.status(200).json({"status":200, "result":"Updated!"});
		}
	});

}