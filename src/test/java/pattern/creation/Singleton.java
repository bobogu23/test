package pattern.creation;

/**
 * 单例模式。
 * 除了恶汉模式,懒汉模式之外的模式
 * Initialization On Demand Holder(IoDH).使用静态内部类中的静态变量创建单例对象
 * @author:ben.gu
 * @Date:2020/5/2 5:24 下午
 */
public class Singleton {

    private Singleton(){

    }

    private static class SingletonHolder {
        private static Singleton singleton = new Singleton();
    }

    public static Singleton getInstance(){
        return SingletonHolder.singleton;
    }

    public static void main(String[] args) {
        Singleton s1,s2;
        s1= Singleton.getInstance();
        s2= Singleton.getInstance();
        System.err.println("s1==s2:"+(s1==s2));

    }

}
