package Views;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;

public class Background extends JPanel {
    Image img;
    int width, height;

    public Background(String backgroundPath, int width, int height) {
        super();
        this.width = width;
        this.height = height;
        setLayout(new GridBagLayout());
        try {
            img = ImageIO.read(new File(backgroundPath));
        } catch (Exception e) {
            System.out.println(e);
        }

        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(img, 0, 0, width, height, null);
    }
}
