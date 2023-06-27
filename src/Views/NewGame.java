package Views;

import Model.Question;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

import static Helpers.Helper.*;

public class NewGame extends JDialog {
    String backgroundPath = "src/Resources/backgroundBlured.jpg";

    Background myBackground = new Background(backgroundPath, 600, 600);
    JTextArea questionText;
    JButton answer1, answer2, answer3, answer4;
    JFrame mainFrame;
    JPanel panel;
    JLabel scoreLabel;
    private ArrayList<Question> questions;
    private int currentQuestionIndex;
    private int playerScore;

    public NewGame(JFrame mainFrame) {
        this.mainFrame = mainFrame;
        init();

    }

    private void init() {

        /*
        a quick check if the server is up, if not, show the player
        that even if he will be the best, he will not go down in history
         */
        Socket socket = ConnectToServer();
        try {
            socket.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,"Couldn't connect to server. Check connection.");
            return;
        }


        setTitle("Let´s play!");
        add(myBackground);
        setSize(600, 600);
        setLocationRelativeTo(null);
        setResizable(false);
        myBackground.setBackground(new Color(0, 0, 0, 0));

        questions = new ArrayList<>();
        questions = Question.loadQuestions("src/Resources/questions.json");
        currentQuestionIndex = 0;
        playerScore = 0;

        panel = new JPanel(new GridBagLayout());

        questionText = new JTextArea("");
        questionText.setLineWrap(true);
        questionText.setSize(300, 100);
        questionText.setWrapStyleWord(true);
        questionText.setFont(new Font("TimesRoman", Font.PLAIN, 15));
        questionText.setBackground(new Color(0, 0, 0, 0));
        questionText.setForeground(Color.white);
        questionText.setEditable(false);

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 0, 10, 0);
        panel.add(questionText, gbc);

        answer1 = new MyButton("");
        answer2 = new MyButton("");
        answer3 = new MyButton("");
        answer4 = new MyButton("");


        answer1.addActionListener(e -> {
            try {
                actionListener(e);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        answer2.addActionListener(e -> {
            try {
                actionListener(e);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        answer3.addActionListener(e -> {
            try {
                actionListener(e);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        answer4.addActionListener(e -> {
            try {
                actionListener(e);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });


        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 5, 10, 5);
        panel.add(answer1, gbc);

        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.insets = new Insets(10, 5, 10, 5);
        panel.add(answer2, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.insets = new Insets(10, 5, 10, 5);
        panel.add(answer3, gbc);

        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.insets = new Insets(10, 5, 10, 5);
        panel.add(answer4, gbc);

        scoreLabel = new JLabel("Score: " + playerScore);
        scoreLabel.setFont(new Font("TimesRoman", Font.PLAIN, 18));
        scoreLabel.setForeground(Color.green);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.insets = new Insets(10, 0, 10, 10);
        panel.add(scoreLabel, gbc);

        panel.setOpaque(false);
        myBackground.setLayout(new GridBagLayout());
        myBackground.add(panel);

        addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
                mainFrame.setVisible(false);
                try {
                    displayNextQuestion();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }

            @Override
            public void windowClosing(WindowEvent e) {
                mainFrame.setVisible(true);
            }

            @Override
            public void windowClosed(WindowEvent e) {
            }

            @Override
            public void windowIconified(WindowEvent e) {
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowActivated(WindowEvent e) {
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            }
        });
    }

    private void actionListener(ActionEvent e) throws IOException {
        Object obj = e.getSource();

        if (obj == answer1) {
            checkAnswer(answer1.getText());
        } else if (obj == answer2) {
            checkAnswer(answer2.getText());
        } else if (obj == answer3) {
            checkAnswer(answer3.getText());
        } else if (obj == answer4) {
            checkAnswer(answer4.getText());
        }
    }

    /*
    Loading qestion to window ( question text and answers buttons )
    if last answer correct currentQuestionIndex == questions.size --> second option and sending playerName#score to server,
    server know what to do :)
     */
    private void displayNextQuestion() throws IOException {
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
            String name = JOptionPane.showInputDialog(this, "Great! Your score: " + (playerScore), "You won!", JOptionPane.INFORMATION_MESSAGE);

            SendToServer(name + "#" + (playerScore));
            mainFrame.setVisible(true);
            dispose();


        }

    }

    /*
    function to check if you choose correct answer or not.
    if correct pts + 1 if not, player has to write name and send his data to server
     */
    private void checkAnswer(String selectedAnswer) throws IOException {
        Question currentQuestion = questions.get(currentQuestionIndex - 1);
        if (currentQuestion.isCorrectAnswer(selectedAnswer)) {
            playerScore++;
            scoreLabel.setText("Score: " + playerScore);
            displayNextQuestion();
        } else if (!currentQuestion.isCorrectAnswer(selectedAnswer)) {
            String name = JOptionPane.showInputDialog(this, "Game Over! Your score: " + playerScore + ". Write Your name:", "Game Over", JOptionPane.INFORMATION_MESSAGE);
            SendToServer(name + "#" + playerScore);
            dispose();
            mainFrame.setVisible(true);

        }


    }
}
