import java.util.ArrayList;

public class Quiz {
    static final String PENDING = "PENDING";
    static final String APPROVED = "APPROVED";
    static final String REJECTED = "REJECTED";

    int id;
    String subject;
    String name;
    User instructor;
    ArrayList<Question> questions = new ArrayList<>();
    String status = PENDING;
    int takers = 0;   // number of students who finished this quiz
    int timeLimit;    // time limit in minutes

    Quiz(int id, String subject, String name, User instructor, int timeLimit) {
        this.id = id;
        this.subject = subject;
        this.name = name;
        this.instructor = instructor;
        this.timeLimit = timeLimit;
    }

    public void addQuestion(Question q) {
        questions.add(q);
    }

    public void quizInfo() {
        System.out.println("[" + id + "] " + subject + " - " + name
                + " | Instructor: " + instructor.name
                + " | Items: " + questions.size()
                + " | Time: " + timeLimit + " min"
                + " | Status: " + status);
    }
}
