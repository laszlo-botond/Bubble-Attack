package game.gameplay;

import game.GameInfo;
import game.entities.Entity;
import game.entities.PlayerSquare;
import game.hud.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;

import static java.lang.System.exit;

// move all game logic to Model
public class GameplayView extends JPanel {
    private final GameplayModel model;
    private BufferedImage backgroundImg;
    private final ScoreText scoreText;
    private final JLabel timeLabel;
    public GameplayView(GameplayModel model) {
        this.model = model;
        try {
            backgroundImg = ImageIO.read(getClass().getResourceAsStream("/bg5.png"));;
        } catch (IOException e) {
            System.out.println("Hiba: A kep nem talalhato!");
            exit(0);
        }
        // KeyListener
        addKeyListener(new PlayerControls(model.getPlayerSquare()));

        setLayout(null);

        // UI elements
        // score
        add(scoreText = new ScoreText());
        // pause
        PauseButton pauseButton = new PauseButton(this);
        pauseButton.setBounds(1200,10,50,50);
        add(pauseButton);
        // time
        timeLabel = new JLabel("TIME: 0:00", SwingConstants.CENTER);
        timeLabel.setFont(new Font(Font.SANS_SERIF,  Font.BOLD, 16));
        timeLabel.setBounds(500, 0, 280, 100);
        timeLabel.setForeground(new Color(145, 218, 218));
        add(timeLabel);

        setBounds(0, 0, GameInfo.frameWidth, GameInfo.frameHeight);
        setFocusable(true);
        setVisible(true);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImg,0,0,null);

        ArrayList<Entity> entities = model.getEntities();
        try {
            if (!entities.isEmpty()) {
                for (Entity thisEntity : entities) {
                    thisEntity.drawEntity(g);
                }
            }
        } catch (ConcurrentModificationException e) {
            // first frame problem
        };

    }

    public void updateTime(int seconds) {
        int sec = seconds%60;
        String mid = ":";
        if (sec < 10) {
            mid = ":0";
        }
        timeLabel.setText("TIME: " + seconds/60 + mid + seconds%60);
    }

    public void drawStep() {
        scoreText.setScore(model.getScore());
        repaint();
    }

    public void changePausedVw() {
        model.changePaused();
    }

    // controls listener declaration
    // because input listener must be inside the panel element
    private static class PlayerControls implements KeyListener {
        private final PlayerSquare player;
        public PlayerControls(game.entities.PlayerSquare player) {
            this.player = player;
        }

        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();

            switch (key) {
                case KeyEvent.VK_RIGHT:
                    player.setInput('r');
                    break;
                case KeyEvent.VK_LEFT:
                    player.setInput('l');
                    break;
                case KeyEvent.VK_UP:
                    player.setInput('u');
                    break;
                case KeyEvent.VK_DOWN:
                    player.setInput('d');
                    break;
                case 32: // SpaceBar
                    player.setInput(' ');
                    break;
            }

        }

        @Override
        public void keyReleased(KeyEvent e) {}
        @Override
        public void keyTyped(KeyEvent e) {}
    }
}
