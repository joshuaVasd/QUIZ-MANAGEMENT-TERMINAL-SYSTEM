import java.util.ArrayList;

public class Subject {
    String name;
    User instructor;                         // null = not assigned to an instructor yet
    ArrayList<String> blocks = new ArrayList<>();   // e.g. "BSIT 2-D"

    Subject(String name, User instructor) {
        this.name = name;
        this.instructor = instructor;
    }

    public void subjectInfo() {
        System.out.println(name
                + " | Instructor: " + (instructor == null ? "(none)" : instructor.name)
                + " | Blocks: " + (blocks.isEmpty() ? "(none)" : String.join(", ", blocks)));
    }
}
