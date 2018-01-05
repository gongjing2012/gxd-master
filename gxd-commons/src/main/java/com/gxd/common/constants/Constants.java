package com.gxd.common.constants;

/**
 * @Author:gxd
 * @Description:常量类
 * @Date: 11:42 2017/12/27
 */
public class Constants {

    /* 分页操作时，每页只显示10条 */
    public static final Integer PAGE_SIZE = 10;

    /* 状态,1=有效，0=失效 */
    public static final Integer STATUS_VALID = 1;
    public static final Integer STATUS_INVALID = 0;

    /* session & session key */
    public static final String PERMISSION_SESSION = "permission_session";
    public static final String SESSION_KEY = "session_key";

    // url & roleName
    public static final String ROLE_CODE = "role_code";
    public static final String PERMISSION_URL = "permission_url";

    public static final String ROLE_BOSS_CODE = "boss_role";
    public static final String ROLE_MANAGER_CODE = "manager_role";// 管理员
    public static final String COMMON_ROLE_CODE = "common_role";// 普通用户

    public static final String ROLE_BOSS_NAME = "总经理";
    public static final String ROLE_MANAGER_NAME = "管理员";
    public static final String ROLE_COMMON_NAME = "普通用户";


    // 系统过期时间:2099-12-30 00:00:00
    public static final Long PACKAGE_SYSTEM_EXPIRE_TIME = 4102243200000L;

    //内部调用Rpc接口
    public static final int RPC_INVOKE_SUCCESS = 1;//成功
    /**
     * Topic：IM聊天记录、精灵会话记录
     */
    public static final String KAFKA_TOPIC_MSG = "thoth_msg";
    /**
     * 会话
     */
    public static final String KAFKA_TOPIC_SESSION = "thoth_session";
    /**
     * 聊天记录
     */
    public static final String KAFKA_TOPIC_IM_MSG = "_msg";


    //redis的各类key模板
    //过期时间几个档位，单位：秒
    public static final int REDIS_EXPIRE_TIME_ST = 60 * 1;
    public static final int REDIS_EXPIRE_TIME_T = 60 * 5;
    public static final int REDIS_EXPIRE_TIME_S = 60 * 30;
    public static final int REDIS_EXPIRE_TIME_M = 60 * 60 * 6;
    public static final int REDIS_EXPIRE_TIME_L = 60 * 60 * 24;
}
