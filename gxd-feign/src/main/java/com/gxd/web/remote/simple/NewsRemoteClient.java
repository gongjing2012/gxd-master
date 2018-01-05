package com.gxd.web.remote.simple;

import com.github.pagehelper.PageInfo;
import com.gxd.common.exception.BusinessException;
import com.gxd.model.simple.News;
import com.gxd.web.hystrix.simple.NewsRemoteClientHystrix;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(path = "simple", value = "gxd-provider", fallback = NewsRemoteClientHystrix.class)
public interface NewsRemoteClient {

    @RequestMapping(value = "/addNews", method = RequestMethod.POST)
    public Boolean addNews(@RequestBody News news) throws BusinessException;

    @RequestMapping(value = "/findNewsById/{id}", method = RequestMethod.GET)
    public News findNewsById(@PathVariable("id") String id) throws BusinessException;

    @RequestMapping(value = "/editNews", method = RequestMethod.POST)
    public Boolean editNews(@RequestBody News news) throws BusinessException;

    @RequestMapping(value = "/findNewsByPage", method = RequestMethod.GET)
    public PageInfo<News> findNewsByPage(@RequestParam(value = "keywords", required = false) String keywords, @RequestParam(value = "pageNum", required = false) Integer pageNum) throws BusinessException;

    @RequestMapping(value = "getNews", method = RequestMethod.GET)
    public News getNews() throws BusinessException;
}
