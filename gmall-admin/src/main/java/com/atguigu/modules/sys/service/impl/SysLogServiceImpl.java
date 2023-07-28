/**
 * Copyright (c) 2016-2019 谷粒开源 All rights reserved.
 *
 * https://www.guli.cloud
 *
 * 版权所有，侵权必究！
 */

package com.atguigu.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.common.utils.PageUtils;
import com.atguigu.common.utils.Query;
import com.atguigu.modules.sys.dao.SysLogDao;
import com.atguigu.modules.sys.entity.SysLogEntity;
import com.atguigu.modules.sys.service.SysLogService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@Service("sysLogService")
public class SysLogServiceImpl extends ServiceImpl<SysLogDao, SysLogEntity> implements SysLogService {

    @Autowired
    SysLogDao logDao;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Autowired
    DataSourceTransactionManager transactionManager;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String key = (String)params.get("key");

        IPage<SysLogEntity> page = this.page(
            new Query<SysLogEntity>().getPage(params),
            new QueryWrapper<SysLogEntity>().like(StringUtils.isNotBlank(key),"username", key)
        );

        return new PageUtils(page);
    }

    @Override
    public void multiplyThread() throws InterruptedException {
        List<CompletableFuture<Void>> futures = new ArrayList<>();
        List<Runnable> tasks = new ArrayList<>();
        List<SysLogEntity> list = new ArrayList<>();
        Date date = new Date();
        CountDownLatch latch = new CountDownLatch(10);
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        AtomicReference<Boolean> isError = new AtomicReference<>(false);

        ExecutorService executorService = Executors.newFixedThreadPool(11);
        for (int i = 0; i < 10; i++) {
            SysLogEntity logEntity = new SysLogEntity();
            logEntity.setUsername("DT测试" + i);
            logEntity.setOperation("20");
            if (i == 6) {
                logEntity.setOperation("20000000000000");
            }
            logEntity.setTime(new Date().getTime());
            list.add(logEntity);

//            tasks.add(new Runnable() {
//                @Override
//                public void run() {
//                    logDao.insert(logEntity);
//                }
//            });


            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                DefaultTransactionDefinition def = new DefaultTransactionDefinition();
                def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
                TransactionStatus status = transactionManager.getTransaction(def);
                try {
                    // 开启新事务，导入一条数据
                    logDao.insert(logEntity);
                } catch (Exception e) {
                    // 出错后触发回滚操作
                    // 接受异常 处理异常
                    isError.set(true);
                    log.error(Thread.currentThread().getName()+" 导入数据失败: ");
                    transactionManager.rollback(status);
                    throw new RuntimeException(e);
                } //finally {
                    latch.countDown();
                    log.info(Thread.currentThread().getName()+ " 减1  --------------");
                //}


                try {
                    log.info(Thread.currentThread().getName()+" 等待----------");
                    // 等所有处理结果
                    //latch.await(20, TimeUnit.SECONDS);
                    latch.await();
                    if(isError.get()) {
                        // 事务回滚
                        log.info(Thread.currentThread().getName()+" 事务回滚");
                        transactionManager.rollback(status);
                    }else {
                        //事务提交
                        log.info(Thread.currentThread().getName()+" 事务提交");
                        transactionManager.commit(status);
                    }
                } catch (InterruptedException e) {
//                    e.printStackTrace();
                    log.error(Thread.currentThread().getName()+" 等待异常了："+e);
                    //Thread.currentThread().interrupt();
                }
            }, executorService).exceptionally((e)->{
                latch.countDown();
                log.error(Thread.currentThread().getName()+" 线程池 减1 -----");
                log.error(Thread.currentThread().getName()+" 线程池异常了："+e);
                return null;
            });
//            futures.add(future);
        }

//        latch.await(25, TimeUnit.SECONDS);
        latch.await();
        if(isError.get()) {
            // 主线程抛出自定义的异常
            throw new RuntimeException("事务异常");
        }



////        List<List<Runnable>> lists = averageAssign(tasks, 5);
//        //ExecutorService executorService = Executors.newFixedThreadPool(4);
//
//        // 等待所有子线程结束
//        //CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
//
//        // 检查子线程是否都成功执行，如果有子线程回滚则触发主事务的回滚操作
//        boolean hasRollback = futures.stream()
//                .anyMatch(future -> future.isCompletedExceptionally() || atomicBoolean.get());
//        //if (atomicBoolean.get()) {
//            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//            log.info("触发事务回滚: ");
//        //}

    }





    public static <T> List<List<T>> averageAssign(List<T> source, int n) {
        List<List<T>> result = new ArrayList<List<T>>();
        int remaider = source.size() % n;
        int number = source.size() / n;
        int offset = 0;//偏移量
        for (int i = 0; i < n; i++) {
            List<T> value = null;
            if (remaider > 0) {
                value = source.subList(i * number + offset, (i + 1) * number + offset + 1);
                remaider--;
                offset++;
            } else {
                value = source.subList(i * number + offset, (i + 1) * number + offset);
            }
            result.add(value);
        }
        return result;
    }


}
