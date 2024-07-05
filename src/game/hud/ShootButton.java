package game.hud;

import game.GameInfo;
import game.gameplay.GameplayModel;
import game.entities.Entity;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static java.lang.System.exit;

public class ShootButton implements Entity {
    private int x, y;
    private static final int cooldown = GameInfo.shootCooldown; // in ticks
    private int cooldownPassed = 0;
    private BufferedImage img;
    public ShootButton(int x, int y) {
        this.x = x;
        this.y = y;
        try {
            img = ImageIO.read(getClass().getResourceAsStream("/shoot1.png"));
        } catch (IOException e) {
            System.out.println("Hiba: A kep nem talalhato!");
            exit(0);
        }
    }

    @Override
    public void drawEntity(Graphics g) {
        float level = (float) cooldownPassed / (float) cooldown;
        int loadedBarWidth = (int) (54 * level);
        g.setColor(GameInfo.playerColor);
        g.fillRect(x+11, y+61, loadedBarWidth, 7);
        if (canShoot()) {
            g.fillOval(x+26,y+19,24,24);
        }
        g.drawImage(img, x, y, null);
    }

    @Override
    public void doStep(GameplayModel model) {
        if (cooldownPassed < cooldown) {
            cooldownPassed++;
        }
    }

    public boolean canShoot() {
        return cooldownPassed == cooldown;
    }

    public void resetCooldown() {
        cooldownPassed = 0;
    }
}
