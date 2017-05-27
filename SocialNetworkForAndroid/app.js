/**
 * Copyright 2017, Google, Inc.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

'use strict';

// [START app]
const express = require('express');

const app = express();

var http = require('http');
var path = require('path');
var mongoose = require('mongoose');
var mongoSessionConnectURL = "mongodb://pavanshah77:pavanshah77@ds127101.mlab.com:27101/socialnetworkandroiddb";
var mongo = require("./routes/mongo");
var user = require("./routes/login");
var profile = require("./routes/profile");
var friendRequest = require("./routes/friendRequest");
var posts = require("./routes/posts");
var message = require("./routes/messages");

//serverurl = "https://cmpe277nodejsserver.appspot.com/"

var bodyParser = require('body-parser');
var methodOverride = require('method-override');
//serverurl = "https://cmpe277nodejsserver.appspot.com/"


app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: false }));
app.use(methodOverride());


//app.get('/', user.login);

app.post('/login', user.login);
app.post('/signUp', user.signUp);
app.post('/verifyCode', user.verifyCode);
app.post('/getProfile', profile.getProfile);
app.post('/updateProfile', profile.updateProfile);
app.post('/setVisibilityLevel', profile.setVisibilityLevel);
app.post('/setNotificationOption', profile.setNotificationOption);
app.post('/addFriendByEmail', friendRequest.addFriendByEmail);
app.post('/fetchInvitations', friendRequest.fetchInvitations);
app.post('/fetchSentRequests', friendRequest.fetchSentRequests);
app.post('/acceptInvitation', friendRequest.acceptInvitation);
app.post('/createPost', posts.createPost);
app.post('/fetchTimeline', posts.fetchTimeline);
app.post('/browseProfile', profile.browseProfile);
app.post('/fetchPost', posts.fetchPost);
app.post('/followProfile', friendRequest.followProfile);
app.post('/fetchFollowingProfile', friendRequest.fetchFollowingProfile);
app.post('/sendMessage', message.sendMessage);
app.post('/getIndividualMessage', message.getIndividualMessage);
app.post('/getInbox', message.getInbox);
app.post('/rejectFriendRequest', friendRequest.rejectFriendRequest);
app.post('/browseProfileWithoutFollowingProfiles', profile.browseProfileWithoutFollowingProfiles);
app.post('/deleteMessage', message.deleteMessage);
app.post('updateProfilePic', profile.updateProfilePic);


// Start the server
const PORT = process.env.PORT || 8080;
mongoose.connect(mongoSessionConnectURL, function(){

		console.log('Connected to mongo at: ' + mongoSessionConnectURL);	
		app.listen(PORT, () => {
  		console.log(`App listening on port ${PORT}`);
  		console.log('Press Ctrl+C to quit.');
	});

});

// [END app]
