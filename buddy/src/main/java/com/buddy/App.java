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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Contractor Buddy 
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {
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
        rowNames.add("1.5in Flashing"); rowNames.add("2in Flashing"); rowNames.add("3in flashing "); 
        /* For V2
        rowNames.add("Customer Name"); rowNames.add("Customer Address"); rowNames.add("Customer Email");
        rowNames.add("Customer Phone");
        */

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

        /* 
        Job Cost Field Generation - Add labels as necessary
        *See Avalon Roofing Blankl Pricing Sheet for Pricing - Must be requested to see*
        **For future releases - there has to be a way to improve how this is done, no time for that currently**
        */
        // Tracks total cost
        double TotalCost = 0;

        // Displays Total square footage
        double squareFootage = data.get(0);
        Label squareFootageL = new Label("Total Square Footage: " + squareFootage);
        squareFootageL.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));
        grid2.add(squareFootageL, 0, 2);

        // Displays permitter
        double permitter = data.get(3);
        Label permitterL = new Label("Permitter Length: " + permitter);
        permitterL.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));
        grid2.add(permitterL, 0, 3);

        // Displays number of vents
        double vents = data.get(4);
        Label ventsL = new Label("Total Vents: " + vents);
        ventsL.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));
        grid2.add(ventsL, 0, 4);

        // Displays number of skylights and chimmenys
        double SkyChim = data.get(5) + data.get(6);
        Label SkyChimL = new Label("Total Skylights and Chimmenys: " + SkyChim);
        SkyChimL.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));
        grid2.add(SkyChimL, 0, 5);

        // Caluates and displays numnber of squares
        double numSquares = squareFootage / 100;
        Label numSquaresL = new Label("Number of Squares: " + numSquares); 
        numSquaresL.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));
        grid2.add(numSquaresL, 0, 6);

        // Displays hip length (They don't lie...)
        double HipLength = data.get(1);
        Label HipLengthL = new Label("Hip Length: " + HipLength); 
        HipLengthL.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));
        grid2.add(HipLengthL, 0, 7);

        // Displays ridge length
        double RidgeLength = data.get(2);
        Label RidgeLengthL = new Label("Ridge Length: " + RidgeLength); 
        RidgeLengthL.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));
        grid2.add(RidgeLengthL, 0, 8);

        // Calcualtes and displays cost for ridge shingles, adds to total
        double RidgeShinglesCost = RidgeLength/20 * 47.30;
        TotalCost += RidgeShinglesCost;
        Label RidgeShinglesCostL = new Label("Ridge Shingles Cost: $" + RidgeShinglesCost); 
        RidgeShinglesCostL.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));
        grid2.add(RidgeShinglesCostL, 0, 9);

        // Caluates and displays cost of plywood by square, adds to total
        double PlywoodCost = numSquares * 70;
        TotalCost += PlywoodCost;
        Label PlywoodCostL = new Label("Plywood Cost: $" + PlywoodCost); 
        PlywoodCostL.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));
        grid2.add(PlywoodCostL, 0, 10);

        // Caluates and displays cost of Shingles by square, adds to total
        double ShinglesCost = numSquares * 110;
        TotalCost += ShinglesCost;
        Label ShinglesCostL = new Label("Shingles Cost: $" + ShinglesCost); 
        ShinglesCostL.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));
        grid2.add(ShinglesCostL, 0, 11);

        // Caluates and displays cost of felt by square, adds to total
        double FeltCost = numSquares/2 * 32.25;
        TotalCost += FeltCost;
        Label FeltCostL = new Label("Felt Cost: $" + FeltCost); 
        FeltCostL.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));
        grid2.add(FeltCostL, 0, 12);

        // Caluates and displays cost of 1.5in flashing, adds to total
        double flashing1_5inCost = numSquares * 9.96;
        TotalCost += flashing1_5inCost;
        Label flashing1_5inCostL = new Label("1.5in Flashing Cost: $" + flashing1_5inCost); 
        flashing1_5inCostL.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));
        grid2.add(flashing1_5inCostL, 0, 13);

        // Caluates and displays cost of 2in flashing, adds to total
        double flashing2inCost = numSquares * 9.96;
        TotalCost += flashing2inCost;
        Label flashing2inCostL = new Label("2in Flashing Cost: $" + flashing2inCost); 
        flashing2inCostL.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));
        grid2.add(flashing2inCostL, 0, 14);

        // Caluates and displays cost of 3in flashing, adds to total
        double flashing3inCost = numSquares * 9.96;
        TotalCost += flashing3inCost;
        Label flashing3inCostL = new Label("3in Flashing Cost: $" + flashing3inCost); 
        flashing3inCostL.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));
        grid2.add(flashing3inCostL, 0, 15);

        // Caluates and displays cost of torch down, adds to total
        double torchDownCost = numSquares * 106;
        TotalCost += torchDownCost;
        Label torchDownCostL = new Label("Torch Down Cost: $" + torchDownCost); 
        torchDownCostL.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));
        grid2.add(torchDownCostL, 0, 16);

        // Caluates and displays cost of propane, adds to total
        double propaneCost = numSquares * 106;
        TotalCost += propaneCost;
        Label propaneCostL = new Label("Propane Cost: $" + propaneCost); 
        propaneCostL.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));
        grid2.add(propaneCostL, 0, 17);

        // Calcualtes and displays total cost
        Label TotalCostL = new Label("Total: $" + TotalCost); 
        TotalCostL.setFont(Font.font("Tahoma", FontWeight.BOLD, 14));
        grid2.add(TotalCostL, 0, 18);

        // Sets scene size, title, and displays the scene
		Scene jobCostScene = new Scene(grid2, 300, 500);
        jobCostStage.setScene(jobCostScene);
        jobCostStage.setTitle("Job Cost Report");
		jobCostStage.show(); 
    }

/* ----------------------------------------- End of Meaningful Code ---------------------------------------------------- */
    // Note: There is a lot of "junk" code that is an artifcate of creating a maven project
    //         below this note, and in the other files. Some is in use, most is not.

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

