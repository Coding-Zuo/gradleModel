package com.zuo.model.controller.async;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.Callable;

@RestController
public class AsyncController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private MyQueue myQueue;

    @Autowired
    private DeferredResultHolder deferredResultHolder;

    @RequestMapping("/async")
    public DeferredResult<String> async() throws Exception {
        logger.info("主线程开始");

        String asyncNumber= RandomStringUtils.randomNumeric(8);
        myQueue.setStart(asyncNumber);
        DeferredResult<String> result=new DeferredResult<>();
        deferredResultHolder.getMap().put(asyncNumber,result);

//        Callable<String> result = new Callable<String>() {
//            @Override
//            public String call() throws Exception {
//                logger.info("副线程开始");
//                Thread.sleep(1000);
//                logger.info("副线程返回");
//                return "success";
//            }
//        };
        logger.info("主线程结束");
        return result;
    }
}
