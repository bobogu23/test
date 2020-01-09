package netty.simplerpcframework.test;

import java.util.concurrent.CountDownLatch;

import netty.simplerpcframework.core.client.RPCServiceProxy;
import netty.simplerpcframework.service.HelloWordService;

/**
 * @author:ben.gu
 * @Date:2020/1/7 10:21 PM
 */
public class NettyRPCConcurrentTest {

    public static void main(String args[]) throws InterruptedException {
        RPCServiceProxy serviceProxy = new RPCServiceProxy("localhost:8099");
        int parallel = 1000;
        CountDownLatch signal = new CountDownLatch(1);
        CountDownLatch finish = new CountDownLatch(parallel);

        long startTime = System.currentTimeMillis();
        for (int i = 0; i < parallel; i++) {
            new Thread(new HelloTask(signal, finish, i + "", serviceProxy)).start();
        }
        signal.countDown();
        finish.await();

        System.err.println("rpc invoke time cost:" + (System.currentTimeMillis() - startTime)+" ms");

        serviceProxy.stop();
    }

    static class HelloTask implements Runnable {
        private CountDownLatch signal;

        private CountDownLatch finish;

        private String msg;

        private RPCServiceProxy proxy;

        public HelloTask(CountDownLatch signal, CountDownLatch finish, String msg, RPCServiceProxy proxy) {
            this.signal = signal;
            this.finish = finish;
            this.msg = msg;
            this.proxy = proxy;
        }

        @Override
        public void run() {
            try {
                //等待指令
                signal.await();
                HelloWordService service = this.proxy.getProxy(HelloWordService.class);
                String result = service.hello(msg);
                System.err.println("result:" + result);
                finish.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
