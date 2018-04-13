package com.cmpe277labs.amipa.facebookclone;

/**
 * Created by Pavan Shah on 5/20/2017.
 */

public class PostInfo {

    String relationship;
    String postText;
    String[] postImages;

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public String getPostText() {
        return postText;
    }

    public void setPostText(String postText) {
        this.postText = postText;
    }

    public String[] getPostImages() {
        return postImages;
    }

    public void setPostImages(String[] postImages) {
        this.postImages = postImages;
    }

}
