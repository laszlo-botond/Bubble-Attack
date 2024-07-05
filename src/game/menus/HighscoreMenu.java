package game.menus;

import game.GameFrame;
import game.GameInfo;
import game.Main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static java.lang.System.exit;

public class HighscoreMenu extends JPanel {
    private BufferedImage img;
    private GameFrame frame;
    public HighscoreMenu(GameFrame frame) {
        this.frame = frame;

        setLayout(null);
        setBounds(0,0,1280,720);
        try {
            img = ImageIO.read(getClass().getResourceAsStream("/hsScreen.png"));
        } catch (IOException e) {
            System.out.println("Hiba: A kep nem talalhato!");
            exit(0);
        }

        // back button
        JButton backButton = new JButton("BACK");
        backButton.setBounds(1000, 500, 200, 100);
        backButton.setFont(new Font(Font.SANS_SERIF,  Font.BOLD, 32));
        backButton.setFocusPainted(false);
        backButton.addActionListener(e -> frame.changePanel("menu", null));
        add(backButton);

        // load and show highscores
        String path = getFilePath() + "/highscores.dat";
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            List<Integer> bestScores = new ArrayList<>(
                    reader.lines()
                            .map(Integer::parseInt)
                            .toList());
            reader.close();
            // fill with 0
            while (bestScores.size() < 5) {
                bestScores.add(0);
            }
            // show each
            IntStream.range(0,5)
                    .forEach(i -> {
                        JLabel scoreLabel = new JLabel(String.valueOf(bestScores.get(i)), SwingConstants.CENTER);
                        scoreLabel.setFont(new Font(Font.SANS_SERIF,  Font.BOLD, 75));
                        scoreLabel.setForeground(GameInfo.playerColor);
                        scoreLabel.setBounds(410, 250+i*75, 460, 75);
                        add(scoreLabel);
                    });
        } catch (IOException e) {
            System.out.println("Hiba a pontszamok betoltesekor!");
        }

        setVisible(true);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(img, 0, 0, null);
    }

    private static String getFilePath() {
        File file = null;
        try {
            file = new File(Main.class.getProtectionDomain().getCodeSource()
                    .getLocation().toURI().getPath());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        return file.getParent();
    }
}
