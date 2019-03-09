package java_core;

/**
 * @author:ben.gu
 * @Date:2019/2/19 1:03 PM
 */
public final class FinalClass {

    int z =0;
    public B getB(final  int x){
        final int y =100;
        return new B() {
            @Override
            public int addXYZ() {
                return x+y+z;
            }
        };

    }

     interface  B{
        int addXYZ();
    }
}
