package netty.simplerpcframework.core.client;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import netty.simplerpcframework.core.client.channel.RPCMessageSenderHandler;
import netty.simplerpcframework.core.registry.zk.ServiceNodeCallBack;

/**
 * @author:ben.gu
 * @Date:2020/1/16 5:59 PM
 */
public class ServiceNodeCallBackImpl implements ServiceNodeCallBack {
    private Logger logger = LoggerFactory.getLogger(getClass());

    private RpcServerLoader rpcServerLoader;

    public ServiceNodeCallBackImpl(RpcServerLoader rpcServerLoader) {
        this.rpcServerLoader = rpcServerLoader;
    }

    @Override
    public void callBack(int type, Map<String, List<String>> nodeChildsMap) {
        switch (type) {
        case 2:
            removeHandler(nodeChildsMap);
            break;

        case 1:
            rpcServerLoader.buildMessageHandler(nodeChildsMap);
            break;

        default:
            break;
        }

    }

    private void removeHandler(Map<String, List<String>> nodeChildsMap) {
        Map<String, RPCMessageSenderHandler[]> addressHandlerMap = rpcServerLoader.getAddressHandlerMap();

        Map<String, RPCMessageSenderHandler[]> serviceNameHandlerMap = rpcServerLoader.getServiceNameHandlerMap();

        Map<String, RPCMessageSenderHandler[]> newServiceNameHandlerMap = new HashMap<>(serviceNameHandlerMap);

        List<RPCMessageSenderHandler> handlerNeedRemove = new ArrayList<>();

        //服务下线先把引用去掉
        for (Map.Entry<String, List<String>> entry : nodeChildsMap.entrySet()) {
            if (addressHandlerMap.get(entry.getKey()) != null) {
                handlerNeedRemove = Arrays.asList(addressHandlerMap.get(entry.getKey()));
                break;
            }
        }

        if (!handlerNeedRemove.isEmpty()) {
            for (Map.Entry<String, RPCMessageSenderHandler[]> e : newServiceNameHandlerMap.entrySet()) {
                RPCMessageSenderHandler[] handlers = e.getValue();
                List<RPCMessageSenderHandler> finalHandles = new ArrayList<>();
                for (int i = 0; i < handlers.length; i++) {
                    if (handlerNeedRemove.contains(handlers[i])) {
                        handlers[i] = null;
                    } else {
                        finalHandles.add(handlers[i]);
                    }

                }
                e.setValue(finalHandles.toArray(new RPCMessageSenderHandler[finalHandles.size()]));
            }

            rpcServerLoader.setServiceNameHandlerMap(newServiceNameHandlerMap);

            for (Map.Entry<String, List<String>> entry : nodeChildsMap.entrySet()) {
                if (addressHandlerMap.get(entry.getKey()) != null) {
                    addressHandlerMap.remove(entry.getKey());
                    break;
                }
            }
            //关闭 channel
            handlerNeedRemove.forEach(RPCMessageSenderHandler::close);
        }
    }
}
