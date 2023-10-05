public class TextAnswer extends Answer {
    private String answer;

    public TextAnswer(String answer) {
        this.answer = answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void displayAnswer() {
        System.out.println(this.answer);
    }

    public String toString() {
        return this.answer;
    }
}
