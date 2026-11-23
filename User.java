public class User {
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