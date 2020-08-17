package pattern.creation.simplefactory;

/**
 * @author:ben.gu
 * @Date:2020/8/10 7:39 下午
 */
public class CircleShape implements IShape {
    @Override public void draw() {
        System.out.println("draw circle");
    }

    @Override public void erase() {
        System.out.println("erase circle");
    }
}
