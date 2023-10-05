import java.util.ArrayList;

public class Question {
    protected String question;
    protected ArrayList<Answer> answerList;

    public Question() {
        this.question = "";
        this.answerList = new ArrayList();
    }

    public Question(String question, ArrayList<Answer> answerList) {
        this.question = question;
        this.answerList = new ArrayList();

        for(int i = 0; i < answerList.size(); ++i) {
            this.answerList.add((Answer)answerList.get(i));
        }

    }

    public ArrayList<Answer> answerList() {
        return this.answerList;
    }

    public void setAnswers(ArrayList<Answer> answerList) {
        for(int i = 0; i < answerList.size(); ++i) {
            this.answerList.add((Answer)answerList.get(i));
        }

    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String question() {
        return this.question;
    }

    public void displayAnswers() {
        for(int i = 0; i < this.answerList.size(); ++i) {
            ((Answer)this.answerList.get(i)).displayAnswer();
        }

    }

    public String toString() {
        return this.question;
    }
}
