import java.util.*;


public class Main {

	static ArrayList<User> users = new ArrayList<>();
	static ArrayList<Quiz> quizzes = new ArrayList<>();
	static ArrayList<Subject> subjects = new ArrayList<>();
	static Scanner sc = new Scanner(System.in);

	// "Last name, First name M.I."  (letters, spaces, hyphens, apostrophes; initial is one letter, dot optional)
	static final String NAME_FORMAT =
			"[A-Za-z][A-Za-z'-]*( [A-Za-z][A-Za-z'-]*)*, ?[A-Za-z][A-Za-z'-]*( [A-Za-z][A-Za-z'-]*)* [A-Za-z]\\.?";

	// ---------- Start ----------
	public static void main(String[] args) {
		//sample accounts
		users.add(new User("joshua", "26-3088", "admin123", "ADMIN"));
		users.add(new User("kent", "26-3089", "instructor123", "INSTRUCTOR"));
		users.add(new Student("racman", "25-3098", "student123", "STUDENT", "BSIT", 2, 'D'));

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


	// checks if user is real/registered
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
			System.out.println("1. Add/Remove user");
			System.out.println("2. Create/Assign subject");
			System.out.println("3. Approve quiz");
			System.out.println("4. View users");
			System.out.println("5. Logout");
			System.out.print("Select task: ");
			String choice = sc.nextLine().trim();

			switch (choice) {
			case "1" -> manageUsers(currentAdmin);
			case "2" -> manageSubjects();
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

	// Add/Remove user: pick which one to do
	static void manageUsers(User currentAdmin) {
		System.out.println("\n--- ADD/REMOVE USER ---");
		System.out.println("1. Add user");
		System.out.println("2. Remove user");
		System.out.println("3. Back");
		int choice = readInt("Select: ", 1, 3);

		if (choice == 1) addUser();
		else if (choice == 2) removeUser(currentAdmin);
	}

	// Create/Assign subject: pick what to do
	static void manageSubjects() {
		System.out.println("\n--- CREATE/ASSIGN SUBJECT ---");
		System.out.println("1. Create subject and assign to an instructor");
		System.out.println("2. Assign existing subject to an instructor");
		System.out.println("3. Assign an instructor's subject to a block");
		System.out.println("4. Back");
		int choice = readInt("Select: ", 1, 4);

		if (choice == 1) createSubject();
		else if (choice == 2) assignSubject();
		else if (choice == 3) assignSubjectToBlock();
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

		// show only the subjects assigned to this instructor
		ArrayList<Subject> mine = new ArrayList<>();
		for (Subject sub : subjects) {
			if (sub.instructor == instructor) mine.add(sub);
		}

		if (mine.isEmpty()) {
			System.out.println("You have no assigned subjects yet. Ask an admin to assign one to you.");
			return;
		}

		System.out.println("Your assigned subjects:");
		for (int i = 0; i < mine.size(); i++) {
			System.out.println("   " + (i + 1) + ". " + mine.get(i).name);
		}
		int pick = readInt("Pick a subject (1-" + mine.size() + "): ", 1, mine.size());
		String subject = mine.get(pick - 1).name;
		System.out.println("Subject: " + subject);

		// quiz names must be unique because they are used to identify quizzes
		String name;
		while (true) {
			name = readNonEmpty("Enter quiz name: ");
			if (findQuiz(name) == null) break;
			System.out.println("A quiz with that name already exists. Choose another name.");
		}

		int count = readInt("Number of questions (1-20): ", 1, 20);
		int timeLimit = readInt("Time limit in minutes (1-180): ", 1, 180);

		Quiz quiz = new Quiz(subject, name, instructor, timeLimit);

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
		System.out.println("\nQuiz \"" + quiz.name + "\" created. Status: PENDING - waiting for admin approval.");
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
				Quiz q = findQuiz(g.quizName);
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

		System.out.print("Enter quiz name to review (blank to cancel): ");
		String input = sc.nextLine().trim();
		if (input.isEmpty()) {
			System.out.println("Cancelled.");
			return;
		}

		Quiz target = null;
		for (Quiz q : pending) {
			if (q.name.equalsIgnoreCase(input)) {
				target = q;
				break;
			}
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



	// Create a new subject and assign it to an instructor
	static void createSubject() {
		System.out.println("\n--- CREATE SUBJECT ---");

		String name;
		while (true) {
			name = readNonEmpty("Enter subject name: ");
			if (findSubject(name) == null) break;
			System.out.println("That subject already exists. Use \"Assign existing subject\" to give it to another instructor.");
		}

		Subject subject = new Subject(name, null);

		if (hasInstructor()) {
			User chosen = pickInstructor();
			if (chosen == null) {
				System.out.println("No instructor selected. Subject created without an instructor.");
			} else {
				subject.instructor = chosen;
				System.out.println("Assigned to " + chosen.name);
			}
		} else {
			System.out.println("No instructors registered yet. Subject created without an instructor.");
		}

		subjects.add(subject);
		System.out.println("Subject created.");
	}

	// Assign an existing subject to an instructor.
	// Several instructors can teach the same subject; each one gets their own entry.
	static void assignSubject() {
		System.out.println("\n--- ASSIGN SUBJECT TO INSTRUCTOR ---");

		if (subjects.isEmpty()) {
			System.out.println("No subjects yet. Create one first.");
			return;
		}
		if (!hasInstructor()) {
			System.out.println("No instructors registered.");
			return;
		}

		// list each subject name once
		ArrayList<String> names = new ArrayList<>();
		for (Subject sub : subjects) {
			boolean listed = false;
			for (String n : names) {
				if (n.equalsIgnoreCase(sub.name)) listed = true;
			}
			if (!listed) names.add(sub.name);
		}
		for (int i = 0; i < names.size(); i++) {
			System.out.println((i + 1) + ". " + names.get(i));
		}
		String subjectName = names.get(readInt("Pick a subject (1-" + names.size() + "): ", 1, names.size()) - 1);

		User chosen = pickInstructor();
		if (chosen == null) {
			System.out.println("Cancelled.");
			return;
		}

		if (findSubject(subjectName, chosen) != null) {
			System.out.println(chosen.name + " already has " + subjectName + ".");
			return;
		}

		// reuse the entry if the subject has no instructor yet, otherwise add a new one
		Subject unassigned = findSubject(subjectName, null);
		if (unassigned != null) {
			unassigned.instructor = chosen;
		} else {
			subjects.add(new Subject(subjectName, chosen));
		}
		System.out.println("Assigned " + subjectName + " to " + chosen.name);
	}

	// Assign one instructor's subject to a block, so that block only gets that instructor's quizzes
	static void assignSubjectToBlock() {
		System.out.println("\n--- ASSIGN INSTRUCTOR'S SUBJECT TO BLOCK ---");

		if (!hasInstructor()) {
			System.out.println("No instructors registered.");
			return;
		}

		User instructor = pickInstructor();
		if (instructor == null) {
			System.out.println("Cancelled.");
			return;
		}

		ArrayList<Subject> theirs = new ArrayList<>();
		for (Subject sub : subjects) {
			if (sub.instructor == instructor) theirs.add(sub);
		}
		if (theirs.isEmpty()) {
			System.out.println(instructor.name + " has no assigned subjects yet.");
			return;
		}

		System.out.println("\nSubjects of " + instructor.name + ":");
		for (int i = 0; i < theirs.size(); i++) {
			System.out.print((i + 1) + ". ");
			theirs.get(i).subjectInfo();
		}
		Subject subject = theirs.get(readInt("Pick a subject (1-" + theirs.size() + "): ", 1, theirs.size()) - 1);

		String course;
		while (true) {
			System.out.print("Enter course: ");
			course = sc.nextLine().trim().toUpperCase();
			if (!course.isEmpty()) break;
			System.out.println("Course can't be empty.");
		}
		int year = readInt("Enter year (1-4): ", 1, 4);
		char block;
		/*
		while (true) {
			System.out.print("Enter block (single letter): ");
			String b = sc.nextLine().trim().toUpperCase();
			if (b.length() == 1 && Character.isLetter(b.charAt(0))) {
				block = b.charAt(0);
				break;
			}
		*/
			System.out.println("Block must be a single letter.");
		}

		String label = "BSIT" + " " + year + "-" + block;
		if (subject.blocks.contains(label)) {
			System.out.println(subject.name + " (" + instructor.name + ") is already assigned to " + label + ".");
		} else {
			subject.blocks.add(label);
			System.out.println(subject.name + " (" + instructor.name + ") assigned to block " + label
					+ ". Only this instructor's " + subject.name + " quizzes will reach that block.");
		}
	}

	static boolean hasInstructor() {
		for (User u : users) {
			if (u.role.equals("INSTRUCTOR")) return true;
		}
		return false;
	}

	// Lists instructors and returns the chosen one (null if cancelled/invalid)
	static User pickInstructor() {
		ArrayList<User> instructors = new ArrayList<>();
		for (User u : users) {
			if (u.role.equals("INSTRUCTOR")) instructors.add(u);
		}
		for (int i = 0; i < instructors.size(); i++) {
			System.out.println((i + 1) + ". " + instructors.get(i).name + " (" + instructors.get(i).id + ")");
		}
		System.out.print("Pick an instructor (blank to cancel): ");
		String input = sc.nextLine().trim();
		if (input.isEmpty()) return null;
		try {
			int n = Integer.parseInt(input);
			if (n >= 1 && n <= instructors.size()) return instructors.get(n - 1);
		} catch (NumberFormatException e) {
			// fall through
		}
		System.out.println("Invalid selection.");
		return null;
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
			if (q.status.equals(Quiz.APPROVED) && quizReachesStudent(q, student)) {
				available.add(q);
				QuizGrade g = findGrade(student, q.name);
				String state = (g == null) ? "Not taken" : "Taken - " + g.Grade + "/" + g.total;
				System.out.println(q.subject + " - " + q.name
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

		System.out.print("Enter quiz name to take (blank to go back): ");
		String input = sc.nextLine().trim();
		if (input.isEmpty()) return;

		Quiz target = null;
		for (Quiz q : available) {
			if (q.name.equalsIgnoreCase(input)) {
				target = q;
				break;
			}
		}

		if (target == null) {
			System.out.println("Quiz not found.");
			return;
		}
		if (findGrade(student, target.name) != null) {
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
		student.addQuizGrade(new QuizGrade(quiz.subject, quiz.name, score, total));
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

		// role (INSTRUCTOR / STUDENT only)
		String role;
		while (true) {
			System.out.print("Enter role (Instructor/Student): ");
			role = sc.nextLine().trim().toUpperCase();
			if (role.equals("INSTRUCTOR") || role.equals("STUDENT")) {
				break;
			}
			if (role.equals("ADMIN")) {
				System.out.println("Admin accounts can't be created.");
				continue;
			}
			System.out.println("Invalid role.");
		}

		// name: one input in the format "Last name, First name M.I."
		String name;
		while (true) {
			System.out.print("Enter name (Last name, First name M.I. - e.g. Dela Cruz, Juan P.): ");
			String input = sc.nextLine().trim();
			if (input.matches(NAME_FORMAT)) {
				name = formatName(input);
				break;
			}
			System.out.println("Invalid name format. Use: Last name, First name M.I. (example: Dela Cruz, Juan P.)");
		}

		// password
		String password;
		while (true) {
			System.out.print("Enter password: ");
			password = sc.nextLine();
			if (password.length() >= 6) break;
			System.out.println("Password must be at least 6 characters.");
		}

		// id (format: 26-3088, must be unique)
		String newId;
		while (true) {
			System.out.print("Enter user ID (format 26-3088): ");
			newId = sc.nextLine().trim();
			if (!newId.matches("\\d{2}-\\d{4}")) {
				System.out.println("Invalid ID format. Use 2 digits, a dash, then 4 digits (example: 26-3088).");
				continue;
			}
			if (idExists(newId)) {
				System.out.println("That ID is already taken.");
				continue;
			}
			break;
		}

		if (role.equals("STUDENT")) {
			/*
			String course;
			while (true) {
				System.out.print("Enter course: ");
				course = sc.nextLine().trim().toUpperCase();
				if (!course.isEmpty()) break;
				System.out.println("Course can't be empty.");
			}
			*/

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

			users.add(new Student(name, newId, password, role, "BSIT", year, block));
		} else {
			users.add(new User(name, newId, password, role));
		}

		System.out.println("User added. ID: " + newId);
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
			// subjects they handled become unassigned
			Iterator<Subject> it = subjects.iterator();
			while (it.hasNext()) {
				Subject sub = it.next();
				if (sub.instructor != removed) continue;

				boolean sharedWithOthers = false;
				for (Subject other : subjects) {
					if (other != sub && other.name.equalsIgnoreCase(sub.name)) sharedWithOthers = true;
				}
				if (sharedWithOthers) {
					it.remove();              // another instructor still teaches it
				} else {
					sub.instructor = null;    // keep the subject, just unassigned
					sub.blocks.clear();
				}
			}
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

			// instructors also show their subjects and the blocks for each
			if (u.role.equals("INSTRUCTOR")) {
				boolean any = false;
				for (Subject sub : subjects) {
					if (sub.instructor != u) continue;
					any = true;
					System.out.println("      Subject: " + sub.name
							+ " | Blocks: " + (sub.blocks.isEmpty() ? "(none)" : String.join(", ", sub.blocks)));
				}
				if (!any) System.out.println("      Subject: (none assigned)");
			}
		}
	}

	// Makes the first letter of every word uppercase (keeps the rest as typed)
	static String capitalizeWords(String text) {
		String[] words = text.split(" ");
		StringBuilder sb = new StringBuilder();
		for (String w : words) {
			if (w.isEmpty()) continue;
			if (sb.length() > 0) sb.append(' ');
			sb.append(Character.toUpperCase(w.charAt(0))).append(w.substring(1));
		}
		return sb.toString();
	}

	// Cleans a valid name into "Last, First M."
	static String formatName(String input) {
		int comma = input.indexOf(',');
		String last = capitalizeWords(input.substring(0, comma).trim());
		String rest = input.substring(comma + 1).trim();
		int sp = rest.lastIndexOf(' ');
		String first = capitalizeWords(rest.substring(0, sp).trim());
		char initial = Character.toUpperCase(rest.charAt(sp + 1));
		return last + ", " + first + " " + initial + ".";
	}

	// true if a user already has this ID
	static boolean idExists(String id) {
		for (User u : users) {
			if (u.id.equals(id)) return true;
		}
		return false;
	}



	// =====================================================
	//                       HELPERS
	// =====================================================

	static Quiz findQuiz(String name) {
		for (Quiz q : quizzes) {
			if (q.name.equalsIgnoreCase(name)) return q;
		}
		return null;
	}

	// The entry for this subject taught by this instructor (instructor may be null for "unassigned")
	static Subject findSubject(String name, User instructor) {
		for (Subject sub : subjects) {
			if (sub.name.equalsIgnoreCase(name) && sub.instructor == instructor) return sub;
		}
		return null;
	}

	// A student only sees quizzes whose instructor teaches that subject to the student's block
	static boolean quizReachesStudent(Quiz quiz, Student student) {
		String label = student.course + " " + student.Year + "-" + student.Block;
		for (Subject sub : subjects) {
			if (sub.instructor == quiz.instructor
					&& sub.name.equalsIgnoreCase(quiz.subject)
					&& sub.blocks.contains(label)) {
				return true;
			}
		}
		return false;
	}

	static Subject findSubject(String name) {
		for (Subject sub : subjects) {
			if (sub.name.equalsIgnoreCase(name)) return sub;
		}
		return null;
	}

	// Returns the student's grade for a quiz, or null if they haven't taken it
	static QuizGrade findGrade(Student student, String quizName) {
		for (QuizGrade g : student.grades) {
			if (g.quizName.equalsIgnoreCase(quizName)) return g;
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

	// Asks until the user enters a whole number from min to max
	static int readInt(String prompt, int min, int max) {
		while (true) {
			System.out.print(prompt);
			String input = sc.nextLine().trim();

			if (input.matches("\\d{1,5}")) {
				int n = Integer.parseInt(input);
				if (n >= min && n <= max) return n;
			}
			System.out.println("Enter a number from " + min + " to " + max + ".");
		}
	}

}
