package com.gxd.mapper.simple;

import com.gxd.model.simple.News;
import com.gxd.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * @Author:gxd
 * @Description:新闻mapper接口
 * @Date: 13:49 2017/12/27
 */
@Mapper
public interface NewsMapper extends BaseMapper<String, News> {

    List<News> findNewsByKeywords(@Param("keywords") String keywords);

    List<News> findNewsByPage(@Param("keywords") String keywords);

    List<News> findNewsByTitle(@Param("title") String title);
}
