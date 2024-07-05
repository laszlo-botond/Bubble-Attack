package game.gameplay;

public class GameTimer implements Runnable {
    private int seconds = 0;
    private GameplayModel model;
    private GameplayView view;
    public GameTimer(GameplayModel model, GameplayView view) {
        this.model = model;
        this.view = view;
    }
    @Override
    public void run() {
        while (!model.isEnded()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("Hiba a szal futasakor.");
            }
            view.updateTime(seconds++);
        }
    }
}
