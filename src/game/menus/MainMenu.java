package game.menus;

import game.GameFrame;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static java.lang.System.exit;

public class MainMenu extends JPanel {
    private BufferedImage backgroundImg;
    public MainMenu(GameFrame frame) {
        setLayout(null);
        setBounds(0,0,1280, 720);
        try {
            //hibas: backgroundImg = ImageIO.read(new File("resources/title.png"));
            backgroundImg = ImageIO.read(getClass().getResourceAsStream("/title.png"));
        } catch (IOException e) {
            System.out.println("Hiba: A kep nem talalhato!");
            exit(0);
        }

        // play button
        JTextField seedField = new JTextField();
        seedField.setBounds(641,290,229,73);
        seedField.setFont(new Font(Font.SANS_SERIF,  Font.BOLD, 32));
        seedField.setText("Enter seed");
        add(seedField);

        JButton playButton = new JButton();
        playButton.setBounds(410, 290, 229, 73);
        playButton.setFont(new Font(Font.SANS_SERIF,  Font.BOLD, 32));
        playButton.setText("PLAY");
        playButton.setFocusPainted(false);
        playButton.addActionListener(e -> {
            int[] playData = new int[1];
            playData[0] = seedField.getText().hashCode();
            frame.changePanel("game", playData);
        });
        add(playButton);

        // high scores button
        JButton scoresButton = new JButton();
        scoresButton.setBounds(410, 406, 460, 73);
        scoresButton.setFont(new Font(Font.SANS_SERIF,  Font.BOLD, 32));
        scoresButton.setText("HIGHSCORES");
        scoresButton.setFocusPainted(false);
        scoresButton.addActionListener(e -> frame.changePanel("highscore", null));
        add(scoresButton);

        // quit button
        JButton quitButton = new JButton();
        quitButton.setBounds(410, 522, 460, 73);
        quitButton.setFont(new Font(Font.SANS_SERIF,  Font.BOLD, 32));
        quitButton.setText("QUIT");
        quitButton.setFocusPainted(false);
        quitButton.addActionListener(e -> exit(0));
        add(quitButton);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImg,0,0,null);
    }
}
