package pattern.creation.simplefactory;

/**
 * @author:ben.gu
 * @Date:2020/8/10 7:39 下午
 */
public class TriangleShape implements IShape {
    @Override public void draw() {
        System.out.println("draw triangle");
    }

    @Override public void erase() {
        System.out.println("erase triangle");
    }
}
