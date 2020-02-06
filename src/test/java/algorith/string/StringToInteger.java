package algorith.string;

/**
 * @author:ben.gu
 * @Date:2020/2/4 9:37 PM
 */
public class StringToInteger {

    public static void main(String args[]) {
        StringToInteger t = new StringToInteger();
        System.err.println(t.string2Int("4193 with words"));
        ;
        System.err.println(t.string2Int("words and 987"));
        ;
        System.err.println(t.string2Int("-91283472332"));
        ;
        System.err.println(t.string2Int("91283472332"));
        ;
        System.err.println(t.string2Int("-42"));
        ;
    }

    /**
     * int_max : 2^31-1 = 2147483647 = 214748364*10+7
     * int_min : -2^31 = -2147483648
     * @param str
     * @return
     */
    public int string2Int(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }
        int sign = 1, result = 0, index = 0;
        while (index < str.length() && str.charAt(index) == ' ') {
            index++;
        }
        if (index < str.length() && (str.charAt(index) == '-' || str.charAt(index) == '+')) {
            sign = str.charAt(index) == '-' ? -1 : 1;
            index++;
        }
        //traverse string

        while (index < str.length() && str.charAt(index) >= '0' && str.charAt(index) <= '9') {
            char c = str.charAt(index);
            if (result > Integer.MAX_VALUE / 10 || (result == Integer.MAX_VALUE / 10 && c > '7')) {
                if (sign == -1) {
                    return Integer.MIN_VALUE;
                } else {
                    return Integer.MAX_VALUE;
                }
            }
            result = result * 10 + c - '0';
            index++;
        }
        return sign == 1 ? result : -result;
    }
}
