package pattern.creation.simplefactory;

/**
 * @author:ben.gu
 * @Date:2020/8/10 10:00 上午
 */
public class Shape {
    private String shape;

    public Shape(String shape){
        this.shape =shape;
        if(shape.equalsIgnoreCase("circle")){
           //初始化圆形
        }else if(shape.equalsIgnoreCase("square")){
            //初始化方形
        }else if(shape.equalsIgnoreCase("triangle")){
            //初始化三角形
        }else {
            throw  new UnSupportedShapeException();
        }
    }

    public void draw(){
        if(shape.equalsIgnoreCase("circle")){
            //画圆形
            System.out.println("draw circle");
        }else if(shape.equalsIgnoreCase("square")){
            //画方形
            System.out.println("draw square");
        }else if(shape.equalsIgnoreCase("triangle")){
            //画三角形
            System.out.println("draw triangle");

        }

    }


    public void erase(){
        if(shape.equalsIgnoreCase("circle")){
            //擦除圆形
            System.out.println("erase circle");
        }else if(shape.equalsIgnoreCase("square")){
            //擦除方形
            System.out.println("erase square");
        }else if(shape.equalsIgnoreCase("triangle")){
            //擦除三角形
            System.out.println("erase triangle");
        }
    }

    public static void main(String[] args) {
        Shape shape = new Shape("circle");
        shape.draw();
        shape.erase();
    }

}
