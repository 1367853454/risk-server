package com.graduation.project.risk.project.biz.manager;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ConsumerManager {

    private final Logger logger = LoggerFactory.getLogger(ConsumerManager.class);

//    @Value("${rocketmq.name-server}")
    private String nameServerAddress;

    public void consume(String group, String topic, String tags){

        try{
            DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(group);
            consumer.setNamesrvAddr(nameServerAddress);
            consumer.subscribe(topic, tags);
            consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);

            consumer.registerMessageListener((MessageListenerConcurrently)(list,context) ->{

                for (MessageExt messageExt : list){

                    String msgId = messageExt.getMsgId();

                    try {
                        System.out.println("get something");
                        //持久化操作
                    }catch (Exception e){//UnsupportedEncodingException e

                        logger.error("Consume msg failed, msgId = {}", msgId, e);
                        return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                    }
                }
                return  ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            });
            consumer.start();
        }catch (MQClientException e){
            e.printStackTrace();
        }

    }

}
