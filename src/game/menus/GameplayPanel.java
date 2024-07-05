package game.menus;

import game.GameInfo;
import game.GameFrame;
import game.gameplay.GameplayControl;
import game.gameplay.GameplayModel;
import game.gameplay.GameplayView;
import game.gameplay.GameTimer;

import javax.swing.*;

public class GameplayPanel extends JPanel {
    public GameplayPanel(int seed, GameFrame frame) {
        setLayout(null);
        GameplayModel model = new GameplayModel(seed);
        GameplayView view = new GameplayView(model);
        GameplayControl control = new GameplayControl(model, view, frame);
        GameTimer gameTimer = new GameTimer(model, view);

        Thread gameSzal = new Thread(control);
        Thread timerSzal = new Thread(gameTimer);
        gameSzal.start();
        timerSzal.start();
        add(view);

        setBounds(0, 0, GameInfo.frameWidth, GameInfo.frameHeight);
        setVisible(true);
    }
}
