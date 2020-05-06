package algorith.leetcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * https://leetcode-cn.com/problems/letter-combinations-of-a-phone-number/
 * @author:ben.gu
 * @Date:2020/5/5 10:50 下午
 */
public class LetterCombinations {

    public List<String> letterCombinations(String digits) {
        List<String> ans = new ArrayList<String>();
        if (digits == null || digits.length() == 0) {
            return ans;
        }

        String[][] digitChar = new String[][] { { "a", "b", "c" }, { "d", "e", "f" }, { "g", "h", "i" },
                { "j", "k", "l" }, { "m", "n", "o" }, { "p", "q", "r", "s" }, { "t", "u", "v" },
                { "w", "x", "y", "z" } };

        //从digit 的第一个字符开始,逐个拼接
        char[] arr = digits.toCharArray();
        ans.add("");
        for(char c :arr){
            ans = addChar(ans,digitChar[c-'2']);
        }

        return ans;

    }

    private List<String> addChar(List<String> ans, String[] strings) {
        List<String> result= new ArrayList<>();
        for(String s: ans){
            for(String a:strings){
                result.add(s+a);
            }
        }
        return result;
    }

}
