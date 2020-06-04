package artshop.Entities;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "post")
public class Post implements Serializable {

    private String postId;
    private String userId;
    private String imgLocation;
    private String postTitle;
    private Long likesCount;
    private String postText;
    private int postStatus;
    private Long createdOn;


    public Post() {
    }


    public Post(String userId, String postTitle, String postText, int postStatus, Long createdOn, Long likesCount) {
        this.userId = userId;
        this.postTitle = postTitle;
        this.postText = postText;
        this.postStatus = postStatus;
        this.createdOn = createdOn;
        this.likesCount = likesCount;
    }

    @Id
    @Column(name="post_id")
    public String getPostId() {
        return postId;
    }


    public void setPostId(String postId) {
        this.postId = postId;
    }
    @Column(name="user_id")
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Column(name="img_location")
    public String getImgLocation() {
        return imgLocation;
    }
    public void setImgLocation(String imgLocation) {
        this.imgLocation = imgLocation;
    }

    @Column(name="post_title")
    public String getPostTitle() {
        return postTitle;
    }
    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    @Column(name="post_text")
    public String getPostText() {
        return postText;
    }
    public void setPostText(String postText) {
        this.postText = postText;
    }

    @Column(name="post_status")
    public int getPostStatus() {
        return postStatus;
    }
    public void setPostStatus(int postStatus) {
        this.postStatus = postStatus;
    }

    @Column(name="created_on")
    public Long getCreatedOn() {
        return createdOn;
    }
    public void setCreatedOn(Long createdOn) {
        this.createdOn = createdOn;
    }

    @Column(name="likes_count")
    public Long getLikesCount() {
        return likesCount;
    }
    public void setLikesCount(Long likesCount) {
        this.likesCount = likesCount;
    }

    @Override
    public String toString() {
        return "Post{" +
                "postId='" + postId + '\'' +
                ", userId='" + userId + '\'' +
                ", imgLocation='" + imgLocation + '\'' +
                ", postTitle='" + postTitle + '\'' +
                ", likesCount=" + likesCount +
                ", postText='" + postText + '\'' +
                ", postStatus=" + postStatus +
                ", createdOn=" + createdOn +
                '}';
    }
}
