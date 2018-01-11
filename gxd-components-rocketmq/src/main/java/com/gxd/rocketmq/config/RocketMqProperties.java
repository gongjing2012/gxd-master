package com.gxd.rocketmq.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @Author:gj
 * @Date: 19:49 2018/1/10
 * @Description:mq相关参数
 */
@ConfigurationProperties("spring.rocketmq")
public class RocketMqProperties {

    private String namesrvAddr = "";
    private String instanceName = "";
    /**
     * 普通生成者
     */
    private String defaultProducer = "";
    private String transactionProducer = "";
    /**
     * 普通消费者
     */
    private String defaultConsumer = "";
    /**
     * 顺序消费者
     */
    private String orderConsumer = "";

    private Set<String> defaultTopic = new LinkedHashSet<>();

    private Set<String> orderTopic = new LinkedHashSet<>();

    public int maxMessageSize;

    public int sendMsgTimeout;

    public int consumeThreadMin;

    public int consumeThreadMax;

    public int consumerMessageBatchMaxSize;

    public String getNamesrvAddr() {
        return namesrvAddr;
    }

    public void setNamesrvAddr(String namesrvAddr) {
        this.namesrvAddr = namesrvAddr;
    }

    public String getInstanceName() {
        return instanceName;
    }

    public void setInstanceName(String instanceName) {
        this.instanceName = instanceName;
    }

    public String getDefaultProducer() {
        return defaultProducer;
    }

    public void setDefaultProducer(String defaultProducer) {
        this.defaultProducer = defaultProducer;
    }

    public String getTransactionProducer() {
        return transactionProducer;
    }

    public void setTransactionProducer(String transactionProducer) {
        this.transactionProducer = transactionProducer;
    }

    public String getDefaultConsumer() {
        return defaultConsumer;
    }

    public void setDefaultConsumer(String defaultConsumer) {
        this.defaultConsumer = defaultConsumer;
    }

    public String getOrderConsumer() {
        return orderConsumer;
    }

    public void setOrderConsumer(String orderConsumer) {
        this.orderConsumer = orderConsumer;
    }

    public Set<String> getDefaultTopic() {
        return defaultTopic;
    }

    public void setDefaultTopic(Set<String> defaultTopic) {
        this.defaultTopic = defaultTopic;
    }

    public Set<String> getOrderTopic() {
        return orderTopic;
    }

    public void setOrderTopic(Set<String> orderTopic) {
        this.orderTopic = orderTopic;
    }

    public int getMaxMessageSize() {
        return maxMessageSize;
    }

    public void setMaxMessageSize(int maxMessageSize) {
        this.maxMessageSize = maxMessageSize;
    }

    public int getSendMsgTimeout() {
        return sendMsgTimeout;
    }

    public void setSendMsgTimeout(int sendMsgTimeout) {
        this.sendMsgTimeout = sendMsgTimeout;
    }

    public int getConsumeThreadMin() {
        return consumeThreadMin;
    }

    public void setConsumeThreadMin(int consumeThreadMin) {
        this.consumeThreadMin = consumeThreadMin;
    }

    public int getConsumeThreadMax() {
        return consumeThreadMax;
    }

    public void setConsumeThreadMax(int consumeThreadMax) {
        this.consumeThreadMax = consumeThreadMax;
    }

    public int getConsumerMessageBatchMaxSize() {
        return consumerMessageBatchMaxSize;
    }

    public void setConsumerMessageBatchMaxSize(int consumerMessageBatchMaxSize) {
        this.consumerMessageBatchMaxSize = consumerMessageBatchMaxSize;
    }
}