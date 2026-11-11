import java.util.*;


public class Main {

	static class User {
		String name;
		String id;
		String password;
		String role; // "ADMIN", "INSTRUCTOR", "STUDENT"

		User(String name, String id, String password, String role) {
			this.name = name;
			this.id = id;
			this.password = password;
			this.role = role;
		}

		public void userInfo() {
			System.out.println("ID: " + id + " | Name: " + name + " | Role: " + role);
		}
	}

	static class Student extends User {
		String course;
		int Year;
		char Block;

		Student(String name, String id, String password, String role, String course, int Year, char Block) {
			super(name, id, password, role);
			this.course = course;
			this.Year = Year;
			this.Block = Block;
		}

		@Override
		public void userInfo() {
			System.out.println("ID: " + id + " | Name: " + name + " | Role: " + role
			                   + " | " + course + " " + Year + "-" + Block);
		}
	}


	static ArrayList<User> users = new ArrayList<>();
	static ArrayList<Quiz> quizzes = new ArrayList<>();
	static ArrayList<Result> results = new ArrayList<>();
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
			System.out.println("\n===== MAIN MENU =====");
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
			instructorDashboard(user);     // pass the logged-in instructor so grades are filtered to their own quizzes
			break;
		case "STUDENT":
			studentDashboard();
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
			case "3" -> System.out.println("(Approve quiz: not implemented yet)");
			case "4" -> viewUsers();
			case "5" -> {
					    System.out.println("Logged out.");
					    return;
					}
					default -> System.out.println("Invalid choice.");
			}
		}
	}

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

			if (choice.equals("4")) return;
			else if (choice.equals("1")) System.out.println("(Create quiz: not implemented yet)");
			else if (choice.equals("2")) ViewStudentGrades.show(instructor);
			else if (choice.equals("3")) System.out.println("(View quiz status: not implemented yet)");
			else System.out.println("Invalid choice.");
		}
	}



	// Finds a quiz by its number (returns null if there is none)
	static Quiz findQuiz(int quizId) {
		for (Quiz q : quizzes) {
			if (q.id == quizId) return q;
		}
		return null;
	}



	// ---------- Student ----------
	static void studentDashboard() {
		while (true) {
			System.out.println("\n===== STUDENT DASHBOARD =====");
			System.out.println("1. View instructor list");
			System.out.println("2. View and take quiz");
			System.out.println("3. View grades");
			System.out.println("4. Logout");
			System.out.print("Select task: ");
			String choice = sc.nextLine().trim();

			if (choice.equals("4")) return;
			else if (choice.equals("1")) System.out.println("(Instructor list: not implemented yet)");
			else if (choice.equals("2")) System.out.println("(Take quiz: not implemented yet)");
			else if (choice.equals("3")) System.out.println("(View grades: not implemented yet)");
			else System.out.println("Invalid choice.");
		}
	}
}