package com.yoj.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.WebContentInterceptor;

//使用WebMvcConfigurer可以来扩展SpringMVC的功能
@Configuration
public class MyMvcConfig implements WebMvcConfigurer {


    /**
    * @Description: 静态资源
    * @Param: [registry] 
    * @return: void 
    * @Author: lmz
    * @Date: 2019/8/11 
    */ 
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //静态文件
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
        //webjar文件
        registry.addResourceHandler("/webjars/**").addResourceLocations("/webjars/");
    }
    /**
    * @Description: 所有的WebMvcConfigurerAdapter组件都会一起起作用
    * @Param: []
    * @return: org.springframework.web.servlet.config.annotation.WebMvcConfigurer
    * @Author: lmz
    * @Date: 2019/8/11
    */
    @Bean
    public WebMvcConfigurer webMvcConfigurer(){
        WebMvcConfigurer configurer = new WebMvcConfigurer() {
            @Override
            public void addViewControllers(ViewControllerRegistry registry) {
                registry.addViewController("/").setViewName("home");
                registry.addViewController("/index.html").setViewName("home");
                registry.addViewController("/register.html").setViewName("register");
                registry.addViewController("/login.html").setViewName("login");
                registry.addViewController("/home.html").setViewName("home");
//                registry.addViewController("/**").setViewName("login");
            }

            @Override
            public void addInterceptors(InterceptorRegistry registry) {
//                super.addInterceptors(registry);
                //静态资源；  *.css , *.js
               // SpringBoot已经做好了静态资源映射,2.0以上版本也拦截了静态资源
                registry.addInterceptor(new WebContentInterceptor())
                        .addPathPatterns("/**")
                        .excludePathPatterns("/index.html", "/main.html", "/", "/assets/**", "/resources/**");
            }
        };

        return configurer;
    }

}
