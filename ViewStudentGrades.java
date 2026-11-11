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

			r.display(q);                    // the output is printed by Result.display()
			totalPercent += r.getPercent();
			count++;
		}

		if (count == 0) {
			System.out.println("No grades recorded for this student yet.");
		} else {
			System.out.printf("Average: %.1f%% over %d quiz(zes)%n", totalPercent / count, count);
		}
	}
}