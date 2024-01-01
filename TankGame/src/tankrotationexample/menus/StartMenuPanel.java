package tankrotationexample.menus;

import tankrotationexample.Launcher;
import tankrotationexample.Resources.ResourceManager;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class StartMenuPanel extends JPanel {

    private BufferedImage background; // Background image for the start menu panel
    private final Launcher gameLauncher; // Reference to the game launcher

    // Constructor for the StartMenuPanel
    public StartMenuPanel(Launcher launcher) {
        this.gameLauncher = launcher;
        background = ResourceManager.getSprite("menu");
        this.setBackground(Color.BLACK);
        this.setLayout(null);

        JButton startButton = new JButton("Start");
        startButton.setFont(new Font("Courier New", Font.BOLD, 24));
        startButton.setBounds(150, 300, 150, 50);
        startButton.addActionListener(actionEvent -> this.gameLauncher.setFrame("game"));

        JButton exitButton = new JButton("Exit");
        exitButton.setSize(new Dimension(200, 100));
        exitButton.setFont(new Font("Courier New", Font.BOLD, 24));
        exitButton.setBounds(150, 400, 150, 50);
        exitButton.addActionListener((actionEvent -> this.gameLauncher.closeGame()));

        this.add(startButton);
        this.add(exitButton);
    }

    // Paint the components of the StartMenuPanel
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(this.background, 0, 0, null);
    }
}
