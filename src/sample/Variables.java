package sample;

import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.util.HashMap;

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

        initRadioButtonsGroups();
        setProperties();
        setValues();
        setLayout();
    }

    private void initRadioButtonsGroups() {
        radioButtonsGroup = new RadioButtonsGroup[4];

        RadioChoices[][] choices = {
                {
                        new RadioChoices("mm", 0.0),
                        new RadioChoices("cm", 0.0),
                        new RadioChoices("m", 0.0),
                },
                {
                        new RadioChoices("oz", 0.0),
                        new RadioChoices("lb", 0.0),
                        new RadioChoices("stone", 0.0),
                },
                {
                        new RadioChoices("g", 0.0),
                        new RadioChoices("kg", 0.0),
                        new RadioChoices("ton", 0.0),
                },
        };

        for (int i = 0; i < choices.length; i++) {
            radioButtonsGroup[i] = new RadioButtonsGroup(choices[i]);
        }
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
        gridPane.add(radioButtonsGroup[0].hBox, 1, 2, 1,1);
        gridPane.add(radioButtonsGroup[1].hBox, 3, 2, 1,1);

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

    RadioButtonsGroup radioButtonsGroup[];

    private GridPane gridPane;
}
