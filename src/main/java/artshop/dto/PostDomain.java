package artshop.dto;

import artshop.Entities.Comments;

import java.io.Serializable;
import java.util.List;

public class PostDomain implements Serializable {

    private String postId;
    private String userName;
    private String userImgLocation;
    private String imgLocation;
    private String postTitle;
    private Long likesCount;
    private String postText;
    private Long createdOn;
    private String likedBy;
    List<Comments> topComments;

    public PostDomain() {
    }

    public PostDomain(String postId, String userName, String userImgLocation, String imgLocation, String postTitle, Long likesCount, String postText, Long createdOn, String likedBy, List<Comments> topComments) {
        this.postId = postId;
        this.userName = userName;
        this.userImgLocation = userImgLocation;
        this.imgLocation = imgLocation;
        this.postTitle = postTitle;
        this.likesCount = likesCount;
        this.postText = postText;
        this.createdOn = createdOn;
        this.likedBy = likedBy;
        this.topComments = topComments;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserImgLocation() {
        return userImgLocation;
    }

    public void setUserImgLocation(String userImgLocation) {
        this.userImgLocation = userImgLocation;
    }

    public String getImgLocation() {
        return imgLocation;
    }

    public void setImgLocation(String imgLocation) {
        this.imgLocation = imgLocation;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public Long getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(Long likesCount) {
        this.likesCount = likesCount;
    }

    public String getPostText() {
        return postText;
    }

    public void setPostText(String postText) {
        this.postText = postText;
    }

    public Long getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Long createdOn) {
        this.createdOn = createdOn;
    }

    public String getLikedBy() {
        return likedBy;
    }

    public void setLikedBy(String likedBy) {
        this.likedBy = likedBy;
    }

    public List<Comments> getTopComments() {
        return topComments;
    }

    public void setTopComments(List<Comments> topComments) {
        this.topComments = topComments;
    }

    @Override
    public String toString() {
        return "PostDomain{" +
                "postId='" + postId + '\'' +
                ", userName='" + userName + '\'' +
                ", userImgLocation='" + userImgLocation + '\'' +
                ", imgLocation='" + imgLocation + '\'' +
                ", postTitle='" + postTitle + '\'' +
                ", likesCount=" + likesCount +
                ", postText='" + postText + '\'' +
                ", createdOn=" + createdOn +
                ", likedBy='" + likedBy + '\'' +
                ", topComments=" + topComments +
                '}';
    }
}