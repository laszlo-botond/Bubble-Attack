package game.menus;

import game.GameFrame;
import game.GameInfo;
import game.Main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.exit;

public class EndScreen extends JPanel {
    private BufferedImage img;
    private final int score;
    public EndScreen(int seed, int score, GameFrame frame) {
        try {
            img = ImageIO.read(getClass().getResourceAsStream("/endScreen.png"));
        } catch (IOException e) {
            System.out.println("Hiba: A kep nem talalhato!");
            exit(0);
        }
        setLayout(null);
        this.score = score;

        // score text
        JLabel scoreLabel = new JLabel(""+score);
        scoreLabel.setBounds(140, 520, 1000, 150);
        scoreLabel.setFont(new Font(Font.SANS_SERIF,  Font.BOLD, 144));
        scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        scoreLabel.setForeground(GameInfo.playerColor);
        add(scoreLabel);

        // restart button
        JButton restartButton = new JButton("RESTART");
        restartButton.setBounds(220,388,190,110);
        restartButton.setBackground(GameInfo.playerColor);
        restartButton.setFocusPainted(false);
        restartButton.setFont(new Font(Font.SANS_SERIF,  Font.BOLD, 32));
        int[] restartData = new int[1];
        restartData[0] = seed;
        restartButton.addActionListener(e -> frame.changePanel("game", restartData));
        add(restartButton);

        // menu button
        JButton menuButton = new JButton("<html>MAIN<br/>MENU</html>");
        menuButton.setBounds(870,388,190,110);
        menuButton.setBackground(GameInfo.playerColor);
        menuButton.setFocusPainted(false);
        menuButton.setFont(new Font(Font.SANS_SERIF,  Font.BOLD, 32));
        menuButton.addActionListener(e -> frame.changePanel("menu", null));
        add(menuButton);

        // check potential highscore
        checkForHighscore();

        setBounds(0, 0, GameInfo.frameWidth, GameInfo.frameHeight);
        setVisible(true);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(img, 0, 0, null);
    }

    private void checkForHighscore() {
        String path = getFilePath() + "/highscores.dat";
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            List<Integer> bestScores = new ArrayList<>(
                    reader.lines()
                            .map(Integer::parseInt)
                            .toList());
            reader.close();
            bestScores.add(score);
            bestScores.sort((a,b) -> Integer.compare(b,a)); // descending order
            try (FileWriter fileWriter = new FileWriter(path)) {
                int count = 0;
                for (Integer i : bestScores) {
                    count++;
                    fileWriter.write(i + System.lineSeparator());
                    if (count == 5) {
                        break;
                    }
                }
                while (count < 5) {
                    count++;
                    fileWriter.write(0 + System.lineSeparator());
                }
                fileWriter.close();
            } catch (Exception e) {
                System.out.println("Hiba tortent a pontszam mentesekor!");
                exit(0);
            }
        } catch (IOException e) {
            System.out.println("Hiba tortent a pontszam mentesekor!");
            e.printStackTrace();
            exit(0);
        }
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
