package com.gxd.web;

import com.gxd.model.RedisLoginDTO;
import com.gxd.redis.config.RedisService;
import com.gxd.redis.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class HelloWorldController {

	@Autowired
	private RedisUtils redisUtils;
	
	@RequestMapping("/test")
	public String test(HttpServletRequest request){
		RedisLoginDTO redisLoginDTO = (RedisLoginDTO) redisUtils.getObject("hellokoding");

		if(redisLoginDTO != null){
			request.setAttribute("hello2", redisLoginDTO.getUid());
		}else{
			request.setAttribute("hello2", "hello2");

		}
		return "test";
	}
	
}
