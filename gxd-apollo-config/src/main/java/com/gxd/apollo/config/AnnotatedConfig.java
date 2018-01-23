package com.gxd.apollo.config;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import com.gxd.apollo.bean.AnnotatedBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/**
 * @Author:gxd
 * @Description:
 * @Date: 17:12 2018/1/18
 * @Modified By:
 */
@Configuration
@EnableApolloConfig
public class AnnotatedConfig {
    @Bean
    public AnnotatedBean annotatedBean() {
        return new AnnotatedBean();
    }
}
