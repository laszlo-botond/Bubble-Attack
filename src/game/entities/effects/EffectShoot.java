package game.entities.effects;

import game.entities.Entity;
import game.GameInfo;
import game.gameplay.GameplayModel;

import java.awt.*;

public class EffectShoot implements Entity {
    private int state = 1;
    private final int x;
    private final int y;
    private final int duration = 5;
    private final Color color;
    public EffectShoot(int x, int y) {
        color = GameInfo.shotColor;
        this.x = x;
        this.y = y;
    }
    @Override
    public void drawEntity(Graphics g) {
        Color tmpColor = new Color(color.getRed(), color.getGreen(), color.getBlue(), 255 - (state - 1) * 50);
        g.setColor(tmpColor);
        //g.fillRect(x,0,10,y);
        g.fillRect(x,0,10,GameInfo.frameHeight);
    }

    @Override
    public void doStep(GameplayModel model) {
        state++;
        if (state > duration) {
            model.removeEntity(this);
        }
    }
}
