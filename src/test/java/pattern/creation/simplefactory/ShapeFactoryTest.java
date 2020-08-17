package pattern.creation.simplefactory;

/**
 * @author:ben.gu
 * @Date:2020/8/10 7:51 下午
 */
public class ShapeFactoryTest {

    public static void main(String[] args) {
        IShape shape = ShapeFactory.getShape("circle");
        shape.draw();
        shape.erase();
    }
}
