package pattern;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * @author:ben.gu
 * @Date:2020/8/8 8:08 下午
 */
public class XmlUtil {

    //定义解析器和文档对象
    private SAXReader saxReader;

    private Document document;

    public XmlUtil(String path) {
        //获取解析器
        saxReader = new SAXReader();
        try {
            //获取文档对象
            document = saxReader.read(path);
        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
         * 根据节点名称获取内容
         * @param name 节点名称
         * @return 节点内容
         */
    public String getElementText(String name) {
        //定位根节点
        Element root = document.getRootElement();
        //根据名称定位节点
        Element element = root.element(name);
        //返回节点内容
        return element.getText();
    }

    public  Object getBean(){
        String clazz = getElementText("class");
        try {
            return Class.forName(clazz).newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
