package game;

import game.menus.GameplayPanel;
import game.menus.EndScreen;
import game.menus.HighscoreMenu;
import game.menus.MainMenu;

import javax.sound.sampled.*;
import javax.swing.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class GameFrame extends JFrame {
    public GameFrame() {
        setLayout(null);
        setTitle("Bubble Attack!");
        add(new MainMenu(this));

        // music
        try {
            //hibas: AudioInputStream audioInputStream  = AudioSystem.getAudioInputStream(new File("resources/bgMusic.wav"));
            byte[] soundBytes = getClass().getResourceAsStream("/bgMusic.wav").readAllBytes();
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(soundBytes);
            AudioInputStream audioInputStream  = AudioSystem.getAudioInputStream(byteArrayInputStream);

            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.setLoopPoints(0,-1);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            System.out.println("IO error");
            e.printStackTrace();
            //exit(0);
        }

        setBounds(0, 0, GameInfo.frameWidth, GameInfo.frameHeight);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }

    public void changePanel(String newPanel, int[] data) {
        getContentPane().removeAll();
        setVisible(false);

        switch (newPanel) {
            case "endscreen":
                add(new EndScreen(data[0], data[1], this));
                break;
            case "game":
                add(new GameplayPanel(data[0], this));
                break;
            case "menu":
                add(new MainMenu(this));
                break;
            case "highscore":
                add(new HighscoreMenu(this));
                break;
        }

        setVisible(true);
        revalidate();
        repaint();
    }
}
