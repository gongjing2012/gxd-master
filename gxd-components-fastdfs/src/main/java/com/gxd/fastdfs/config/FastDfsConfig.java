package com.gxd.fastdfs.config;

import com.github.tobato.fastdfs.FdfsClientConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.context.annotation.Import;
import org.springframework.jmx.support.RegistrationPolicy;

/**
 * @Author:gxd
 * @Description:
 * @Date: 19:16 2018/1/13
 * @Modified By:
 */
@ComponentScan(value={"com.github.tobato.fastdfs.service"})
@Configuration
@Import(FdfsClientConfig.class)
// 解决jmx重复注册bean的问题
@EnableMBeanExport(registration = RegistrationPolicy.IGNORE_EXISTING)
public class FastDfsConfig {
    ///**
    // * 文件上传配置
    // *
    // * @return
    // */
    //@Bean
    //public MultipartConfigElement multipartConfigElement() {
    //    MultipartConfigFactory factory = new MultipartConfigFactory();
    //    //  单个数据大小
    //    factory.setMaxFileSize("10240KB"); // KB,MB
    //    /// 总上传数据大小
    //    factory.setMaxRequestSize("102400KB");
    //    return factory.createMultipartConfig();
    //}
}
