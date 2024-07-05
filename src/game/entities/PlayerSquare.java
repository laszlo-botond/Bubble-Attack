package game.entities;

import game.*;
import game.entities.effects.EffectPop;
import game.entities.effects.EffectShoot;
import game.gameplay.GameplayModel;
import game.hud.ShootButton;

import javax.sound.sampled.*;
import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class PlayerSquare implements Entity {
    private static final int size = 20;
    private int xPos;
    private int yPos;
    private char lastControlInput;
    private final ShootButton shootButton;
    private Clip shotSound;
    public PlayerSquare(ShootButton shootButton) {
        xPos = 12 + GameInfo.frameWidth / 2;
        yPos = GameInfo.frameHeight - GameInfo.heightLimit; // player spawns on height limit
        this.shootButton = shootButton;

        // getting shoot sound
        try {
            //hibas: AudioInputStream audioInputStream  = AudioSystem.getAudioInputStream(new File("resources/shotSound.wav"));
            byte[] soundBytes = getClass().getResourceAsStream("/shotSound.wav").readAllBytes();
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(soundBytes);
            AudioInputStream audioInputStream  = AudioSystem.getAudioInputStream(byteArrayInputStream);

            shotSound = AudioSystem.getClip();
            shotSound.open(audioInputStream);
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            System.out.println("IO error");
        }
    }

    // get and set methods
    public int getxPos() {
        return xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public void setxPos(int xPos) {
        this.xPos = xPos;
    }

    public void setyPos(int yPos) {
        this.yPos = yPos;
    }

    public int getSize() {
        return size;
    }

    @Override
    public void drawEntity(Graphics g) {
        g.setColor(Color.BLACK);
        g.drawRect(xPos, yPos, size, size);
        g.setColor(GameInfo.playerColor);
        g.fillRect(xPos, yPos, size, size);
    }

    @Override
    public void doStep(GameplayModel model) {
        executeInput(model);
        if (isUnderground(model)) {
            model.setEnded(true);
        }
    }

    public void setInput(char c) {
        lastControlInput = c;
    }

    private void executeInput(GameplayModel model) {
        int zone = xPos - GameInfo.barGapSize / 2;
        zone = 1 + zone / (GameInfo.barWidth + GameInfo.barGapSize); // index of bar under player

        int groundHeight = GameInfo.heightLimit - model.getArrayElement(zone) * GameInfo.barHeightUnit;
        if (yPos + size < groundHeight)
            yPos = yPos + GameInfo.gravity;

        switch (lastControlInput) {
            case 'l': // left
                if (xPos > GameInfo.barGapSize + GameInfo.barWidth) {
                    int adjHeight = GameInfo.heightLimit - model.getArrayElement(zone - 1) * GameInfo.barHeightUnit;
                    if (yPos + size < adjHeight) {
                        xPos = xPos - GameInfo.barWidth - GameInfo.barGapSize;
                    }
                }
                lastControlInput = '0';
                break;

            case 'r': // right
                if (xPos < GameInfo.frameWidth - GameInfo.barGapSize - GameInfo.barWidth) {
                    int adjHeight = GameInfo.heightLimit - model.getArrayElement(zone + 1) * GameInfo.barHeightUnit;
                    if (yPos + size < adjHeight) {
                        xPos = xPos + GameInfo.barWidth + GameInfo.barGapSize;
                    }
                }
                lastControlInput = '0';
                break;

            case 'u': // up
                if (yPos > GameInfo.frameHeight - GameInfo.heightLimit + GameInfo.jumpHeight) {
                    yPos = yPos - GameInfo.jumpHeight;
                }
                lastControlInput = '0';
                break;

            case ' ': // shoot
                lastControlInput = '0';
                if (!shootButton.canShoot()) return;
                shootButton.resetCooldown();
                shoot(model);
                break;
        }
    }

    public boolean isUnderground(GameplayModel model) {
        int zone = xPos - GameInfo.barGapSize / 2;
        zone = 1 + zone / (GameInfo.barWidth + GameInfo.barGapSize); // index of bar under player

        int groundHeight = GameInfo.heightLimit - model.getArrayElement(zone) * GameInfo.barHeightUnit;
        return (yPos + size > groundHeight);
    }

    private void shoot(GameplayModel model) {
        shotSound.setFramePosition(0);
        shotSound.start();

        ArrayList<Entity> entities = model.getEntities();
        EnemyBubble thisEnemy;
        EnemyBubble lowestEnemy = null;
        int lowestHeight = -1;

        // get closest bubble above player
        for (Entity e : entities) {
            if (e.getClass() == EnemyBubble.class) {
                thisEnemy = (EnemyBubble) e;
                if (thisEnemy.getPosY() < yPos &&
                        thisEnemy.getPosX() < xPos + size / 2 + 5 &&
                        thisEnemy.getPosX() + thisEnemy.getSize() > xPos + size / 2 - 5 &&
                        thisEnemy.getPosY() > lowestHeight) {
                    lowestEnemy = thisEnemy;
                    lowestHeight = thisEnemy.getPosY();
                }
            }
        }

        if (lowestEnemy != null) { // enemy hit
            model.addEntity(new EffectPop(lowestEnemy.getPosX(), lowestEnemy.getPosY()));
            model.removeEntity(lowestEnemy);
            model.increaseScore(5);
        }

        model.addEntity(new EffectShoot(xPos + 5, yPos));
    }
}
