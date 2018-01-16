package com.gxd.rocketmq.message;
import com.alibaba.rocketmq.common.message.MessageExt;
import org.springframework.stereotype.Component;
/**
 * @Author:gxd
 * @Description:
 * @Date: 15:41 2018/1/10
 * @Modified By:
 */
@Component
public class MessageProcessorImplTest implements MessageProcessor {
    @Override
    public boolean handleMessage(MessageExt messageExt) {
        System.out.println("receive : " + messageExt.toString());
        return true;
    }
}
