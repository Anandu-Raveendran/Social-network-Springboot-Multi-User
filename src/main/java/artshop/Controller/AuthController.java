package artshop.Controller;

import artshop.Entities.User;
import artshop.Services.UserService;
import artshop.Services.VerificationCodeService;
import artshop.dto.ChangePasswordDomain;
import artshop.dto.UserRegDomain;
import artshop.exception.CustomException;
import artshop.utils.Constants;
import artshop.utils.EmailClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;


/**
 * Created by Anandu on 9/11/17
 */

@Controller
public class AuthController {

    private static final Logger LOG = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private EmailClient emailClient;

    @Autowired
    private VerificationCodeService verificationCodeService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * get the registration page
     *
     * @return
     */
    @GetMapping(value = "/registration")
    public ModelAndView registrationArtist() {
        LOG.info("Get registraion called");
        ModelAndView modelAndView = new ModelAndView();
        UserRegDomain userRegDomain = new UserRegDomain();
        modelAndView.addObject("userRegDomain", userRegDomain);
        modelAndView.setViewName("registration_user");
        return modelAndView;
    }


    /**
     * post the registration  data
     */
    @PostMapping(value = "/registration")
    @Transactional
    public ModelAndView createNewUser(
            @Valid UserRegDomain userRegDomain, BindingResult bindingResult, HttpServletRequest request) {
        LOG.info("Post request for create user: user = {},bindingResult= {}, request = {}", userRegDomain, bindingResult,
                request);

        ModelAndView modelAndView = new ModelAndView();
        //        For auto Login
        request.getSession().setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                SecurityContextHolder.getContext());


        try {
            User user = userService.registerUser(userRegDomain, bindingResult);

            if(bindingResult.hasErrors())
            {
                LOG.error("Registration failed bind error");
                modelAndView.setViewName("registration_user");
            } else {
                LOG.info("User registration successful");
                //            Auto login
                try {
                    request.login(user.getEmail(), userRegDomain.getPassword());
                    LOG.info("auto loggged in");
                    modelAndView = new ModelAndView("redirect:/completereg");

                } catch (ServletException e) {
                    e.printStackTrace();
                    LOG.error("auto Login failed email = {}, pass ={}", userRegDomain.getEmail(), userRegDomain.getPassword());
                    modelAndView = new ModelAndView("redirect:/login");
                }
                //return new ModelAndView("redirect:/message?message=Thank you for registering with us&redirectUrl=/home&buttonString=Okay!");
            }
            return modelAndView;
        } catch (Exception | CustomException e) {
            LOG.error("Save user error {}",e);
            return new ModelAndView("redirect:/message?message=We are facing an issue on our end. Our best brains are looking into it. Please try again later&redirectUrl=/home&buttonString=Okay!");
        }

    }


    /**
     * Get the edit profile page. includes the password field
     */
    @GetMapping("/change-password")
    @PreAuthorize("hasRole('ROLE_ARTIST') or hasRole('ROLE_USER')")
    public ModelAndView getChangePassword() {
        LOG.info("get password change page");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        ChangePasswordDomain changePasswordDomain = new ChangePasswordDomain();
        ModelAndView modelAndView = new ModelAndView("change_password");
        modelAndView.addObject("ChangePasswordDomain", changePasswordDomain);
        return modelAndView;
    }


    /**
     * Post the new profile data to be updated
     *
     * @return
     */
    @PostMapping(value = "/change-password")
    @PreAuthorize("hasRole('ROLE_ARTIST') or hasRole('ROLE_USER')")
    public ModelAndView changePassword(ChangePasswordDomain changePasswordDomain) {
        LOG.info("change password post {}", changePasswordDomain);
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            LOG.error("Unauthorised user");
            modelAndView = new ModelAndView("redirect:/message?message=Unauthorised user. Try logging in&redirectUrl=/login&buttonString=Login");
            return modelAndView;
        }

        User user = userService.findUserByEmail(auth.getName());
        if (user == null) {
            LOG.error("User not found");
            modelAndView = new ModelAndView("redirect:/message?message=User not found. Try logging in&redirectUrl=/login&buttonString=Login");
            return modelAndView;
        }
        if (bCryptPasswordEncoder.matches(changePasswordDomain.getOldPassword(), user.getPassword())) {


            if (changePasswordDomain.getNewPassword().length() < 5) {
                modelAndView = new ModelAndView("redirect:/message?message=Password length should contain at least 5 characters&redirectUrl=/change-password&buttonString=Try again");
                return modelAndView;
            }
            user.setPassword(bCryptPasswordEncoder.encode(changePasswordDomain.getNewPassword()));
            userService.saveUser(user);
            LOG.info("Password updated successfully");
            modelAndView = new ModelAndView("redirect:/message?message=Password updated successfully&redirectUrl=/home");
        } else {
            LOG.error("Old password not match");
            modelAndView = new ModelAndView("redirect:/message?message=Old Password mismatch&redirectUrl=/change-password&buttonString=Try again");
        }
        return modelAndView;
    }


    @GetMapping(value = "/verify/email/{code}")
    public String verifyEmail(@PathVariable("code") String code) {

        LOG.info("Verifying email with code {}", code);
        try {
            if (verificationCodeService.verifyCode(code, Constants.VerificationCodeMode.EMAIL)) {
                return "email verified";
            }
        } catch (CustomException e) {
            LOG.error("Verification code expired");
            return "fail";
        } catch (Exception e) {
            LOG.error("Verification code expired ", e);
            return "fail";
        }
        return "fail";
    }


    @PostMapping(value = "/verify/phone")
    public ResponseEntity verifyPhone(String code) {

        LOG.info("Verifying phone with code {}", code);
        try {
            if (verificationCodeService.verifyCode(code, Constants.VerificationCodeMode.PHONE)) {
                return new ResponseEntity("email verified", HttpStatus.OK);
            }
        } catch (CustomException e) {
            LOG.error("Verification code expired");
        } catch (Exception e) {
            LOG.error("Verification code expired ", e);
        }
        return new ResponseEntity("Verification fail", HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "/getrole")
    public ResponseEntity getrole() {

        LOG.info("gettting user role");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        List<GrantedAuthority> grantedAuthorities = (List<GrantedAuthority>) auth.getAuthorities();
        if (grantedAuthorities.get(0).getAuthority().equals("ROLE_ARTIST")) {
            return new ResponseEntity("ROLE_ARTIST", HttpStatus.OK);
        } else if (grantedAuthorities.get(0).getAuthority().equals("ROLE_USER")) {
            return new ResponseEntity("ROLE_USER", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("UNAUTHORIZED", HttpStatus.OK);
        }
    }

}
