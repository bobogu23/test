package algorith.leetcode;

import sun.plugin2.gluegen.runtime.CPU;

import java.util.ArrayList;
import java.util.List;

/**
 * https://leetcode-cn.com/problems/generate-parentheses/
 * 数字 n 代表生成括号的对数，请你设计一个函数，用于能够生成所有可能的并且 有效的 括号组合。
 * 输入：n = 3
 * 输出：[
 *        "((()))",
 *        "(()())",
 *        "(())()",
 *        "()(())",
 *        "()()()"
 *      ]
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/generate-parentheses
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * @author:ben.gu
 * @Date:2020/5/10 10:50 下午
 */
public class Parenthenis {
    public static void main(String[] args) {
        Parenthenis p =new Parenthenis();
        List<String> strings = p.generateParenthesis(3);
        System.err.println("list"+strings);
    }

    public List<String> generateParenthesis(int n) {
        List<String> list =new ArrayList<>();
        generate(list,n,0,0,"");
        return list;
    }

    public void generate(List<String> list ,int n,int countLeft,int countRight,String s){
        if(countLeft>n||countRight>n){
            return;
        }
        if(countLeft == n && countRight==n){
            list.add(s);
        }
        if(countLeft >= countRight ){
            generate(list,n, countLeft+1,countRight,s+"(");
            generate(list,n, countLeft,countRight+1,s+")");
        }


    }
}
