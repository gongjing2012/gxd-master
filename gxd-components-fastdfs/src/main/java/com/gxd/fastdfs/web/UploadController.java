package com.gxd.fastdfs.web;

import com.github.tobato.fastdfs.domain.FileInfo;
import com.gxd.fastdfs.client.FastDFSClient;
import com.xiaoleilu.hutool.date.DateUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author:gxd
 * @Description:
 * @Date: 11:14 2018/1/13
 * @Modified By:
 */
@Controller
@RequestMapping("/file")
public class UploadController {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private FastDFSClient fastDFSClient;


    @RequestMapping(value = "/toupload", method = RequestMethod.GET)
    private String toUpload(){
        return "view/upload";
    }
    // 上传文件
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> fileUp(MultipartHttpServletRequest multipartFile, HttpServletRequest request){
        Map<String,String> result = new HashMap<String,String>();
        String param = request.getParameter("param");//参数名称
        if(StringUtils.isEmpty(param)){
            result.put("result","false");
            result.put("msg","请添加参数");
        }
        InputStream is = null;

        String fileName = multipartFile.getFile(param).getOriginalFilename();
        try {
            long size = multipartFile.getFile(param).getSize();
            is = multipartFile.getFile(param).getInputStream();
            String path = fastDFSClient.upload(is,size,fileName);
            result.put("result","true");
            //图片地址
            result.put("srckey",path);
        }catch (IOException e){
            result.put("result","false");
            logger.error("file:"+fileName,e.fillInStackTrace());
        }finally {
            if (is !=null){
                try {
                    is.close();
                }catch (IOException io){
                    logger.error(io.getMessage());
                }
            }
        }
        return result;
    }

    @RequestMapping(value = "/delete")
    @ResponseBody
    public String deleteFile(){
        String path = "group1/M00/00/00/wKgyJVpZ1kKAIBoWAABRSUbvB8Y9.2.png";
        fastDFSClient.deleteFile(path);
        return "success";
    }

    @RequestMapping(value = "/download")
    @ResponseBody
    public String download(){
        try {
            String path = "M00/00/00/wKgyJVpbRu-EH67OAAAAAKmTJqU771.txt";
            InputStream inputStream = fastDFSClient.downloadFile("group1", path);
            System.out.println(inputStream);
            File destFile = new File("E:/tmp/37.txt");
            FileUtils.copyInputStreamToFile(inputStream, destFile);
        } catch (Exception e) {
            e.printStackTrace();
            return "false";
        }
        return "success";
    }

    @RequestMapping(value = "/modifyFile")
    @ResponseBody
    public String modifyFile(){
        try {
            String path = "M00/00/00/wKgyJVpbRu-EH67OAAAAAKmTJqU771.txt";
            File f = new File("E:"  + File.separator + "2.png");
            InputStream fis = new FileInputStream(f);
            fastDFSClient.modifyFile("group1", path,fis,f.length(),0);
        } catch (Exception e) {
            e.printStackTrace();
            return "false";
        }
        return "success";
    }

    @RequestMapping(value = "/queryFileInfo")
    @ResponseBody
    public String queryFileInfo(){
        String path = "M00/00/00/wKgyJVpbRu-EH67OAAAAAKmTJqU771.txt";

        FileInfo fileInfo = fastDFSClient.queryFileInfo("group1",path);

        String res = "创建时间："+ DateUtil.date(fileInfo.getCreateTime())+","
                +"IP地址："+fileInfo.getSourceIpAddr()+","
                +"文件大小："+fileInfo.getFileSize();
        return res;
    }
}
