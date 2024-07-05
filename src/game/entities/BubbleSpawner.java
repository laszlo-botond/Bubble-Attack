package game.entities;

import game.GameInfo;
import game.gameplay.GameplayModel;

import java.awt.*;
import java.util.Random;

public class BubbleSpawner implements Entity {
    private final Random r;
    public BubbleSpawner(int seed) {
        r = new Random(seed);
    }
    @Override
    public void drawEntity(Graphics g) {} // nothing to draw

    @Override
    public void doStep(GameplayModel model) {
        int ticksPassed = model.getTicksPassed();
        if (ticksPassed % GameInfo.bubbleSpawnTime != 0) return;

        model.addEntity(new EnemyBubble(r.nextInt()));
    }
}
