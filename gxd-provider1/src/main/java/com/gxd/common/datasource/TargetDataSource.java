package com.gxd.common.datasource;

import java.lang.annotation.*;

/** 
 * @Description 在方法上使用，用于指定使用哪个数据源
 * @author gxd
 * @date Mar 17, 2017 9:04:49 AM  
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TargetDataSource {
    
    /** 数据源名称 */
    DataSourceEnum value();
}
