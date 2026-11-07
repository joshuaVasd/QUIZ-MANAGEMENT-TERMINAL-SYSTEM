public class Question {
    String text;
    String[] choices;   // always 4 choices
    int correct;        // index 0-3 of the correct choice

    Question(String text, String[] choices, int correct) {
        this.text = text;
        this.choices = choices;
        this.correct = correct;
    }

    // Prints the question with its choices numbered 1-4
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
