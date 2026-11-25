import java.util.ArrayList;

public class Quiz {
    static final String PENDING = "PENDING";
    static final String APPROVED = "APPROVED";
    static final String REJECTED = "REJECTED";

    static final String MULTIPLE_CHOICE = "MULTIPLE CHOICE";
    static final String TRUE_OR_FALSE = "TRUE OR FALSE";

    String subject;
    String name;
    User instructor;
    String type;
    ArrayList<Question> questions = new ArrayList<>();
    String status = PENDING;
    int takers = 0;   // number of students who finished this quiz
    int timeLimit;    // time limit in minutes

    Quiz(String subject, String name, User instructor, int timeLimit, String type) {
        this.subject = subject;
        this.name = name;
        this.instructor = instructor;
        this.timeLimit = timeLimit;
        this.type = type;
    }

    public void addQuestion(Question q) {
        questions.add(q);
    }

    public void quizInfo() {
        System.out.println(subject + " - " + name
                + " | Type: " + type
                + " | Instructor: " + instructor.name
                + " | Items: " + questions.size()
                + " | Time: " + timeLimit + " min"
                + " | Status: " + status);
    }
}
