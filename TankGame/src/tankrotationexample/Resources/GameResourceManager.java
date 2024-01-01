package tankrotationexample.Resources;

import tankrotationexample.game.Sound;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;

public class GameResourceManager {

    private final static Map<String, BufferedImage> sprites = new HashMap<>();
    private final static Map<String, Sound> sounds = new HashMap<>();
    private final static Map<String, List<BufferedImage>> animations = new HashMap<>();
    private static final Map<String, Integer> animationInfo = new HashMap<>() {{
        put("bullethit", 24);
        put("bulletshoot", 24);
        put("powerpick", 32);
        put("puffsmoke", 32);
        put("rocketflame", 16);
        put("rockethit", 32);
    }};

    private static BufferedImage loadSprite(String path) throws IOException {
        return ImageIO.read(
                Objects.requireNonNull(GameResourceManager
                        .class
                        .getClassLoader()
                        .getResource(path)));
    }

    private static Sound loadSound(String path) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        AudioInputStream ais = AudioSystem.getAudioInputStream(
                Objects.requireNonNull(
                        GameResourceManager.class.getClassLoader().getResource(path)
                )
        );
        Clip c = AudioSystem.getClip();
        c.open(ais);
        Sound s = new Sound(c);
        s.setVolume(.2f);
        return s;
    }

    private static void initSprites() {
        try {
            sprites.put("tank1", loadSprite("tank/tank1.png"));
            sprites.put("tank2", loadSprite("tank/tank2.png"));
            sprites.put("bullet", loadSprite("bullet/bullet.jpg"));
            sprites.put("rocket1", loadSprite("bullet/rocket1.png"));
            sprites.put("rocket2", loadSprite("bullet/rocket2.png"));
            sprites.put("break1", loadSprite("walls/break1.jpg"));
            sprites.put("break2", loadSprite("walls/break2.jpg"));
            sprites.put("unbreak", loadSprite("walls/unbreak.jpg"));
            sprites.put("floor", loadSprite("floor/bg.bmp"));
            sprites.put("health", loadSprite("powerups/health.png"));
            sprites.put("shield", loadSprite("powerups/shield.png"));
            sprites.put("speed", loadSprite("powerups/speed.png"));
            sprites.put("menu", loadSprite("menu/title.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static BufferedImage getSprite(String type) {
        if (!sprites.containsKey(type)) {
            throw new RuntimeException("%s is missing from sprite resources".formatted(type));
        }
        return sprites.get(type);
    }

    public static List<BufferedImage> getAnimation(String type) {
        if (!animations.containsKey(type)) {
            throw new RuntimeException("%s is missing from animations resources".formatted(type));
        }
        return animations.get(type);
    }

    public static Sound getSound(String type) {
        if (!sounds.containsKey(type)) {
            throw new RuntimeException("%s is missing from sounds resources".formatted(type));
        }
        return sounds.get(type);
    }

    private static void initAnimations() {
        String baseName = "animations/%s/%s_%04d.png";

        animationInfo.forEach((animationName, frameCount) -> {
            try {
                List<BufferedImage> frames = new ArrayList<>();
                for (int i = 0; i < frameCount; i++) {
                    String spritePath = baseName.formatted(animationName, animationName, i);
                    frames.add(loadSprite(spritePath));
                }
                animations.put(animationName, frames);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public static void initSounds() {
        try {
            sounds.put("bullet_shoot", loadSound("sounds/bullet_shoot.wav"));
            sounds.put("explosion", loadSound("sounds/explosion.wav"));
            sounds.put("background", loadSound("sounds/Music.mid"));
            sounds.put("pickup", loadSound("sounds/pickup.wav"));
            sounds.put("shotfire", loadSound("sounds/shotfiring.wav"));
        } catch (Exception e) {
            System.out.println("initSounds() e : " + e);
        }
    }

    public static void loadResources() {
        initSprites();
        initAnimations();
        initSounds();
    }

    public static void main(String[] args) {
        loadResources();
        Sound background = getSound("background");
        while (true) {
            try {
                getSound("explosion").playSound();
                Thread.sleep(1500);
                getSound("bullet_shoot").playSound();
                Thread.sleep(1500);
                getSound("pickup").playSound();
                Thread.sleep(1500);
                getSound("shotfire").playSound();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
