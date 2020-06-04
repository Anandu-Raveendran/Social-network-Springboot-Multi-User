package artshop.Entities;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "COMMENTS")
public class Comments implements Serializable {

    private String commentId;
    private String replyToCommentId;
    private String userId;
    private String postId;
    private String comment;
    private Long createdOn;

    public Comments() {
    }

    public Comments(String commentId, String replyToCommentId, String userId, String postId, String comment, Long createdOn) {
        this.commentId = commentId;
        this.replyToCommentId = replyToCommentId;
        this.userId = userId;
        this.postId = postId;
        this.comment = comment;
        this.createdOn = createdOn;
    }

    @Id
    @Column(name="comment_id")
    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
    @Column(name="created_on")
    public Long getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Long createdOn) {
        this.createdOn = createdOn;
    }
    @Column(name="reply_to_comment_id")
    public String getReplyToCommentId() {
        return replyToCommentId;
    }

    public void setReplyToCommentId(String replyToCommentId) {
        this.replyToCommentId = replyToCommentId;
    }

    @Override
    public String toString() {
        return "Comments{" +
                "commentId='" + commentId + '\'' +
                ", replyToCommentId='" + replyToCommentId + '\'' +
                ", userId='" + userId + '\'' +
                ", postId='" + postId + '\'' +
                ", comment='" + comment + '\'' +
                ", createdOn=" + createdOn +
                '}';
    }
}
