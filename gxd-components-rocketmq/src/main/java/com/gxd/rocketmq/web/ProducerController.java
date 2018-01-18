package com.gxd.rocketmq.web;


import com.alibaba.rocketmq.client.Validators;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.*;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.common.message.MessageConst;
import com.alibaba.rocketmq.common.message.MessageQueue;
import com.alibaba.rocketmq.remoting.common.RemotingHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.alibaba.rocketmq.common.message.MessageAccessor.putProperty;


@RestController
public class ProducerController {
    private static Logger log = LoggerFactory.getLogger(ProducerController.class);

    @Autowired
    @Qualifier("default")
    private DefaultMQProducer defaultMQProducer;


    @Autowired
    @Qualifier("trans")
    private TransactionMQProducer transactionMQProducer;

    /**
     * 普通消息
     * @return
     */
    @RequestMapping("sendNormalMsg")
    @ResponseBody
    public String sendNormalMsg() {
        long t1 = System.currentTimeMillis();
        SendResult sendResult = null;
        int fail = 0;
        for (int i = 1; i < 11; i++) {
            try {
                Message msg = new Message("test",// topic
                        "tag1",// tag
                        "OrderID"+i,// key
                        ("这是一个测试消息"+i).getBytes(RemotingHelper.DEFAULT_CHARSET));// body

                //defaultMQProducer.send(msg, new SendCallback() {
                //
                //        @Override
                //        public void onSuccess(SendResult sendResult) {
                //            System.out.println(sendResult);
                //            //TODO 发送成功处理
                //        }
                //
                //        @Override
                //        public void onException(Throwable e) {
                //            System.out.println(e);
                //            //TODO 发送失败处理
                //        }
                //});
                sendResult = defaultMQProducer.send(msg);//同步发送
                if (sendResult.getSendStatus().name().equals(SendStatus.SEND_OK.name())) {
                    fail++;
                } else {
                    System.out.println(sendResult.getSendStatus().name());
                }
            } catch (Exception e) {
                log.error("异常信息："+e.getMessage());
                return "exception";
            }
        }
        long t2 = System.currentTimeMillis();
        return "所花时间：" + (t2 - t1) + "成功次数" + fail;
    }

    /**
     * 顺序消息
     * @throws Exception
     */
    @RequestMapping(value = "/sendOrderlyMsg", method = RequestMethod.GET)
    public String sendOrderlyMsg() throws Exception {
        long t1 = System.currentTimeMillis();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Message msg = new Message("order", "TagA", "OrderID11113", (i + ":" + j).getBytes());
                defaultMQProducer.send(msg, (List<MessageQueue> mqs, Message msg1, Object arg) -> {
                    // TODO Auto-generated method stub
                    int value = arg.hashCode();
                    if (value < 0) {
                        value = Math.abs(value);
                    }
                    value = value % mqs.size();
                    return mqs.get(value);
                }, i);
            }
        }
        long t2 = System.currentTimeMillis();
        return "所花时间：" + (t2 - t1) ;
    }



    @RequestMapping(value = "/sendTransactionMsg", method = RequestMethod.GET)
    public String sendTransactionMsg() {
        SendResult sendResult = null;
        try {
            //构造消息
            Message msg = new Message("test",// topic
                    "tag2",// tag
                    "test1",// key
                    ("这是一个事物测试消息").getBytes(RemotingHelper.DEFAULT_CHARSET));// body

            //发送事务消息，LocalTransactionExecute的executeLocalTransactionBranch方法中执行本地逻辑
            //sendResult = transactionMQProducer.sendMessageInTransaction(msg, new LocalTransactionExecuter() {
            //    @Override
            //    public LocalTransactionState executeLocalTransactionBranch(Message message, Object o) {
            //        int value = 1;
            //        //TODO 执行本地事务，改变value的值
            //        //===================================================
            //        System.out.println("执行本地事务。。。完成");
            //        if(o instanceof Integer){
            //            value = (Integer)o;
            //        }
            //        //===================================================
            //
            //        if (value == 0) {
            //            throw new RuntimeException("Could not find db");
            //        } else if ((value % 5) == 0) {
            //            return LocalTransactionState.ROLLBACK_MESSAGE;
            //        } else if ((value % 4) == 0) {
            //            return LocalTransactionState.COMMIT_MESSAGE;
            //        }
            //        return LocalTransactionState.ROLLBACK_MESSAGE;
            //    }
            //}, 4);
            // 发送事务消息，LocalTransactionExecute的executeLocalTransactionBranch方法中执行本地逻辑
            sendResult = sendMessageInTransaction(msg, (Message msg1, Object arg) -> {
                int value = Integer.valueOf(arg.toString());
                System.out.println("执行本地事务(结合自身的业务逻辑)。。。完成");
                if (value == 0) {
                    throw new RuntimeException("Could not find db");
                } else if ((value % 5) == 0) {
                    return LocalTransactionState.ROLLBACK_MESSAGE;
                } else if ((value % 4) == 0) {
                    return LocalTransactionState.COMMIT_MESSAGE;
                }
                return LocalTransactionState.ROLLBACK_MESSAGE;
            }, 4);
            System.out.println(sendResult);

        } catch (Exception e) {
            e.printStackTrace();
            return "exception";
        }
        if (sendResult == null){
            return "error";
        }
        return sendResult.toString();
    }

    /**
     * 模拟无法发送事务确认（测试事务回查）
     *
     * @param msg
     * @param tranExecuter
     * @param arg
     * @return
     * @throws MQClientException
     */
    public TransactionSendResult sendMessageInTransaction(final Message msg, final LocalTransactionExecuter tranExecuter, final Object arg)
            throws MQClientException {
        if (null == tranExecuter) {
            throw new MQClientException("tranExecutor is null", null);
        }
        Validators.checkMessage(msg, transactionMQProducer);

        SendResult sendResult = null;
        putProperty(msg, MessageConst.PROPERTY_TRANSACTION_PREPARED, "true");
        putProperty(msg, MessageConst.PROPERTY_PRODUCER_GROUP, transactionMQProducer.getProducerGroup());
        try {
            sendResult = transactionMQProducer.send(msg);
        } catch (Exception e) {
            throw new MQClientException("send message exception", e);
        }
        LocalTransactionState localTransactionState = LocalTransactionState.UNKNOW;
        switch (sendResult.getSendStatus()) {
            case SEND_OK: {
                try {
                    if (sendResult.getTransactionId() != null) {
                        msg.putUserProperty("__transactionId__", sendResult.getTransactionId());
                    }
                    localTransactionState = tranExecuter.executeLocalTransactionBranch(msg, arg);
                    if (null == localTransactionState) {
                        localTransactionState = LocalTransactionState.UNKNOW;
                    }

                    if (localTransactionState != LocalTransactionState.COMMIT_MESSAGE) {
                        log.info("executeLocalTransactionBranch return {}", localTransactionState);
                        log.info(msg.toString());
                    }
                } catch (Throwable e) {
                    log.info("executeLocalTransactionBranch exception", e);
                    log.info(msg.toString());
                }
            }
            break;
            case FLUSH_DISK_TIMEOUT:
            case FLUSH_SLAVE_TIMEOUT:
            case SLAVE_NOT_AVAILABLE:
                localTransactionState = LocalTransactionState.ROLLBACK_MESSAGE;
                break;
            default:
                break;
        }
        //测试事务回查
        //this.endTransaction(sendResult, localTransactionState, localException);
        TransactionSendResult transactionSendResult = new TransactionSendResult();
        transactionSendResult.setSendStatus(sendResult.getSendStatus());
        transactionSendResult.setMessageQueue(sendResult.getMessageQueue());
        transactionSendResult.setMsgId(sendResult.getMsgId());
        transactionSendResult.setQueueOffset(sendResult.getQueueOffset());
        transactionSendResult.setTransactionId(sendResult.getTransactionId());
        transactionSendResult.setLocalTransactionState(localTransactionState);
        return transactionSendResult;
    }
}
