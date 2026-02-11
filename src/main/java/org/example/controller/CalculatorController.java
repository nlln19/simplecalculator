package org.example.controller;

import java.util.function.UnaryOperator;
import java.util.regex.Pattern;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import org.example.calc.ExpressionEvaluator;
import org.example.config.UiConfig;

/**
 * Owns all interaction logic (keyboard + button actions) and display behavior.
 */
public final class CalculatorController {

    private final UiConfig config;
    private final ExpressionEvaluator evaluator = new ExpressionEvaluator();

    private TextField display;

    // Special buttons (used exactly like the original implementation, via equals checks)
    private Button enterButton;
    private Button deleteButton;
    private Button clearButton;

    public CalculatorController(UiConfig config) {
        this.config = config;
    }

    public void attachDisplay(TextField display) {
        this.display = display;
        this.display.setOnKeyPressed(this::handleKeyPressed);
        ignoreLetters();
    }

    public void registerSpecialButtons(Button enter, Button delete, Button clear) {
        this.enterButton = enter;
        this.deleteButton = delete;
        this.clearButton = clear;
    }

    /** Binds one button to the original "operation/number" handler. */
    public void bindButton(Button button, String action) {
        button.setOnAction(event -> {
            resizeDisplay();

            /* User pressed equals button */
            if (button.equals(this.enterButton)) {
                handleEnterAction();
            }
            /* User pressed delete button */
            else if (button.equals(this.deleteButton)) {
                handleDeleteAction();
            }
            /* User pressed clear button */
            else if (button.equals(this.clearButton)) {
                clearDisplay();
            }
            /* User pressed other button */
            else {
                this.display.appendText(action);
            }
        });
    }

    /* Handles Keys for display (kept equivalent, including TAB fall-through) */
    private void handleKeyPressed(KeyEvent key) {
        KeyCode code = key.getCode();
        switch (code) {
            case TAB:
                this.display.isFocused();
            case ENTER:
                handleEnterAction();
                break;
            case DELETE:
                clearDisplay();
                break;
            case BACK_SPACE:
                handleDeleteAction();
                break;
        }
    }

    /* Helper functions for button events */
    private void handleDeleteAction() {
        String input = this.display.getText();
        if (input.isEmpty()) return;

        char[] inputAsChar = input.toCharArray();
        char[] sol = new char[inputAsChar.length - 1];

        // same as: copy array but without the last index
        System.arraycopy(inputAsChar, 0, sol, 0, inputAsChar.length - 1);

        this.display.setText(String.valueOf(sol));
    }

    private void handleEnterAction() {
        String input = this.display.getText();
        try {
            double result = evaluator.evaluate(input);

            this.display.clear();
            this.display.setText(String.valueOf(result));
            resizeDisplay();
        } catch (Exception e) {
            this.display.setText("Syntax Error");
        }
    }

    /* Resizes the display text (kept equivalent) */
    private void resizeDisplay() {
        String text = this.display.getText();
        if (text.isEmpty()) return;

        double fontSize = this.display.getFont().getSize();
        Text textNode = new Text(text);
        textNode.setFont(Font.font(fontSize));

        // Forces layout bounds computation similarly to the original code.
        new Scene(new Group(textNode));

        if (fontSize < 40) {
            while (textNode.getLayoutBounds().getWidth() < (config.width() - 100)) {
                fontSize += 1;
                textNode.setFont(Font.font(fontSize));

                if (fontSize >= 40) break;
            }
        }

        while (textNode.getLayoutBounds().getWidth() > (config.width() - 120) && fontSize > 6) {
            fontSize -= 1;
            textNode.setFont(Font.font(fontSize));
        }
        this.display.setFont(Font.font(fontSize));
    }

    /*
     * The display ignores letters from keyboard inputs.
     * Inspired from: https://stackoverflow.com/questions/34407694/javafx-textfield-allow-only-one-letter-to-be-typed
     */
    private void ignoreLetters() {
        Pattern validPattern = Pattern.compile("[0-9+\\-*/().^]*"); // Only numbers and operations are allowed

        UnaryOperator<Change> filter = change -> {
            String newText = change.getControlNewText();
            if (validPattern.matcher(newText).matches()) {
                return change;
            } else {
                return null;
            }
        };

        TextFormatter<String> formatter = new TextFormatter<>(filter);
        this.display.setTextFormatter(formatter);
    }

    private void clearDisplay() {
        this.display.clear();
    }
}
