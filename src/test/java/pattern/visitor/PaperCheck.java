package pattern.visitor;

/**
 * @author:ben.gu
 * @Date:2020/8/20 9:58 上午
 */
public class PaperCheck implements AwardCheck {
    @Override
    public void visit(Teacher teacher) {
        if (teacher.getPaperNum() > 10) {
            System.out.println(teacher.getName() + ":科研奖");
        }else {
            System.out.println(teacher.getName() + ":无科研奖");
        }

    }

    @Override
    public void visit(Student teacher) {
        if (teacher.getPaperNum() > 2) {
            System.out.println(teacher.getName() + ":科研奖");
        }else {
            System.out.println(teacher.getName() + ":无科研奖");
        }
    }
}
