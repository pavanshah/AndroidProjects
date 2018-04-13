var mongo = require("./mongo");
var mongoose = require('mongoose');
var Users = require('../models/UserProfile');
var Message = require('../models/Message');

var nodemailer = require('nodemailer');

var transporter = nodemailer.createTransport({
    service: 'gmail',
    auth: {
        user: 'cmpe277socialnetworkapp@gmail.com',
        pass: 'cmpe277finalprojec'
    }
});

exports.sendMessage = function(req,res){
	var email = req.body.data.email;
	var screenName = req.body.data.screenName;
	var toEmail;// = req.body.data.toEmail;
	var toScreenName = req.body.data.toScreenName;
	var subject = req.body.data.subject;
	var message = req.body.data.message;


/*senderScreenName : String, 
			receiverScreenName : String, 
			subject : String, 
			message : String, */


	Users.find({ $or:[{"email":email}, {"screenName":toScreenName}]}, function(err,result){
		if(err){
			res.status(400).json({"status":400, "result":err});
		}else{
			if(result.length==2){

				if(result[0].screenName == toScreenName){
					toEmail = result[0].email;
				}else{
					toEmail = result[1].email;
				}
				
/*				var json = {
					"senderScreenName":screenName,
					"receiverScreenName":toScreenName,
					"subject":subject,
					"message":message
				}

				messageJson = Message(json);

				messageJson.save(function(err,result){
					if(err){
						res.status(400).json({"status":400, "result":err});			
					}
					else{
						res.status(400).json({"status":400, "result":"Message Sent!"});
					}
				});*/

				Message.findOne({"from":screenName, "to":toScreenName}, function(err,result){
					if(err){
						res.status(400).json({"status":400, "result":err});				
					}else{
						if(result){
							Message.findOneAndUpdate({"from":screenName, "to":toScreenName}, {$push: {messageList:{"subject":subject, "message":message}}}, function(err,result){
								if(err){
									res.status(400).json({"status":400, "result":err});				
								}else{
									Message.findOne({"from":toScreenName,"to":screenName}, function(err,result){
										if(err){
											res.status(400).json({"status":400, "result":err});
										}else{
											if(result){
												Message.findOneAndUpdate({"from":toScreenName, "to":screenName}, {$set:{"status":"unread"}}, function(err,result){
													if(err){
														res.status(400).json({"status":400, "result":err});
													}else{
														//implement email and return;


														var mailOptions = {
													    from: '"SocialNetworkApp" <cmpe277socialnetworkapp@gmail.com>',
													    to: toEmail, // list of receivers
													    subject: "New message from "+screenName+" !", // Subject line
													    text: "Message details:\n" +
													    		"Subject: "+subject+"\n"+
													    		"Message: "+message
													    
													};

													// send mail with defined transport object
													transporter.sendMail(mailOptions, function(err, result){
													    if (err) {
													        console.log(err);
															res.status(400).json({"status":400,"result":err});
													    }
													    else{
													    	console.log("Mail sent");
															res.status(200).json({"status":200, "result":"Message Sent!"});
													    }
													    
													});



														
													}
												});

											}else{
												pushJson = {
													"from":toScreenName,
													"to":screenName,
													"messageList":[],
													"status":"unread"
												}

												messageJson = Message(pushJson);
												messageJson.save(function(err,result){
													if(err){
														res.status(400).json({"status":400, "result":err});
													}else{
														//email sending to be done

														var mailOptions = {
														    from: '"SocialNetworkApp" <cmpe277socialnetworkapp@gmail.com>',
														    to: toEmail, // list of receivers
														    subject: "New message from "+screenName+" !", // Subject line
														    text: "Message details:\n" +
														    		"Subject: "+subject+"\n"+
														    		"Message: "+message
														    
														};

														// send mail with defined transport object
														transporter.sendMail(mailOptions, function(err, result){
														    if (err) {
														        console.log(err);
																res.status(400).json({"status":400,"result":err});
														    }
														    else{
														    	console.log("Mail sent");
																res.status(200).json({"status":200, "result":"Message Sent!"});
														    }
														    
														});

														//res.status(200).json({"status":200, "result":"Message Sent!"});						
													}
												});
											}
										}
									})

												
								}
							});
						}else{
							newJson = {
								"from":screenName,
								"to":toScreenName,
								"messageList":[{
									"subject":subject,
									"message":message
								}],
								"status":"read"
							}
							newMessageJson = Message(newJson);
							newMessageJson.save(function(err,result){
								if(err){
									res.status(400).json({"status":400, "result":err});
								}else{
									Message.findOne({"from":toScreenName,"to":screenName}, function(err,result){
										if(err){
											res.status(400).json({"status":400, "result":err});
										}else{
											if(result){
												Message.findOneAndUpdate({"from":toScreenName, "to":screenName}, {$set:{"status":"unread"}}, function(err,result){
													if(err){
														res.status(400).json({"status":400, "result":err});
													}else{
														//implement email and return;

														var mailOptions = {
														    from: '"SocialNetworkApp" <cmpe277socialnetworkapp@gmail.com>',
														    to: toEmail, // list of receivers
														    subject: "New message from "+screenName+" !", // Subject line
														    text: "Message details:\n" +
														    		"Subject: "+subject+"\n"+
														    		"Message: "+message
														    
														};

														// send mail with defined transport object
														transporter.sendMail(mailOptions, function(err, result){
														    if (err) {
														        console.log(err);
																res.status(400).json({"status":400,"result":err});
														    }
														    else{
														    	console.log("Mail sent");
																res.status(200).json({"status":200, "result":"Message Sent!"});
														    }
														    
														});

														//res.status(200).json({"status":200, "result":"Message Sent!"});
													}
												});

											}else{
												pushJson = {
													"from":toScreenName,
													"to":screenName,
													"messageList":[],
													"status":"unread"
												}

												messageJson = Message(pushJson);
												messageJson.save(function(err,result){
													if(err){
														res.status(400).json({"status":400, "result":err});
													}else{
														//email sending to be done

														var mailOptions = {
														    from: '"SocialNetworkApp" <cmpe277socialnetworkapp@gmail.com>',
														    to: toEmail, // list of receivers
														    subject: "New message from "+screenName+" !", // Subject line
														    text: "Message details:\n" +
														    		"Subject: "+subject+"\n"+
														    		"Message: "+message
														    
														};

														// send mail with defined transport object
														transporter.sendMail(mailOptions, function(err, result){
														    if (err) {
														        console.log(err);
																res.status(400).json({"status":400,"result":err});
														    }
														    else{
														    	console.log("Mail sent");
																res.status(200).json({"status":200, "result":"Message Sent!"});
														    }
														    
														});

														//res.status(200).json({"status":200, "result":"Message Sent!"});						
													}
												});
											}
										}
									})

												
								}
							});
						}
					}
				});


			}else{
				res.status(400).json({"status":400, "result":"Sender or Receiver not valid!"});
			}
		}
	})


}

exports.getIndividualMessage = function(req,res){
	var screenName = req.body.data.screenName;
	var toScreenName = req.body.data.toScreenName;

	var messageJson = [];
	var firstResult, secondResult;

	Message.findOne({"from":screenName, "to":toScreenName}, function(err,result){
		if(err){
			res.status(400).json({"status":400, "result":err});
		}else{
			if(result){
				console.log(result);
				firstResult = result;
				console.log("jlabvdd:::"+firstResult);
				console.log("inside");
				var i=0;
				if(firstResult.messageList.length==0){




					Message.findOne({"from":toScreenName, "to":screenName}, function(err,result){
								if(err){
									res.status(400).json({"status":400, "result":err});						
								}else{
									if(result){
										secondResult = result;
										var j=0;

										if(secondResult.messageList.length==0){
											res.status(200).json({"status":200, "result":messageJson});
										}
										else{
										for(j=0;j<secondResult.messageList.length;j++){
											(function(j){
												
												var tempJsonn = {
													"side": "left",
													"message":secondResult.messageList[j]
												}
												messageJson.push(tempJsonn);

												//secondResult.messageList.side = "left";

												if(j==secondResult.messageList.length-1){
													//messageJson.concat(secondResult.messageList);

													messageJson.sort(function(a,b){
														return a.message.timeStamp - b.message.timeStamp;
													});

													Message.findOneAndUpdate({"from":screenName, "to":toScreenName}, {$set:{"status":"read"}}, function(err,result){
														if(err){
															res.status(400).json({"status":400, "result":err});
														}else{
															res.status(200).json({"status":200, "result":messageJson});		
														}
													});



													
												}
											})(j);
										}
									}
									}
								}
							});



				}
				else{
				for(i=0;i<firstResult.messageList.length;i++){
					(function(i){
						console.log("messagelist right:"+firstResult.messageList[i]);
						var tempJson = {
							"side": "right",
							"message":firstResult.messageList[i]
						}
						console.log(tempJson);
						messageJson.push(tempJson);
						/*
						firstResult.messageList[i].side = "right";
						console.log("messagelist right:"+firstResult.messageList[i]);
						var temp = firstResult.messagelist;
						console.log("messagelist right:"+temp);*/
						if(i==firstResult.messageList.length-1){
							//messageJson.concat(firstResult.messageList);
							console.log(";akdlnfdaigfahdhauogd"+messageJson);
							Message.findOne({"from":toScreenName, "to":screenName}, function(err,result){
								if(err){
									res.status(400).json({"status":400, "result":err});						
								}else{
									if(result){
										secondResult = result;
										var j=0;

										if(secondResult.messageList.length==0){
											messageJson.sort(function(a,b){
														return a.message.timeStamp - b.message.timeStamp;
													});

											res.status(200).json({"status":200, "result":messageJson});		
										}
										else{

										for(j=0;j<secondResult.messageList.length;j++){
											(function(j){
												
												var tempJsonn = {
													"side": "left",
													"message":secondResult.messageList[j]
												}
												messageJson.push(tempJsonn);

												//secondResult.messageList.side = "left";

												if(j==secondResult.messageList.length-1){
													//messageJson.concat(secondResult.messageList);

													messageJson.sort(function(a,b){
														return a.message.timeStamp - b.message.timeStamp;
													});

													Message.findOneAndUpdate({"from":screenName, "to":toScreenName}, {$set:{"status":"read"}}, function(err,result){
														if(err){
															res.status(400).json({"status":400, "result":err});
														}else{
															res.status(200).json({"status":200, "result":messageJson});		
														}
													});



													
												}
											})(j);
										}
									}
									}
								}
							});

						}

					})(i);
				}
			}

				
			}else{
				res.status(200).json({"status":200, "result":[]});
			}
		}
	});
}


exports.getInbox = function(req,res){
	//var email = req.body.data.email;
	var screenName = req.body.data.screenName;

	Message.find({"from":screenName},{to:1, status:1}, function(err,result){
		if(err){
			res.status(400).json({"status":400, "result":err});
		}else{
			res.status(200).json({"status":200, "result":result});
		}
	});
}

exports.deleteMessage = function(req,res){
	//var screenName = req.body.data.screenName;
	//var toScreenName = req.body.data.toScreenName;
	var id = req.body.data.id;

	Message.findOneAndUpdate({}, {$pull:{messageList:{_id:id}}}, function(err,result){
		if(err){
			res.status(400).json({"status":400, "result":err});			
		}
		else{
			res.status(200).json({"status":200, "result":"Deleted!"});
		}
	});
}