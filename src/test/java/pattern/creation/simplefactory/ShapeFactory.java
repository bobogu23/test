package pattern.creation.simplefactory;

/**
 * @author:ben.gu
 * @Date:2020/8/10 7:49 下午
 */
public class ShapeFactory {

    public static IShape getShape(String shape){
        if(shape.equalsIgnoreCase("circle")){
            CircleShape shape1 = new CircleShape();
            //初始化圆形

            return shape1;
        }else if(shape.equalsIgnoreCase("square")){
            SquareShape squareShape = new SquareShape();
            //初始化方形
            return squareShape;
        }else if(shape.equalsIgnoreCase("triangle")){
            TriangleShape triangleShape = new TriangleShape();
            //做些初始化操作
            return triangleShape;
        }else {
            throw  new UnSupportedShapeException();
        }
    }
}
