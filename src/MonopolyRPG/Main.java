package MonopolyRPG;

import MonopolyRPG.Controller.GameModePage;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.Scanner;


public class Main extends Application {
    private static MusicList musicList;
    public static void main(String[] args) {

        musicList = new MusicList();
        musicList.play(1);

        GameModePage gameModePage = new GameModePage();
        gameModePage.start();

    }

    @Override
    public void start(Stage stage) throws Exception {

    }
}