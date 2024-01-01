package tankrotationexample;

import tankrotationexample.Resources.ResourceManager;
import tankrotationexample.game.GameWorld;
import tankrotationexample.game.Tank;
import tankrotationexample.menus.EndGamePanel;
import tankrotationexample.menus.StartMenuPanel;
import tankrotationexample.menus.WinnerGamePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;

public class GameLauncher {

    private JPanel mainPanel;
    private GameWorld gamePanel;
    private final JFrame gameFrame;
    private CardLayout cardLayout;
    private JPanel winnerPanel; // Updated winner game panel

    public GameLauncher(){
        this.gameFrame = new JFrame();
        this.gameFrame.setTitle("Tank Wars Game");
        this.gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void initializeUIComponents(){
        this.mainPanel = new JPanel();
        JPanel startPanel = new StartMenuPanel(this);
        this.gamePanel = new GameWorld(this);
        this.gamePanel.InitializeGame();
        JPanel endPanel = new EndGamePanel(this);
        this.winnerPanel = new WinnerGamePanel(this, Tank.getWinnerName()); // Updated winner game panel
        cardLayout = new CardLayout();
        this.mainPanel.setLayout(cardLayout);
        this.mainPanel.add(startPanel, "start");
        this.mainPanel.add(gamePanel, "game");
        this.mainPanel.add(endPanel, "end");
        this.mainPanel.add(winnerPanel, "winner"); // Updated winner game panel
        this.gameFrame.add(mainPanel);
        this.gameFrame.setResizable(false);
        this.setFrame("start");
    }

    public void setFrame(String panelType){
        this.gameFrame.setVisible(false);
        switch (panelType) {
            case "start" -> this.gameFrame.setSize(GameConstants.START_MENU_WIDTH, GameConstants.START_MENU_HEIGHT);
            case "game" -> {
                this.gameFrame.setSize(GameConstants.SCREEN_WIDTH, GameConstants.SCREEN_HEIGHT);
                (new Thread(this.gamePanel)).start();
            }
            case "end" -> this.gameFrame.setSize(GameConstants.END_MENU_WIDTH, GameConstants.END_MENU_HEIGHT);
            case "winner" -> this.gameFrame.setSize(GameConstants.END_MENU_WIDTH, GameConstants.END_MENU_HEIGHT);
        }
        this.cardLayout.show(mainPanel, panelType);
        this.gameFrame.setVisible(true);
    }

    public JFrame getGameFrame() {
        return gameFrame;
    }

    public void closeGame(){
        this.gameFrame.dispatchEvent(new WindowEvent(this.gameFrame, WindowEvent.WINDOW_CLOSING));
    }

    public static void main(String[] args) {
        ResourceManager.loadResources();
        (new GameLauncher()).initializeUIComponents();
    }
}
