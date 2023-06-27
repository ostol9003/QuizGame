package Service;

import Model.Question;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static Helpers.Helper.SendToServer;

public class NewGameService {
    JDialog frame;
    JFrame mainFrame;
    private ArrayList<Question> questions;
    private int currentQuestionIndex;
    private int playerScore;

    public NewGameService(JDialog frame, JFrame mainFrame) {
        questions = new ArrayList<>();
        questions = Question.loadQuestions("src/Resources/questions.json");
        currentQuestionIndex = 0;
        playerScore = 0;
        this.frame = frame;
        this.mainFrame = mainFrame;
    }

    public int getPlayerScore() {
        return playerScore;
    }


    /*
       Loading qestion to window ( question text and answers buttons )
       if last answer correct currentQuestionIndex == questions.size --> second option and sending playerName#score to server,
       server know what to do :)
        */

    public void displayNextQuestion(JTextArea questionText, JButton answer1, JButton answer2, JButton answer3, JButton answer4) throws IOException {
        if (currentQuestionIndex < questions.size()) {
            Question currentQuestion = questions.get(currentQuestionIndex);

            HashMap<String, Boolean> answers = currentQuestion.getAnswers();

            ArrayList<String> answerTexts = new ArrayList<>(answers.keySet());  // loading just answers text to list from hashMap

            questionText.setText(currentQuestion.getQuestion());
            answer1.setText(answerTexts.get(0));
            answer2.setText(answerTexts.get(1));
            answer3.setText(answerTexts.get(2));
            answer4.setText(answerTexts.get(3));

            currentQuestionIndex++;
        } else if (currentQuestionIndex == questions.size()) {

            String name = JOptionPane.showInputDialog(frame, "Great! Your score: " + (playerScore), "You won!", JOptionPane.INFORMATION_MESSAGE);

            SendToServer(name + "#" + (playerScore));
            mainFrame.setVisible(true);
            frame.dispose();

        }

    }

    /*
    function to check if you choose correct answer or not.
    if correct pts + 1 if not, player has to write name and send his data to server
     */
    public void checkAnswer(String selectedAnswer, JLabel scoreLabel, JTextArea questionText, JButton answer1, JButton answer2, JButton answer3, JButton answer4) throws IOException {
        Question currentQuestion = questions.get(currentQuestionIndex - 1);
        if (currentQuestion.isCorrectAnswer(selectedAnswer)) {
            playerScore++;
            scoreLabel.setText("Score: " + playerScore);
            displayNextQuestion(questionText, answer1, answer2, answer3, answer4);
        } else if (!currentQuestion.isCorrectAnswer(selectedAnswer)) {
            String name = JOptionPane.showInputDialog(frame, "Game Over! Your score: " + playerScore + ". Write Your name:", "Game Over", JOptionPane.INFORMATION_MESSAGE);
            SendToServer(name + "#" + playerScore);
            frame.dispose();
            mainFrame.setVisible(true);
        }
    }
}
