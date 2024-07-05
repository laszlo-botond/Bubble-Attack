package game.entities;

import game.GameInfo;
import game.gameplay.GameplayModel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import static java.lang.System.exit;

public class EnemyBubble implements Entity {
    private BufferedImage enemyImg;
    private int posX, posY, size;
    private final Random r;
    public EnemyBubble(int seed) {
        r = new Random(seed);
        posX = r.nextInt(GameInfo.frameWidth - size);
        posY = 0;
        size = GameInfo.enemySize;
        try {
            enemyImg = ImageIO.read(getClass().getResourceAsStream("/bubble.png"));
        } catch (IOException e) {
            System.out.println("Hiba: A kep nem talalhato!");
            exit(0);
        }
    }
    public BufferedImage getEnemyImg() {
        return enemyImg;
    }

    public int getPosY() {
        return posY;
    }

    public int getPosX() {
        return posX;
    }

    public int getSize() {
        return size;
    }

    @Override
    public void drawEntity(Graphics g) {
        g.drawImage(enemyImg, posX, posY, null);
    }

    @Override
    public void doStep(GameplayModel model) {
        move();
        if (posX < -size || posX > GameInfo.frameWidth || posY > GameInfo.frameHeight) {
            model.removeEntity(this);
        }
    }

    private void move() {
        int xDir = r.nextInt(21) - 10;
        int yDir = r.nextInt(21) - 5; // expected to go down after enough time
        posX += xDir;
        posY += yDir;
    }
}
