package org.example;

import java.util.Objects;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.example.config.UiConfig;
import org.example.ui.CalculatorView;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage calculatorStage) {
        // Icon
        var logoUrl = Main.class.getResource("/calculator.png");
        if (logoUrl != null) {
            Image logo = new Image(logoUrl.toExternalForm());
            calculatorStage.getIcons().add(logo);
        }

        UiConfig config = UiConfig.defaultConfig();
        CalculatorView view = new CalculatorView(config);

        // Stage setup
        calculatorStage.setTitle("");
        calculatorStage.setResizable(false);
        calculatorStage.setWidth(config.width());
        calculatorStage.setHeight(config.height());

        // Scene setup
        Scene calculatorScene = new Scene(view.getRoot());
        var cssUrl = Objects.requireNonNull(Main.class.getResource("/style.css"));
        calculatorScene.getStylesheets().add(cssUrl.toExternalForm());

        calculatorStage.setScene(calculatorScene);
        calculatorStage.show();
    }
}
