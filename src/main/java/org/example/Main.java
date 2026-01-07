package org.example;

import java.util.Objects;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class Main extends Application {

	/* Scaling */
	private final double WIDTH = 500;
	private final double HEIGHT = 600;
	private final double BUTTON_HEIGHT = HEIGHT / 6;
	private final double BUTTON_WIDTH = WIDTH / 5;

	/*
	* Icon from: https://www.flaticon.com/free-icon/calculator_1011863
	*/
	private final Image logo = new Image("./calculator.png");

	/* Contains the display and number/operation buttons */
	private final VBox calculator = new VBox();

	/* TextField functions as a display for input/output */
	private final TextField display = new TextField();

	/* HBoxes for the buttons */
	private HBox input_and_solution;
	private HBox operations_row;
	private HBox first_number_row;
	private HBox second_number_row;
	private HBox third_number_row;
	private HBox fourth_number_row;

	/* Buttons for number input */
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

	/* Buttons for operations */
	private final Button b_plus  = new Button("+");
	private final Button b_minus  = new Button("-");
	private final Button b_division  = new Button("÷");
	private final Button b_multiplication  = new Button("×");
	private final Button b_comma  = new Button(".");
	private final Button b_root  = new Button("√");
	private final Button b_pow  = new Button("x²");
	private final Button b_pow_x  = new Button("xˣ");
	private final Button b_enter = new Button("=");
	private final Button b_delete = new Button("⌫");
	private final Button b_left_bracket = new Button("(");
	private final Button b_right_bracket = new Button(")");
	private final Button b_clear = new Button("C");
	private final Button b_percent = new Button("%");
	private final Button b_pi = new Button("π");

	/* Main method to start the Application */
	public static void main(String[] args) {
		launch(args);
	}

	/* Starts the Application */
	@Override
	public void start(Stage calculatorStage) {
		init_calculator_ui();

		/* Stage setup */
		calculatorStage.setTitle("");
		calculatorStage.setResizable(false);
		calculatorStage.setWidth(WIDTH);
		calculatorStage.setHeight(HEIGHT);
		calculatorStage.getIcons().add(this.logo);

		/* Scene setup */
		Scene calculatorScene = new Scene(displayCalculator());
		calculatorScene.getStylesheets().add(Objects.requireNonNull(
			getClass().getResource("/style.css")).toExternalForm());
		calculatorStage.setScene(calculatorScene);
		calculatorStage.show();
	}

	/* Handles Keys for display */
	private void handle_key_pressed(KeyEvent key) {
			KeyCode code = key.getCode();
			switch (code) {
				case TAB:
					this.display.isFocused();
				case ENTER:
					handle_enter_action();
					break;
				case DELETE:
					clear_display();
					break;
				case BACK_SPACE:
					handle_delete_action();
					break;
			}
	}

	/* Returns the calculator */
	private VBox displayCalculator() {
		return this.calculator;
	}

	/* Initializes the calculator */
	private void init_calculator_ui(){
		init_first_number_row();
		init_second_number_row();
		init_third_number_row();
		init_fourth_number_row();

		init_operations_row();

		init_display();

		init_calculator();
	}

	/* Init calculator (VBox) */
	private void init_calculator() {
		this.calculator.setPrefWidth(WIDTH);
		this.calculator.setPrefHeight(HEIGHT);
		this.calculator.getChildren().addAll(this.input_and_solution, this.operations_row,
			this.first_number_row, this.second_number_row,
			this.third_number_row, this.fourth_number_row
		);
	}

	/* Init TextField */
	private void init_display() {
		this.display.setAlignment(Pos.CENTER);
		this.display.setFont(Font.font(40));
		this.display.setPromptText("0");
		this.display.setEditable(true);
		this.display.setFocusTraversable(false);
		this.display.setPrefWidth(WIDTH);
		this.display.setPrefHeight(BUTTON_HEIGHT);
		this.display.getStyleClass().add("my-display");
		this.display.setOnKeyPressed(this::handle_key_pressed);
		ignore_letters();

		this.input_and_solution = new HBox(this.display);
		this.input_and_solution.setPrefWidth(WIDTH);
		this.input_and_solution.setPrefHeight(BUTTON_HEIGHT);
	}

	/* Init operations row */
	private void init_operations_row() {
		this.b_plus.setPrefHeight(BUTTON_HEIGHT);
		this.b_plus.setPrefWidth(BUTTON_WIDTH);
		style_button(this.b_plus);
		button_pressed_action_for_operations_and_numbers(this.b_plus, "+");

		this.b_minus.setPrefHeight(BUTTON_HEIGHT);
		this.b_minus.setPrefWidth(BUTTON_WIDTH);
		style_button(this.b_minus);
		button_pressed_action_for_operations_and_numbers(this.b_minus, "-");

		this.b_multiplication.setPrefHeight(BUTTON_HEIGHT);
		this.b_multiplication.setPrefWidth(BUTTON_WIDTH);
		style_button(this.b_multiplication);
		button_pressed_action_for_operations_and_numbers(this.b_multiplication, "*");

		this.b_division.setPrefHeight(BUTTON_HEIGHT);
		this.b_division.setPrefWidth(BUTTON_WIDTH);
		style_button(this.b_division);
		button_pressed_action_for_operations_and_numbers(this.b_division, "/");

		this.b_delete.setPrefHeight(BUTTON_HEIGHT);
		this.b_delete.setPrefWidth(BUTTON_WIDTH);
		style_button(this.b_delete);
		button_pressed_action_for_operations_and_numbers(this.b_delete, "del");

		this.operations_row = new HBox(this.b_plus,
			this.b_minus, this.b_multiplication, this.b_division, this.b_delete
		);
		this.operations_row.setPrefWidth(WIDTH);
		this.operations_row.setPrefHeight(BUTTON_HEIGHT);
	}

	/* Init first number row */
	private void init_first_number_row() {
		this.b1.setPrefHeight(BUTTON_HEIGHT);
		this.b1.setPrefWidth(BUTTON_WIDTH);
		style_button(this.b1);
		button_pressed_action_for_operations_and_numbers(this.b1, "1");

		this.b2.setPrefHeight(BUTTON_HEIGHT);
		this.b2.setPrefWidth(BUTTON_WIDTH);
		style_button(this.b2);
		button_pressed_action_for_operations_and_numbers(this.b2, "2");

		this.b3.setPrefHeight(BUTTON_HEIGHT);
		this.b3.setPrefWidth(BUTTON_WIDTH);
		style_button(this.b3);
		button_pressed_action_for_operations_and_numbers(this.b3, "3");

		this.b_root.setPrefHeight(BUTTON_HEIGHT);
		this.b_root.setPrefWidth(BUTTON_WIDTH);
		style_button(this.b_root);
		button_pressed_action_for_operations_and_numbers(this.b_root, "sqrt(");

		this.b_percent.setPrefHeight(BUTTON_HEIGHT);
		this.b_percent.setPrefWidth(BUTTON_WIDTH);
		style_button(this.b_percent);
		button_pressed_action_for_operations_and_numbers(this.b_percent, "%");

		this.first_number_row = new HBox(this.b1, this.b2, this.b3, this.b_root, this.b_percent);
		this.first_number_row.setPrefWidth(WIDTH);
		this.first_number_row.setPrefHeight(BUTTON_HEIGHT);
	}

	/* Init second number row */
	private void init_second_number_row() {
		this.b4.setPrefHeight(BUTTON_HEIGHT);
		this.b4.setPrefWidth(BUTTON_WIDTH);
		style_button(this.b4);
		button_pressed_action_for_operations_and_numbers(this.b4, "4");

		this.b5.setPrefHeight(BUTTON_HEIGHT);
		this.b5.setPrefWidth(BUTTON_WIDTH);
		style_button(this.b5);
		button_pressed_action_for_operations_and_numbers(this.b5, "5");

		this.b6.setPrefHeight(BUTTON_HEIGHT);
		this.b6.setPrefWidth(BUTTON_WIDTH);
		style_button(this.b6);
		button_pressed_action_for_operations_and_numbers(this.b6, "6");

		this.b_pow.setPrefHeight(BUTTON_HEIGHT);
		this.b_pow.setPrefWidth(BUTTON_WIDTH);
		style_button(this.b_pow);
		button_pressed_action_for_operations_and_numbers(this.b_pow, "^2");

		this.b_pow_x.setPrefHeight(BUTTON_HEIGHT);
		this.b_pow_x.setPrefWidth(BUTTON_WIDTH);
		style_button(this.b_pow_x);
		button_pressed_action_for_operations_and_numbers(this.b_pow_x, "^");

		this.second_number_row = new HBox(this.b4, this.b5, this.b6, this.b_pow, this.b_pow_x);
		this.second_number_row.setPrefWidth(WIDTH);
		this.second_number_row.setPrefHeight(BUTTON_HEIGHT);
	}

	/* Init third number row */
	private void init_third_number_row() {
		this.b7.setPrefHeight(BUTTON_HEIGHT);
		this.b7.setPrefWidth(BUTTON_WIDTH);
		style_button(this.b7);
		button_pressed_action_for_operations_and_numbers(this.b7, "7");

		this.b8.setPrefHeight(BUTTON_HEIGHT);
		this.b8.setPrefWidth(BUTTON_WIDTH);
		style_button(this.b8);
		button_pressed_action_for_operations_and_numbers(this.b8, "8");

		this.b9.setPrefHeight(BUTTON_HEIGHT);
		this.b9.setPrefWidth(BUTTON_WIDTH);
		style_button(this.b9);
		button_pressed_action_for_operations_and_numbers(this.b9, "9");

		this.b_right_bracket.setPrefHeight(BUTTON_HEIGHT);
		this.b_right_bracket.setPrefWidth(BUTTON_WIDTH);
		style_button(this.b_right_bracket);
		button_pressed_action_for_operations_and_numbers(this.b_right_bracket, ")");

		this.b_left_bracket.setPrefHeight(BUTTON_HEIGHT);
		this.b_left_bracket.setPrefWidth(BUTTON_WIDTH);
		style_button(this.b_left_bracket);
		button_pressed_action_for_operations_and_numbers(this.b_left_bracket, "(");

		this.third_number_row = new HBox(this.b7, this.b8, this.b9, this.b_left_bracket, this.b_right_bracket);
		this.third_number_row.setPrefWidth(WIDTH);
		this.third_number_row.setPrefHeight(BUTTON_HEIGHT);
	}

	/* Init fourth number row */
	private void init_fourth_number_row() {
		this.b_clear.setPrefHeight(BUTTON_HEIGHT);
		this.b_clear.setPrefWidth(BUTTON_WIDTH);
		style_button(this.b_clear);
		button_pressed_action_for_operations_and_numbers(this.b_clear, "clear");

		this.b_comma.setPrefHeight(BUTTON_HEIGHT);
		this.b_comma.setPrefWidth(BUTTON_WIDTH);
		style_button(this.b_comma);
		button_pressed_action_for_operations_and_numbers(this.b_comma, ".");

		this.b0.setPrefHeight(BUTTON_HEIGHT);
		this.b0.setPrefWidth(BUTTON_WIDTH);
		style_button(this.b0);
		button_pressed_action_for_operations_and_numbers(this.b0, "0");

		this.b_pi.setPrefHeight(BUTTON_HEIGHT);
		this.b_pi.setPrefWidth(BUTTON_WIDTH);
		style_button(this.b_pi);
		button_pressed_action_for_operations_and_numbers(this.b_pi, "π");

		this.b_enter.setPrefHeight(BUTTON_HEIGHT);
		this.b_enter.setPrefWidth(BUTTON_WIDTH);
		style_button(this.b_enter);
		button_pressed_action_for_operations_and_numbers(this.b_enter, "=");

		this.fourth_number_row = new HBox(this.b_clear, this.b0, this.b_pi, this.b_comma, this.b_enter);
		this.fourth_number_row.setPrefWidth(WIDTH);
		this.fourth_number_row.setPrefHeight(BUTTON_HEIGHT);
	}

	/* Function to handle all button events */
	private void button_pressed_action_for_operations_and_numbers(Button b, String action) {
		b.setOnAction((event) -> {
			resize_display();

			/* User pressed equals button */
			if(b.equals(this.b_enter))
				handle_enter_action();

			/* User pressed delete button */
			else if(b.equals(this.b_delete))
				handle_delete_action();

			/* User pressed clear button */
			else if(b.equals(this.b_clear))
				clear_display();

			/* User pressed other button */
			else
				this.display.appendText(action);
		});
	}

	/* Helper functions for button events */
	private void handle_delete_action() {
		String input = this.display.getText();
		if(input.isEmpty())
			return;
		char[] input_as_char = input.toCharArray();
		char[] sol = new char[input_as_char.length-1];

		/*
		same as: (copy array but without the last index)
		for(int i = 0; i < input_as_char.length-1; i++) {
			sol[i] = input_as_char[i];
		}
		*/
		System.arraycopy(input_as_char, 0, sol, 0, input_as_char.length - 1);

		this.display.setText(String.valueOf(sol));
	}

	private void handle_enter_action() {
		String input = this.display.getText();
		try {
			Expression exp = new ExpressionBuilder(input)
				.variable("π")
				.build()
				.setVariable("π", Math.PI);

			this.display.clear();
			this.display.setText(String.valueOf(exp.evaluate()));
			resize_display();
		} catch (Exception e) {
			this.display.setText("Syntax Error");
		}
	}

	/* Resizes the display text */
	private void resize_display() {
		String text = this.display.getText();
		if (text.isEmpty()) return;

		double fontSize = this.display.getFont().getSize();
		Text textNode = new Text(text);
		textNode.setFont(Font.font(fontSize));

		new Scene(new Group(textNode));

		if(fontSize < 40){
			while(textNode.getLayoutBounds().getWidth() < (WIDTH - 100)) {
				fontSize += 1;
				textNode.setFont(Font.font(fontSize));

				if(fontSize >= 40) break;
			}
		}

		while (textNode.getLayoutBounds().getWidth() > (WIDTH - 120) && fontSize > 6) {
			fontSize -= 1;
			textNode.setFont(Font.font(fontSize));
		}
		this.display.setFont(Font.font(fontSize));
	}

	/*
	* The display ignores letters from keyboard inputs
	* inspired from: https://stackoverflow.com/questions/34407694/javafx-textfield-allow-only-one-letter-to-be-typed
	* */
	private void ignore_letters() {
		Pattern validPattern = Pattern.compile("[0-9+\\-*/().^]*"); /* Only numbers and operations are allowed*/

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

	/* Clears the display */
	private void clear_display() {
		this.display.clear();
	}

	/* Styling for buttons using css (resources/style.css) */
	private void style_button(Button b) {
		b.getStyleClass().add("my-button");
	}
}