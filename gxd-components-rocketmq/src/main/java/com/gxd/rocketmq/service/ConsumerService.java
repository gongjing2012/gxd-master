package com.gxd.rocketmq.service;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.exception.MQBrokerException;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.remoting.common.RemotingHelper;
import com.alibaba.rocketmq.remoting.exception.RemotingException;
import com.gxd.rocketmq.event.RocketMqEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;


@Component
public class ConsumerService {
	public static final Logger log = LoggerFactory.getLogger(ConsumerService.class);

    @Async
	@EventListener(condition = "#event.topic=='test'")
	public void testListen(RocketMqEvent event) {
		DefaultMQPushConsumer consumer = event.getConsumer();
		try {
			String msg = new String(event.getMessageExt().getBody(), RemotingHelper.DEFAULT_CHARSET);
			log.debug("普通消息："+msg);
			System.out.println(msg);
		} catch (Exception e) {
			log.error("异常信息："+e.getMessage());
			if (event.getMessageExt().getReconsumeTimes() <= 1) {// 重复消费1次
				try {
					consumer.sendMessageBack(event.getMessageExt(), 1);
				} catch (RemotingException | MQBrokerException | InterruptedException | MQClientException e1) {
					log.error("异常信息："+e.getMessage());
					//消息进行定时重试
				}
			} else {
				System.out.println("消息消费失败，定时重试");
			}
		}
	}


	@EventListener(condition = "#event.topic=='order'")
	public void normalListen(RocketMqEvent event) {
		try {
			System.out.println("顺序消息：" + new String(event.getMessageExt().getBody(),RemotingHelper.DEFAULT_CHARSET));
		}catch (Exception e){
			log.error("异常信息："+e.getMessage());
		}

	}
}
