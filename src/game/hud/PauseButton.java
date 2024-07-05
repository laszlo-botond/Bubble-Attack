package game.hud;

import game.GameInfo;
import game.gameplay.GameplayView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PauseButton extends JButton {
    public PauseButton(GameplayView view) {
        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.changePausedVw();
            }
        });
        setLayout(new BorderLayout());
        JLabel pauseText = new JLabel("| |", CENTER);
        pauseText.setFont(new Font(Font.SANS_SERIF,  Font.BOLD, 16));
        pauseText.setForeground(GameInfo.playerColor);
        add(pauseText, BorderLayout.CENTER);
        setFocusable(false);
        setBackground(new Color(0, 0, 255));
        setVisible(true);
    }
}
