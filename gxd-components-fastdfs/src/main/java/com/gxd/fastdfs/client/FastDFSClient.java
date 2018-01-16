package com.gxd.fastdfs.client;


import com.github.tobato.fastdfs.conn.FdfsWebServer;
import com.github.tobato.fastdfs.domain.FileInfo;
import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.exception.FdfsServerException;
import com.github.tobato.fastdfs.proto.storage.DownloadByteArray;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;


/**
 * @Author:gxd
 * @Description:上传下载组件
 * @Date: 19:16 2018/1/13
 * @Modified By:
 */
@Component
public class FastDFSClient {


    protected static final Logger FDFS_UPLOAD = LoggerFactory.getLogger("FDFS_UPLOAD");

    /** 支持的图片类型 */
    private static final String[] SUPPORT_IMAGE_TYPE = {"JPG", "JPEG", "PNG", "GIF", "BMP", "WBMP"};

    private static final List<String> SUPPORT_IMAGE_LIST = Arrays.asList(SUPPORT_IMAGE_TYPE);

    @Autowired
    private FastFileStorageClient storageClient;

    @Autowired
    private FdfsWebServer fdfsWebServer;



    /**
     * 上传文件
     *
     * @param is 文件对象
     * @param fileSize 文件大小
     * @return 文件访问地址
     * @throws IOException
     */
    public String upload(InputStream is, long fileSize, String fileExtName) throws IOException {
        StorePath storePath = storageClient.uploadFile(is, fileSize, fileExtName, null);
        FDFS_UPLOAD.debug("uploadFile fullPath:{}", storePath.getFullPath());
        //记录上传文件地址
        FDFS_UPLOAD.info("{}", storePath.getFullPath());
        return getResAccessUrl(storePath);
    }

    /**
     * 上传文件
     *
     * @param file 文件对象
     * @return 文件访问地址
     * @throws IOException
     */
    public String uploadFile(MultipartFile file) throws IOException {
        StorePath storePath = storageClient.uploadFile(file.getInputStream(), file.getSize(),
                FilenameUtils.getExtension(file.getOriginalFilename()), null);
        FDFS_UPLOAD.debug("uploadFile fullPath:{}", storePath.getFullPath());
        //记录上传文件地址
        FDFS_UPLOAD.info("{}", storePath.getFullPath());
        return getResAccessUrl(storePath);
    }


    /**
     * 上传图片，支持的类型为："JPG", "JPEG", "PNG", "GIF", "BMP", "WBMP"
     *
     * @param inputStream 图片对象
     * @param fileSize    图片大小
     * @param fileExtName 图片扩展名
     * @return 文件访问地址
     * @throws IOException
     */
    public StorePath uploadImage(MultipartFile inputStream, long fileSize, String fileExtName) {
        return uploadImage(inputStream, fileSize, fileExtName);
    }

    /**
     * 上传图片，支持的类型为："JPG", "JPEG", "PNG", "GIF", "BMP", "WBMP"
     *
     * @param inputStream 图片对象
     * @param fileSize    图片大小
     * @param fileExtName 图片扩展名
     * @return 文件访问地址
     * @throws IOException
     */
    public StorePath uploadImage(InputStream inputStream, long fileSize, String fileExtName) {
        Validate.notNull(inputStream, "上传文件流不能为空");
        Validate.notBlank(fileExtName, "文件扩展名不能为空");
        // 检查是否能处理此类图片
        if (!isSupportImage(fileExtName)) {
            //throw new Exception();
        }
        return uploadImage(inputStream, fileSize, fileExtName);
    }

    /**
     * 传图片并同时生成一个缩略图
     * "JPG", "JPEG", "PNG", "GIF", "BMP", "WBMP"
     * @param file 文件对象
     * @return 文件访问地址
     * @throws IOException
     */
    public StorePath uploadImageAndCrtThumbImage(MultipartFile file) throws IOException{
        StorePath storePath = storageClient.uploadImageAndCrtThumbImage(file.getInputStream(),file.getSize(),
                FilenameUtils.getExtension(file.getOriginalFilename()),null);
        return storePath;
    }

    /**
     * 传图片并同时生成一个缩略图
     * "JPG", "JPEG", "PNG", "GIF", "BMP", "WBMP"
     * @param content 文件路径
     * @return 文件访问地址
     * @throws FileNotFoundException
     */
    @SuppressWarnings("resource")
    public StorePath uploadImageAndCrtThumbImage(String content) throws FileNotFoundException {
        if(!StringUtils.hasText(content)){
            throw new NullPointerException();
        }
        File file = new File(content);
        FileInputStream inputStream=new FileInputStream(file);
        String fileName=file.getName();
        //获取文件后缀名
        String strs= FilenameUtils.getExtension(fileName);
        if(!StringUtils.hasText(strs)){
            throw new NullPointerException();
        }
        StorePath storePath = storageClient.uploadImageAndCrtThumbImage(inputStream,file.length(),strs,null);
        return storePath;
    }


    /**
     * 将一段字符串生成一个文件上传
     *
     * @param content       文件内容
     * @param fileExtension
     * @return
     */
    public String uploadFile(String content, String fileExtension) {
        byte[] buff = content.getBytes(Charset.forName("UTF-8"));
        ByteArrayInputStream stream = new ByteArrayInputStream(buff);
        StorePath storePath = storageClient.uploadFile(stream, buff.length, fileExtension, null);
        //记录上传文件地址
        FDFS_UPLOAD.info("{}", storePath.getFullPath());
        return getResAccessUrl(storePath);
    }

    /**
     * 封装图片完整URL地址
     *
     * @param storePath
     * @return
     */
    private String getResAccessUrl(StorePath storePath) {
        return fdfsWebServer.getWebServerUrl()+ storePath.getFullPath();
    }

    /**
     * 删除文件
     *
     * @param fileUrl 文件访问地址
     * @return
     */
    public void deleteFile(String fileUrl) {
        FDFS_UPLOAD.debug("FastDFSClient.deleteFile fileUrl:{}", fileUrl);
        if (StringUtils.isEmpty(fileUrl)) {
            return;
        }
        try {
            StorePath storePath = StorePath.praseFromUrl(fileUrl);
            FDFS_UPLOAD.info("FastDFSClient.deleteFile storePath group:{}, path:{}",
                    storePath.getGroup(), storePath.getPath());
            storageClient.deleteFile(storePath.getGroup(), storePath.getPath());
        } catch (FdfsServerException e) {
            FDFS_UPLOAD.warn("FdfsServerException--->{}", e.getMessage());
            //ExceptionUtils.business(GlobalErrorCode.UNSUPPORT_STORE_PATH);
        }
    }

    /**
     *
     * @Title: downloadFile
     * @Description: 文件下载
     * @param groupName
     * @param path
     * @return
     * @return: InputStream
     */
    public InputStream downloadFile(String groupName,String path) {
        try {
            DownloadByteArray callback = new DownloadByteArray();
            byte[] bytes = storageClient.downloadFile(groupName, path, callback);
            InputStream inputStream = new ByteArrayInputStream(bytes);
            return inputStream;
        } catch (Exception ex) {
            FDFS_UPLOAD.error(ex.getMessage());
            return null;
        }
    }

    /**
     * 查询文件信息
     * @param groupName
     * @param path
     * @return
     */
    public FileInfo queryFileInfo(String groupName,String path){
        return storageClient.queryFileInfo(groupName, path);

    }

    /**
     * 是否是支持的图片文件
     *
     * @param fileExtName
     * @return
     */
    private boolean isSupportImage(String fileExtName) {
        return SUPPORT_IMAGE_LIST.contains(fileExtName.toUpperCase());
    }

}
