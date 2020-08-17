package pattern.creation.simplefactory;

/**
 * @author:ben.gu
 * @Date:2020/8/10 10:11 上午
 */
public class UnSupportedShapeException extends RuntimeException {
    public UnSupportedShapeException() {
        super("不支持的图形");
    }


}
