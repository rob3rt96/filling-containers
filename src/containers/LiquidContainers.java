package containers;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LiquidContainers extends Application {

    @Override
    public void start(Stage window) {
        // Creating the layouts
        BorderPane mainLayout = new BorderPane();
        BorderPane bottomLayout = new BorderPane();

        mainLayout.setPadding(new Insets(10));

        // Creating the text instructions
        Label instructions = new Label("The \"Fill\" button will fill only the first container\n"
                + "The \"Transfer\" button moves quantity only from first to the second container\n"
                + "The \"Remove\" button removes quantity only from the second container\n"
                + "The maximum limit of both containers is set to 100 by default");
        BorderPane topLayout = new BorderPane();
        topLayout.setCenter(instructions);


        // Creating the text field where the moving quantity is being set
        TextField quantityText = new TextField();

        // Creating the buttons
        Button fillContainer1 = new Button("Fill");
        Button transfer1 = new Button("Transfer from <- to ->");
        Button removeQnt = new Button("Remove");
        Button reset = new Button("Reset");

        // Adding the action buttons in a layout
        GridPane centerLayout = new GridPane();
        centerLayout.add(fillContainer1, 0, 0);
        centerLayout.add(transfer1, 0, 1);
        centerLayout.add(removeQnt, 0, 2);
        centerLayout.add(quantityText, 0, 3);
        centerLayout.add(reset, 0, 20);

        centerLayout.setAlignment(Pos.CENTER);
        centerLayout.setVgap(10);
        centerLayout.setHgap(10);
        centerLayout.setPadding(new Insets(20, 20, 20, 20));

        // Creating the axis
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis(0, 100, 10);

        yAxis.setTickLabelsVisible(false);
        xAxis.setTickLabelsVisible(false);

        //Creating the chart to show the status of the containers
        BarChart<String, Number> barChart1 = new BarChart<>(xAxis, yAxis);
        BarChart<String, Number> barChart2 = new BarChart<>(xAxis, yAxis);

        barChart1.setAnimated(false);
        barChart2.setAnimated(false);

        barChart1.setLegendVisible(false);
        barChart2.setLegendVisible(false);

        // Positioning the elements on the bottom of the layout
        bottomLayout.setLeft(barChart1);
        bottomLayout.setRight(barChart2);
        bottomLayout.setCenter(centerLayout);

        // Positioning the elements in the main layout
        mainLayout.setTop(topLayout);
        mainLayout.setBottom(bottomLayout);

        // Creating the "body" of the containers
        XYChart.Series container1Quantity = new XYChart.Series();
        XYChart.Series container2Quantity = new XYChart.Series();

        Container container1 = new Container();
        Container container2 = new Container();

        container1Quantity.getData().add(new XYChart.Data("1", container1.contains()));
        container2Quantity.getData().add(new XYChart.Data("1", container2.contains()));


        // Creating the action of what happens if any button is pressed
        fillContainer1.setOnAction((event) -> {
            int amount = Integer.valueOf(quantityText.getText());

            container1.add(amount);
            container1Quantity.getData().add(new XYChart.Data("1", container1.contains()));
        });

        transfer1.setOnAction((event) -> {
            int amount = Integer.valueOf(quantityText.getText());
            if (amount > container1.contains()) {
                amount = container1.contains();
            }
            container1.remove(amount);
            container2.add(amount);

            container2Quantity.getData().add(new XYChart.Data("1", container2.contains()));
            container1Quantity.getData().add(new XYChart.Data("1", container1.contains()));
        });

        removeQnt.setOnAction((event) -> {
            int amount = Integer.valueOf(quantityText.getText());
            container2.remove(amount);
            container2Quantity.getData().add(new XYChart.Data("1", container2.contains()));
        });

        reset.setOnAction((event) -> {
            container1.remove(container1.contains());
            container2.remove((container2.contains()));

            container2Quantity.getData().add(new XYChart.Data("1", container2.contains()));
            container1Quantity.getData().add(new XYChart.Data("1", container1.contains()));
            quantityText.clear();
        });

        // Adding the data series to be represented by the charts
        barChart1.getData().add(container1Quantity);
        barChart2.getData().add(container2Quantity);

        // Setting the scene and showing the main window
        Scene view = new Scene(mainLayout);
        window.setScene(view);
        window.show();
    }

    public static void main(String[] args) {
        launch(LiquidContainers.class);
    }
}
