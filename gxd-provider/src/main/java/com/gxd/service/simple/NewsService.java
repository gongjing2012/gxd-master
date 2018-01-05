package com.gxd.service.simple;

import com.github.pagehelper.PageInfo;
import com.gxd.model.simple.News;

import java.util.List;

/**
 * @Author:gxd
 * @Description: 新闻接口类
 * @Date: 13:54 2017/12/27
 */
public interface NewsService {

    public boolean addNews(News news);

    public boolean editNews(News news);

    public News findNewsById(String newsId);

    public List<News> findNewsByKeywords(String keywords);

    public PageInfo<News> findNewsByPage(Integer pageNum, String keywords);

    public News findNewsByTitle(String title);
}