//package com.gxd.sso.web;
//
//import com.gxd.redis.utils.RedisUtil;
//import com.gxd.sso.model.RedisLoginDTO;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import javax.servlet.http.HttpServletRequest;
//
//@Controller
//public class HelloWorldController {
//
//	@Autowired
//	private RedisUtil redisUtils;
//
//	@RequestMapping("/test")
//	public String test(HttpServletRequest request){
//		RedisLoginDTO redisLoginDTO = null;
//		try {
//			redisLoginDTO = (RedisLoginDTO) redisUtils.getObject("hellokoding");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		if(redisLoginDTO != null){
//			request.setAttribute("hello2", redisLoginDTO.getUid());
//		}else{
//			request.setAttribute("hello2", "hello2");
//
//		}
//		return "login";
//	}
//
//}
