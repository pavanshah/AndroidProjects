var mongo = require("./mongo");
var mongoose = require('mongoose');
var Users = require('../models/UserProfile');


var nodemailer = require('nodemailer');

var transporter = nodemailer.createTransport({
    service: 'gmail',
    auth: {
        user: 'cmpe277socialnetworkapp@gmail.com',
        pass: 'cmpe277finalprojec'
    }
});


exports.login = function(req,res){

	var email = req.body.data.email;
	var password = req.body.data.password;

	Users.findOne({"email":email}, function(err,result){
		if(err)
		{
			res.status(200).json({"status":400,"result":err});
		}
		else
		{
			if(result)
			{
				if(result.status == "verified")
				{
					if(result.password == password)
					{
						res.status(200).json({"status":200,"result":result});
					}
					else{
						res.status(200).json({"status":402,"result":"Wrong Password!"});
					}
				}
				else{
					res.status(200).json({"status":401,"result":"Please verify your emailID first!"});
				}
			}
			else
			{
				res.status(200).json({"status":400,"result":"User doesn't exist! Please Sign Up!"});
			}
		}
	});

}


exports.signUp = function(req,res) {
	
	var email = req.body.data.email;
	var screenName = req.body.data.screenName;
	var firstName = req.body.data.firstName;
	var lastName = req.body.data.lastName;
	var password = req.body.data.password;
	var profilePic = "https://s3-us-west-1.amazonaws.com/cmpefb/WhatsApp+Image+2017-05-25+at+6.18.45+AM.jpeg";
	var address = "";
	var profession = "";
	var aboutMe = "";
	var interests = "";
	var visibility = "friendsOnly";
	var status = "unverified";
	var verificationCode = Math.floor((Math.random())*10000)+"";
	var notification = "disable";

	var following = [];
	var friends = [];
	var pendingRequests = [];
	var sentRequests = [];
	var messages = [];
	var posts = [];


	Users.findOne({"email":email},function(err,result){
		if(!err){
			if(result){
				res.status(200).json({"status":401,"result":"User already registered!"});
			}
			else
			{
				Users.findOne({"screenName":screenName},function(err,result){
				if(!err){
					if(result)
					{
						res.status(200).json({"status":402,"result":"Screen Name already in use, please enter different Screen Name"});
					}
					else{
						var userJson = {
							"email":email,
							"screenName":screenName,
							"firstName":firstName,
							"lastName":lastName,
							"password":password,
							"profilePic":profilePic,
							"address":address,
							"profession":profession,
							"aboutMe":aboutMe,
							"interests":interests,
							"visibility":visibility,
							"status":status,
							"verificationCode":verificationCode,
							"notification":notification,
							"following":following,
							"friends":friends,
							"pendingRequests":pendingRequests,
							"sentRequests":sentRequests,
							"messages":messages,
							"posts":posts
						}

						userProfile = Users(userJson);

						userProfile.save(function(err,result){
							if(err){
								res.status(400).json({"status":400,"result":result});
							}
							else{

								var mailOptions = {
								    from: '"SocialNetworkApp" <cmpe277socialnetworkapp@gmail.com>',
								    to: email, // list of receivers
								    subject: "Hello "+firstName+" "+lastName, // Subject line
								    text: 'Your verification code is:'+verificationCode+" \n Please verify your account using the given code, see you soon!"
								    
								};

								// send mail with defined transport object
								transporter.sendMail(mailOptions, function(err, result){
								    if (err) {
								        console.log(err);
										res.status(400).json({"status":400,"result":err});
								    }
								    else{
								    	console.log("Mail sent");
										res.status(200).json({"status":200,"result":"User Profile created! Please confirm email to Log In!"});			    	
								    }
								    
								});
								//res.status(200).json({"result":"User Profile created! Please confirm email to Log In!"});
							}
						})

					}
				}
			});
			}
		}
		else{
			res.status(400).json({"status":400,"result":err});
		}
	});
}



exports.verifyCode = function(req,res){
	var email = req.body.data.email;
	var code = req.body.data.verificationCode;

	Users.findOne({"email":email}, function(err, result){
		if(err)
		{
			res.status(400).json({"status":400,"result":err});
		}
		else
		{
			if(!result)
			{
				res.status(200).json({"status":401,"result":"User doesn't exist!"});		
			}
			else
			{
				if(result.status == "verified")
				{
					res.status(200).json({"status":402,"result":"User already verified! Please Login!"});
				}
				else if(result.verificationCode == code)
				{
					Users.update({"email":email}, {$set:{"status":"verified"}}, function(err,result){
						if(err)
						{
							res.status(400).json({"status":400,"result":err});
						}
						else
						{
							res.status(200).json({"status":200,"result":"User verified! Please Login!"});
						}
					})
				}
				else
				{
					res.status(200).json({"status":403,"result":"Verification Code mismatched!"});
				}
			}
		}
	});
}