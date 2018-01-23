package com.gxd.fastdfs.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author:gxd
 * @Description:
 * @Date: 12:07 2018/1/23
 * @Modified By:
 */
@ConfigurationProperties(prefix = "spring.http.antiSteal")
@Component("fastDfsAntiLeechConfig")
public class FastDfsAntiLeechConfig {
    //check_token:  true
    //token_ttl:  900 #token TTL，即生成token的有效时长
    //secret_key: FastDFS1234567890 # 生成token的密钥，尽量设置得长一些，千万不要泄露出去
    //token_check_fail:  #token检查失败，返回的文件内容，需指定本地文件名
    private boolean checkToken;
    private int tokenTtl;
    private String secretKey;
    private String tokenCheckFail;

    public boolean isCheckToken() {
        return checkToken;
    }

    public void setCheckToken(boolean checkToken) {
        this.checkToken = checkToken;
    }

    public int getTokenTtl() {
        return tokenTtl;
    }

    public void setTokenTtl(int tokenTtl) {
        this.tokenTtl = tokenTtl;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getTokenCheckFail() {
        return tokenCheckFail;
    }

    public void setTokenCheckFail(String tokenCheckFail) {
        this.tokenCheckFail = tokenCheckFail;
    }
}
