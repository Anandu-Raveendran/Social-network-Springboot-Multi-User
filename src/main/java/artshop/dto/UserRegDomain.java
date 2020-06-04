package artshop.dto;

import artshop.Entities.User;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Transient;


/**
 * Created by Anandu on 9/11/17
 */

public class UserRegDomain {

    @Email(message = "*Please provide a valid Email")
    @NotEmpty(message = "*Please provide an email")
    private String email;
    @Length(min = 10, max = 10, message = "*Your phone number must have 10 characters")
    @NotEmpty(message = "*Please provide your phone number")
    private String phone;

    @Length(min = 5, message = "*Your password must have at least 5 characters")
    @NotEmpty(message = "*Please provide your password")
    @Transient
    private String password;

    @NotEmpty(message = "*Please provide your name")
    private String name;
    /* not added checks because check is in controller
        @Length(min = 2, max = 3, message = "Please provide a valid age")
        @NotEmpty(message = "*Please provide your age")
        */
    private String age;
    @NotEmpty(message = "*Please provide the city you belong to")
    private String region;

    public UserRegDomain() {
    }

    public UserRegDomain(String email, String phone, String password, String name, String age, String region, String role, int status) {
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.name = name;
        this.age = age;
        this.region = region;
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

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }
    @Override
    public String toString() {
        return "UserRegDomain{" +
                "email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", age='" + age + '\'' +
                ", region='" + region +
                '}';
    }
}
