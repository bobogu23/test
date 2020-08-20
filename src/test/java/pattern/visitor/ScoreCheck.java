package pattern.visitor;

/**
 * @author:ben.gu
 * @Date:2020/8/20 9:58 上午
 */
public class ScoreCheck implements AwardCheck {

    @Override
    public void visit(Teacher teacher) {
        if (teacher.getFeedBackScore() >= 90) {
            System.out.println(teacher.getName() + ":成绩优秀奖");
        } else {
            System.out.println(teacher.getName() + ":无成绩优秀奖");
        }
    }

    @Override
    public void visit(Student teacher) {
        if (teacher.getAverageScore() >= 90) {
            System.out.println(teacher.getName() + ":成绩优秀奖");
        } else {
            System.out.println(teacher.getName() + ":无成绩优秀奖");
        }
    }
}
