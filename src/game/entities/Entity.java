package game.entities;

import game.gameplay.GameplayModel;

import java.awt.*;

public interface Entity {
    // interface for each element that has a task every tick
    public void drawEntity(Graphics g);
    public void doStep(GameplayModel model);
}
