package architect;

/**
 * @author:ben.gu
 * @Date:2020/6/24 10:45 下午
 */
public interface Component {

    String getName();

    default void print(Painter painter) {
        painter.paint(this);
    }
}
