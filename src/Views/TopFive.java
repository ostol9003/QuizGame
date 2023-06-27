package Views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import static Helpers.Helper.ConnectToServer;



/*
implements Runnable because it hangs when opening a window.
window loaded faster than players data. The data is loading in parallel to the opening of the window,
you can see the delay.
*/

public class TopFive extends JDialog implements Runnable {
    private final String backgroundPath = "src/Resources/backgroundBlured.jpg";
    private Background myBackground;
    private JPanel mainPanel;
    private JLabel titleLabel;
    private List<JLabel> playerLabels;
    private final JFrame mainFrame;
    private Thread fetchDataThread;

    public TopFive(JFrame mainFrame) {
        this.mainFrame = mainFrame;
        init();
    }

    private void init() {

        setTitle("TOP 5");
        setSize(500, 500);
        setLocationRelativeTo(null);
        setResizable(false);

        myBackground = new Background(backgroundPath, 500, 500);

        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setOpaque(false);

        titleLabel = new JLabel("TOP 5");
        titleLabel.setFont(new Font("TimesRoman", Font.PLAIN, 25));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        mainPanel.add(titleLabel);

        playerLabels = createPlayerLabels(); // list of labels

        for (JLabel playerLabel : playerLabels) { //adding players to main panel
            mainPanel.add(playerLabel);
        }

        myBackground.add(mainPanel);
        add(myBackground);

        /*
        When window opens, main window disapear, same time we´re fetching data from server as a new thread.
        When we close window main window coming back.
         */
        addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
                mainFrame.setVisible(false);
                fetchDataThread = new Thread(TopFive.this);
                fetchDataThread.start();  // The run() function will be triggered automatically
            }
            @Override
            public void windowClosing(WindowEvent e) {
                mainFrame.setVisible(true);
            }
            @Override
            public void windowClosed(WindowEvent e) {}
            @Override
            public void windowIconified(WindowEvent e) {}
            @Override
            public void windowDeiconified(WindowEvent e) {}
            @Override
            public void windowActivated(WindowEvent e) {}
            @Override
            public void windowDeactivated(WindowEvent e) {}
        });

    }

    /*
    creating labels for players
    */
    private List<JLabel> createPlayerLabels() {
        List<JLabel> labels = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            JLabel playerLabel = new JLabel();
            playerLabel.setFont(new Font("TimesRoman", Font.PLAIN, 15));
            playerLabel.setForeground(Color.WHITE);
            playerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            playerLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
            labels.add(playerLabel);
        }
        return labels;
    }

/*
Function to send question to server in response we get string with top5 players.
We use parseResponse to get list of strings ( "playerName#score")
displayPlayerList() will set text in our labels we prepared before. Will replace "#" and add "/20"
 */

    @Override
    public void run() {
        Socket socket = ConnectToServer();
        try {
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                    out.println("top5");
                    String response;
                    while ((response = in.readLine()) != null) {
                        displayPlayerList(parseResponse(response));
                    }
                    in.close();
                    out.close();
                    socket.close();


        } catch (IOException e) {
            System.out.println("asd");
            e.printStackTrace();
        } catch (RuntimeException e){
            JOptionPane.showMessageDialog(this,"Couldn't connect to server. Check connection.");
        }
    }


    private List<String> parseResponse(String response) {
        List<String> players = List.of(response.split("@"));
        return players;
    }

    private void displayPlayerList(List<String> playerList) {
        for (int i = 0; i < playerLabels.size(); i++) {
            playerLabels.get(i).setText(playerList.get(i).replace("#", " ") + "/20");
        }
    }
}
