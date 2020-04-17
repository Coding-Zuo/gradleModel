package com.zuo.model.controller.async;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class MyQueue {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private String start; //开始
    private String finis; //完成

    public String getFinis() {
        return finis;
    }

    public void setFinis(String finis)  {
        this.finis = finis;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) throws Exception {
        new Thread(()->{
           logger.info("接到请求"+start);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.finis = start;
            logger.info("处理完毕"+start);
        }).start();
    }
}

