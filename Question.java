public class Question {
    String text;
    String[] choices;   // 4 choices for Multiple Choice, 2 choices (True/False) for True or False
    int correct;        // index of the correct choice (0-3 or 0-1)

    Question(String text, String[] choices, int correct) {
        this.text = text;
        this.choices = choices;
        this.correct = correct;
    }

    // Prints the question with its choices numbered starting from 1
    public void display(int number) {
        System.out.println("\nQ" + number + ". " + text);
        for (int i = 0; i < choices.length; i++) {
            System.out.println("   " + (i + 1) + ". " + choices[i]);
        }
    }

    public boolean isCorrect(int answerIndex) {
        return answerIndex == correct;
    }
}
