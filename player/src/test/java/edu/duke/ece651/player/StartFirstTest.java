package edu.duke.ece651.player;

import javafx.application.Application;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.ServerSocket;

public class StartFirstTest extends Application {
    @Test
    public void test_start(){
        String[] args = {"null"};
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        Player TestPlayer = new Player();
        PlayerHelper SocketPlayer = new PlayerHelper();
        SocketPlayer.setID(0);
        TestPlayer.showStartView(SocketPlayer, primaryStage);
        System.out.println("In Start");
        StartController start = new StartController(SocketPlayer, primaryStage);
        start.showChooseView(SocketPlayer, primaryStage);
        start.MainPageView(SocketPlayer);
    }
}
