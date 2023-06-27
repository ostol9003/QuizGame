package Views;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;


public class HowToPlay extends JDialog {
    String backgroundPath = "src/Resources/howToPlay.png";
    Background myBackground = new Background(backgroundPath, 500, 500);
    JFrame mainFrame;

    public HowToPlay(JFrame mainFrame) {
        this.mainFrame = mainFrame;
        init();
    }

    private void init() {
        setTitle("How to play");
        setSize(500, 500);
        setLocationRelativeTo(null);
        setResizable(false);

        add(myBackground);

        addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
                mainFrame.setVisible(false);
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
}
