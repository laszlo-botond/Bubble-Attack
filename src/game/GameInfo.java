package game;

import java.awt.*;

public class GameInfo {
    // General info
    public final static int tickTime = 50;
    public final static int frameWidth = 1280;
    public final static int frameHeight = 720;
    // 21*barGapSize + 20*barWidth = Frame Width!

    // Sorted array presentation
    public final static int barHeightUnit = 20;
    public final static int barWidth = 43;
    public final static int barGapSize = 20;

    // Movement settings
    public final static int gravity = 10;
    public final static int heightLimit = 680;
    public final static int jumpHeight = 100;

    // Visual settings
    public final static Color barColor = new Color(175, 197, 197);
    public final static Color barChosenColor = new Color(24, 50, 147);
    public final static Color playerColor = new Color(255, 153, 0);
    public final static Color shotColor = new Color(255, 230, 188);
    public final static Color menuPanelBg = new Color(24, 50, 147, 163);
    public final static int enemySize = 50;

    // Time settings
    public final static int bubbleSortStepTime = 18; // in ticks
    public final static int bubbleSpawnTime = 20; // in ticks
    public final static int shootCooldown = 10; // in ticks
}
