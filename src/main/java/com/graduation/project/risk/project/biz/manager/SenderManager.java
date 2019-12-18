package com.graduation.project.risk.project.biz.manager;

import com.graduation.project.risk.common.core.biz.BizCoreException;
import com.graduation.project.risk.common.core.biz.ErrorCode;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.concurrent.CountDownLatch;

@Service
public class SenderManager {

    private final Logger logger = LoggerFactory.getLogger(SenderManager.class);

//    @Value("${rocketmq.name-server}")
    private String nameServerAddress;

    @Autowired
    private TransactionTemplate transactionTemplate;

//    @Autowired
//    private SendRecordJpaDAO sendRecordJpaDAO

    public void sync(String group, String topic, String key, String tag, String body) {
        //save msg

        DefaultMQProducer producer = new DefaultMQProducer(group);
        producer.setNamesrvAddr(nameServerAddress);

        try {
            producer.start();

            org.apache.rocketmq.common.message.Message msg = new Message(
                    topic,
                    tag,
                    key,
                    body.getBytes(RemotingHelper.DEFAULT_CHARSET)
            );

            //send msg

        } catch (Exception e) {
            logger.error("send msg failed, group = {}, topic = {}, key = {}, tag = {}", group, topic, key, tag, e);

            throw new BizCoreException(ErrorCode.SEND_FAILED);

        } finally {
            producer.shutdown();
        }
    }


    public void async(String group, String topic, String key, String tag, String body){
        //save msg

        DefaultMQProducer producer = new DefaultMQProducer(group);
        producer.setNamesrvAddr(nameServerAddress);

        final CountDownLatch countDownLatch = new CountDownLatch(1);

        try{
            producer.start();
            producer.setRetryTimesWhenSendAsyncFailed(0); // don't retry

            Message msg = new Message(
                    topic,
                    tag,
                    key,
                    body.getBytes(RemotingHelper.DEFAULT_CHARSET)
            );

            //send msg
            producer.send(msg, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    transactionTemplate.execute(status -> {
                    //持久化操作
                    //countDownLatch.countDown();
                    return true;
                    });
                }
                @Override
                public void onException(Throwable e) {
                    logger.error("send msg failed, group = {}, topic = {}, key = {}, tag = {}", group, topic, key, tag, e);
                    transactionTemplate.execute(status -> {
                        //持久化操作
                        countDownLatch.countDown();
                        return true;
                    });
                }
            });
        }catch (Exception e){
            logger.error("send msg failed, group = {}, topic = {}, key = {}, tag = {}", group, topic, key, tag, e);
            transactionTemplate.execute(status -> {
                //持久化操作
                return true;
            });
            throw new BizCoreException(ErrorCode.SEND_FAILED);
        }finally {
            try {
                // Waiting for the message to be sent successfully or failed
                countDownLatch.await();
                producer.shutdown();
            } catch (InterruptedException e) {
                logger.error("close producer failed", e);
            }
        }
    }

}
