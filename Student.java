import java.util.ArrayList;
public class Student extends User {
		String course;
		int Year;
		char Block;
		ArrayList<QuizGrade> grades = new ArrayList<>();

		Student(String name, String id, String password, String role, String course, int Year, char Block) {
			super(name, id, password, role);
			this.course = course;
			this.Year = Year;
			this.Block = Block;
		}

		@Override
		public void userInfo() {
			System.out.println("ID: " + id + " | Name: " + name + " | Role: " + role + " | " + course + " " + Year + "-" + Block);
		}

		public void grades(){
			for(QuizGrade q : grades){
                q.viewQuizGrade();
            }
		}

        public void addQuizGrade(QuizGrade grade){
            grades.add(grade);
        }
}