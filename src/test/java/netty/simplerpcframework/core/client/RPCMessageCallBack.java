package netty.simplerpcframework.core.client;

import netty.simplerpcframework.model.RPCRequest;
import netty.simplerpcframework.model.RPCResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 请求响应回调
 * @author:ben.gu
 * @Date:2020/1/3 5:27 PM
 */
public class RPCMessageCallBack {
    private Logger logger = LoggerFactory.getLogger(getClass());

    private RPCRequest request;

    private RPCResponse response;

    private Lock lock = new ReentrantLock();

    private Condition condition = lock.newCondition();

    public RPCMessageCallBack(RPCRequest request) {
        this.request = request;
    }

    public RPCRequest getRequest() {
        return request;
    }

    public void setRequest(RPCRequest request) {
        this.request = request;
    }

    public RPCResponse getResponse() {
        return response;
    }

    public void setResponse(RPCResponse response) {
        this.response = response;
    }

    //获取结果
    public Object get() {
        if (this.response != null) {
            return this.response.getResult();
        }

        //等待 1s
        lock.lock();
        try {
            condition.await(1000, TimeUnit.MILLISECONDS);
            if (this.response != null) {
                return this.response.getResult();
            }
            logger.error("get result time out ! message id:{} ", request.getMessageId());
            return null;

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return null;
    }

    //拿到结果，回调
    public void done(RPCResponse response) {
        lock.lock();
        try {
            this.response = response;
            condition.signal();
        } finally {
            lock.unlock();
        }

    }

}
