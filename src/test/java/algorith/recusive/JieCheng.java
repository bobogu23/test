package algorith.recusive;

/**
 * 阶乘 n!
 * n=1*2*3...*(n-1)*n
 * 0的阶乘为1
 * @author:ben.gu
 * @Date:2020/2/1 11:05 PM
 */
public class JieCheng {

    public static void main(String args[]) {
       int n =0 ;
        System.err.println("n="+n+",result="+jieCheng(n));
        n =1 ;
        System.err.println("n="+n+",result="+jieCheng(n));
        n =2 ;
        System.err.println("n="+n+",result="+jieCheng(n));
        n =3 ;
        System.err.println("n="+n+",result="+jieCheng(n));
        n =4 ;
        System.err.println("n="+n+",result="+jieCheng(n));
    }

    public static int jieCheng(int n) {
        if (n == 0) {
            return 1;
        }
        //n!=n*(n-1)
        int n1 = jieCheng(n - 1);
        return n * n1;
    }
}
