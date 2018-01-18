package com.gxd.fastdfs.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class HttpUtils {
    private static Logger log = LoggerFactory.getLogger(HttpUtils.class);

    private static MultiThreadedHttpConnectionManager connectionManager = null;
    private static int connectionTimeOut = 200000;
    private static int socketTimeOut = 200000;
    private static int maxConnectionPerHost = 500;
    private static int maxTotalConnections = 10000;
    private static HttpClient client;
    static {
        connectionManager = new MultiThreadedHttpConnectionManager();
        connectionManager.getParams().setConnectionTimeout(connectionTimeOut);
        connectionManager.getParams().setSoTimeout(socketTimeOut);
        connectionManager.getParams().setDefaultMaxConnectionsPerHost(maxConnectionPerHost);
        connectionManager.getParams().setMaxTotalConnections(maxTotalConnections);
        client = new HttpClient(connectionManager);

//        client = new HttpClient();
//        HttpConnectionManagerParams params = client.getHttpConnectionManager().getParams();
//        params.setConnectionTimeout(connectionTimeOut);
//        params.setSoTimeout(socketTimeOut);
    }

    /**
     * 发起https请求并获取结果
     *
     * @param requestUrl 请求地址
     * @param requestMethod 请求方式（GET、POST）
     * @param paramMap 提交的数据
     * @return
     */
    public static String httpRequest(String requestUrl, String requestMethod, Map<String,String> paramMap) {

        log.debug("请求URL: {}", requestUrl);
        log.debug("请求方式: {}", requestMethod);
        log.debug("提交数据: {}", FastJsonUtils.toJSONString(paramMap));

        StringBuffer buffer = new StringBuffer();
        try {
//            // 创建SSLContext对象，并使用我们指定的信任管理器初始化
//            TrustManager[] tm = { new MyX509TrustManager() };
//            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
//            sslContext.init(null, tm, new java.security.SecureRandom());
//            // 从上述SSLContext对象中得到SSLSocketFactory对象
//            SSLSocketFactory ssf = sslContext.getSocketFactory();

            URL url = new URL(requestUrl);
            HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();
//            httpUrlConn.setSSLSocketFactory(ssf);

            httpUrlConn.setDoOutput(true);
            httpUrlConn.setDoInput(true);
            httpUrlConn.setUseCaches(false);
            // 设置请求方式（GET/POST）
            httpUrlConn.setRequestMethod(requestMethod);

            if ("GET".equalsIgnoreCase(requestMethod)) {
                httpUrlConn.connect();
            }
            // 当有数据需要提交时
            if (null != paramMap) {
                StringBuffer paramStr = new StringBuffer();
                for (Map.Entry<String, String> entry : paramMap.entrySet()) {

                    paramStr.append(entry.getKey());
                    paramStr.append("=");
                    paramStr.append(entry.getValue());
                    paramStr.append("&");
                }


                OutputStream outputStream = httpUrlConn.getOutputStream();
                // 注意编码格式，防止中文乱码
                outputStream.write(paramStr.toString().getBytes("UTF-8"));
                outputStream.close();
            }

            // 将返回的输入流转换成字符串
            InputStream inputStream = httpUrlConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            bufferedReader.close();
            inputStreamReader.close();
            // 释放资源
            inputStream.close();
            inputStream = null;
            httpUrlConn.disconnect();
        } catch (ConnectException ce) {
            log.error("服务器连接超时");
        } catch (Exception e) {
            log.error("服务器请求报错:{}", e);
        }
        String result = buffer.toString();
        log.debug("服务器响应数据: " + result);
        return result;
    }

    public static String get(String url, Map<String, String> paramMap) throws Exception {

        String requestParam = "";
        if(paramMap != null) {
            Iterator e = paramMap.entrySet().iterator();
            while(e.hasNext()) {
                Map.Entry entry = (Map.Entry)e.next();
                requestParam += (String)entry.getKey();
                requestParam += "=";
                //requestParam += URLEncoder.encode((String)entry.getValue(), "GBK");
                requestParam += (String)entry.getValue();
                requestParam += "&";
            }
        }
        String getUrl = url + "?" + requestParam.substring(0,requestParam.length()-1);
        HttpMethod method = new GetMethod(getUrl);

        String responseBodyAsString = null;
        try {
            if(log.isDebugEnabled()) {
                log.debug("\n\n\n%%%%%%%%%%%%%%%http send: " + getUrl + "\n%%%%%%%%%%%%%%%");
            }
            int statusCode = client.executeMethod(method);
            if(statusCode == 200) {
                responseBodyAsString = method.getResponseBodyAsString();
            } else {
                responseBodyAsString = method.getResponseBodyAsString();
//                throw new Exception("HTTP请求返回状态失败");
            }

//            if(!responseBodyAsString.contains("UNDERWAY")) {
//                throw new Exception("请求处理失败");
//            }

            if(log.isDebugEnabled()) {
                log.debug("\n%%%%%%%%%%%%%%%http state: " + statusCode + "\n%%%%%%%%%%%%%%%response:" + responseBodyAsString);
            }
        } catch (Exception var10) {
            log.error("http request exception   %%%%% http url:" + getUrl);
            log.error(var10.getMessage(), var10);
            throw var10;
        } finally {
            if(method != null) {
                method.releaseConnection();
                method = null;
            }
        }

        return responseBodyAsString;
    }

    /**
     * 根据手机号获取手机号信息
     * @param servialNumber
     * @return
     * @throws MalformedURLException
     */
    public static JSONObject getMobileInfo(String servialNumber) throws MalformedURLException{
        String jsonString = null;
        JSONArray array = null;
        JSONObject jsonObject = null;
        String urlString = "?tel=" + servialNumber;
        StringBuffer sb = new StringBuffer();
        BufferedReader buffer;
        URL url = new URL(urlString);
        try{
            InputStream in = url.openStream();

            // 解决乱码问题
            buffer = new BufferedReader(new InputStreamReader(in,"gb2312"));
            String line = null;
            while((line = buffer.readLine()) != null){
                sb.append(line);
            }
            in.close();
            buffer.close();
            // System.out.println(sb.toString());
            jsonString = sb.toString();
            // 替换掉“__GetZoneResult_ = ”，让它能转换为JSONArray对象
            jsonString = jsonString.replaceAll("^[__]\\w{14}+[_ = ]+", "[");
            // System.out.println(jsonString+"]");
            String jsonString2 = jsonString + "]";
            // 把STRING转化为json对象
            array = JSONArray.parseArray(jsonString2);
            // 获取JSONArray的JSONObject对象，便于读取array里的键值对
            jsonObject = array.getJSONObject(0);

        }catch(Exception e){
            e.printStackTrace();
        }
        return jsonObject;
    }

    /**http下载*/
    public static boolean httpDownload(String httpUrl,String saveFile){
        // 下载网络文件
        int bytesum = 0;
        int byteread = 0;

        URL url = null;
        try {
            url = new URL(httpUrl);

            URLConnection conn = url.openConnection();
            InputStream inStream = conn.getInputStream();
            FileOutputStream fs = new FileOutputStream(saveFile);

            byte[] buffer = new byte[1204];
            while ((byteread = inStream.read(buffer)) != -1) {
                bytesum += byteread;
                fs.write(buffer, 0, byteread);
            }
            log.debug("下载文件总大小: {}", bytesum);
            fs.close();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        try {
            String url = "http://192.168.50.37:8080/file/queryFileInfo";
            Map map = new HashMap();
            map.put("path","http://192.168.50.253/dfs/group1/M00/00/00/ooYBAFpfAJeACdomAAANNHhXgpM29.conf");
            String get = get(url, map);
            System.out.println("get:"+get);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
