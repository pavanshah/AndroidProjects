var mongo = require("./mongo");
var mongoose = require('mongoose');
var Users = require('../models/UserProfile');
var UserActivities = require('../models/UserActivities');

var nodemailer = require('nodemailer');

var transporter = nodemailer.createTransport({
    service: 'gmail',
    auth: {
        user: 'cmpe277socialnetworkapp@gmail.com',
        pass: 'cmpe277finalprojec'
    }
});


exports.addFriendByEmail = function(req,res){
	var email = req.body.data.email;
	var firstName = req.body.data.firstName;
	var lastName = req.body.data.lastName;
	var friendEmail = req.body.data.friendEmail;

	var emailScreenName, friendScreenName;

	Users.findOne({"email":email},function(err,result){
		if(err){
			res.status(400).json({"status":400,"result":err});
		}
		else{
			if(result){
				//emailScreenName = result.screenName;
				if(result.sentRequests.includes(friendEmail)){
					res.status(400).json({"status":400,"result":"Request already sent once!"});
				}
				else{
					Users.findOne({"email":friendEmail},function(err,result){
						if(err){
							res.status(400).json({"status":400,"result":err});
						}
						else{
							if(result){

								Users.findOneAndUpdate({"email":email},{$push:{sentRequests:friendEmail}}, function(err,result){
									    		if(err){
									    			res.status(400).json({"status":400,"result":err});			
									    		}
									    		else{

									    			Users.findOneAndUpdate({"email":friendEmail},{$push:{pendingRequests:email}},function(err,result){
									    				if(err){
									    					res.status(400).json({"status":400,"result":err});
									    				}
									    				else{
									    					//console.log("Mail sent");
															res.status(200).json({"status":200,"result":"Request sent!"});
									    				}
									    			});
									    			
									    		}
									    	});

							}else{
								var mailOptions = {
									from: '"SocialNetworkApp" <cmpe277socialnetworkapp@gmail.com>',
									to: friendEmail, // list of receivers
									subject: "Hey there!", // Subject line
									text: "Your friend"+firstName+" "+lastName+" want to add you as a friend in Social Network App.\n Please download our app from play store and register in order to connect with him!"
											    
									};

									// send mail with defined transport object
									transporter.sendMail(mailOptions, function(err, result){
									    if (err) {
									        console.log(err);
											res.status(400).json({"status":400,"result":err});
									    }
									    else{
									    	Users.findOneAndUpdate({"email":email},{$push:{sentRequests:friendEmail}}, function(err,result){
									    		if(err){
									    			res.status(400).json({"status":400,"result":err});			
									    		}
									    		else{
									    			console.log("Mail sent");
													res.status(200).json({"status":200,"result":"Request sent!"});
									    		}
									    	});

									    				    	
									    }
										    
									});
							}
						}
					});
				}
			}
			else{
				res.status(400).json({"status":400,"result":"User doesn't exist!"});
			}
		}
	})




	

}



exports.fetchInvitations = function(req,res){
	var email = req.body.data.email;

	var resultList = [];
	Users.findOne({"email":email}, function(err, result){
		if(err){
			res.status(400).json({"status":400, "result":err});
		}
		else{
			if(result){
				if(result.pendingRequests.length == 0){
				res.status(200).json({"status":200, "result":resultList});
				}
				else{
					Users.find({"email" : {$in : result.pendingRequests}}, function(err, result){
						if(err){
							res.status(400).json({"status":400, "result":err});			
						}
						else{
							var i=0,j=0

							for(i=0;i<result.length;i++){
								(function(i){
								 var jsonObj = {
								 	"screenName": result[i].screenName,
								 	"email": result[i].email,
								 	"profilePic": result[i].profilePic
								 };
								 resultList.push(jsonObj);

								 if(i==result.length-1){
								 	res.status(200).json({"status":200, "result":resultList});
								 }

								})(i);
							}
						}
					});
				}	
			}
			else{
				res.status(400).json({"status":400, "result":"No user logged In!"});		
			}
			




			
		}
	});
}

exports.fetchSentRequests = function(req,res){
	var email = req.body.data.email;
	var resultList = [];
	Users.findOne({"email":email}, function(err, result){
		if(err){
			res.status(400).json({"status":400, "result":err});
		}
		else{

			if(result){
					if(result.sentRequests.length == 0){
						res.status(200).json({"status":200, "result":resultList});
					}else{

					Users.find({"email" : {$in : result.sentRequests}}, function(err, result){
						if(err){
							res.status(400).json({"status":400, "result":err});			
						}
						else{
							var i=0,j=0

							for(i=0;i<result.length;i++){
								(function(i){
								 var jsonObj = {
								 	"screenName": result[i].screenName,
								 	"email": result[i].email,
								 	"profilePic": result[i].profilePic
								 };
								 resultList.push(jsonObj);

								 if(i==result.length-1){
								 	res.status(200).json({"status":200, "result":resultList});
								 }

								})(i);
							}
						}
					});
				}
			}
			else{
				res.status(400).json({"status":400, "result":"No user logged in!"});		
			}













			//res.status(200).json({"status":200, "result":result.sentRequests});
		}
	});
}

exports.acceptInvitation = function(req,res){
	var email = req.body.data.email;
	var friendEmail = req.body.data.friendEmail;

	Users.findOneAndUpdate({"email":email}, {$push:{friends:friendEmail}, $pull:{pendingRequests:friendEmail}}, function(err,result){
		if(err){
			res.status(400).json({"status":400, "result":err});
		}
		else{
			Users.findOneAndUpdate({"email":friendEmail}, {$push:{friends:email}, $pull:{sentRequests:email}}, function(err,result){
				if(err){
					res.status(400).json({"status":400, "result":err});
				}
				else{
					res.status(200).json({"status":200, "result":"Request Accepted"});
				}
			})
		}
	});
}


exports.followProfile = function(req,res){
	var email = req.body.data.email;
	var followEmail = req.body.data.followEmail;

	Users.findOneAndUpdate({"email":email}, {$push:{following:followEmail}}, function(err, result){
		if(err){
			res.status(400).json({"status":400, "result":err});	
		}
		else{
			if(result){
				res.status(200).json({"status":200, "result":"Following!"});
			}
			else{
				res.status(400).json({"status":400, "result":"User doesn't exist!"});
			}
		}
	});



}


exports.fetchFollowingProfile = function(req,res){
	var email = req.body.data.email;
	var resultList = [];
	Users.findOne({"email":email}, function(err,result){
		if(err){
			res.status(400).json({"status":400, "result":err});
		}
		else{
			if(result){
			if(result.following.length == 0){
				res.status(200).json({"status":200, "result":resultList});
			}


			Users.find({"email" : {$in : result.following}}, function(err, result){
				if(err){
					res.status(400).json({"status":400, "result":err});			
				}
				else{
					var i=0,j=0

					for(i=0;i<result.length;i++){
						(function(i){
						 var jsonObj = {
						 	"screenName": result[i].screenName,
						 	"email": result[i].email,
						 	"profilePic":result[i].profilePic
						 };
						 resultList.push(jsonObj);
						 console.log("jbadc"+resultList);
						 if(i==result.length-1){
						 	res.status(200).json({"status":200, "result":resultList});
						 }

						})(i);
					}
				}
			});


			}
			else{
				res.status(400).json({"status":400, "result":"User doesnt exist!"});
			}
		}
	});
}

exports.rejectFriendRequest = function(req,res){
	var email = req.body.data.email;
	var rejectEmail = req.body.data.rejectEmail;

	Users.findOneAndUpdate({"email":email},{$pull:{pendingRequests:rejectEmail}}, function(err,result){
		if(err){
			res.status(400).json({"status":400,"result":err});
		}else{
			Users.findOneAndUpdate({"email":rejectEmail}, {$pull:{sentRequests:email}}, function(err,result){
				if(err){
					res.status(400).json({"status":400,"result":err});		
				}else{
					res.status(200).json({"status":200,"result":"Rejected!"});
				}
			});
		}
	});
}