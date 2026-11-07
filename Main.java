import java.util.*;


public class Main {

	static ArrayList<User> users = new ArrayList<>();
	static ArrayList<Quiz> quizzes = new ArrayList<>();
	static int nextQuizId = 1;
	static Scanner sc = new Scanner(System.in);

	// ---------- Start ----------
	public static void main(String[] args) {
		//sample accounts
		users.add(new User("joshua", "1000", "admin123", "ADMIN"));
		users.add(new User("kent", "1001", "instructor123", "INSTRUCTOR"));
		users.add(new Student("racman", "1002", "student123", "STUDENT", "BSIT", 2, 'D'));

		mainMenu();
	}

	// ---------- Main Menu  ----------
	static void mainMenu() {
		while (true) {
			System.out.println("\n==============================");
			System.out.println("QUIZ MANAGEMENT SYSTEM");
			System.out.println("==============================");
			System.out.println("1. Login");
			System.out.println("2. Change password");
			System.out.println("3. Exit");
			System.out.print("Enter choice: ");
			String choice = sc.nextLine().trim();

			switch (choice) {
			case "1":
				login();
				break;
			case "2":
				changePassword();
				break;
			case "3":
				System.out.println("Goodbye!");
				return;
			default:
				System.out.println("Invalid choice.");
			}
		}
	}



	// ---------- Login ----------
	static void login() {
		int attempts = 0;

		while (true) {
			System.out.println("\n--- LOGIN ---");
			System.out.print("Enter user ID: ");
			String id = sc.nextLine();
			System.out.print("Enter password: ");
			String password = sc.nextLine();

			User user = authenticate(id, password);

			if (user != null) {
				openDashboard(user);
				return;
			}

			attempts++;

			if (attempts == 3) {
				System.out.println("Too many failed attempts. Continue to Change Password.");
				changePassword();
				return;
			}

			System.out.println("Invalid credentials. Attempts left: " + (3 - attempts));
		}
	}

	static User authenticate(String id, String password) {
		for (User u : users) {
			if (u.id.equals(id) && u.password.equals(password)) {
				return u;
			}
		}
		return null;
	}



	// ---------- Change password ----------
	static void changePassword() {
		while (true) {
			System.out.print("\nEnter account user ID: ");
			String id = sc.nextLine();

			User found = null;
			for (User u : users) {
				if (u.id.equals(id)) {
					found = u;
					break;
				}
			}

			if (found == null) {
				System.out.println("User not found");
				continue;
			}

			System.out.print("Enter new password: ");
			String newPassword = sc.nextLine().trim();

			if (newPassword.isEmpty()) {
				System.out.println("Password cannot be empty.");
				continue;
			}
			found.password = newPassword;
			System.out.println("Password Updated");
			return;
		}
	}



	// ---------- Dashboards ----------
	static void openDashboard(User user) {
		switch (user.role) {
		case "ADMIN":
			adminDashboard(user);          // pass the logged-in admin so they can't delete themselves
			break;
		case "INSTRUCTOR":
			instructorDashboard(user);
			break;
		case "STUDENT":
			if (user instanceof Student) {
				studentDashboard((Student) user);
			}
			break;
		}
	}



	// ---------- Admin ----------
	static void adminDashboard(User currentAdmin) {
		while (true) {
			System.out.println("\n===== ADMIN DASHBOARD =====");
			System.out.println("1. Add user");
			System.out.println("2. Remove user");
			System.out.println("3. Approve quiz");
			System.out.println("4. View users");
			System.out.println("5. Logout");
			System.out.print("Select task: ");
			String choice = sc.nextLine().trim();

			switch (choice) {
			case "1" -> addUser();
			case "2" -> removeUser(currentAdmin);
			case "3" -> approveQuiz();
			case "4" -> viewUsers();
			case "5" -> {
					    System.out.println("Logged out.");
					    return;
					}
					default -> System.out.println("Invalid choice.");
			}
		}
	}

	// ---------- instructor ----------
	static void instructorDashboard(User instructor) {
		while (true) {
			System.out.println("\n===== INSTRUCTOR DASHBOARD =====");
			System.out.println("1. Create quiz");
			System.out.println("2. View student grade");
			System.out.println("3. View created quiz status");
			System.out.println("4. Logout");
			System.out.print("Select task: ");
			String choice = sc.nextLine().trim();

			if (choice.equals("4")) {
				System.out.println("Logged out.");
				return;
			}
			else if (choice.equals("1")) createQuiz(instructor);
			else if (choice.equals("2")) viewStudentGrades(instructor);
			else if (choice.equals("3")) viewCreatedQuizStatus(instructor);
			else System.out.println("Invalid choice.");
		}
	}


	// ---------- Student ----------
	static void studentDashboard(Student student) {
		while (true) {
			System.out.println("\n===== STUDENT DASHBOARD =====");
			System.out.println("1. View instructor list");
			System.out.println("2. View and take quiz");
			System.out.println("3. View grades");
			System.out.println("4. Logout");
			System.out.print("Select task: ");
			String choice = sc.nextLine().trim();

			if (choice.equals("4")) {
				System.out.println("Logged out.");
				return;
			}
			else if (choice.equals("1")) viewInstructorList();
			else if (choice.equals("2")) viewAndTakeQuiz(student);
			else if (choice.equals("3")) viewMyGrades(student);
			else System.out.println("Invalid choice.");
		}
	}



	// =====================================================
	//                 INSTRUCTOR FEATURES
	// =====================================================

	// Create a quiz; it starts as PENDING until an admin approves it
	static void createQuiz(User instructor) {
		System.out.println("\n--- CREATE QUIZ ---");

		String subject = readNonEmpty("Enter subject: ");
		String name = readNonEmpty("Enter quiz name: ");
		int count = readInt("Number of questions (1-20): ", 1, 20);
		int timeLimit = readInt("Time limit in minutes (1-180): ", 1, 180);

		Quiz quiz = new Quiz(nextQuizId, subject, name, instructor, timeLimit);

		for (int i = 1; i <= count; i++) {
			System.out.println("\n-- Question " + i + " of " + count + " --");
			String text = readNonEmpty("Question: ");

			String[] choices = new String[4];
			for (int c = 0; c < 4; c++) {
				choices[c] = readNonEmpty("Choice " + (c + 1) + ": ");
			}

			int correct = readInt("Correct answer (1-4): ", 1, 4) - 1;
			quiz.addQuestion(new Question(text, choices, correct));
		}

		quizzes.add(quiz);
		nextQuizId++;
		System.out.println("\nQuiz created (ID " + quiz.id + "). Status: PENDING - waiting for admin approval.");
	}

	// Show every student's grades for quizzes made by this instructor
	static void viewStudentGrades(User instructor) {
		System.out.println("\n--- STUDENT GRADES ---");

		boolean anyStudent = false;
		for (User u : users) {
			if (!(u instanceof Student)) continue;
			Student s = (Student) u;
			anyStudent = true;

			System.out.println();
			s.userInfo();

			boolean hasGrade = false;
			for (QuizGrade g : s.grades) {
				Quiz q = findQuiz(g.quizId);
				if (q != null && q.instructor == instructor) {
					System.out.print("   ");
					g.viewQuizGrade();
					hasGrade = true;
				}
			}
			if (!hasGrade) {
				System.out.println("   No grades for your quizzes yet.");
			}
		}

		if (!anyStudent) {
			System.out.println("No students registered.");
		}
	}

	// Show status of every quiz this instructor created
	static void viewCreatedQuizStatus(User instructor) {
		System.out.println("\n--- MY QUIZZES ---");

		boolean any = false;
		for (Quiz q : quizzes) {
			if (q.instructor == instructor) {
				q.quizInfo();
				if (q.status.equals(Quiz.APPROVED)) {
					System.out.println("     Students who took it: " + q.takers);
				}
				any = true;
			}
		}
		if (!any) {
			System.out.println("You haven't created any quizzes yet.");
		}
	}



	// =====================================================
	//                   ADMIN FEATURES
	// =====================================================

	// Approve or reject pending quizzes
	static void approveQuiz() {
		System.out.println("\n--- APPROVE QUIZ ---");

		ArrayList<Quiz> pending = new ArrayList<>();
		for (Quiz q : quizzes) {
			if (q.status.equals(Quiz.PENDING)) {
				pending.add(q);
				q.quizInfo();
			}
		}

		if (pending.isEmpty()) {
			System.out.println("No quizzes waiting for approval.");
			return;
		}

		System.out.print("Enter quiz ID to review (blank to cancel): ");
		String input = sc.nextLine().trim();
		if (input.isEmpty()) {
			System.out.println("Cancelled.");
			return;
		}

		Quiz target = null;
		try {
			int id = Integer.parseInt(input);
			for (Quiz q : pending) {
				if (q.id == id) {
					target = q;
					break;
				}
			}
		} catch (NumberFormatException e) {
			// handled below
		}

		if (target == null) {
			System.out.println("Quiz not found in the pending list.");
			return;
		}

		// let the admin look through the questions before deciding
		System.out.println("\nReviewing: " + target.subject + " - " + target.name);
		for (int i = 0; i < target.questions.size(); i++) {
			Question q = target.questions.get(i);
			q.display(i + 1);
			System.out.println("   (Correct: " + (q.correct + 1) + ")");
		}

		System.out.println("\n1. Approve");
		System.out.println("2. Reject");
		System.out.println("3. Cancel");
		int decision = readInt("Select: ", 1, 3);

		if (decision == 1) {
			target.status = Quiz.APPROVED;
			System.out.println("Quiz approved.");
		} else if (decision == 2) {
			target.status = Quiz.REJECTED;
			System.out.println("Quiz rejected.");
		} else {
			System.out.println("Cancelled.");
		}
	}



	// =====================================================
	//                  STUDENT FEATURES
	// =====================================================

	static void viewInstructorList() {
		System.out.println("\n--- INSTRUCTORS ---");

		boolean any = false;
		for (User u : users) {
			if (!u.role.equals("INSTRUCTOR")) continue;
			any = true;
			u.userInfo();
		}

		if (!any) {
			System.out.println("No instructors available.");
		}
	}

	// List approved quizzes and let the student take one (once only)
	static void viewAndTakeQuiz(Student student) {
		System.out.println("\n--- AVAILABLE QUIZZES ---");

		ArrayList<Quiz> available = new ArrayList<>();
		for (Quiz q : quizzes) {
			if (q.status.equals(Quiz.APPROVED)) {
				available.add(q);
				QuizGrade g = findGrade(student, q.id);
				String state = (g == null) ? "Not taken" : "Taken - " + g.Grade + "/" + g.total;
				System.out.println("[" + q.id + "] " + q.subject + " - " + q.name
						+ " | Instructor: " + q.instructor.name
						+ " | Items: " + q.questions.size()
						+ " | Time: " + q.timeLimit + " min"
						+ " | " + state);
			}
		}

		if (available.isEmpty()) {
			System.out.println("No quizzes available right now.");
			return;
		}

		System.out.print("Enter quiz ID to take (blank to go back): ");
		String input = sc.nextLine().trim();
		if (input.isEmpty()) return;

		Quiz target = null;
		try {
			int id = Integer.parseInt(input);
			for (Quiz q : available) {
				if (q.id == id) {
					target = q;
					break;
				}
			}
		} catch (NumberFormatException e) {
			// handled below
		}

		if (target == null) {
			System.out.println("Quiz not found.");
			return;
		}
		if (findGrade(student, target.id) != null) {
			System.out.println("You already took this quiz. Retakes are not allowed.");
			return;
		}

		takeQuiz(student, target);
	}

	static void takeQuiz(Student student, Quiz quiz) {
		System.out.println("\n===== " + quiz.subject + " - " + quiz.name + " =====");
		System.out.println("Time limit: " + quiz.timeLimit + " minute(s). The timer starts now.");
		System.out.println("Answer with 1-4 for each question. Unanswered questions count as wrong.");

		long deadline = System.currentTimeMillis() + quiz.timeLimit * 60L * 1000L;
		int score = 0;
		boolean timedOut = false;

		for (int i = 0; i < quiz.questions.size(); i++) {
			long remaining = deadline - System.currentTimeMillis();
			if (remaining <= 0) {
				timedOut = true;
				break;
			}

			Question q = quiz.questions.get(i);
			System.out.println("\n[Time left: " + formatTime(remaining) + "]");
			q.display(i + 1);
			int answer = readInt("Your answer (1-4): ", 1, 4) - 1;

			// an answer submitted after the deadline doesn't count
			if (System.currentTimeMillis() > deadline) {
				timedOut = true;
				break;
			}
			if (q.isCorrect(answer)) score++;
		}

		if (timedOut) {
			System.out.println("\nTime's up! The quiz was submitted automatically.");
		}

		int total = quiz.questions.size();
		student.addQuizGrade(new QuizGrade(quiz.id, quiz.subject, quiz.name, score, total));
		quiz.takers++;

		System.out.println("Quiz finished! Your score: " + score + "/" + total);
	}

	static String formatTime(long millis) {
		long totalSeconds = millis / 1000;
		return String.format("%02d:%02d", totalSeconds / 60, totalSeconds % 60);
	}

	static void viewMyGrades(Student student) {
		System.out.println("\n--- MY GRADES ---");
		if (student.grades.isEmpty()) {
			System.out.println("No grades yet.");
			return;
		}
		student.grades();
	}



	// =====================================================
	//                     USER MANAGEMENT
	// =====================================================

	// Add user: asks once, adds one user, then returns to the dashboard
	static void addUser() {
		System.out.println("\n--- ADD USER ---");

		// role (ADMIN / INSTRUCTOR / STUDENT)
		String role;
		while (true) {
			System.out.print("Enter role (Admin/Instructor/Student): ");
			role = sc.nextLine().trim().toUpperCase();
			if (role.equals("ADMIN") || role.equals("INSTRUCTOR") || role.equals("STUDENT")) {
				break;
			}
			System.out.println("Invalid role.");
		}

		// name
		String name;
		while (true) {
			System.out.print("Enter name: ");
			name = sc.nextLine().trim();
			if(name.matches(".*\\d.*")) {
				System.out.println("Name cannot have numbers");
				name = null;
				continue;
			}
			if (!name.isEmpty()) break;
			System.out.println("Name can't be empty.");
		}

		// password
		String password;
		while (true) {
			System.out.print("Enter password: ");
			password = sc.nextLine();
			if (password.length() >= 6) break;
			System.out.println("Password must be at least 6 characters.");
		}

		String newId = nextUserId();

		if (role.equals("STUDENT")) {
			String course;
			while (true) {
				System.out.print("Enter course: ");
				course = sc.nextLine().trim().toUpperCase();
				if (!course.isEmpty()) break;
				System.out.println("Course can't be empty.");
			}

			int year = readInt("Enter year (1-4): ", 1, 4);

			char block;
			while (true) {
				System.out.print("Enter block (single letter): ");
				String b = sc.nextLine().trim().toUpperCase();
				if (b.length() == 1 && Character.isLetter(b.charAt(0))) {
					block = b.charAt(0);
					break;
				}
				System.out.println("Block must be a single letter.");
			}

			users.add(new Student(name, newId, password, role, course, year, block));
		} else {
			users.add(new User(name, newId, password, role));
		}

		System.out.println("User added. Assigned ID: " + newId);
	}

	// Remove user by ID (with safety checks and confirmation)
	static void removeUser(User currentAdmin) {
		System.out.println("\n--- REMOVE USER ---");
		viewUsers();

		System.out.print("Enter ID of user to remove (blank to cancel): ");
		String id = sc.nextLine().trim();
		if (id.isEmpty()) {
			System.out.println("Cancelled.");
			return;
		}

		User target = null;
		for (User u : users) {
			if (u.id.equals(id)) {
				target = u;
				break;
			}
		}

		if (target == null) {
			System.out.println("User not found.");
			return;
		}
		if (target == currentAdmin) {
			System.out.println("You can't remove the account you're logged in with.");
			return;
		}

		System.out.print("Remove " + target.name + " (" + target.role + ")? (y/n): ");
		if (sc.nextLine().trim().equalsIgnoreCase("y")) {
			users.remove(target);
			// an instructor's quizzes go away with them
			final User removed = target;
			quizzes.removeIf(q -> q.instructor == removed);
			System.out.println("User removed.");
		} else {
			System.out.println("Cancelled.");
		}
	}

	static void viewUsers() {
		System.out.println("\n--- USERS ---");
		if (users.isEmpty()) {
			System.out.println("No users.");
			return;
		}
		for (User u : users) {
			u.userInfo();
		}
	}

	// Next ID = highest existing numeric ID + 1 (safe even after removals / empty list)
	static String nextUserId() {
		int max = 999;
		for (User u : users) {
			try {
				max = Math.max(max, Integer.parseInt(u.id));
			} catch (NumberFormatException ignored) {
			}
		}
		return String.valueOf(max + 1);
	}



	// =====================================================
	//                       HELPERS
	// =====================================================

	static Quiz findQuiz(int id) {
		for (Quiz q : quizzes) {
			if (q.id == id) return q;
		}
		return null;
	}

	// Returns the student's grade for a quiz, or null if they haven't taken it
	static QuizGrade findGrade(Student student, int quizId) {
		for (QuizGrade g : student.grades) {
			if (g.quizId == quizId) return g;
		}
		return null;
	}

	// Re-asks until the input isn't blank
	static String readNonEmpty(String prompt) {
		while (true) {
			System.out.print(prompt);
			String s = sc.nextLine().trim();
			if (!s.isEmpty()) return s;
			System.out.println("This can't be empty.");
		}
	}

	// Reads an int in [min, max] using nextLine(), re-asking until valid
	static int readInt(String prompt, int min, int max) {
		while (true) {
			System.out.print(prompt);
			try {
				int n = Integer.parseInt(sc.nextLine().trim());
				if (n >= min && n <= max) return n;
			} catch (NumberFormatException e) {
				System.out.println("Enter a number within the options");
			}
			System.out.println("Enter a number from " + min + " to " + max + ".");
		}
	}

}
