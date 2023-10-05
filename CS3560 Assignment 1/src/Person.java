import java.util.ArrayList;

public abstract class Person {
    public Person() {
    }

    abstract void submitAnswer(ArrayList<Answer> var1, IVoteService var2);

    abstract String identifier();
}