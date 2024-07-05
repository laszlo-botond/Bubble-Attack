package game.entities;

import game.GameInfo;
import game.gameplay.GameplayModel;

import java.awt.*;
import java.util.Random;

public class BubbleSort implements Entity {
    private int currentIndex = 1;
    private final int[] heights;
    private final Random r;
    private int localTicksPassed;
    private int fullIterations = 0;
    public BubbleSort(int seed) {
        r = new Random(seed);
        heights = new int[21];
        GenerateArray();
    }
    @Override
    public void doStep(GameplayModel model) {
        localTicksPassed = model.getTicksPassed();
        if (localTicksPassed % GameInfo.bubbleSortStepTime != 0) return;

        if (heights[currentIndex] > heights[currentIndex + 1]) {
            int tmp = heights[currentIndex];
            heights[currentIndex] = heights[currentIndex + 1];
            heights[currentIndex + 1] = tmp;
        }

        currentIndex = currentIndex + 1;
        if (currentIndex >= heights.length - 1 - fullIterations)
        {
            currentIndex = 1;
            fullIterations++;
            if (fullIterations >= heights.length) {
                model.setEnded(true);
            }
        }
    }

    @Override
    public void drawEntity(Graphics g) {
        int yLimit = GameInfo.heightLimit;
        int hUnit = GameInfo.barHeightUnit;
        int gap = GameInfo.barGapSize;
        int width = GameInfo.barWidth;
        int xpos, ypos;
        int height;
        for (int i = 1; i <= 20; i++) {
            xpos = gap / 2 + (width + gap) * (i - 1);
            ypos = yLimit - hUnit * heights[i];
            height = heights[i] * hUnit;
            if (i == currentIndex) {
                float fadeLevel = (localTicksPassed % GameInfo.bubbleSortStepTime) / (float) GameInfo.bubbleSortStepTime;
                //g.setColor(game.GameInfo.barChosenColor);
                g.setColor(fadingColor(GameInfo.barColor, GameInfo.barChosenColor, fadeLevel));
            } else {
                g.setColor(GameInfo.barColor);
            }
            g.fillRect(xpos, ypos, width, height);
            g.setColor(Color.BLACK);
            g.drawRect(xpos, ypos, width, height);
        }
    }

    public int[] getArray() {
        return heights;
    }

    private void GenerateArray() {
        boolean[] done = new boolean[21]; // initializes false
        int nr;
        for (int i = 1; i <= 20; i++) {
            do {
                nr = r.nextInt(21);
            } while (nr == 0 || done[nr]);
            heights[i] = nr;
            done[nr] = true;
        }
    }

    private Color fadingColor(Color c1, Color c2, float level) {
        // from c1 to c2
        int r1 = c1.getRed();
        int g1 = c1.getGreen();
        int b1 = c1.getBlue();
        int r2 = c2.getRed();
        int g2 = c2.getGreen();
        int b2 = c2.getBlue();
        int r = r1 + (int) ((r2-r1) * level);
        int g = g1 + (int) ((g2-g1) * level);
        int b = b1 + (int) ((b2-b1) * level);
        return new Color(r,g,b);
    }
}
