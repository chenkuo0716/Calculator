package calculette;

import java.util.Stack;
import javafx.application.Application;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Calculette extends Application {
	// Generate text box
	TextField text1 = new TextField("");
	TextField text2 = new TextField("");
	TextField text3 = new TextField("");
	TextField text4 = new TextField("");
	TextField text5 = new TextField("");

	public void start(Stage pr) {
		// Text box parameters
		text1.setFont(Font.font(23.5));
		text2.setFont(Font.font(23.5));
		text3.setFont(Font.font(23.5));
		text4.setFont(Font.font(23.5));
		text5.setFont(Font.font(23.5));
		text1.setEditable(false);
		text2.setEditable(false);
		text3.setEditable(false);
		text4.setEditable(false);
		text5.setEditable(false);

		// Array Button
		String[] number = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
		Button[] b = new Button[10];
		for (int i = 0; i < 10; i++) {
			b[i] = new Button(number[i]);
			b[i].setPrefSize(80, 40);
		}

		// Operation symbol button set
		Stack<String> stack = new Stack<>();
		String[] operate = {"+", "-", "*", "/", "<>"};
		Button[] o = new Button[5];
		for(int i = 0; i < 5; i++) {
			o[i] = new Button(operate[i]);
			o[i].setPrefSize(80, 40);
			String s = operate[i].toString();		// String array to string
			o[i].setOnAction(e->calculer(s, stack));
		}

		// Function button
		Button c = new Button("C");
		c.setPrefSize(80, 40);
		c.setOnAction(e->calculer("C", stack));
		Button point = new Button(".");
		point.setPrefSize(80, 40);
		point.setOnAction(e->calculer(".", stack));
		Button negative = new Button("+/-");
		negative.setPrefSize(80, 40);
		negative.setOnAction(e->calculer("n", stack));

		// JAVAFX layout
		BorderPane pane0 = new BorderPane();
		GridPane pane1 = new GridPane();
		GridPane pane2 = new GridPane();
		pane0.setTop(pane1);
		pane0.setBottom(pane2);

		pane1.setPadding(new Insets(8, 8, 8, 8));
		pane1.add(text5, 0, 0);
		pane1.add(text4, 0, 1);
		pane1.add(text3, 0, 2);
		pane1.add(text2, 0, 3);
		pane1.add(text1, 0, 4);

		for(int i=0;i<5;i++)
			pane2.add(o[i], 3, i+1);
		for (int i = 0, count = 7; i < 3; i++, count = count - 3)
			for (int j = 0; j < 3; j++)
				pane2.add(b[count + j], j, i+2);
		pane2.add(b[0], 0, 5);
		pane2.add(c, 0, 1);
		pane2.add(point, 1, 5);
		pane2.add(negative, 2, 5);

		// Button implementation
		for (int i = 0; i < 10; i++) {
			String carriage = String.valueOf(i);
			b[i].setOnAction(e -> calculer(carriage, stack));
		}
		Scene scene = new Scene(pane0);
		pr.setTitle("Ma Calculatrice");
		pr.setScene(scene);
		pr.show();
	}

	private void play(String out, Stack<String> stack, int n) {
		if (n >= 4) {
			Stack<String> stack_read = (Stack<String>) stack.clone();
			text5.setText(stack_read.pop());
			text4.setText(stack_read.pop());
			text3.setText(stack_read.pop());
			text2.setText(stack_read.pop());
		}
		else {
			switch(n) {
				case 1:
					text2.setText(out);
					text3.setText("");
					break;
				case 2:
					text3.setText(out);
					text4.setText("");
					break;
				case 3:
					text4.setText(out);
					text5.setText("");
					break;
			}
		}
	}

	private void calculer(String s, Stack<String> stack) {
		String out = "", a, b;
		int n;

		if (text1.getText().equals("0") || text1.getText().equals("404")) {
			text1.setText("");
			out = "";
		}

		switch (s) {
			case "+":
				a = stack.pop();
				b = stack.pop();
				out = Double.toString(Double.parseDouble(b) + Double.parseDouble(a));
				stack.push(out);
				n = stack.size();
				play(out, stack, n);
				out = "";
				break;

			case "-":
				a = stack.pop();
				b = stack.pop();
				out = Double.toString(Double.parseDouble(b) - Double.parseDouble(a));
				stack.push(out);
				n = stack.size();
				play(out, stack, n);
				out = "";
				break;

			case "*":
				a = stack.pop();
				b = stack.pop();
				out = Double.toString(Double.parseDouble(b) * Double.parseDouble(a));
				stack.push(out);
				n = stack.size();
				play(out, stack, n);
				out = "";
				break;

			case "/":
				a = stack.pop();
				b = stack.pop();
				out = Double.toString(Double.parseDouble(b) / Double.parseDouble(a));
				stack.push(out);
				n = stack.size();
				play(out, stack, n);
				out = "";
				break;

			case "<>":
				out = text1.getText();
				stack.push(out);
				if (text2.getText().equals("")) {
					text2.setText(out);
				}
				else {
					if (text3.getText().equals("")) {
						text3.setText(out);
					}
					else {
						if (text4.getText().equals("")) {
							text4.setText(out);
						}
						else {
							if (text5.getText().equals("")) {
								text5.setText(out);
							}
							else {
								text2.setText(text3.getText());
								text3.setText(text4.getText());
								text4.setText(text5.getText());
								text5.setText(out);
							}
						}
					}
				}
				out = "";
				break;

			case "C":
				out = "";
				text1.setText("");
				text2.setText("");
				text3.setText("");
				text4.setText("");
				text5.setText("");
				stack.clear();		// Clear stack
				break;

			case "n":
				out = "-" + text1.getText();
				break;

			default:
				out = text1.getText() + s;
				break;
		}
		text1.setText(out);
	}

	public static void main(String[] args) {
		Application.launch(args);
	}
}
