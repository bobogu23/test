package netty.simplerpcframework.core.server.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author:ben.gu
 * @Date:2020/1/9 9:45 AM
 */
public class AbortPolicyWithReport implements RejectedExecutionHandler {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {

        String msg = String.format(
                "RpcServer["
                        + "  Pool Size: %d (active: %d, core: %d, max: %d, largest: %d), Task: %d (completed: %d),"
                        + " Executor status:(isShutdown:%s, isTerminated:%s, isTerminating:%s, queue size:%s)]",
                e.getPoolSize(), e.getActiveCount(), e.getCorePoolSize(), e.getMaximumPoolSize(),
                e.getLargestPoolSize(), e.getTaskCount(), e.getCompletedTaskCount(), e.isShutdown(),
                e.isTerminated(), e.isTerminating(),e.getQueue().size());
        logger.error("task reject!!! {}",msg);
        throw new RejectedExecutionException(msg);
    }
}
