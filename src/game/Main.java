package game;

import java.io.*;
import java.net.URISyntaxException;

import static java.lang.System.exit;

public class Main {
    public static void main(String[] args) {
        String hsPath = getFilePath() + "/highscores.dat";
        fixNoFile(hsPath);

        new GameFrame();
    }

    private static void fixNoFile(String path) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
        } catch (Exception e1) {
            try (FileWriter fileWriter = new FileWriter(path)) {

            } catch (IOException e2) {
                System.out.println("Error on game startup!");
                exit(0);
            }
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