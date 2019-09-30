package rabbitmq;

import com.google.common.collect.Maps;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * @author:ben.gu
 * @Date:2019/7/31 9:09 PM
 */
public class DeadLetterQueueTest {
    private static final String vhost="test_host";

    public static void main(String args[]) throws IOException, TimeoutException {
        Connection rabbitConn = getRabbitConn();
        Channel channel = rabbitConn.createChannel();
        channel.exchangeDeclare("exchange.dlx", "direct", true);
        channel.exchangeDeclare("exchange.normal","fanout",true);
        Map<String,Object> param = Maps.newHashMap();
        param.put("x-message-ttl",10000);
        param.put("x-dead-letter-exchange","exchange.dlx");
        param.put("x-dead-letter-routing-key","routingkey");

        channel.queueDeclare("queue.normal",true,false,false,param);
        channel.queueBind("queue.normal","exchange.normal","");


        channel.queueDeclare("queue.dlx",true,false,false,null);
        channel.queueBind("queue.dlx","exchange.dlx","routingkey");

        channel.basicPublish("exchange.normal","rk", MessageProperties.PERSISTENT_TEXT_PLAIN,"dx".getBytes());

        channel.close();

        long time =System.currentTimeMillis();
        Channel consumeChannel = rabbitConn.createChannel();

        consumeChannel.queueDeclare("queue.dlx",true,false,false,null);
        consumeChannel.basicConsume("queue.dlx",false,"",new DefaultConsumer(consumeChannel){
            @Override public void handleDelivery(String consumerTag, Envelope envelope,
                    AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.err.println("time cost:"+(System.currentTimeMillis()-time));
                System.err.println("consumerTag:"+consumerTag);
                System.err.println("msg:"+new String(body));
                consumeChannel.basicAck(envelope.getDeliveryTag(),false);
            }
        });


    }


    public static Connection getRabbitConn() {
        ConnectionFactory factory = new ConnectionFactory();
        // 连接格式：amqp://userName:password@hostName:portNumber/virtualHost
        String uri = String.format("amqp://%s:%s@%s:%d/%s", "admin", "123456", "localhost", 5672, vhost);
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

    


}
