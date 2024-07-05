package game.entities.effects;

import game.gameplay.GameplayModel;
import game.entities.Entity;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static java.lang.System.exit;

public class EffectPop implements Entity {

    private final int duration = 3;
    private final int x;
    private final int y;
    private int state;
    private BufferedImage pop1;
    private BufferedImage pop2;
    private BufferedImage pop3;
    public EffectPop(int x, int y) {
        this.x = x;
        this.y = y;
        try {
            pop1 = ImageIO.read(getClass().getResourceAsStream("/bubblePop1.png"));
            pop2 = ImageIO.read(getClass().getResourceAsStream("/bubblePop2.png"));
            pop3 = ImageIO.read(getClass().getResourceAsStream("/bubblePop3.png"));
        } catch (IOException e) {
            System.out.println("Hiba: A kep nem talalhato!");
            exit(0);
        }
    }

    @Override
    public void drawEntity(Graphics g) {
        switch (state) {
            case 1:
                g.drawImage(pop1, x, y, null);
                break;
            case 2:
                g.drawImage(pop2, x, y, null);
                break;
            case 3:
                g.drawImage(pop3, x, y, null);
                break;
        }
    }

    @Override
    public void doStep(GameplayModel model) {
        state++;
        if (state > duration) {
            model.removeEntity(this);
        }
    }
}
