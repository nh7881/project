package yut;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Getter;
import yut.utils.ContextUtil;

public class YutGameApp extends Application {

    @Getter
    private static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
        primaryStage.setTitle("Yut Game");
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.show();

        this.primaryStage = primaryStage;
        ContextUtil.saveData(ContextUtil.ContextKey.IS_START, false);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
