package org.example.ui;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import org.example.config.UiConfig;
import org.example.controller.CalculatorController;

public final class CalculatorView {

    private final UiConfig config;
    private final CalculatorController controller;

    private final VBox root = new VBox();
    private final TextField display = new TextField();

    // Rows
    private HBox inputAndSolution;
    private HBox operationsRow;
    private HBox firstNumberRow;
    private HBox secondNumberRow;
    private HBox thirdNumberRow;
    private HBox fourthNumberRow;

    // Number buttons
    private final Button b0 = new Button("0");
    private final Button b1 = new Button("1");
    private final Button b2 = new Button("2");
    private final Button b3 = new Button("3");
    private final Button b4 = new Button("4");
    private final Button b5 = new Button("5");
    private final Button b6 = new Button("6");
    private final Button b7 = new Button("7");
    private final Button b8 = new Button("8");
    private final Button b9 = new Button("9");

    // Operation buttons
    private final Button bPlus = new Button("+");
    private final Button bMinus = new Button("-");
    private final Button bDivision = new Button("÷");
    private final Button bMultiplication = new Button("×");
    private final Button bComma = new Button(".");
    private final Button bRoot = new Button("√");
    private final Button bPow = new Button("x²");
    private final Button bPowX = new Button("xˣ");
    private final Button bEnter = new Button("=");
    private final Button bDelete = new Button("⌫");
    private final Button bLeftBracket = new Button("(");
    private final Button bRightBracket = new Button(")");
    private final Button bClear = new Button("C");
    private final Button bPercent = new Button("%");
    private final Button bPi = new Button("π");

    public CalculatorView(UiConfig config) {
        this.config = config;
        this.controller = new CalculatorController(config);

        initUi();
    }

    public VBox getRoot() {
        return root;
    }

    private void initUi() {
        initFirstNumberRow();
        initSecondNumberRow();
        initThirdNumberRow();
        initFourthNumberRow();
        initOperationsRow();
        initDisplay();
        initRoot();

        controller.attachDisplay(display);
        controller.registerSpecialButtons(bEnter, bDelete, bClear);
    }

    /* Root VBox */
    private void initRoot() {
        root.setPrefWidth(config.width());
        root.setPrefHeight(config.height());
        root.getChildren().addAll(
                inputAndSolution,
                operationsRow,
                firstNumberRow,
                secondNumberRow,
                thirdNumberRow,
                fourthNumberRow
        );
    }

    /* Display */
    private void initDisplay() {
        display.setAlignment(Pos.CENTER);
        display.setFont(Font.font(40));
        display.setPromptText("0");
        display.setEditable(true);
        display.setFocusTraversable(false);
        display.setPrefWidth(config.width());
        display.setPrefHeight(config.buttonHeight());
        display.getStyleClass().add("my-display");

        inputAndSolution = new HBox(display);
        inputAndSolution.setPrefWidth(config.width());
        inputAndSolution.setPrefHeight(config.buttonHeight());
    }

    /* Operations row */
    private void initOperationsRow() {
        setupButton(bPlus, "+");
        setupButton(bMinus, "-");
        setupButton(bMultiplication, "*");
        setupButton(bDivision, "/");
        setupButton(bDelete, "del");

        operationsRow = new HBox(bPlus, bMinus, bMultiplication, bDivision, bDelete);
        operationsRow.setPrefWidth(config.width());
        operationsRow.setPrefHeight(config.buttonHeight());
    }

    /* First number row */
    private void initFirstNumberRow() {
        setupButton(b1, "1");
        setupButton(b2, "2");
        setupButton(b3, "3");
        setupButton(bRoot, "sqrt(");
        setupButton(bPercent, "%");

        firstNumberRow = new HBox(b1, b2, b3, bRoot, bPercent);
        firstNumberRow.setPrefWidth(config.width());
        firstNumberRow.setPrefHeight(config.buttonHeight());
    }

    /* Second number row */
    private void initSecondNumberRow() {
        setupButton(b4, "4");
        setupButton(b5, "5");
        setupButton(b6, "6");
        setupButton(bPow, "^2");
        setupButton(bPowX, "^");

        secondNumberRow = new HBox(b4, b5, b6, bPow, bPowX);
        secondNumberRow.setPrefWidth(config.width());
        secondNumberRow.setPrefHeight(config.buttonHeight());
    }

    /* Third number row */
    private void initThirdNumberRow() {
        setupButton(b7, "7");
        setupButton(b8, "8");
        setupButton(b9, "9");
        setupButton(bLeftBracket, "(");
        setupButton(bRightBracket, ")");

        thirdNumberRow = new HBox(b7, b8, b9, bLeftBracket, bRightBracket);
        thirdNumberRow.setPrefWidth(config.width());
        thirdNumberRow.setPrefHeight(config.buttonHeight());
    }

    /* Fourth number row */
    private void initFourthNumberRow() {
        setupButton(bClear, "clear");
        setupButton(b0, "0");
        setupButton(bPi, "π");
        setupButton(bComma, ".");
        setupButton(bEnter, "=");

        fourthNumberRow = new HBox(bClear, b0, bPi, bComma, bEnter);
        fourthNumberRow.setPrefWidth(config.width());
        fourthNumberRow.setPrefHeight(config.buttonHeight());
    }

    /* Common button setup */
    private void setupButton(Button button, String action) {
        button.setPrefHeight(config.buttonHeight());
        button.setPrefWidth(config.buttonWidth());
        button.getStyleClass().add("my-button");
        controller.bindButton(button, action);
    }
}
