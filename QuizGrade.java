public class QuizGrade {
    String quizSubject;
    String quizName;
    int Grade;
    int total;

    QuizGrade(String quizSubject, String quizName, int Grade, int total) {
        this.quizSubject = quizSubject;
        this.quizName = quizName;
        this.Grade = Grade;
        this.total = total;
    }

    public void viewQuizGrade() {
        System.out.println(quizName + " (" + quizSubject + ") : " + Grade + "/" + total);
    }
}
