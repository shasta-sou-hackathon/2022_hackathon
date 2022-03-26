package com.buddy;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text; 

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Contractor Buddy 
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage primaryStage) {
        // Creates scene grid and pane title
        primaryStage.setTitle("Contractor Buddy");
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        // Creates Top title
        Text scenetitle = new Text("Contactor Buddy");
        // Need to set font to avoid gibberish (Mac Issue?)
        scenetitle.setFont(Font.font("Tahoma", FontWeight.BOLD, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        Text sceneSubTitle = new Text("Please fill in all fields. Insert all measurements in feet");
        // Need to set font to avoid gibberish (Mac Issue?)
        sceneSubTitle.setFont(Font.font("Tahoma", FontWeight.THIN, 14));
        grid.add(sceneSubTitle, 0, 0, 2, 3);
        
        /* 
        Form Row Geneartion
        */
        // Inital rows lists, one for the row names, one for labels, and one for text fields
        List<String> rowNames = new ArrayList<String>();
        List<Label> rowLabels = new ArrayList<Label>();
        List<TextField> rowTextFields = new ArrayList<TextField>();

        // Add desired rows for the form
        rowNames.add("Square Footage"); rowNames.add("Hip"); rowNames.add("Ridge"); rowNames.add("Permitter"); 
        rowNames.add("Pipes/Vents"); rowNames.add("Skylights"); rowNames.add("Chimmenys"); 
        rowNames.add("1/4in Flashing"); rowNames.add("1/2in Flashing"); rowNames.add("1in flashing "); 

        // Loop throguh the row names and creates a form row for each
        for (int i = 0; i < rowNames.size(); i++) {
            /*
            Label Creation
            */
            // Creates label to add to rowLabels to be used in the rest of the process
            Label tmpL = new Label(rowNames.get(i) + ":");
            // Add the label to the list
            rowLabels.add(tmpL);
            // Use the label in the list to generate the physical label
            rowLabels.get(i).setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));
            grid.add(rowLabels.get(i), 0, i + 2);
            
            /*
            Text Field Creation
            */
            // Similar as above, Creates textfield to add to rowTextFields to be used in the rest of the process
            TextField tmpTF = new TextField();
            // Add the text field to the list
            rowTextFields.add(tmpTF);

            rowTextFields.get(i).setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));
            grid.add(rowTextFields.get(i), 1, i + 2);
        }

        
        /*
        Job Cost Report Creation
        */
        // Creates a button labeled "Generate Job Cost"
        Button btn = new Button("Generate Job Cost");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        btn.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));
        grid.add(hbBtn, 1, rowNames.size() + 2);
        /*// Text control for button
        final Text actiontarget = new Text();
        grid.add(actiontarget, 1, rowNames.size() + 1); */


        // Button event, triggers job cost generation
        btn.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                /*
                Data collection
                */
                List<Integer> data = new ArrayList<Integer>();
                for (int i = 0; i < rowNames.size(); i++) {
                    // Gets the inputed data, add it to the data list
                    data.add(Integer.parseInt(rowTextFields.get(i).getText()));
                }
                
				/*
                Job Cost Creation - Generates the job cost report using the listed data
                */
				Stage jobCostWindow = new Stage();
                next(jobCostWindow, data);
				
            }
        });

        // Generates the scene (pop-up window)
        Scene scene = new Scene(grid, 400, 450);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public void next(Stage jobCostStage, List<Integer> data) { 
        // Creates scene grid
        GridPane grid2 = new GridPane();
        grid2.setAlignment(Pos.CENTER);
        grid2.setHgap(10);
        grid2.setVgap(10);
        grid2.setPadding(new Insets(25, 25, 25, 25));

        // Adds window title
        Text scenetitle = new Text("Job Cost Report");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.BOLD, 20));
        grid2.add(scenetitle, 0, 0, 2, 1);

        // Displays Total square footage
        int squareFootage = data.get(0);
        Label squareFootageL = new Label("Total Square Footage: " + squareFootage);
        squareFootageL.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));
        grid2.add(squareFootageL, 0, 3);

        // Caluates and displays numnber of squares
        double numSquares = squareFootage / 100;
        Label numSquaresL = new Label("Number of Squares: " + numSquares); 
        numSquaresL.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));
        grid2.add(numSquaresL, 0, 4);

         // Caluates and displays cost of plywood by square
         double costForPlywood = numSquares * 110;
         Label costSquaresL = new Label("Plywood Cost: " + costForPlywood); 
         costSquaresL.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));
         grid2.add(costSquaresL, 0, 5);

        


        // Sets scene size, title, and displays the scene
		Scene jobCostScene = new Scene(grid2, 300, 450);
        jobCostStage.setScene(jobCostScene);
        jobCostStage.setTitle("Job Cost Report");
		jobCostStage.show(); 
    }

    // Note: There is a lot of "junk" code that is an artifcate of creating a maven project and is unused


    // Below is Autogenerated, does stuff to run, forsure does smth...yup
    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }


}

