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

exports.createPost = function(req,res){
	var email = req.body.data.email;
	var postText = req.body.data.postText;
	var postImages = req.body.data.postImages;
	var username = "";

	Users.findOneAndUpdate({"email":email},{$push:{posts:{"postText":postText, "postImages":postImages}}}, function(err,result){
		if(err){
			res.status(400).json({"status":400, "result":err});
		}
		else{
			username = result.firstName+" "+result.lastName;
			Users.find({ $and:[{ $or: [ { friends: email }, { following: email } ] },{notification:"enable"}] }, function(err,result){
				if(err){
					res.status(400).json({"status":400, "result":err});			
				}
				else{
					var emailList = [];
					if(result){
						var i;
						for(i=0;i<result.length;i++){
							(function(i){
								emailList.push(result[i].email);
								if(i==result.length-1){
									var mailOptions = {
								    from: '"SocialNetworkApp" <cmpe277socialnetworkapp@gmail.com>',
								    to: emailList, // list of receivers
								    subject: "Hey there!", // Subject line
								    text: username+" just posted an update. LogIn to the app to see the latest updates from your friends and followers!"
								    
								};

								// send mail with defined transport object
								transporter.sendMail(mailOptions, function(err, result){
								    if (err) {
								        console.log(err);
										res.status(400).json({"status":400,"result":err});
								    }
								    else{
								    	console.log("Mail sent");
										res.status(200).json({"status":200,"result":"Post created and mail sent!"});			    	
								    }
								    
								});
								}
							})(i);
						}


					}else{
						res.status(200).json({"status":200, "result":"Posted!"});
					}
				}
			});				
		}
	});
}


exports.fetchTimeline = function(req,res){
	var email = req.body.data.email;
	var postList = [];
	var friends=[], following=[];
	Users.find({"email":email}, function(err,result){
		if(err){
			res.status(400).json({"status":400, "result":err});
		}
		else{
				if(result){
					friends = result[0].friends,
					following = result[0].following;
					console.log("friends:"+friends+"...following:"+following);
					Users.find({ $or: [ {email: { $in: friends }}, {email: { $in: following }}, {email: email}, {visibility:"public"} ] }, function(err,result){
						if(err){
							res.status(400).json({"status":400, "result":err});			
						}
						else{
							var i=0,j=0;
							for(i=0;i<result.length;i++){
								(function(i){

									if(i==result.length-1 && result[i].posts.length==0){
										res.status(200).json({"status":200,"result":postList});
									}
									for(j=0;j<result[i].posts.length;j++){
										(function(j){
											//if(!(postList.includes(result[i].posts[j]))){
												var jsonPost = {};
												jsonPost.screenName = result[i].screenName;
												jsonPost.post = result[i].posts[j];
												result[i].posts[j].screenName = result[i].screenName;
												console.log("postsss:::"+result[i].posts[j]);
												postList.push(jsonPost);
											//}
											postList.sort(function(a,b){
												return b.post.timeStamp - a.post.timeStamp;
											});
											if(i==result.length-1 && j==result[i].posts.length-1){
												res.status(200).json({"status":200,"result":postList});
											}
										})(j);
									}
								})(i);
							}
						}
					});
				}else{
					res.status(400).json({"status":400, "result":"No user logged In!"});
				}
		}
	});

}


exports.fetchPost = function(req,res){
	var email = req.body.data.email;


	Users.findOne({"email":email}, function(err,result){
		if(err){
			res.status(400).json({"status":400, "result":err});
		}
		else{
			if(result){
				var postList = [];
				postList = result.posts;
				postList.sort(function(a,b){
					return b.timeStamp - a.timeStamp;
				});
				
				res.status(200).json({"status":200, "result":postList});
			}else{
				res.status(400).json({"status":400, "result":"User not logged In"});
			}

			
		}
	});
}