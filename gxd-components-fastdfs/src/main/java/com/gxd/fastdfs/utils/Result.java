package com.gxd.fastdfs.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @Author:gxd
 * @Description:
 * @Date: 10:47 2017/12/29
 * @Modified By:
 */
public class Result implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(Result.class);
    /**
     * 成功状态 *
     */
    public static final Integer SUCCESS = 1;

    /**
     * 失败状态 *
     */
    public static final Integer FAIL = -1;


    private Integer state = Result.SUCCESS;
    private boolean success;
    private String message = "";

    private Integer page = 0;//记录数

    private final Map<String, Object> data = new HashMap<String, Object>();

    public Result() {
    }

    /**
     * @param success
     *            是否成功
     */
    public Result(boolean success) {
        this.success = success;
    }

    /**
     * @param success
     *            是否成功
     * @param state
     *            状态码
     */
    public Result(boolean success, Integer state) {
        this.success = success;
        this.state = state;
    }

    public Result(boolean success, String msg) {
        this.success = success;
        this.message = msg;
    }

    public Result(Integer state, String msg) {
        this.state = state;
        this.message = msg;
    }

    public Result(boolean success, Integer state, String msg) {
        this.success = success;
        this.state = state;
        this.message = msg;
    }


    /**
     * 将属性绑定至request中
     *
     * @param request
     */
    public void rendering(HttpServletRequest request) {
        Set<String> keySet = data.keySet();
        for (String key : keySet) {
            request.setAttribute(key, data.get(key));
        }
    }

    /**
     * 将属性绑定至request中
     */
    public void rendering() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Set<String> keySet = data.keySet();
        for (String key : keySet) {
            request.setAttribute(key, data.get(key));
        }
    }

    /**
     * 将属性绑定至Model中
     *
     * @param model
     */
    public void rendering(Model model) {
        model.addAllAttributes(data);
    }

    /**
     * 将属性绑定至ModelAndView中
     *
     * @param model
     */
    public void rendering(ModelAndView model) {
        model.addAllObjects(data);
    }

    // ------------------------------------------------
    private static final String JSON_CONTENT_TYPE = "text/html; charset=UTF-8";

    /**
     * 输出result中的键值只response中，值需为json格式
     *
     * @param response
     */
    public void renderingByJsonData(HttpServletResponse response) {
        if (response.getContentType() == null) {
            response.setContentType(JSON_CONTENT_TYPE);
        }

        PrintWriter writer = null;
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("state", this.getState());
            map.put("success", this.isSuccess());
            map.put("data", this.data);
            map.put("message", this.message);
            String str = FastJsonUtils.collectToString(map);
            writer = response.getWriter();
            writer.print(str);
            writer.flush();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(String key, Object obj) {
        data.put(key, obj);
    }


    /**
     * 检查key是否存在
     *
     * @param key
     * @return
     */
    public boolean containsKey(String key) {
        return data.containsKey(key);
    }

    /**
     * 检查value是否存在
     *
     * @param value
     * @return
     */
    public boolean containsValue(Object value) {
        return data.containsValue(value);
    }


    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }
}
