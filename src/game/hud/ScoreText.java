package game.hud;

import game.GameInfo;

import javax.swing.*;
import java.awt.*;

public class ScoreText extends JLabel {
    public ScoreText() {
        setText("<html><center>SCORE:<br/>   0   </html>");
        setHorizontalAlignment(SwingConstants.CENTER);
        setVerticalAlignment(SwingConstants.CENTER);
        setFont(new Font(Font.SANS_SERIF,  Font.BOLD, 32));
        setBounds(0,0,200,100);
        //add(text);

        setOpaque(true);
        setBounds(10,10,200,100);
        setBackground(GameInfo.menuPanelBg);
        setForeground(new Color(145, 218, 218));
        setVisible(true);
    }

    public void setScore(int score) {
        setText("<html><center>SCORE:<br/>" + score + "</html>");
    }
}
