package pattern.composite.sub;


/**
 * @author:ben.gu
 * @Date:2020/8/16 2:59 下午
 */
public class Client {
    public static void main(String[] args) {
        Component root = new Folder("根文件夹");
        Component f1 = new Folder("图像文件夹1");
        Component f2 = new Folder("文本文件夹2");
        root.add(f1);
        root.add(f2);

        Component imageFile1= new ImageFile("图1");
        Component imageFile2= new ImageFile("图2");
        f1.add(imageFile1);
        f1.add(imageFile2);


        Component txtFile1= new TextFile("文本1");
        Component txtFile2= new TextFile("文本2");
        f2.add(txtFile1);
        f2.add(txtFile2);

        root.print();
    }
}
