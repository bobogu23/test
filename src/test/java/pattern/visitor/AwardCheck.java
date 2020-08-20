package pattern.visitor;

/**
 * @author:ben.gu
 * @Date:2020/8/20 9:52 上午
 */
public interface AwardCheck {

    void visit(Teacher teacher);

    void visit(Student teacher);
}
