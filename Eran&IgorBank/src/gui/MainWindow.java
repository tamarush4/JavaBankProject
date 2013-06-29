package gui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MainWindow extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		
		Text welcomText = new Text("Welcome to the Bank System!");
		welcomText.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		grid.add(welcomText, 0, 0, 3, 1);
		Text commandText = new Text("What would you like to do?");
		commandText.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		grid.add(commandText, 0, 1, 3, 1);
		
		Button depositBtn = new Button();
        depositBtn.setText("Deposit");
        depositBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("DEPOSIT action was chosen by the user!");
            }
        });
        grid.add(depositBtn,0,4,1,1);
        
		Button withdrawBtn = new Button();
		withdrawBtn.setText("Withdraw");
		withdrawBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("WITHDRAW action was chosen by the user!");
            }
        });
		grid.add(withdrawBtn,1,4,1,1);
		
		Button giveAuthorizationBtn = new Button();
		giveAuthorizationBtn.setText("Give Authorization");
		giveAuthorizationBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("GIVE AUTHORIZATION action was chosen by the user!");
            }
        });
		grid.add(giveAuthorizationBtn,2,4,1,1);
		
		Button bankChargeBtn = new Button();
		bankChargeBtn.setText("Bank Charge");
		bankChargeBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("BANK CHARGE action was chosen by the user!");
            }
        });
		grid.add(bankChargeBtn,0,6,1,1);
		
		Button bankCreditBtn = new Button();
		bankCreditBtn.setText("Bank Crdeit");
		bankCreditBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("BANK CREDIT action was chosen by the user!");
            }
        });
		grid.add(bankCreditBtn,1,6,1,1);
		
        Scene scene = new Scene(grid, 600, 400);
        
        primaryStage.setTitle("Bank System");
        primaryStage.setScene(scene);
        primaryStage.show();
	}
	
	public static void main(String[] args) {
        launch(args);
    }

}
