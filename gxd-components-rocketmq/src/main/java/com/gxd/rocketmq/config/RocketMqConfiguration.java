package com.gxd.rocketmq.config;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.LocalTransactionState;
import com.alibaba.rocketmq.client.producer.TransactionMQProducer;
import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.gxd.rocketmq.event.RocketMqEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Set;

/**
 * @Author:gj
 * @Date: 8:36 2018/1/11
 * @Description:
 */
@Configuration
@EnableConfigurationProperties(RocketMqProperties.class)
public class RocketMqConfiguration {
    public static final Logger log = LoggerFactory.getLogger(RocketMqConfiguration.class);

	@Autowired
	private RocketMqProperties rmqProperties;

	@Autowired
	private ApplicationEventPublisher publisher;

	/**
	 * 发送普通消息
	 */
	@Bean(name = "default")
	public DefaultMQProducer defaultMQProducer() throws MQClientException {
		DefaultMQProducer producer = new DefaultMQProducer(rmqProperties.getDefaultProducer());
		producer.setNamesrvAddr(rmqProperties.getNamesrvAddr());
		producer.setInstanceName(rmqProperties.getInstanceName());
		producer.setMaxMessageSize(rmqProperties.maxMessageSize);
		producer.setSendMsgTimeout(rmqProperties.sendMsgTimeout);
		producer.setVipChannelEnabled(false);
		producer.start();
		//System.out.println("DefaultMQProducer is Started.");
		log.info("生产者DefaultMQProducer启动成功..........");
		return producer;
	}

	/**
	 * 发送事务消息
	 */
	@Bean(name = "trans")
	public TransactionMQProducer transactionMQProducer() throws MQClientException {
		TransactionMQProducer producer = new TransactionMQProducer(rmqProperties.getTransactionProducer());
		producer.setNamesrvAddr(rmqProperties.getNamesrvAddr());
		producer.setInstanceName(rmqProperties.getInstanceName());
		producer.setMaxMessageSize(rmqProperties.maxMessageSize);
		producer.setSendMsgTimeout(rmqProperties.sendMsgTimeout);
		producer.setTransactionCheckListener((MessageExt msg) ->{
			System.out.println("事务回查机制！");
			return  LocalTransactionState.COMMIT_MESSAGE;
		});
		// 事务回查最小并发数
		producer.setCheckThreadPoolMinSize(2);
		// 事务回查最大并发数
		producer.setCheckThreadPoolMaxSize(5);
		// 队列数
		producer.setCheckRequestHoldMax(2000);
		producer.start();
		log.info("生产者TransactionMQProducer启动成功..........");
		return producer;
	}

	/**
	 * 消费者
	 */
	@Bean
	public DefaultMQPushConsumer pushConsumer() throws MQClientException {
		DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(rmqProperties.getDefaultConsumer());
		Set<String> setTopic = rmqProperties.getDefaultTopic();
		for (String topic : setTopic) {
			consumer.subscribe(topic, "*");
		}
		consumer.setNamesrvAddr(rmqProperties.getNamesrvAddr());
		consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
		consumer.setConsumeThreadMin(rmqProperties.consumeThreadMin);
		consumer.setConsumeThreadMax(rmqProperties.consumeThreadMax);
		//一次消费一条
		consumer.setConsumeMessageBatchMaxSize(rmqProperties.consumerMessageBatchMaxSize);
		consumer.registerMessageListener((List<MessageExt> msgs, ConsumeConcurrentlyContext context) -> {
				MessageExt msg = msgs.get(0);
				try {
					publisher.publishEvent(new RocketMqEvent(msg, consumer));
				} catch (Exception e) {
					if (msg.getReconsumeTimes() <= 1) {
						return ConsumeConcurrentlyStatus.RECONSUME_LATER;
					} else {
						System.out.println("定时重试！");
					}
				}
				return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
		});
		new Thread(() -> {
				try {
					Thread.sleep(5000);
					//延迟5秒再启动，主要是等待spring事件监听相关程序初始化完成，否则，回出现对RocketMQ的消息进行消费后立即发布消息到达的事件，然而此事件的监听程序还未初始化，从而造成消息的丢失
					/**
					 * Consumer对象在使用之前必须要调用start初始化，初始化一次即可<br>
					 */
					try {
						consumer.start();
						log.info("普通消费者开启成功..........");
					} catch (MQClientException e) {
						log.error("异常信息："+e.getErrorMessage());
					}
				} catch (InterruptedException e) {
					log.error("异常信息："+e.getMessage());
				}
		}).start();
		return consumer;
	}

	/**
	 * 顺序消费者
	 */
	@Bean
	public DefaultMQPushConsumer pushOrderConsumer() throws MQClientException {
		DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(rmqProperties.getOrderConsumer());
		Set<String> setTopic = rmqProperties.getOrderTopic();
		for (String topic : setTopic) {
			consumer.subscribe(topic, "*");
		}
		consumer.setNamesrvAddr(rmqProperties.getNamesrvAddr());
		consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
		consumer.setConsumeMessageBatchMaxSize(1);
		consumer.registerMessageListener((List<MessageExt> msgs, ConsumeOrderlyContext context) -> {
				MessageExt msg = msgs.get(0);
				try {
					publisher.publishEvent(new RocketMqEvent(msg, consumer));
				} catch (Exception e) {
					if (msg.getReconsumeTimes() <= 1) {
						return ConsumeOrderlyStatus.SUCCESS;
					} else {
						System.out.println("定时重试！");
					}
				}
				return ConsumeOrderlyStatus.SUCCESS;
		});
		new Thread(() ->{
				try {
					Thread.sleep(5000);
					try {
						consumer.start();
						log.info("顺序消费开启成功..........");
					} catch (MQClientException e) {
						log.error("异常信息："+e.getErrorMessage());
					}
				} catch (InterruptedException e) {
					log.error("异常信息："+e.getMessage());
				}
		}).start();
		return consumer;
	}

}
