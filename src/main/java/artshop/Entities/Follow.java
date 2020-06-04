package artshop.Entities;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Follow")
public class Follow implements Serializable {

    private String followId;
    private String userId;
    private String followedByUserId;

    public Follow() {
    }

    public Follow(String userId, String followedByUserId) {
        this.userId = userId;
        this.followedByUserId = followedByUserId;
    }

    @Id
    @Column(name="follow_id")
    public String getFollowId() {
        return followId;
    }

    public void setFollowId(String followId) {
        this.followId = followId;
    }
    @Column(name="user_id")
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    @Column(name="followed_by_user_id")
    public String getFollowedByUserId() {
        return followedByUserId;
    }

    public void setFollowedByUserId(String followedByUserId) {
        this.followedByUserId = followedByUserId;
    }

    @Override
    public String toString() {
        return "Follow{" +
                "followId='" + followId + '\'' +
                ", userId='" + userId + '\'' +
                ", followedByUserId='" + followedByUserId + '\'' +
                '}';
    }
}
