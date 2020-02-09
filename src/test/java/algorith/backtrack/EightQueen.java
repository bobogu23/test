package algorith.backtrack;

/**
 * 8皇后问题。在8x8的棋盘上摆放8个棋子,要求每个棋子所在的行，列，对角线 不能有其他棋子.
 * 使用回溯法,从第一行的第一列开始摆放棋子,判断如果满足行列,对角线没有其他棋子的要求，摆放棋子，进入第二行第一列，开始尝试摆放棋子
 * @author:ben.gu
 * @Date:2020/2/9 10:08 AM
 */
public class EightQueen {

    public static void main(String args[]) {
        EightQueen queen= new EightQueen();
        queen.cal8Queen(0);
    }

    //全局或成员变量,下标表示行,值表示queen存储在哪一列
    private int[] result = new int[8];

    public void cal8Queen(int row) {
        //已经摆放好,打印结果
        if (row == 8) {
            print(result);
            return;
        }

        //每行 8中摆放方法
        for (int column = 0; column < 8; column++) {
            if (isValid(row, column)) {
                result[row] = column;
                //摆放下一行。如果
                cal8Queen(row + 1);
            }
        }

    }

    //判断row 行,column 列 摆放是否合适.
    //往前判断（往后还没有棋子）,所在行无须判断，因为每行只放一个棋子。
    //逐行往上一行判断。左对角线column坐标 column-1。右对角线,column坐标,column+1
    private boolean isValid(int row, int column) {
        int left = column - 1;
        int right = column + 1;
        for (int i = row - 1; i >= 0; i--) {
            //所在列已有棋子
            if (result[i] == column) {
                return false;
            }
            if (left >= 0) { //左对角线
                if (result[i] == left) {
                    return false;
                }
            }
            if (right < 8) { //右对角线
                if (result[i] == right) {
                    return false;
                }
            }
            left--;
            right++;
        }
        return true;
    }

    private void print(int[] result) {
        //行
        for (int row = 0; row < result.length; row++) {
            //列
            for (int column = 0; column < result.length; column++) {
                if (result[row] == column) {
                    System.err.print("Q ");
                } else {
                    System.err.print("* ");
                }
            }
            //打印完一行,换行
            System.err.println();
        }
        //打印完一遍空一行
        System.err.println();

    }

}