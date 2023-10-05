import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class IVoteServiceCombined implements IVoteService {
    private Question question;
    private HashMap<String, ArrayList<Answer>> multipleChoiceSubmissions;
    private HashMap<String, Answer> singleChoiceSubmissions;
    private HashMap<String, Integer> results;

    public IVoteServiceCombined(Question question) {
        this.question = question;
        this.multipleChoiceSubmissions = new HashMap();
        this.singleChoiceSubmissions = new HashMap();
        this.results = new HashMap();
    }

    public void receiveAnswer(ArrayList<Answer> answers, String identifier) {
        if (this.question.answerList().size() > 1) {
            this.handleMultipleChoice(answers, identifier);
        } else {
            this.handleSingleChoice(answers, identifier);
        }

    }

    private void handleMultipleChoice(ArrayList<Answer> answers, String identifier) {
        HashSet<Answer> selectedOptions = new HashSet();
        ArrayList<Answer> answersNoDuplicates = new ArrayList();
        if (this.multipleChoiceSubmissions.containsKey(identifier)) {
            this.multipleChoiceSubmissions.remove(identifier);
        }

        ArrayList<Answer> possibilities = this.question.answerList();
        Iterator var6 = possibilities.iterator();

        Answer checkMe;
        while(var6.hasNext()) {
            checkMe = (Answer)var6.next();
            this.results.putIfAbsent(checkMe.toString(), 0);
        }

        var6 = answers.iterator();

        while(var6.hasNext()) {
            checkMe = (Answer)var6.next();
            if (!selectedOptions.contains(checkMe)) {
                answersNoDuplicates.add(checkMe);
                selectedOptions.add(checkMe);
            }
        }

        this.multipleChoiceSubmissions.put(identifier, answersNoDuplicates);
    }

    private void handleSingleChoice(ArrayList<Answer> answers, String identifier) {
        if (!answers.isEmpty()) {
            this.singleChoiceSubmissions.put(identifier, (Answer)answers.get(answers.size() - 1));
        }

    }

    public void displayResults() {
        this.results.clear();
        if (this.question.answerList().size() > 1) {
            this.handleMultipleChoiceResults();
        } else {
            this.handleSingleChoiceResults();
        }

        TreeMap<String, Integer> sortedResults = new TreeMap<>(this.results);
        System.out.println("Results for \"" + String.valueOf(this.question) + "\": ");

        ArrayList<Answer> correctAnswers = this.question.answerList(); // Get correct answers

        for (Map.Entry<String, Integer> entry : sortedResults.entrySet()) {
            String answerText = entry.getKey();
            int count = entry.getValue();
            String correctIndicator = correctAnswers.contains(new TextAnswer(answerText)) ? " [CORRECT]" : "";

            System.out.printf("%-25s %d%s%n", answerText + ":", count, correctIndicator);
        }
    }

    private void handleMultipleChoiceResults() {
        Iterator var1 = this.multipleChoiceSubmissions.values().iterator();

        while(var1.hasNext()) {
            ArrayList<Answer> answers = (ArrayList)var1.next();
            Iterator var3 = answers.iterator();

            while(var3.hasNext()) {
                Answer answer = (Answer)var3.next();
                String key = answer.toString();
                this.results.put(key, (Integer)this.results.getOrDefault(key, 0) + 1);
            }
        }

    }

    private void handleSingleChoiceResults() {
        Iterator var1 = this.singleChoiceSubmissions.values().iterator();

        while(var1.hasNext()) {
            Answer answer = (Answer)var1.next();
            String key = answer.toString();
            this.results.put(key, (Integer)this.results.getOrDefault(key, 0) + 1);
        }

    }
}