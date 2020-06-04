package artshop.ServiceImpl;

import artshop.Controller.AuthController;
import artshop.Entities.Role;
import artshop.Entities.User;
import artshop.Entities.VerificationCode;
import artshop.Repositories.RoleRepository;
import artshop.Repositories.UserRepository;
import artshop.Repositories.VerificationCodeRepo;
import artshop.Services.UserService;
import artshop.Services.VerificationCodeService;
import artshop.dto.UserRegDomain;
import artshop.exception.CustomException;
import artshop.utils.Constants;
import artshop.utils.EmailClient;
import artshop.utils.Helper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.HashSet;
import java.util.UUID;


@Service("userService")
public class UserServiceImpl implements UserService {

    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    @Value("${admin.email}")
    private String adminEmail;
    @Value("${domain.name}")
    private String domainName;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private VerificationCodeService verificationCodeService;
    @Autowired
    Helper helper;
    @Autowired
    private UserService userService;
    @Autowired
    private EmailClient emailClient;


    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    @Override
    public User findUserByPhone(String phone) {
        return userRepository.findByPhone(phone);
    }

    @Override
    public User findUserByid(String user_id) {
        return userRepository.findOne(user_id);
    }


    @Override
    public User registerUser(UserRegDomain userRegDomain, BindingResult bindingResult) throws CustomException {

        User user = userService.findUserByEmail(userRegDomain.getEmail());
        if (user != null) {
            bindingResult.rejectValue("email", "error.user", "There is already a user registered with the email provided");
            LOG.error("Email in use");
        }
        if (userService.findUserByPhone(userRegDomain.getPhone()) != null) {
            bindingResult.rejectValue("phone", "error.user", "This phone number is already in use.");
            LOG.error("Phone number in use");
        }

        try {
            if (Integer.parseInt(userRegDomain.getAge()) < 15) {
                LOG.error("age less than 15");
                bindingResult.rejectValue("age", "error.user", "You should be more than 15 years of age");
            }
        } catch (Exception e) {
            LOG.error("age empty or wrong" + e);
            bindingResult.rejectValue("age", "error.user", "Please provide a valid age");
        }
        if (userRegDomain.getPassword().length() < 5) {
            LOG.error("Small password");
            bindingResult.rejectValue("password", "error.user", "Password length lesser than 5");
        }

        if (bindingResult.hasErrors())
            return null;

        user = saveUser(new User(userRegDomain), "ROLE_USER");
        if (user == null) {
            throw new CustomException("User save failed");
        }

        //create verification code
        try {
            String verificationCode = verificationCodeService.createCode(user.getUserId(), Constants.VerificationCodeMode.EMAIL);
            LOG.info("Verification code {} created for {}", verificationCode, user.getEmail());
            emailClient.sendEmail(user.getEmail(), "Welcome to Artshop", "Hi " + user.getName() +
                    ",\nPlease verify your email id by clicking the link. http://" + domainName + "/verify/email/" + verificationCode);

            verificationCodeService.createCode(user.getUserId(), Constants.VerificationCodeMode.PHONE);
            // TODO: implement send message to phone
        } catch (CustomException e) {
            LOG.info("Verification code not created sending error mail to admin");
            emailClient.sendEmail(adminEmail, "Artshop Error", "Hi,\n" +
                    "Error while creating verification code for " + user.getEmail() + " on " +
                    new Timestamp(System.currentTimeMillis()) + "\n Stack trace: \n" + e);

        }
        return user;
    }


    @Override
    public User saveUser(User user, String role) {
        Role userRole = roleRepository.findByRole(role);
        user.setRoles(new HashSet<>(Arrays.asList(userRole)));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return saveUser(user);
    }
    @Override
    public User saveUser(User user) {

        //only for first save this will be null
        if (user.getUserId() == null) {
            User Old;
            do {
                user.setUserId(String.valueOf(UUID.randomUUID()));
                Old = userRepository.findOne(user.getUserId());
                //Old will be null when the UUID is not in use
            } while (Old != null);
        }
        LOG.debug("saving user: {}", user);
        return userRepository.save(user);
    }


    @Override
    public void emailVerified(String email) throws Exception {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new Exception("Artist not found");
        }
        user.setEmailVerified(true);
        userRepository.save(user);
    }

    @Override
    public void phoneVerified(String email) throws Exception {
            User user = userRepository.findByEmail(email);
            if (user == null) {
                throw new Exception("Artist not found");
            }
            user.setPhoneVerified(true);
            userRepository.save(user);
    }

}
