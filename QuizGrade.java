public class QuizGrade {
    String quizName;
    int Grade;

    QuizGrade(String quizName, int Grade){
        this.quizName = quizName;
        this.Grade = Grade;
    }

    public void viewQuizGrade(){
        System.out.println(quizName + " : " + Grade);
    }
}

