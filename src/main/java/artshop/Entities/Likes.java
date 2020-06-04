package artshop.Entities;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "LIKES")
public class Likes implements Serializable {

    private String likeId;
    private String userId;
    private String postId;

    public Likes() {
    }

    public Likes(String userId, String postId) {
        this.userId = userId;
        this.postId = postId;
    }

    @Id
    @Column(name="like_id")
    public String getLikeId() {
        return likeId;
    }

    public void setLikeId(String likeId) {
        this.likeId = likeId;
    }
    @Column(name="user_id")
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    @Column(name="post_id")
    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    @Override
    public String toString() {
        return "Likes{" +
                "likeId='" + likeId + '\'' +
                ", userId='" + userId + '\'' +
                ", postId='" + postId + '\'' +
                '}';
    }
}
