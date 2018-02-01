package com.gxd.es.web;

import com.gxd.es.service.AddressNavByEs;
import com.gxd.es.model.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashSet;

/**
 * @Author:gxd
 * @Description:
 * @Date: 14:50 2018/1/31
 * @Modified By:
 */
@Controller
@RequestMapping("/es")
public class ESController {

    @Autowired
    private AddressNavByEs addressNavByEs;

    @RequestMapping(value = "/getCommunityInfo")
    @ResponseBody
    public Result getCommunityInfo(String name){
        Result result = new Result();
        HashSet<String> search = addressNavByEs.search(name);
        result.setPage(search.size());
        result.setState(Result.SUCCESS);
        result.setSuccess(Boolean.TRUE);
        result.setMessage("success");
        result.addAttribute("data",search);
        return result;
    }
}
