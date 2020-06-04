package artshop.Entities;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "VERIFICATION_CODE")
public class VerificationCode implements Serializable {

    private String vCodeId;
    private String userId;
    private String code;
    private int mode;
    private Long createdOn;

    public VerificationCode() {
    }

    public VerificationCode(String userId, String code, int mode, Long createdOn) {
        this.userId = userId;
        this.code = code;
        this.mode = mode;
        this.createdOn = createdOn;
    }

    @Id
    @Column(name="v_code_id")
    public String getvCodeId() {
        return vCodeId;
    }

    public void setvCodeId(String vCodeId) {
        this.vCodeId = vCodeId;
    }
    @Column(name="user_id")
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }
    @Column(name="created_on")
    public Long getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Long createdOn) {
        this.createdOn = createdOn;
    }

    @Override
    public String toString() {
        return "VerificationCode{" +
                "vcode_id='" + vCodeId + '\'' +
                ", user_id='" + userId + '\'' +
                ", code='" + code + '\'' +
                ", mode=" + mode +
                ", created_on=" + createdOn +
                '}';
    }
}
