import game.GameScene;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        GameScene gameScene = new GameScene();

        Scene mainScene = new Scene(gameScene.getRootLayout(), 1300, 1000);

        Button settingsButton = new Button("Settings");
        settingsButton.setOnAction(e -> {
            gameScene.showSettingsMenu();
        });

        gameScene.getRootLayout().getChildren().add(settingsButton);

        primaryStage.setTitle("Tower Defense");
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}