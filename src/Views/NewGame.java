package Views;

import Model.Question;
import Service.NewGameService;

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
    public JTextArea questionText;
    JButton answer1, answer2, answer3, answer4;
    JFrame mainFrame;
    JPanel panel;
    JLabel scoreLabel;
    NewGameService service;



    public NewGame(JFrame mainFrame) {
        this.mainFrame = mainFrame;
        init();

    }

    private void init() {
        service = new NewGameService(this,mainFrame);
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

        scoreLabel = new JLabel("Score: " + service.getPlayerScore());
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
                    service.displayNextQuestion(questionText,answer1,answer2,answer3,answer4);
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
            service.checkAnswer(answer1.getText(),scoreLabel,questionText,answer1,answer2,answer3,answer4);
        } else if (obj == answer2) {
            service.checkAnswer(answer2.getText(),scoreLabel,questionText,answer1,answer2,answer3,answer4);
        } else if (obj == answer3) {
            service.checkAnswer(answer3.getText(),scoreLabel,questionText,answer1,answer2,answer3,answer4);
        } else if (obj == answer4) {
            service.checkAnswer(answer4.getText(),scoreLabel,questionText,answer1,answer2,answer3,answer4);
        }
    }
}
