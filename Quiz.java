import java.io.Serializable;

// A quiz made by an instructor.
// Serializable = objects of this class can be saved to a file and loaded back later.
// (Vince: add the questions, timer, etc. here)
public class Quiz implements Serializable {
	private static final long serialVersionUID = 1L;

	int id;
	String subject;
	String instructorId; // ID of the instructor who created it

	public Quiz(int id, String subject, String instructorId) {
		this.id = id;
		this.subject = subject;
		this.instructorId = instructorId;
	}
}
