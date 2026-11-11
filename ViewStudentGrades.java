// Instructor Dashboard -> option 2: View student grade
public class ViewStudentGrades {

	public static void show(Main.User instructor) {
		System.out.println("\n--- VIEW STUDENT GRADE ---");

		// keep asking until the ID contains numbers only
		String id;
		while (true) {
			System.out.print("Enter student ID: ");
			id = Main.sc.nextLine().trim();
			if (id.matches("\\d+")) break;
			System.out.println("Student ID must contain numbers only.");
		}

		// search the registered students
		Main.Student student = null;
		for (Main.User u : Main.users) {
			if (u instanceof Main.Student && u.id.equals(id)) {
				student = (Main.Student) u;
				break;
			}
		}

		if (student == null) {
			System.out.println("Student not found.");
			return;
		}

		System.out.println("\nGrades of " + student.name + " (" + student.course + " "
		                   + student.Year + "-" + student.Block + ")");

		int count = 0;
		double totalPercent = 0;
		for (Result r : Main.results) {
			if (!r.studentId.equals(student.id)) continue;
			Quiz q = Main.findQuiz(r.quizId);
			if (q == null || !q.instructorId.equals(instructor.id)) continue;

			double percent = r.total == 0 ? 0 : (r.score * 100.0) / r.total;
			System.out.printf("Quiz #%d | %s | %d/%d (%.1f%%)%n",
			                  q.id, q.subject, r.score, r.total, percent);
			totalPercent += percent;
			count++;
		}

		if (count == 0) {
			System.out.println("No grades recorded for this student yet.");
		} else {
			System.out.printf("Average: %.1f%% over %d quiz(zes)%n", totalPercent / count, count);
		}
	}
}
