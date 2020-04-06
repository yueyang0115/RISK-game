package edu.duke.ece651.player;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ShowView {
    public void ShowDoneView(String validation,PlayerHelper CurrPlayer, Stage Window) throws IOException {
        FXMLLoader loaderStart = new FXMLLoader(getClass().getResource("/Validation.fxml"));
        loaderStart.setControllerFactory(c -> {
            return new DoneAction(CurrPlayer, validation, Window);
        });
        Scene scene = new Scene(loaderStart.load());
        Window.setScene(scene);
        Window.show();
    }

    public void ShowLoseView(String validation, PlayerHelper CurrPlayer, Stage Window) throws IOException {
        FXMLLoader loaderStart = new FXMLLoader(getClass().getResource("/Lose.fxml"));
        loaderStart.setControllerFactory(c->{
            return new Lose(CurrPlayer, Window, validation);
        });
        Scene scene = new Scene(loaderStart.load());
        Window.setScene(scene);
        Window.show();
    }

    public void ShowEndVIew(String answer, PlayerHelper CurrPlayer, Stage Window) throws IOException {
        FXMLLoader loaderStart = new FXMLLoader(getClass().getResource("/End.fxml"));
        loaderStart.setControllerFactory(c->{
            return new End(CurrPlayer, answer);
        });
        Scene scene = new Scene(loaderStart.load());
        Window.setScene(scene);
        Window.show();
    }
}
