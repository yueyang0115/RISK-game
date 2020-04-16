package edu.duke.ece651.player;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ShowView {
    //------------- Evolution 2 --------------//
    //This Class is aim to load new page by using each methods

    public static void ShowDoneView(String validation,PlayerHelper CurrPlayer, Stage Window) throws IOException {
        FXMLLoader loaderStart = new FXMLLoader(ShowView.class.getResource("/Views/Validation.fxml"));
        loaderStart.setControllerFactory(c -> {
            return new DoneAction(CurrPlayer, validation, Window);
        });
        Scene scene = new Scene(loaderStart.load());
        Window.setScene(scene);
        Window.show();
    }

    public static void ShowLoseView(String validation, PlayerHelper CurrPlayer, Stage Window) throws IOException {
        FXMLLoader loaderStart = new FXMLLoader(ShowView.class.getResource("/Views/Lose.fxml"));
        loaderStart.setControllerFactory(c->{
            return new Lose(CurrPlayer, Window, validation);
        });
        Scene scene = new Scene(loaderStart.load());
        Window.setScene(scene);
        Window.show();
    }

    public static void ShowEndVIew(String answer, PlayerHelper CurrPlayer, Stage Window) throws IOException {
        FXMLLoader loaderStart = new FXMLLoader(ShowView.class.getResource("/Views/End.fxml"));
        loaderStart.setControllerFactory(c->{
            return new End(CurrPlayer, answer);
        });
        Scene scene = new Scene(loaderStart.load());
        Window.setScene(scene);
        Window.show();
    }
    public static void MainPageView(PlayerHelper player, Stage Window, Boolean first) throws IOException {
        FXMLLoader loaderStart = new FXMLLoader(ShowView.class.getResource("/Views/Map.fxml"));
        loaderStart.setControllerFactory(c->{
            return new Map(player, Window, first);
        });
        Scene scene = new Scene(loaderStart.load());
        Window.setScene(scene);
        Window.show();
    }
    public static void ShowWatchView(PlayerHelper CurrPlayer, Stage Window) throws IOException {
        FXMLLoader loaderStart = new FXMLLoader(ShowView.class.getResource("/Views/Watch.fxml"));
        loaderStart.setControllerFactory(c->{
            return new Watch(CurrPlayer, Window);
        });
        System.out.println("================Reload Watch Page================");
        Scene scene = new Scene(loaderStart.load());
        Window.setScene(scene);
        Window.show();
    }
    public static void ShowMoveAttack(PlayerHelper CurrPlayer, String ActionType, Stage Window) throws IOException {
        FXMLLoader loaderStart = new FXMLLoader(ShowView.class.getResource("/Views/Move_attack.fxml"));
        loaderStart.setControllerFactory(c->{
            return new MoveOrAttack(CurrPlayer, ActionType, Window);
        });
        Scene scene = new Scene(loaderStart.load());
        Window.setScene(scene);
        Window.show();
    }

    public static void promptError(String msg) throws IOException {
        Stage newWindow = new Stage();
        FXMLLoader loaderStart = new FXMLLoader(ShowView.class.getResource("/Views/ErrorMsg.fxml"));
        loaderStart.setControllerFactory(c->{
            return new ErrorMsgController(msg, newWindow);
        });
        Scene scene = new Scene(loaderStart.load());
        newWindow.setScene(scene);
        newWindow.show();
    }

}
