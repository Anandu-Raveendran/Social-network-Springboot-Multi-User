package artshop.Services;


import artshop.Entities.User;
import artshop.dto.UserRegDomain;
import artshop.exception.CustomException;
import org.springframework.validation.BindingResult;

public interface UserService {
    User findUserByEmail(String email);

    User saveUser(User user, String role);

    User registerUser(UserRegDomain userRegDomain, BindingResult bindingResult) throws CustomException;

    User saveUser(User user);

    void emailVerified(String email) throws Exception;

    void phoneVerified(String email) throws Exception;

    User findUserByPhone(String phone);

    User findUserByid(String user_id);
}
