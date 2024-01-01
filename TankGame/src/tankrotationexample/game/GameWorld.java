package tankrotationexample.game;

import tankrotationexample.GameConstants;
import tankrotationexample.Launcher;
import tankrotationexample.Resources.ResourceManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GameWorld extends JPanel implements Runnable {

    private BufferedImage gameWorldBuffer;
    private Tank playerTank1;
    private Tank playerTank2;
    private Wall gameWall;
    private final Launcher launcher;
    private long tickCount = 0;
    List<GameObject> gameObjectsList = new ArrayList<>(800);
    private ResourceManager gameResources;
    List<Animation> animationsList = new ArrayList<>();
    Sound backgroundMusic = ResourceManager.getSound("background");

    public GameWorld(Launcher launcher) {
        this.launcher = launcher;
        ResourceManager.loadResources();
    }

    @Override
    public void run() {
        try {
            backgroundMusic.setLooping();
            backgroundMusic.playSound();

            while (true) {
                this.tickCount++;
                this.playerTank1.update(this);
                this.playerTank2.update(this);
                this.animationsList.forEach(animation -> animation.update());
                this.checkCollision();
                this.gameObjectsList.removeIf(gameObject -> gameObject.hasCollided());
                this.repaint();
                Thread.sleep(1000 / 144);
            }
        } catch (InterruptedException ignored) {
            System.out.println(ignored);
        }
    }

    private void checkCollision() {
        for (int i = 0; i < this.gameObjectsList.size(); i++) {
            GameObject obj1 = this.gameObjectsList.get(i);

            if (obj1 instanceof Wall || obj1 instanceof Health || obj1 instanceof Speed || obj1 instanceof Shield) {
                continue;
            }

            for (int j = 0; j < this.gameObjectsList.size(); j++) {
                if (i == j) continue;
                GameObject obj2 = this.gameObjectsList.get(j);

                if (obj2 instanceof Tank) {
                    continue;
                }

                if (obj1.getHitBox().intersects(obj2.getHitBox())) {
                    obj1.handleCollision(obj2);

                    if (obj1 instanceof Tank && obj2 instanceof Health) {
                        System.out.println("Tank has hit health");
                        ResourceManager.getSound("pickup").playSound();
                    }

                    if (obj1 instanceof Tank && obj2 instanceof Shield) {
                        System.out.println("Tank has hit shield");
                        ResourceManager.getSound("pickup").playSound();
                    }

                    if (obj1 instanceof Tank && obj2 instanceof Speed) {
                        System.out.println("Tank has hit speed");
                        ResourceManager.getSound("pickup").playSound();
                    }

                    if (obj1 instanceof Tank && obj2 instanceof Bullet) {
                        System.out.println("Tank has been hit by bullet");
                        ResourceManager.getSound("explosion").playSound();
                    }

                    if (obj1 instanceof Bullet && obj2 instanceof BreakableWall) {
                        System.out.println("BreakableWall has been hit by bullet");
                        ResourceManager.getSound("explosion").playSound();
                    }
                }
            }
        }
    }

    public void resetGame() {
        this.tickCount = 0;
        this.playerTank1.setX(300);
        this.playerTank1.setY(300);
    }

    public void initializeGame() {
        this.gameWorldBuffer = new BufferedImage(GameConstants.GAME_WORLD_WIDTH,
                GameConstants.GAME_WORLD_HEIGHT,
                BufferedImage.TYPE_INT_RGB);

        InputStreamReader isr = new InputStreamReader(
                Objects.requireNonNull(
                        ResourceManager
                                .class
                                .getClassLoader()
                                .getResourceAsStream("maps/map1.csv"))
        );

        try (BufferedReader mapReader = new BufferedReader(isr)) {
            int row = 0;
            String[] gameItems;
            while (mapReader.ready()) {
                gameItems = mapReader.readLine().strip().split(";");
                for (int col = 0; col < gameItems.length; col++) {
                    String gameObject = gameItems[col];
                    if ("0".equals(gameObject)) {
                        continue;
                    }
                    this.gameObjectsList.add(GameObject.newInstance(gameObject, col * 30, row * 30));
                }
                row++;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.playerTank1 = new Tank(300, 300, 0, 0, (short) 0, ResourceManager.getSprite("tank1"), launcher, "T. Red");
        TankControl tankControl1 = new TankControl(playerTank1, KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_SPACE);
        this.launcher.getJf().addKeyListener(tankControl1);

        this.playerTank2 = new Tank(1400, 1000, 0, 0, (short) 0, ResourceManager.getSprite("tank2"), launcher, "T. Blue");
        TankControl tankControl2 = new TankControl(playerTank2, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_V);
        this.launcher.getJf().addKeyListener(tankControl2);

        this.gameObjectsList.add(playerTank1);
        this.gameObjectsList.add(playerTank2);
    }

    private void drawFloor(Graphics2D buffer) {
        BufferedImage floor = gameResources.getSprite("floor");
        for (int i = 0; i < GameConstants.GAME_WORLD_WIDTH; i += 320) {
            for (int j = 0; j < GameConstants.GAME_WORLD_HEIGHT; j += 240) {
                buffer.drawImage(floor, i, j, null);
            }
        }
    }

    private void renderMiniMap(Graphics2D g2, BufferedImage world) {
        BufferedImage minimap = world.getSubimage(
                0,
                0,
                GameConstants.GAME_WORLD_WIDTH,
                GameConstants.GAME_WORLD_HEIGHT);

        g2.scale(0.2, 0.2);
        int minimapX = (GameConstants.GAME_SCREEN_HEIGHT * 5) / 2 - (GameConstants.GAME_WORLD_WIDTH / 11);
        int minimapY = (GameConstants.GAME_SCREEN_HEIGHT * 5) - (GameConstants.GAME_WORLD_HEIGHT) - 250;
        g2.drawImage(minimap, minimapX, minimapY, null);
    }

    private void renderSplitScreens(Graphics2D g2, BufferedImage world) {
        BufferedImage lh = world.getSubimage(
                (int) this.playerTank1.getScreen_x(),
                (int) this.playerTank1.getScreen_y(),
                GameConstants.GAME_SCREEN_WIDTH / 2,
                GameConstants.GAME_SCREEN_HEIGHT);

        BufferedImage rh = world.getSubimage(
                (int) this.playerTank2.getScreen_x(),
                (int) this.playerTank2.getScreen_y(),
                GameConstants.GAME_SCREEN_WIDTH / 2,
                GameConstants.GAME_SCREEN_HEIGHT);

        g2.drawImage(lh, 0, 0, null);
        g2.drawImage(rh, GameConstants.GAME_SCREEN_WIDTH / 2 + 4, 0, null);
    }

    public void addGameObject(Bullet bullet) {
        this.gameObjectsList.add(bullet);
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        Graphics2D buffer = gameWorldBuffer.createGraphics();

        this.drawFloor(buffer);
        this.gameObjectsList.forEach(gameObject -> gameObject.drawImage(buffer));

        this.playerTank1.drawImage(buffer);
        this.playerTank2.drawImage(buffer);

        this.animationsList.forEach(animation -> animation.drawImage(buffer));

        this.renderSplitScreens(g2, gameWorldBuffer);
        this.renderMiniMap(g2, gameWorldBuffer);
    }
}
