package com.gxd.fastdfs.web;

import com.github.tobato.fastdfs.domain.FileInfo;
import com.gxd.fastdfs.client.FastDFSClient;
import com.gxd.fastdfs.utils.Result;
import com.xiaoleilu.hutool.date.DateUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
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

    /**
     * 访问路径为：http://ip:port/file/fileupload
     * @return
     */
    @RequestMapping(value = "/fileupload", method = RequestMethod.GET)
    public String upload() {
        return "view/fileupload";
    }

    /**
     * 访问路径为：http://ip:port/file/mutifileupload
     * @return
     */
    @RequestMapping(value = "/mutifileupload", method = RequestMethod.GET)
    public String batchUpload() {
        return "view/mutifileupload";
    }

    /**
     * 文件上传具体实现方法（单文件上传）
     *
     * @param file
     * @return
     *
     * @author gj
     * @create 2018年1月17日
     */
    @RequestMapping(value = "/upload1", method = RequestMethod.POST)
    @ResponseBody
    public String upload(@RequestParam("file") MultipartFile file) {
        logger.debug("开始上传。。。");
        long start = System.currentTimeMillis();
        logger.debug("start:"+start);
        String path = "";
        if (!file.isEmpty()) {
            try {
                // 这里只是简单例子，文件直接输出到项目路径下。
                // 实际项目中，文件需要输出到指定位置，需要在增加代码处理。
                // 还有关于文件格式限制、文件大小限制，详见：中配置。
                InputStream is = file.getInputStream();
                String fileName = file.getOriginalFilename();
                long size = file.getSize();
                path = fastDFSClient.upload(is,size,fileName);
            } catch (FileNotFoundException e) {
                logger.error("上传失败："+e.getMessage());
                return "上传失败," + e.getMessage();
            } catch (IOException e) {
                logger.error("上传失败："+e.getMessage());
                return "上传失败," + e.getMessage();
            }
            long end = System.currentTimeMillis();
            logger.debug("end:"+end);
            // 总体耗时
            System.out.println("总体耗时:" + formatDuring(end - start)+"millis:"+(end - start));
            logger.debug("总体耗时:" + formatDuring(end - start)+"millis:"+(end - start));
            return "上传成功,路径:"+path;
        } else {
            return "上传失败，因为文件是空的.";
        }
    }

    /**
     * 多文件上传 主要是使用了MultipartHttpServletRequest和MultipartFile
     *
     * @param request
     * @return
     * @author gx
     * @create 2018年1月17日
     */
    @RequestMapping(value = "/upload/batch", method = RequestMethod.POST)
    public @ResponseBody String batchUpload(HttpServletRequest request) {
        logger.debug("开始上传。。。");
        long start = System.currentTimeMillis();
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
        MultipartFile file = null;
        String path = "";
        String fileName = "";
        InputStream is = null;
        long size = 0L;
        StringBuffer sb = new StringBuffer();
        try {
            for (int i = 0; i < files.size(); ++i) {
                file = files.get(i);
                if (!file.isEmpty()) {
                    is = file.getInputStream();
                    fileName = file.getOriginalFilename();
                    size = file.getSize();
                    path = fastDFSClient.upload(is,size,fileName);
                    sb.append("文件："+fileName+"，上传路径："+path+"<br/>");
                } else {
                    return "You failed to upload " + i + " because the file was empty.";
                }
            }
        } catch (Exception e) {
            return "You failed to upload  => " + e.getMessage();
        }
        long end = System.currentTimeMillis();
        // 总体耗时
        System.err.println("cost time:" + formatDuring(end - start));
        logger.debug("cost time:" + formatDuring(end - start));
        return "upload successful<br/>"+sb.toString();
    }

    public static String formatDuring(long mss) {
        long days = mss / (1000 * 60 * 60 * 24);
        long hours = (mss % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
        long minutes = (mss % (1000 * 60 * 60)) / (1000 * 60);
        long seconds = (mss % (1000 * 60)) / 1000;
        return days + " days " + hours + " hours " + minutes + " minutes "
                + seconds + " seconds ";
    }
    /**
     * 上传文件
     * @param multipartFile
     * @param request
     * @return
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> fileUp(MultipartHttpServletRequest multipartFile, HttpServletRequest request){
        Map<String,String> result = new HashMap<String,String>(16);
        //参数名称
        String param = request.getParameter("param");
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

    /**
     * http://img.hexun.com/2011-06-21/130726386.jpg
     * @param path
     * @return
     */
    @RequestMapping(value = "/upload2")
    @ResponseBody
    public Result uploadFile(String path) {
        String filePath = "";
        Result result = new Result();
        InputStream inputStream = null;
        if (StringUtils.isBlank(path)) {
            result.setState(-1);
            result.setSuccess(false);
            result.setMessage("文件路径为空");
            return result;
        }
        long size = 0L;
        try {
            //new一个URL对象
            URL url = new URL(path);
            //打开链接
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            ////设置请求方式为"GET"
            //conn.setRequestMethod("GET");
            ////超时响应时间为5秒
            conn.setConnectTimeout(30 * 1000);
            //通过输入流获取图片数据
            inputStream = conn.getInputStream();
            size = inputStream.available();
            filePath = fastDFSClient.upload(inputStream, size, path);
            inputStream.close();
        } catch (IOException e) {
            logger.error(e.toString());
            result.setState(-1);
            result.setSuccess(false);
            result.setMessage("上传出现异常：" + e.getMessage());
            return result;
        }
        result.setData("filePath", filePath);
        result.setState(0);
        result.setSuccess(true);
        result.setMessage("上传成功");
        return result;
    }

    /**
     *
     * @param path
     * @return
     */
    @RequestMapping(value = "/delete",produces="text/plain;charset=UTF-8")
    @ResponseBody
    public Result deleteFile(String path){
        //String path = "group1/M00/00/00/wKgyJVpZ1kKAIBoWAABRSUbvB8Y9.2.png";
        Result result = new Result();
        if(StringUtils.isBlank(path)){
            result.setState(-1);
            result.setSuccess(false);
            result.setMessage("文件路径为空");
            return result;
        }
        int index = path.indexOf("group1");
        String temPath = path.substring(index);
        fastDFSClient.deleteFile(temPath);
        result.setState(0);
        result.setSuccess(true);
        result.setMessage("删除成功");
        return result;
    }

    /**
     * 下载到本地C:/tmp/
     * @param path
     * @param fileName
     * @return
     */
    @RequestMapping(value = "/downloadLocal",produces="text/plain;charset=UTF-8")
    @ResponseBody
    public Result download(String path,String fileName){
        Result result = new Result();
        try {
            //String path = "M00/00/00/wKgyJVpbRu-EH67OAAAAAKmTJqU771.txt";
            if(StringUtils.isBlank(path)){
                result.setState(-1);
                result.setSuccess(false);
                result.setMessage("文件路径为空");
                return result;
            }
            if(StringUtils.isBlank(fileName)){
                result.setState(-1);
                result.setSuccess(false);
                result.setMessage("文件名为空");
                return result;
            }
            int index = path.indexOf("group1");
            String temPath = path.substring(index+7);
            InputStream inputStream = fastDFSClient.downloadFile("group1", temPath);
            System.out.println(inputStream);
            File destFile = new File("C:/tmp/"+fileName);
            FileUtils.copyInputStreamToFile(inputStream, destFile);
        } catch (Exception e) {
            logger.error("下载失败："+e.getMessage());
            result.setState(-1);
            result.setSuccess(false);
            result.setMessage("下载出现异常："+e.getMessage());
            return result;
        }
        result.setState(0);
        result.setSuccess(true);
        result.setMessage("下载成功");
        return result;
    }

    /**
     * 下载到任意路径
     * @param path
     * @param fileName
     * @param response
     * @return
     */
    @RequestMapping(value = "/download",produces="text/plain;charset=UTF-8")
    @ResponseBody
    public Result download(String path,String fileName, HttpServletResponse response) {
        Result result = new Result();
        try {
            if(StringUtils.isBlank(path)){
                result.setState(-1);
                result.setSuccess(false);
                result.setMessage("文件路径为空");
                return result;
            }
            if(StringUtils.isBlank(fileName)){
                result.setState(-1);
                result.setSuccess(false);
                result.setMessage("文件名为空");
                return result;
            }
            // 清空response
            response.reset();
            int index = path.indexOf("group1");
            String temPath = path.substring(index+7);
            //设置响应头，控制浏览器下载该文件
            response.setHeader("content-disposition", "attachment;filename=" + new String(fileName.getBytes()));
            //读取要下载的文件，保存到文件输入流
            InputStream in = fastDFSClient.downloadFile("group1", temPath);
            //创建输出流
            OutputStream out = response.getOutputStream();
            //缓存区
            byte[] buffer = new byte[1024];
            int len = 0;
            //循环将输入流中的内容读取到缓冲区中
            while((len = in.read(buffer)) > 0){
                out.write(buffer, 0, len);
            }
            //关闭
            in.close();
            out.close();
        } catch (Exception e) {
            logger.error("下载失败："+e.getMessage());
            result.setState(-1);
            result.setSuccess(false);
            result.setMessage("下载出现异常："+e.getMessage());
            return result;
        }
        result.setState(0);
        result.setSuccess(true);
        result.setMessage("下载成功");
        return result;
    }

    @RequestMapping(value = "/modifyFile")
    @ResponseBody
    public String modifyFile(){
        try {
            String path = "group1/M00/00/00/oYYBAFpXd42AHWghAAAADFmwwCQ305.txt";
            File f =  new File("E:/tmp/37.txt");
            InputStream fis = new FileInputStream(f);
            long modifySize = fis.available();
            fastDFSClient.modifyFile("group1", path,fis,modifySize,0);
        } catch (Exception e) {
            logger.error("修改失败："+e.getMessage());
            return "false";
        }
        return "success";
    }

    @RequestMapping(value = "/queryFileInfo",produces="text/plain;charset=UTF-8")
    @ResponseBody
    public Result queryFileInfo(String path){
        //String path = "M00/00/00/oYYBAFpdx_WAYES0AABzR9zSCGE.f1.png";
        Result result = new Result();
        FileInfo fileInfo = null;
        if (StringUtils.isBlank(path)) {
            result.setState(-1);
            result.setSuccess(false);
            result.setMessage("文件路径为空");
            return result;
        }
        //去掉group1
        int index = path.indexOf("group1");
        String temPath = path.substring(index+7);
        try {
            fileInfo = fastDFSClient.queryFileInfo("group1",temPath);
        }catch (Exception e){
            logger.error("获取文件信息失败："+e.getMessage());
            result.setState(-1);
            result.setSuccess(false);
            result.setMessage("获取文件信息异常："+e.getMessage());
            return result;
        }
        String res = "创建时间："+ DateUtil.date(fileInfo.getCreateTime())+","
                +"IP地址："+fileInfo.getSourceIpAddr()+","
                +"文件大小："+fileInfo.getFileSize();
        logger.debug(res);
        result.setData("fileInfo",fileInfo);
        result.setState(0);
        result.setSuccess(true);
        result.setMessage("获取文件信息成功");
        return result;
    }
}
