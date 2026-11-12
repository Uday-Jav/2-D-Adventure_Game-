package MAIN;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;

public class Main {
    public static void main(String[] args) {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Echoes of the Lost Kingdom");

        // Load application icon
        try {
            InputStream is = Main.class.getResourceAsStream("/src/object/relic_sunstone.png");
            if (is == null) is = Main.class.getResourceAsStream("/object/relic_sunstone.png");
            if (is != null) {
                BufferedImage icon = ImageIO.read(is);
                window.setIconImage(icon);
            }
        } catch (Exception e) {}

        GamePannel gamePannel = new GamePannel();
        window.add(gamePannel);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        window.setAlwaysOnTop(true);
        window.toFront();
        window.requestFocus();
        window.setAlwaysOnTop(false);

        gamePannel.StartGameThread();        gamePannel.requestFocusInWindow();
    }
}