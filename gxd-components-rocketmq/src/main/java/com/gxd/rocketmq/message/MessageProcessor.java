package com.gxd.rocketmq.message;
import com.alibaba.rocketmq.common.message.MessageExt;

/**
 * @Author:gxd
 * @Description:
 * @Date: 15:39 2018/1/10
 * @Modified By:
 */
public interface MessageProcessor {
    /**
     * 处理消息的接口
     * @param messageExt
     * @return
     */
    public boolean handleMessage(MessageExt messageExt);
}
