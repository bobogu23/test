package pattern.visitor;

/**
 * @author:ben.gu
 * @Date:2020/8/20 9:55 上午
 */
public class Student implements Participator{
    private int paperNum;

    private double averageScore;

    private String name;


    public Student(int paperNum, double averageScore) {
        this.paperNum = paperNum;
        this.averageScore = averageScore;
    }

    public Student(int paperNum, double averageScore, String name) {
        this.paperNum = paperNum;
        this.averageScore = averageScore;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPaperNum() {
        return paperNum;
    }

    public double getAverageScore() {
        return averageScore;
    }

    @Override
    public void accept(AwardCheck awardCheck) {
        awardCheck.visit(this);
    }
}
