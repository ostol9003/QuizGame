package Helpers;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;


public class Helper {

    public static void addComponentsToParent(Component parent, Component... components) {
        if (parent instanceof JPanel) {
            for (Component component : components) {
                ((JPanel) parent).add(component);
            }
        }
    }

    public static Socket ConnectToServer() {
        try {
            Socket socket = new Socket("localhost", 3000);
            System.out.println("Client connected");
            return socket;
        } catch (IOException e) {
            System.out.println("Run server");
            System.out.println(e);
            return null;
        }
    }

    public static void SendToServer(String msg) throws IOException {
        Socket socket = ConnectToServer();
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        out.println(msg);
        socket.close();
    }

    /*
    function to round the corners of the buttons - new type of border
     */
    public static class RoundedBorder implements Border {
        private final int radius;

        public RoundedBorder(int radius) {
            this.radius = radius;
        }

        public Insets getBorderInsets(Component c) {
            return new Insets(this.radius + 2, this.radius + 10, this.radius + 2, this.radius + 10);
        }

        public boolean isBorderOpaque() {
            return true;
        }

        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            g.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
        }
    }

    /*
    Our own button style
     */
    public static class MyButton extends JButton {
        public MyButton(String title) {
            super(title);
            setBackground(Color.BLACK);
            setForeground(Color.WHITE);
            setBorder(new RoundedBorder(5));
            setFocusable(false);
        }
    }

}
