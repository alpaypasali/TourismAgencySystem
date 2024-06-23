package core.utilities.helpers;

import javax.swing.*;
import java.awt.*;

public class FrameHelper {
    // Çerçeveyi ortalayacak metot
    public static Point getCenterLocation(int width, int height) {
        int x = (Toolkit.getDefaultToolkit().getScreenSize().width - width) / 2;
        int y = (Toolkit.getDefaultToolkit().getScreenSize().height - height) / 2;
        return new Point(x, y);
    }

    // Çerçeve ayarlarını yapacak metot
    public static void setupFrame(JFrame frame, int width, int height, String title) {
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setTitle(title);
        frame.setSize(width, height);
        Point centerLocation = getCenterLocation(width, height);
        frame.setLocation(centerLocation);

        frame.setVisible(true);
    }
}