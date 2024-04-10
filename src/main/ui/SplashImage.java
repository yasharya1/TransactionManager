package ui;

import javax.swing.*;
import java.awt.*;

//  The SplashImage class is responsible for displaying a splash screen with an image. This splash screen is used
//  to visually indicate certain actions within the application, such as the successful addition of a transaction.

public class SplashImage extends JWindow {

    //REQUIRES:
    //MODIFIES:
    //EFFECTS: initialises a Splash image with its path, duration to display and dimensions
    public SplashImage(String imagePath, int duration, int width, int height) {
        ImageIcon imageIcon = new ImageIcon(imagePath);
        Image image = imageIcon.getImage();
        Image resizedImage = image.getScaledInstance(width, height,  java.awt.Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(resizedImage);

        JLabel label = new JLabel(resizedIcon);
        getContentPane().add(label, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(null);
        new Timer(duration, e -> dispose()).start();
    }

}
