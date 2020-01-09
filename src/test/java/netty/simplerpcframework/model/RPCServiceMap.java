package netty.simplerpcframework.model;

import java.util.Map;

/**
 * @author:ben.gu
 * @Date:2020/1/7 9:35 AM
 */
public class RPCServiceMap {
    private Map<String, Object> serviceNameValue;

    public Map<String, Object> getServiceNameValue() {
        return serviceNameValue;
    }

    public void setServiceNameValue(Map<String, Object> serviceNameValue) {
        this.serviceNameValue = serviceNameValue;
    }
}
