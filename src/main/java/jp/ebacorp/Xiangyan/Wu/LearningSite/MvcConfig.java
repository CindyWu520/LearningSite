package jp.ebacorp.Xiangyan.Wu.LearningSite;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/home").setViewName("home"); //11,12行はないとmappingエラーが発生しまう
        registry.addViewController("/").setViewName("home");
        registry.addViewController("/hello").setViewName("hello");//ないとhelloページを表示する際にmappingエラーが発生
        registry.addViewController("/login").setViewName("login");//ないとloginページを表示する際にmappingエラーが発生
        registry.addViewController("/signup").setViewName("signup");//ないとloginページを表示する際にmappingエラーが発生
    }
}