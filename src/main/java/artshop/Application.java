package artshop;

import org.apache.coyote.http11.AbstractHttp11Protocol;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@SpringBootApplication
@EnableSwagger2
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    //Tomcat large file upload connection reset
    //http://www.mkyong.com/spring/spring-file-upload-and-connection-reset-issue/
    @Bean
    public TomcatEmbeddedServletContainerFactory tomcatEmbedded() {

        TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory();

        tomcat.addConnectorCustomizers((TomcatConnectorCustomizer) connector -> {
            if ((connector.getProtocolHandler() instanceof AbstractHttp11Protocol<?>)) {
                //-1 means unlimited
                ((AbstractHttp11Protocol<?>) connector.getProtocolHandler()).setMaxSwallowSize(-1);
            }
        });

        return tomcat;
    }
}

// TODO: favicon
// TODO: free subscription
// TODO: like button and big data
// TODO:  list of orders
// TODO: list of art orders
// TODO: pricing and payment
// TODO: images to put in carousal welcome to artshop with an artist paintng,boy gifting, house decoration, show case ur talent
// TODO: cron jobs checking the expired accounts
// TODO: on login check if email and phone is verified else redirect to enter verification code page
// TODO: verify phone number
// TODO: reduce the data art piece sends to gallery
// TODO: implement the buy from artist button
// TODO: remove commented code and verify the html css javascript code is proper
// TODO: when user clicks buy from artist a mail goes to artist saying this user was interested update db
// TODO: when user uploads update the customer order db table
// TODO: send notification to artist on buy