package game.gameplay;

import game.GameInfo;
import game.GameFrame;

public class GameplayControl implements Runnable {
    private final int sleepTime;
    private final GameplayView view;
    private final GameplayModel model;
    private final GameFrame frame;
    public GameplayControl(GameplayModel model, GameplayView view, GameFrame frame) {
        this.view = view;
        this.model = model;
        this.sleepTime = GameInfo.tickTime;
        this.frame = frame;
    }
    @Override
    public void run() {
        while (!model.isEnded()) {
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                System.out.println("Hiba a szal futasakor.");
            }
            model.gameStep();
            view.drawStep();
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        int[] data = new int[2];
        data[0] = model.getSeed();
        data[1] = model.getScore();
        frame.changePanel("endscreen", data);
    }
}
