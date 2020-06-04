package artshop.Entities;

import artshop.dto.UserRegDomain;
import artshop.utils.Constants;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.Transient;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;


@Entity
@Table(name = "user")
public class User {

    @Id
    @Column(name = "user_id")
    private String userId;

    private String email;
    private String phone;

    @JsonIgnore
    @Transient
    private String password;

    private String name;
    private String age;
    @Column(name = "image_location")
    private String imageLocation;
    private String region;
    @Column(name = "date_of_joining")
    private Long dateOfJoining;
    @Column(name = "last_active_on")
    private Long lastActiveOn;
    @Column(name = "email_verified")
    private boolean emailVerified;
    @Column(name = "phone_verified")
    private boolean phoneVerified;
    @JsonIgnore
    private int status;

    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "userRole", joinColumns = @JoinColumn(name = "userId"),
            inverseJoinColumns = @JoinColumn(name = "roleId"))
    private Set<Role> roles;

    public User() {

    }

    public User(String email, String phone, String password, String name, String age, String imageLocation, Long dateOfJoining, Long lastActiveOn, boolean emailVerified, boolean phoneVerified, int status, Set<Role> roles) {
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.name = name;
        this.age = age;
        this.imageLocation = imageLocation;
        this.dateOfJoining = dateOfJoining;
        this.lastActiveOn = lastActiveOn;
        this.emailVerified = emailVerified;
        this.phoneVerified = phoneVerified;
        this.status = status;
        this.roles = roles;
    }

    public User(User user) {
        this.userId = user.getUserId();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.name = user.getName();
        this.age = user.getAge();
        this.phone = user.getPhone();
        this.imageLocation = user.getImageLocation();
        this.dateOfJoining = user.getDateOfJoining();
        this.lastActiveOn = user.getLastActiveOn();
        this.emailVerified = user.isEmailVerified();
        this.phoneVerified = user.isPhoneVerified();
        this.status = user.getStatus();
        this.roles = user.getRoles();
    }


    public User(UserRegDomain user) {
        this.userId = String.valueOf(UUID.randomUUID());
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.name = user.getName();
        this.age = user.getAge();
        this.phone = user.getPhone();
        this.dateOfJoining = System.currentTimeMillis();
        this.lastActiveOn = System.currentTimeMillis();
        this.emailVerified = false;
        this.phoneVerified = false;
        this.status = Constants.UserStatus.REGISTERED.getValue();
        this.roles = roles;
    }


    @JsonIgnore
    public Set<Role> getRoles() {
        return roles;
    }


    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageLocation() {
        return imageLocation;
    }

    public void setImageLocation(String imageLocation) {
        this.imageLocation = imageLocation;
    }

    public Long getDateOfJoining() {
        return dateOfJoining;
    }

    public void setDateOfJoining(Long dateOfJoining) {
        this.dateOfJoining = dateOfJoining;
    }

    public Long getLastActiveOn() {
        return lastActiveOn;
    }

    public void setLastActiveOn(Long lastActiveOn) {
        this.lastActiveOn = lastActiveOn;
    }

    public boolean isEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public boolean isPhoneVerified() {
        return phoneVerified;
    }

    public void setPhoneVerified(boolean phoneVerified) {
        this.phoneVerified = phoneVerified;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", age='" + age + '\'' +
                ", imageLocation='" + imageLocation + '\'' +
                ", region='" + region + '\'' +
                ", dateOfJoining=" + dateOfJoining +
                ", last_active_on=" + lastActiveOn +
                ", emailVerified=" + emailVerified +
                ", phoneVerified=" + phoneVerified +
                ", status=" + status +
                ", roles=" + roles +
                '}';
    }
}
