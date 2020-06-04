package artshop.Controller;

import artshop.dto.MessagePageDomain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class CommonController {

    private static final Logger LOG = LoggerFactory.getLogger(AuthController.class);

    @GetMapping("/message")
    public static ModelAndView getMessageView(String message, String redirectUrl, Integer errorCode, String buttonString) {
        LOG.info("Get Message view message=" + message + " redirectURl=" + redirectUrl + " errorCode=" + errorCode + " buttonString=" + buttonString);
        ModelAndView modelAndView = new ModelAndView();
        if (message == null)
            message = "Oops! Something went wrong. we are looking into it.";
        if (redirectUrl == null)
            redirectUrl = "/";
        if (buttonString == null)
            buttonString = "Okay !";
        if (errorCode == null)
            errorCode = 0;
        MessagePageDomain messagePageDomain = new MessagePageDomain(message, redirectUrl, errorCode, buttonString);

        modelAndView.addObject("messagePageDomain", messagePageDomain);
        modelAndView.setViewName("message");
        return modelAndView;
    }

    /**
     * Redirect to the proper url based on role
     *
     * @return
     */
    @GetMapping("/completereg")
    public ModelAndView completeUserRegisteration() {
        LOG.info("Redirecting to completereg page");

        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        List<GrantedAuthority> grantedAuthorities = (List<GrantedAuthority>) auth.getAuthorities();
        if (grantedAuthorities.get(0).getAuthority().equals("ROLE_ARTIST")) {
            LOG.info("Artist  redirecting to artist reg complete");
            modelAndView = new ModelAndView("redirect:/artist/completereg");
        } else if (grantedAuthorities.get(0).getAuthority().equals("ROLE_USER")) {
            LOG.info("Customer  redirecting to customer reg complete");
            modelAndView = new ModelAndView("redirect:/customer/completereg");
        }
        return modelAndView;
    }


    /**
     * Get the edit profile page. includes the password field
     *
     * @return
     */
    @GetMapping("/user/edit-profile")
    @PreAuthorize("hasRole('ROLE_ARTIST') or hasRole('ROLE_USER')")
    public String getEditProfile() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        LOG.info("change password auth = " + auth.getPrincipal() + auth.getAuthorities() + auth.getCredentials() + auth
                .getDetails());
        //TODO: return the page
        return auth.getName();
    }


    /**
     * Post the new profile data to be updated
     *
     * @param model
     * @return
     */
    @PostMapping(value = "/user/edit-profile")
    @PreAuthorize("hasRole('ROLE_ARTIST') or hasRole('ROLE_USER')")
    public String editProfile(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        LOG.info("change password auth = " + auth.getPrincipal() + auth.getAuthorities() + auth.getCredentials() + auth
                .getDetails());
        // TODO : add the data to the db
        return auth.getName();
    }
}
