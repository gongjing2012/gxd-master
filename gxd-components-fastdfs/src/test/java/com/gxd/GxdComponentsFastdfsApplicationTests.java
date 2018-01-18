//package com.gxd;
//
//import com.github.tobato.fastdfs.domain.StorePath;
//import com.github.tobato.fastdfs.service.FastFileStorageClient;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class GxdComponentsFastdfsApplicationTests {
//	@Autowired
//	private FastFileStorageClient storageClient;
//	@Test
//	public void contextLoads() {
//	}
//
//	@Test
//	public void testupload(){
//		//FileInputStream fis = null;
//		//try {
//		//	fis = new FileInputStream(new File("E:"  + File.separator + "2.png"));
//		//	ByteArrayOutputStream bos = new ByteArrayOutputStream();
//        //
//		//	byte[] cache = new byte[4096];
//		//	while (fis.read(cache) != -1) {
//		//		bos.write(cache);
//		//	}
//		//	fis.close();
//		//	//FastDSFile fastDSFile = new FastDSFile();
//		//	//fastDSFile.setContent(bos.toByteArray());
//		//	//fastDSFile.setExt("jpg");
//        //
//		//	// -------上传----
//		//	JSONArray rs = storageClient.upload(fastDSFile);
//		//	System.out.println("上传结束:" + rs);
//        //
//		//	// -------下载----
//		//	//byte[] dfile = FastDFSClient.download(rs.getString(0), rs.getString(1));
//         //   //
//		//	//FileOutputStream fos = new FileOutputStream(new File("C:/Users/xq/Pictures/tx-fdfs.jpg"));
//		//	//fos.write(dfile);
//		//	//fos.flush();
//		//	//fos.close();
//         //   //
//		//	//// -------删除-----
//		//	//int ds=FastDFSClient.delete(rs.getString(0), rs.getString(1));
//		//	////
//		//	System.out.println("Delete:"+ds);
//		//	System.out.println("---End----");
//		//} catch (FileNotFoundException e) {
//		//	e.printStackTrace();
//		//}catch (IOException ee){
//		//	ee.printStackTrace();
//		//}
//
//		//try {
//		//	InputStream fis = new FileInputStream("E:"  + File.separator + "test.txt");
//		//	//String test = new FastDFSClient().upload(fis, 10L, "test");
//		//	//System.out.println("上传文件路径："+test);
//		//	StorePath storePath = storageClient.uploadFile(fis, 10L, "test", null);
//		//	//记录上传文件地址
//		//	String uploadPath = getResAccessUrl(storePath);
//		//	System.out.println("上传文件路径："+uploadPath);
//        //
//		//} catch (exception e) {
//		//	e.printStackTrace();
//		//}
//	}
//
//	private String getResAccessUrl(StorePath storePath) {
//		String fileRoot = "";
//		String filePath = String.format("%s/%s", fileRoot, storePath.getFullPath());
//		return filePath;
//	}
//}
