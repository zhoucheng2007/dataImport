/*
 * <p>Copyright Foresee Science & Technology Co.</p>
 * @author <a href="mailto:zhangyong@foresee.com.cn">zhangyong</a>
 * $Id: DelegateManager.java 8101 2007-08-13 09:38:58Z zhangyong $
 */
package com.blogzhou.common.code;

import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.blogzhou.common.DateToolkit;

/**
 * <p>定时刷新基础数据: 每天早上refreshHour=5 点刷新. 先判断当前时间是否5点,过了就不刷新了. 
 * 刷新后计算下一个5:00钟距离现在有多远(可能今天或者明天),然后sleep()等待下一个5:00的时候醒来再运行</p>
 * @author <a href="mailto:zhangyong@foresee.com.cn">zhangyong</a>
 * @version $LastChangedRevision: 8101 $
 */
public class TimerManager {
    /**
     * 当前类自己的logger
     */
    private static Logger logger = LoggerFactory.getLogger(TimerManager.class);
    
    /**
     * 守护线程
     */
    private static Monitor threadMonitor = null;
    
  
    /**
     * 每天何时刷新(上午6:00)
     */
    private static int refreshHour = 6;    

    /**
     * 单例模式
     */
    private TimerManager() {
    }

    /**
     * 初始化
     */
    public static synchronized void init() {
        logger.debug("TimerManager trigger initialization.");        
        if (threadMonitor == null || !threadMonitor.isAlive()) {
            initMonitorThread();
        }
    }

    /**
     * 创建或重新创建监控Thread
     */
    private synchronized static void initMonitorThread() {
        if (threadMonitor == null) {
            threadMonitor = new Monitor();
            threadMonitor.start();
            logger.debug("Deamon thread started, monitoring CodeTable.");
        } else {
            threadMonitor.monitorStop(); // 先尝试结束上个线程
            threadMonitor = new Monitor();
            threadMonitor.start();
            logger.warn("Deamon thread seems dead, started again.");
        }
    }


    /**
     * <p>EAI服务监控进程</p>
     * @author <a href="mailto:lindahai@foresee.com.cn">Lin Dahai</a>
     * @version $LastChangedRevision: 8101 $
     */
    private static class Monitor extends Thread {
        /**
         * 标志：执行
         */
        private boolean flagRun = false;
        
        /**
         * 构造函数
         * @param threadName 线程名称
         */
        public Monitor() {
            this("FSTAX-ReLoadCodeTable");
        }

        /**
         * 构造函数
         * @param threadName 线程名称
         */
        public Monitor(String threadName) {
            super(threadName);
        }

        /**
         * 执行动作
         */
        public void run() {
            flagRun = true;
            while (flagRun) {
                /* 是否需要重新装载基础数据 */
                if (needReLoadCodeTable()) {
                    logger.debug("Try to load codeTable again.");
                    reLoadCodeTable();
                }
                
                /*计算第二天的上午5:00距离现在多少 long millis */
                long sleepMillis = calc();
                
                logger.debug("距离下一个重新装载时间"+refreshHour+"点还有:"+sleepMillis);

                /* 等待 */
                try {
                    Thread.sleep(sleepMillis);
                   
                } catch (InterruptedException e) {
                    // Nothing to do.
                }
            }
        }

        /**
         * 是否需要重新装载基础数据,在>=5点并且<=6之间装载点时需要, 否则不需要
         * @return true需要，false不需要
         */
        private synchronized boolean needReLoadCodeTable() {
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());
            if (cal.get(Calendar.HOUR_OF_DAY) == refreshHour)
                return true;
            else 
                return false;
        }
        
        /**
         * 重新调用CodeTable
         */
        private synchronized void reLoadCodeTable() {
            CodeManager.reLoadCodeTable();
        }
        
        /**
         * 计算下一个"上午5:00"距离现在多少 long millis
         */
        private synchronized long calc() {
            Calendar calNow = Calendar.getInstance();
            calNow.setTime(new Date());
            if (calNow.get(Calendar.HOUR_OF_DAY) < refreshHour) {
                /*返回当天上午5:00的距离时间*/
                calNow.set(Calendar.HOUR_OF_DAY, refreshHour);
                calNow.set(Calendar.MINUTE , 0);   
                return calNow.getTimeInMillis() - (new Date()).getTime();
            } else {
                /*返回明天上午5:00的距离时间*/
                Date nextDate = DateToolkit.incDay(new Date());
                Calendar cal = Calendar.getInstance();
                cal.setTime(nextDate);
                cal.set(Calendar.HOUR_OF_DAY, refreshHour);
                cal.set(Calendar.MINUTE , 0);  //算下一个5:10的距离, 5:00至6:00之间即可.
                return cal.getTimeInMillis() - (new Date()).getTime();
            }
        }      

        /**
         *  停止本线程的工作，设置flagRun为false，则线程中的循环将终止
         */
        protected void monitorStop() {
            flagRun = false;
        }
    }

    /**
     * 测试启动函数
     * @param args 这里不使用
     * @throws Exception 测试失败
     */
    public static void main(String[] args) throws Exception {
        TimerManager.init();
    }
}

