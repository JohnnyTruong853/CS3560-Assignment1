import java.util.ArrayList;

public class Student extends Person {
    String studentID;

    public Student(String studentID) {
        this.studentID = studentID;
    }

    public void submitAnswer(ArrayList<Answer> answers, IVoteService iVoteService) {
        iVoteService.receiveAnswer(answers, this.studentID);
    }

    public String identifier() {
        return this.studentID;
    }

    public String toString() {
        return this.studentID;
    }

    public boolean equals(Object other) {
        return other.toString().equals(this.studentID);
    }
}