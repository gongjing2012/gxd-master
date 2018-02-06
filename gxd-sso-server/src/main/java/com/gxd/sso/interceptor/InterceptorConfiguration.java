//package com.gxd.sso.interceptor;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
//
//@EnableWebMvc
//@Configuration
//public class InterceptorConfiguration extends WebMvcConfigurerAdapter {
//
//	@Bean
//    SSOInterceptor localInterceptor() {
//        return new SSOInterceptor();
//    }
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        // 注册拦截器
//        InterceptorRegistration ir = registry.addInterceptor(localInterceptor());
//        // 配置拦截的路径
//        ir.addPathPatterns("/**");
//        // 配置不拦截的路径
//        //ir.excludePathPatterns("/**.html");
//        super.addInterceptors(registry);
//        // 还可以在这里注册其它的拦截器
//        //registry.addInterceptor(new OtherInterceptor()).addPathPatterns("/**");
//    }
//}
