import java.util.ArrayList;
import java.util.HashSet;
import java.util.UUID;

public class SimulationDriver {
    public SimulationDriver() {
    }

    public static void main(String[] args) {
        demo();
    }

    static HashSet<Student> randStudentList(int number) {
        HashSet<Student> randStudents = new HashSet();

        for(int i = 0; i < number; ++i) {
            String studentID = "";

            for(int j = 0; j < 6; ++j) {
                studentID = studentID + (char)randNum(48, 57);
            }

            if (!randStudents.contains(studentID)) {
                randStudents.add(new Student(studentID));
            } else {
                --i;
            }
        }

        return randStudents;
    }

    static int randNum(int min, int max) {
        return (int)(Math.random() * (double)(max - min + 1) + (double)min);
    }

    static void submitRandAnswers(Question question, ArrayList<Student> students, IVoteService iVoteService) {
        ArrayList<Answer> possibilities = question.answerList();
        ArrayList<Answer> chosen = new ArrayList();

        for(int i = 0; i < students.size(); ++i) {
            int numChosen = randNum(1, possibilities.size());

            for(int j = 0; j < numChosen; ++j) {
                chosen.add((Answer)possibilities.get(randNum(0, possibilities.size() - 1)));
            }

            ((Student)students.get(i)).submitAnswer(chosen, iVoteService);
            chosen.clear();
        }

    }

    static void demo() {
        int numStudents = 75;
        HashSet<Student> students = randStudentList(numStudents);
        System.out.println("Testing Combined Question with " + numStudents + " students.");
        ArrayList<Answer> options = new ArrayList();
        Answer a = new TextAnswer("A. Watching Anime");
        Answer b = new TextAnswer("B. Playing Video Games");
        Answer c = new TextAnswer("C. Reading");
        Answer d = new TextAnswer("D. Working Out");
        Answer e = new TextAnswer("E. Sports");
        Answer f = new TextAnswer("F. None of the above");
        options.add(a);
        options.add(b);
        options.add(c);
        options.add(d);
        options.add(e);
        options.add(f);
        Question mcQuestion = new Question("Which of these activities do you spend most your free time doing? Select all answers that apply.", options);

        IVoteService iVoteService = new IVoteServiceCombined(mcQuestion);
        submitRandAnswers(mcQuestion, new ArrayList(students), iVoteService);
        iVoteService.displayResults();

        System.out.println("\nTesting True/False Question with " + numStudents + " students.");
        Answer one = new TextAnswer("1. True");
        Answer two = new TextAnswer("2. False");
        options.clear();
        options.add(one);
        options.add(two);

        Question scQuestion = new Question("Baldur's Gate 3 is the best game ever made.", options);
        iVoteService = new IVoteServiceCombined(scQuestion);
        students = randStudentList(numStudents);
        submitRandAnswers(scQuestion, new ArrayList(students), iVoteService);

        iVoteService.displayResults();
        iVoteService = new IVoteServiceCombined(scQuestion);
        Student student = new Student(UUID.randomUUID().toString());
        System.out.println("\nStudent " + student.toString() + " resubmitting their answer: ");

        ArrayList<Answer> studentAnswer = new ArrayList();
        studentAnswer.add(new TextAnswer("1. True"));
        student.submitAnswer(studentAnswer, iVoteService);
        iVoteService.displayResults();

        System.out.println("\nStudent " + student.toString() + " changed their mind.");
        studentAnswer.clear();
        studentAnswer.add(new TextAnswer("2. False"));
        student.submitAnswer(studentAnswer, iVoteService);
        iVoteService.displayResults();
    }
}