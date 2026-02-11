package org.example;

import java.util.Objects;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.example.config.UiConfig;
import org.example.ui.CalculatorView;

public class Main extends Application {

    /*
     * Icon from: https://www.flaticon.com/free-icon/calculator_1011863
     */
    private final Image logo = new Image("./calculator.png");

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage calculatorStage) {
        UiConfig config = UiConfig.defaultConfig();
        CalculatorView view = new CalculatorView(config);

        /* Stage setup */
        calculatorStage.setTitle("");
        calculatorStage.setResizable(false);
        calculatorStage.setWidth(config.width());
        calculatorStage.setHeight(config.height());
        calculatorStage.getIcons().add(this.logo);

        /* Scene setup */
        Scene calculatorScene = new Scene(view.getRoot());
        calculatorScene.getStylesheets().add(Objects.requireNonNull(
                getClass().getResource("/style.css")).toExternalForm());
        calculatorStage.setScene(calculatorScene);
        calculatorStage.show();
    }
}
