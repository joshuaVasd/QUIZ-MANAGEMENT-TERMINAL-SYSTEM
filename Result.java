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

	// Score as a percentage (0 if the quiz has no items)
	public double getPercent() {
		return total == 0 ? 0 : (score * 100.0) / total;
	}

	// Shows this result as one line, e.g. "Quiz #1 | Math | 8/10 (80.0%)"
	public void display(Quiz q) {
		System.out.printf("Quiz #%d | %s | %d/%d (%.1f%%)%n",
		                  q.id, q.subject, score, total, getPercent());
	}
}