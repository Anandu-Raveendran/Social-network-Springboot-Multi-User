package artshop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/uploadedimages/**")
                .addResourceLocations("file:/opt/images/");
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }


    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/home").setViewName("home");
        registry.addViewController("/index").setViewName("home");
        registry.addViewController("/").setViewName("home");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/artistbio").setViewName("artist_bio");
        registry.addViewController("/artistlist").setViewName("list_of_artist");
        registry.addViewController("/upload").setViewName("upload");
        registry.addViewController("/about").setViewName("about_us");
        registry.addViewController("/privacy_policy").setViewName("privacy_policy");
        registry.addViewController("/terms_and_condition").setViewName("terms_and_condition");
        registry.addViewController("/gallery").setViewName("gallery2");
        registry.addViewController("/cart").setViewName("cart");
        registry.addViewController("/delivery_address").setViewName("delivery_address");

    }
}