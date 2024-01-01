package tankrotationexample.menus;

import tankrotationexample.Launcher;
import tankrotationexample.Resources.ResourceManager;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class EndGamePanel extends JPanel {

    private BufferedImage background; // Background image for the end game panel
    private final Launcher gameLauncher; // Reference to the game launcher
    private String winningPlayer; // String indicating the winner

    // Constructor for the EndGamePanel
    public EndGamePanel(Launcher launcher) {
        this.gameLauncher = launcher;
        background = ResourceManager.getSprite("menu");
        this.setBackground(Color.BLACK);
        this.setLayout(null);

        JButton restartButton = new JButton("Restart Game");
        restartButton.setFont(new Font("Courier New", Font.BOLD, 24));
        restartButton.setBounds(150, 300, 250, 50);
        restartButton.addActionListener((actionEvent -> this.gameLauncher.setFrame("game")));

        JButton exitButton = new JButton("Exit");
        exitButton.setFont(new Font("Courier New", Font.BOLD, 24));
        exitButton.setBounds(150, 400, 250, 50);
        exitButton.addActionListener((actionEvent -> this.gameLauncher.closeGame()));

        this.add(restartButton);
        this.add(exitButton);
    }

    // Paint the components of the EndGamePanel
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(this.background, 0, 0, null);
    }
}
