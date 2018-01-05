package com.gxd.common.authority.filter;

import com.alibaba.fastjson.JSON;
import com.gxd.common.authority.service.xss.XSSHttpRequestWrapper;
import com.gxd.common.authority.service.xss.XSSSecurityConfig;
import com.gxd.common.authority.service.xss.XSSSecurityConstants;
import com.gxd.common.authority.service.xss.XSSSecurityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

/**
 * @Author:gxd
 * @Description:xss攻击脚本过滤器
 * @Date: 14:42 2017/12/27
 */
@WebFilter(urlPatterns = "/*", filterName = "XSSCheck", initParams = { @WebInitParam(name = "securityconfig", value = "classpath:conf/xss_security_config.xml") })
public class XSSSecurityFilter implements Filter {

    private static final Logger log = LoggerFactory.getLogger(XSSSecurityFilter.class);

    /**
     * 初始化操作
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        XSSSecurityManager.init(filterConfig);
    }

    /**
     * 销毁操作
     */
    @Override
    public void destroy() {
        log.debug("XSSSecurityFilter destroy() begin");
        XSSSecurityManager.destroy();
        log.debug("XSSSecurityFilter destroy() end");
    }

    /**
     * 安全审核 读取配置信息
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 判断是否使用HTTP
        checkRequestResponse(request, response);

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // http信息封装类
        XSSHttpRequestWrapper xssRequest = new XSSHttpRequestWrapper(httpRequest);

        // 对request信息进行封装并进行校验工作，若校验失败（含非法字符），根据配置信息进行日志记录和请求中断处理
        if (xssRequest.validateParameter(httpResponse)) {
            if (XSSSecurityConfig.IS_LOG) {
                String paramStr = "";
                Map<String, String[]> submitParams = httpRequest.getParameterMap();
                Set<String> submitNames = submitParams.keySet();
                String[] submitValues = null;
                for (String submitName : submitNames) {
                    submitValues = submitParams.get(submitName);
                    for (String submitValue : (String[]) submitValues) {
                        paramStr = paramStr + submitValue;
                    }
                }

                log.debug("XSS Security Filter RequestURL:" + httpRequest.getRequestURL().toString());
                log.debug("param:" + paramStr);
                log.debug("XSS Security Filter RequestParameter:{}", JSON.toJSONString(httpRequest.getParameterMap()));
            }
            // 是否中断操作
            if (XSSSecurityConfig.IS_CHAIN) {
                request.setAttribute("err", "您输入的参数有非法字符，请输入正确的参数！");
                request.setAttribute("pageUrl",httpRequest.getRequestURI());
                request.getRequestDispatcher(request.getServletContext().getContextPath() + XSSSecurityConstants.FILTER_ERROR_PAGE).forward(request, response);
                return;
            }

        }
        chain.doFilter(request, response);
    }

    /**
     * 判断Request ,Response 类型
     *
     * @param request
     *            ServletRequest
     * @param response
     *            ServletResponse
     * @throws ServletException
     */
    private void checkRequestResponse(ServletRequest request, ServletResponse response) throws ServletException {
        if (!(request instanceof HttpServletRequest)) {
            throw new ServletException("Can only process HttpServletRequest");
        }
        if (!(response instanceof HttpServletResponse)) {
            throw new ServletException("Can only process HttpServletResponse");
        }
    }
}
