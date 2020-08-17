package pattern.composite;

import java.util.ArrayList;
import java.util.List;

/**
 * @author:ben.gu
 * @Date:2020/8/15 10:44 下午
 */
public class Folder {
    private String name;
    private List<Folder> folders =new ArrayList<>();
    private List<ImageFile> imageFiles= new ArrayList<>();
    private List<TextFile> txtFiles= new ArrayList<>();

    public Folder(String name) {
        this.name = name;
    }

    public void addFolder(Folder f){
        folders.add(f);
    }
    public void addImageFile(ImageFile f){
        imageFiles.add(f);
    }
    public void addTextFile(TextFile f){
        txtFiles.add(f);
    }

    public void print(){
        System.out.println("文件夹:"+name);
        for(ImageFile imageFile:imageFiles){
            imageFile.print();
        }
        for(TextFile textFile:txtFiles){
            textFile.print();
        }
        for(Folder folder:folders){
            folder.print();
        }
    }
}
