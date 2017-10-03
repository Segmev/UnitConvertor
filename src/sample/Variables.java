package sample;

import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

class Variables {

    void init() {
        rootPane = new BorderPane();
        gridPane = new GridPane();

        ConvTypeLabel = new Label("Conversion Type");
        ImperialLabel = new Label("Imperial");
        MetricLabel = new Label("Metric");
        AccuracyLabel = new Label("Accuracy");

        ImperialTextField = new TextField();
        MetricTextField = new TextField();
        AccuracyTextField = new TextField();

        ConversionTypeBox = new ComboBox<>();

        AccuracySlider = new Slider();

        setProperties();
        setValues();
        setLayout();
    }

    private void setProperties() {
        AccuracySlider.setMin(0);
        AccuracySlider.setMax(6);
        AccuracySlider.setShowTickMarks(true);
        AccuracySlider.setShowTickLabels(true);
        AccuracySlider.setMajorTickUnit(1);
        AccuracySlider.setBlockIncrement(1);

    }

    private void setValues() {
        ConversionTypeBox.getItems().addAll("Length", "Mass");
        ConversionTypeBox.getSelectionModel().selectFirst();
    }

    private void setLayout() {
        rootPane.setCenter(gridPane);

        // 0
        gridPane.add(ConvTypeLabel, 0, 0, 1, 1);
        gridPane.add(ConversionTypeBox, 1, 0, 1, 1);

        // 1
        gridPane.add(ImperialLabel, 0, 1, 1 ,1);
        gridPane.add(ImperialTextField, 1, 1, 1,1);
        gridPane.add(MetricLabel, 2,1,1,1);
        gridPane.add(MetricTextField, 3, 1, 1,1);

        // 2

        // 3
        gridPane.add(AccuracyLabel, 0, 3, 1, 1);
        gridPane.add(AccuracySlider,1,3,1,1);
        gridPane.add(AccuracyTextField, 3, 3,1,1);
    }

    BorderPane rootPane;
    private Label ConvTypeLabel, ImperialLabel, MetricLabel, AccuracyLabel;

    TextField ImperialTextField, MetricTextField, AccuracyTextField;

    ComboBox<String> ConversionTypeBox;

    Slider AccuracySlider;

    RadioButton radioButton[];

    private GridPane gridPane;
}
