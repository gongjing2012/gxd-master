package com.gxd.common;

import com.xiaoleilu.hutool.date.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;
import java.util.Calendar;

/**
 * 将version版本号写入application中，给css,js引用时用
 */
@Component
public class ApplicationContext implements ServletContextAware {

    private static final Logger log = LoggerFactory.getLogger(ApplicationContext.class);

    @Override
    public void setServletContext(ServletContext context) {
        String datetime = DateUtil.format(Calendar.getInstance().getTime(), "yyyy-mm-dd");
        String contextPath = context.getContextPath();
        log.info("# version={} , contextPath={}", datetime, contextPath);
        context.setAttribute("version_css", datetime);
        context.setAttribute("version_js", datetime);
        context.setAttribute("ctx", contextPath);
    }

}