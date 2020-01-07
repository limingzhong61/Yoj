package com.yoj.custom.judge.threads;

import com.yoj.custom.judge.bean.JudgeSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.stereotype.Component;

import java.util.Queue;
import java.util.concurrent.*;

@Component
@Slf4j
public class JudgeThreadPoolManager implements BeanFactoryAware {

    //用于从IOC里取对象
    private BeanFactory factory; //如果实现Runnable的类是通过spring的application.xml文件进行注入,可通过 factory.getBean()获取，这里只是提一下

    // 线程池维护线程的最少数量
    private final static int CORE_POOL_SIZE = 2;
    // 线程池维护线程的最大数量
    private final static int MAX_POOL_SIZE = 3;
    // 线程池维护线程所允许的空闲时间
    private final static int KEEP_ALIVE_TIME = 0;
    // 线程池所使用的缓冲队列大小
    private final static int WORK_QUEUE_SIZE = 50;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.factory = beanFactory;
    }

    /**
     * 用于储存在队列中的判题任务,防止重复提交,在真实场景中，可用redis代替 验证重复
     */
//    Map<Integer, Object> cacheMap = new ConcurrentHashMap<>();

    /**
     * 判题任务的缓冲队列,当线程池满了，则将判题任务存入到此缓冲队列
     */
    Queue<JudgeSource> judgeQueue = new LinkedBlockingQueue();


    /**
     * 当线程池的容量满了，执行下面代码，将判题任务存入到缓冲队列
     */
    final RejectedExecutionHandler handler = (Runnable r, ThreadPoolExecutor executor) -> {
        //判题任务加入到缓冲队列
        judgeQueue.offer(((JudgeTask) r).getJudgeSource());
        System.out.println("系统任务太忙了,把此判题任务交给(调度线程池)逐一处理，判题任务号：" + ((JudgeTask) r).getJudgeSource());
    };


    /**
     * 创建线程池
     */
    final ThreadPoolExecutor threadPool =
            new ThreadPoolExecutor(CORE_POOL_SIZE, MAX_POOL_SIZE, KEEP_ALIVE_TIME, TimeUnit.SECONDS,
                    new ArrayBlockingQueue(WORK_QUEUE_SIZE), this.handler);
    // get prototype-scoped instance for ioc
    private JudgeTask getNewJudgeTask(JudgeSource judgeSource){
        JudgeTask judgeTask = factory.getBean("judgeTask", JudgeTask.class);
        judgeTask.setJudgeSource(judgeSource);
        return judgeTask;
    }

    /**
     * 将任务加入判题任务线程池
     */
    public void addTask(JudgeSource judgeSource) {
        log.info("此判题任务准备添加到线程池，判题任务号：" + judgeSource);
        // get prototype-scoped instance for ioc
        threadPool.execute(getNewJudgeTask(judgeSource));
    }

    /**
     * 线程池的定时任务----> 称为(调度线程池)。此线程池支持 定时以及周期性执行任务的需求。
     */
    final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(5);


    /**
     * 检查(调度线程池)，每秒执行一次，查看判题任务的缓冲队列是否有 判题任务记录，则重新加入到线程池
     */
    final ScheduledFuture scheduledFuture = scheduler.scheduleAtFixedRate(() -> {
                //判断缓冲队列是否存在记录
                if (!judgeQueue.isEmpty()) {
                    //当线程池的队列容量少于WORK_QUEUE_SIZE，则开始把缓冲队列的判题任务 加入到 线程池
                    if (threadPool.getQueue().size() < WORK_QUEUE_SIZE) {
                        JudgeSource judgeSource = judgeQueue.poll();
                        threadPool.execute(getNewJudgeTask(judgeSource));
                        log.info("(调度线程池)缓冲队列出现判题任务业务，重新添加到线程池，判题任务号：" + judgeSource.getSolutionId());
                    }
                }
            }
            , 0, 1, TimeUnit.SECONDS);


    /**
     * 获取消息缓冲队列
     */
    public Queue<JudgeSource> getMsgQueue() {
        return judgeQueue;
    }

    /**
     * 终止判题任务线程池+调度线程池
     */
    public void shutdown() {
        //true表示如果定时任务在执行，立即中止，false则等待任务结束后再停止
        System.out.println("终止判题任务线程池+调度线程池：" + scheduledFuture.cancel(false));
        scheduler.shutdown();
        threadPool.shutdown();
    }
}