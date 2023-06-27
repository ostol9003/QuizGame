package Views;

import Helpers.Helper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.Arrays;

import static Helpers.Helper.addComponentsToParent;
import static java.lang.System.exit;

public class Menu extends JFrame {
    String backgroundPath = "src/Resources/backgroundMain.jpg";
    MenuBox menuBox = new MenuBox(this);
    Background background = new Background(backgroundPath, 650, 650);

    public Menu(String title) {
        super(title);
        init();
    }

    void init() {
        setSize(650, 650);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());
        background.add(menuBox);
        add(background);

        setVisible(true);
    }


}

class MenuBox extends JPanel {
    JButton newGame, top5, howToPlay, aboutUs, exit;
    JFrame mainFrame;

    MenuBox(JFrame mainFrame) {
        this.mainFrame = mainFrame;
        init();
    }

    void init() {
        setBackground(new Color(0, 0, 0, 0));
        setLayout(new GridLayout(10, 1));
        newGame = new Helper.MyButton("New Game");
        top5 = new Helper.MyButton("Top 5");
        howToPlay = new Helper.MyButton("How to Play");
        aboutUs = new Helper.MyButton("About Us");
        exit = new Helper.MyButton("Exit");

        for (JButton jButton : Arrays.asList(newGame, top5, howToPlay, aboutUs, exit)) {        // loop to add buttons with lambda function
            jButton.addActionListener(e1 -> {
                try {
                    actionListener(e1);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }


        addComponentsToParent(this,
                Box.createVerticalStrut(20),
                newGame,
                Box.createVerticalStrut(20),
                top5,
                Box.createVerticalStrut(20),
                howToPlay,
                Box.createVerticalStrut(20),
                aboutUs,
                Box.createVerticalStrut(20),
                exit);
    }

    public void actionListener(ActionEvent e) throws IOException {      //mainFrame as an argument becouse some of the classes use it
        Object obj = e.getSource();

        if (obj == newGame) {
            NewGame jDialog = new NewGame(mainFrame);
            jDialog.setVisible(true);

        } else if (obj == top5) {
            TopFive jDialog = new TopFive(mainFrame);
            jDialog.setVisible(true);

        } else if (obj == howToPlay) {
            HowToPlay jDialog = new HowToPlay(mainFrame);
            jDialog.setVisible(true);


        } else if (obj == aboutUs) {
            AboutUs jDialog = new AboutUs();
            jDialog.setVisible(true);

        } else if (obj == exit) exit(0);

    }

}
