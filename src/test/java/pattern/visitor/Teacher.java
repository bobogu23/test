package pattern.visitor;

/**
 * @author:ben.gu
 * @Date:2020/8/20 9:54 上午
 */
public class Teacher implements Participator {
    private int paperNum;

    private double feedBackScore;

    private String name;

    public Teacher(int paperNum, double feedBackScore) {
        this.paperNum = paperNum;
        this.feedBackScore = feedBackScore;
    }

    public Teacher(int paperNum, double feedBackScore, String name) {
        this.paperNum = paperNum;
        this.feedBackScore = feedBackScore;
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

    public double getFeedBackScore() {
        return feedBackScore;
    }

    @Override
    public void accept(AwardCheck awardCheck) {
        awardCheck.visit(this);
    }
}
