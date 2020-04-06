package edu.duke.ece651.player;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ShowView {
    //------------- Evolution 2 --------------//
    //This Class is aim to load new page by using each methods

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
    public void MainPageView(PlayerHelper player, Stage Window) throws IOException {
        FXMLLoader loaderStart = new FXMLLoader(getClass().getResource("/Map.fxml"));
        loaderStart.setControllerFactory(c->{
            return new Map(player, Window);
        });
        Scene scene = new Scene(loaderStart.load());
        Window.setScene(scene);
        Window.show();
    }
    public void ShowWatchView(PlayerHelper CurrPlayer, Stage Window) throws IOException {
        FXMLLoader loaderStart = new FXMLLoader(getClass().getResource("/Watch.fxml"));
        loaderStart.setControllerFactory(c->{
            return new Watch(CurrPlayer, Window);
        });
        System.out.println("================Reload Watch Page================");
        Scene scene = new Scene(loaderStart.load());
        Window.setScene(scene);
        Window.show();
    }

}
