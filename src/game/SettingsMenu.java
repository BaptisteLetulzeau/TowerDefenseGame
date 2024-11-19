package game;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class SettingsMenu {

    private final VBox settingsLayout;
    private double currentBrightness = 0.5;
    private double currentVolume = 50;
    private Scene mainScene;
    private MediaPlayer mediaPlayer;

    public SettingsMenu(Scene mainScene, MediaPlayer mediaPlayer) {
        this.mainScene = mainScene;
        this.mediaPlayer = mediaPlayer;
        settingsLayout = new VBox(15);

        Label brightnessLabel = new Label("Brightness :");
        Slider brightnessSlider = new Slider(0, 1, currentBrightness);
        brightnessSlider.setShowTickLabels(true);
        brightnessSlider.setShowTickMarks(true);

        brightnessSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            adjustBrightness(newValue.doubleValue());
        });

        Label volumeLabel = new Label("Volume :");
        Slider volumeSlider = new Slider(0, 100, currentVolume);
        volumeSlider.setShowTickLabels(true);
        volumeSlider.setShowTickMarks(true);

        volumeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            adjustVolume(newValue.doubleValue());
        });

        Button pauseMusicButton = new Button("Pause music");
        pauseMusicButton.setOnAction(e -> {
            if (mediaPlayer != null) {
                mediaPlayer.pause();
                System.out.println("Musique mise en pause.");
            }
        });

        Button playMusicButton = new Button("Play music");
        playMusicButton.setOnAction(e -> {
            if (mediaPlayer != null) {
                mediaPlayer.play();
                System.out.println("Musique relancée.");
            }
        });

        settingsLayout.getChildren().addAll(brightnessLabel, brightnessSlider, volumeLabel, volumeSlider, pauseMusicButton, playMusicButton);
    }

    private void adjustBrightness(double value) {
        currentBrightness = value;
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(currentBrightness - 0.5);
        mainScene.getRoot().setEffect(colorAdjust);
    }

    private void adjustVolume(double value) {
        currentVolume = value;
        if (mediaPlayer != null) {
            mediaPlayer.setVolume(currentVolume / 100);
        }
    }

    public void display() {
        Stage settingsStage = new Stage();
        settingsStage.setTitle("Settings");

        Scene settingsScene = new Scene(settingsLayout, 400, 300);
        settingsStage.setScene(settingsScene);
        settingsStage.show();
    }
}