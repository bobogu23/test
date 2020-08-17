package pattern.composite;

/**
 * @author:ben.gu
 * @Date:2020/8/15 10:53 下午
 */
public class Client {
    public static void main(String[] args) {
        Folder root = new Folder("根文件夹");
        Folder f1 = new Folder("图像文件夹1");
        Folder f2 = new Folder("文本文件夹2");
        root.addFolder(f1);
        root.addFolder(f2);

        ImageFile imageFile1= new ImageFile("图1");
        ImageFile imageFile2= new ImageFile("图2");
        f1.addImageFile(imageFile1);
        f1.addImageFile(imageFile2);


        TextFile txtFile1= new TextFile("文本1");
        TextFile txtFile2= new TextFile("文本2");
        f2.addTextFile(txtFile1);
        f2.addTextFile(txtFile2);

        root.print();

    }
}
