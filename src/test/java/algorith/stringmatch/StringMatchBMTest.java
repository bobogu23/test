package algorith.stringmatch;

/**
 * 字符串匹配,BM算法.
 *
 * 主串:被匹配的字符串
 * 模式串:要匹配的字符串
 * 如：
 * 字符串A：AAAAAGGGGCCCC
 * 字符串B：AAVVF
 * 我们拿字符串B去匹配字符串A，判断字符串A中是否包含字符串B。那么字符串A 就是主串，字符串B是模式串
 *
 * 最坏字符串规则：
 * 主串与模式串比较,主串每次截取跟模式串等长的字串,子串中"第一个"与模式串不匹配的字符就是坏字符.
 * 匹配规则是字符串从右往左开始匹配。
 * 上面的字符串例子中,坏字符就是 字符串A中的字串 AAAAA 的最后一个A。
 * 然后从右往左找到模式串中出现的第一个坏字符,将模式串中的这个坏字符与主串中的坏字符对齐。
 * 模式串移动位数=坏字符出现的时候子串遍历的位置-坏字符在模式串中出现的位置(从右往左查找)
 * AAAAAGGGGCCCC
 *    AAVVF
 *继续截取主串中的等长的字串,从右往左开始逐个字符匹配.
 *
 * 我们发现模式串往后移动时可能发生后退，往前进。比如:
 * AAAAAGGGGCCCC
 * BAA
 * 坏字符是A，出现的位置是2.移动位置=0+(0-2)=-2
 *
 * 要用到好后缀 字串 AAA 对于字符A 的好后缀有 A AA
 *
 *
 * @author:ben.gu
 * @Date:2020/2/20 9:27 AM
 */
public class StringMatchBMTest {

    public static void main(String args[]) {
        String s1 = "AAAAAGGGGCCCC";
        String sub = "CC";

        System.err.println("position:" + boyerMooreV1(s1, sub));

    }

    public static int boyerMooreV1(String str, String pattern) {
        if (str == null || pattern == null || str.length() < pattern.length()) {
            return -1;
        }

        int stringLen = str.length();
        int patternLen = pattern.length();

        for (int index = patternLen - 1; index < stringLen;) {
            //找到坏字符索引
            int badCharIndex = findBadChar(str, index, pattern);
            //没找到,返回模式串在主串中第一次出现的索引
            if (badCharIndex == -1) {
                return index - patternLen + 1;
            }
            //寻找模式串中坏字符出现的索引
            char badChar = str.charAt(badCharIndex);

            //            int firstBadCharIndex = findIndexFromEnd(badChar, str, index);
            //            int patternBadCharIndex = findIndexFromEnd(badChar, pattern);
            int patternBadCharIndex = findIndexFromEnd(badChar, pattern);

            //如果没有找到坏字符,直接从主串坏字符索引之后开始下一轮匹配
            if (patternBadCharIndex == -1) {
                index = badCharIndex + patternLen;
            } else {
                //模式串中的坏字符对齐主串中的坏字符。此时可能出现后退的情况
                index = badCharIndex + (patternLen - 1 - (index - badCharIndex) - patternBadCharIndex);

                //
            }

        }
        return -1;

    }

    private static int findIndexFromEnd(char badChar, String pattern) {
        for (int i = pattern.length() - 1; i >= 0; i--) {
            if (pattern.charAt(i) == badChar) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 
     * @param badChar
     * @param pattern
     * @param end 遍历结束位置
     * @return
     */
    private static int findIndexFromBegin(char badChar, String pattern, int end) {
        for (int i = 0; i <= end; i++) {
            if (pattern.charAt(i) == badChar) {
                return i;
            }
        }
        return -1;
    }

    /**
     *
     * @param str 主串
     * @param index 主串从右往左开始遍历的索引
     * @param pattern 模式串
     * @return
     */
    private static int findBadChar(String str, int index, String pattern) {
        for (int i = index, j = pattern.length() - 1; i > index - pattern.length(); i--) {
            if (str.charAt(i) != pattern.charAt(j)) {
                return i;
            }
            j--;
        }
        return -1;
    }

}
