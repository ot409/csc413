package tankrotationexample.menus;

import tankrotationexample.Launcher;
import tankrotationexample.Resources.ResourceManager;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class WinnerGamePanel extends JPanel {

    private BufferedImage background; // Background image for the winner game panel
    private final Launcher gameLauncher; // Reference to the game launcher
    private String winnerName; // Name of the winner

    // Constructor for the WinnerGamePanel
    public WinnerGamePanel(Launcher launcher, String winner) {
        this.gameLauncher = launcher;
        this.winnerName = winner;
        background = ResourceManager.getSprite("menu");
        this.setBackground(Color.BLACK);
        this.setLayout(null);

        JButton winnerButton = new JButton("Winner is " + this.winnerName); // Create a button with winner's name
        winnerButton.setFont(new Font("Courier New", Font.BOLD, 24));
        winnerButton.setBounds(120, 300, 250, 50);
        winnerButton.addActionListener((actionEvent -> this.gameLauncher.setFrame("game"))); // Add action listener

        // Add the winnerButton to the panel
        this.add(winnerButton);
    }

    // Paint the components of the WinnerGamePanel
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(this.background, 0, 0, null);
    }
}
