package Views;

import javax.swing.*;
import java.awt.*;

public class AboutUs extends JDialog {
    String backgroundPath = "src/Resources/backgroundAboutUs.jpg";
    Background myBackground = new Background(backgroundPath, 500, 150);
    JTextArea myTextArea;

    public AboutUs() {
        setTitle("About us");
        add(myBackground);
        setSize(500, 150);
        setLocationRelativeTo(null);
        setResizable(false);

        myTextArea = new JTextArea("Authors:\nPaulina Ostolska\nMarcin Ostolski");
        myTextArea.setFont(new Font("TimesRoman", Font.PLAIN, 25));
        myTextArea.setBackground(new Color(0, 0, 0, 0));
        myTextArea.setForeground(Color.white);
        myTextArea.setAlignmentX(CENTER_ALIGNMENT);
        myTextArea.setAlignmentY(CENTER_ALIGNMENT);
        myTextArea.setEditable(false);

        myBackground.add(myTextArea);
    }

}

