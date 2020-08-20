package pattern.visitor;

/**
 * Sunny软件公司欲为某高校开发一套奖励审批系统，该系统可以实现教师奖励和学生奖励 的审批(Award Check)，
 * 如果教师发表论文数超过10篇或者学生论文超过2篇可以评选科研 奖，如果教师教学反馈分大于等于90分或者学生平均成绩大于等于90分可以评选成绩优 秀奖。
 * 试使用访问者模式设计该系统，以判断候选人集合中的教师或学生是否符合某种 获奖要求。
 * @author:ben.gu
 * @Date:2020/8/20 9:51 上午
 */
public class Client {

    public static void main(String[] args) {
        Student st =new Student(1,80,"学生1");
        Student st1 =new Student(3,90,"学生2");
        Teacher t1 =new Teacher(1,20,"老师1");
        Teacher t2 =new Teacher(11,80,"老师2");
        AwardCheck sc =new ScoreCheck();
        AwardCheck pc =new PaperCheck();

        st.accept(sc);
        st.accept(pc);

        st1.accept(sc);
        st1.accept(pc);

        t1.accept(sc);
        t1.accept(pc);

        t2.accept(sc);
        t2.accept(pc);
    }

}
