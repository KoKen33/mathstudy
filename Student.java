package officialInternalAssessment;

import java.util.ArrayList;
import java.util.List;

public class Student extends User {

    public static class PracticeAttempt {
        String topic;
        int score;
        int timeTaken;

        public PracticeAttempt(String topic, int score, int timeTaken) {
            this.topic = topic;
            this.score = score;
            this.timeTaken = timeTaken;
        }

        @Override
        public String toString() {
            return topic + "," + score + "," + timeTaken;
        }

        public static PracticeAttempt fromString(String s) {
            String[] p = s.split(",");
            return new PracticeAttempt(p[0], Integer.parseInt(p[1]), Integer.parseInt(p[2]));
        }
    }

    private List<PracticeAttempt> history = new ArrayList<>();

    public Student(String username, String password) {
        super(username, password, "student");
    }

    public List<PracticeAttempt> getHistory() {
        return history;
    }

    public void addAttempt(String topic, int score, int time) {
        history.add(new PracticeAttempt(topic, score, time));
    }

    @Override
    public boolean isStudent() {
        return true;
    }
}
