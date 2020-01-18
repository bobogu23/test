package netty.simplerpcframework.core.registry.zk;

import java.util.List;
import java.util.Map;

/**
 * zk 节点变更回调通知
 * @author:ben.gu
 * @Date:2020/1/16 12:58 PM
 */
public interface ServiceNodeCallBack {

    /**
     * 回调通知
     * @param type 事件类型。1:新增，2:删除
     * @param nodeChildsMap 节点，子节点映射关系.key:node(serviceName),value:child list(service Address).
     * 如果 type ==2,value中的 list 是删除的地址
     */
    void  callBack(int type,Map<String,List<String>> nodeChildsMap);
}
