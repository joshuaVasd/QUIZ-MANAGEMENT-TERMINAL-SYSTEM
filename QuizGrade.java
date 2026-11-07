public class QuizGrade {
    String quizSubject;
    String quizName;
    int Grade;

    QuizGrade(String quizSubject, String quizName, int Grade){
        this.quizSubject = quizSubject;
        this.quizName = quizName;
        this.Grade = Grade;
    }

    public void viewQuizGrade(){
        System.out.println(quizName + " : " + Grade);
    }
}

