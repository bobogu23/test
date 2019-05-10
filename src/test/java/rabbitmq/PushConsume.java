package rabbitmq;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author:ben.gu
 * @Date:2019/5/9 2:58 PM
 */
public class PushConsume {
    private static final String vhost = "/";

    private static final String queue_name = "queue-test-001";
    private static final String routing_key = "rk-001";

    public static void main(String args[]) throws InterruptedException {
        sendMsg();
        consume();

        Thread.sleep(5000);
    }

    public static Connection getRabbitConn() {
        ConnectionFactory factory = new ConnectionFactory();
        // 连接格式：amqp://userName:password@hostName:portNumber/virtualHost
        String uri = String.format("amqp://%s:%s@%s:%d%s", "guest", "guest", "192.168.3.161", 5672, vhost);
        Connection conn = null;
        try {
            factory.setUri(uri);
            factory.setVirtualHost(vhost);
            conn = factory.newConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static void sendMsg() {
        Connection rabbitConn = getRabbitConn();

        Channel channel = null;
        try {
            channel = rabbitConn.createChannel();
            channel.queueDeclare(queue_name, false, false, false, null);
            String content = "hello11";
            channel.basicPublish("", queue_name, null, content.getBytes("utf-8"));

            System.err.println("发送消息：" + content);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if(channel != null){
                    channel.close();
                }
                rabbitConn.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
        }
    }

    public static void consume() {
        Connection rabbitConn = getRabbitConn();

        try {
            final Channel channel = rabbitConn.createChannel();
            channel.queueDeclare(queue_name, false, false, false, null);

            channel.basicConsume(queue_name, false, "", new DefaultConsumer(channel) {

                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
                        byte[] body) throws IOException {
                    System.err.println("接收到消息:" + new String(body, "utf-8"));

                    System.err.println("routingKey:" + envelope.getRoutingKey());
                    System.err.println("contentType:" + properties.getContentType());
                    channel.basicAck(envelope.getDeliveryTag(), false);

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
//            try {
//                rabbitConn.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        }
    }


}
