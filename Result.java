import java.io.Serializable;

// One student's score on one quiz (the student's "take quiz" part will create these).
// Serializable = objects of this class can be saved to a file and loaded back later.
public class Result implements Serializable {
	private static final long serialVersionUID = 1L;

	String studentId;
	int quizId;
	int score;
	int total;

	public Result(String studentId, int quizId, int score, int total) {
		this.studentId = studentId;
		this.quizId = quizId;
		this.score = score;
		this.total = total;
	}
}
