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

        initAndSetRadioButtonsGroups();
        setProperties();
        setValues();
        setLayout();
    }

    private void initAndSetRadioButtonsGroups() {
        unitsGroup = new UnitsGroup[4];

        String types[] = {
                "Length",
                "Mass",
        };

        double accuracy[][] = {
                { 0, 6 }, { 0, 5 },
        };

        RadioChoices[][][] choices = {
                {
                        {
                                new RadioChoices("m", 1.0),
                                new RadioChoices("cm", 0.1),
                                new RadioChoices("mm", 0.01),
                        },
                        {
                                new RadioChoices("in", 1 / 0.0254),
                                new RadioChoices("foot", 12.0),
                                new RadioChoices("yard", 36.0),
                        },
                },
                {
                        {
                                new RadioChoices("g", 1.0),
                                new RadioChoices("kg", 1000.0),
                                new RadioChoices("ton", 1000000.0),
                        },
                        {
                                new RadioChoices("oz", 1 / 28.3495),
                                new RadioChoices("lb", 16.0),
                                new RadioChoices("stone", 224.0),
                        },
                }
        };

        for (int i = 0; i < choices.length; i++) {
            if (i < types.length && i < accuracy.length) {
                unitsGroup[i] = new UnitsGroup(types[i], choices[i][0], choices[i][1], accuracy[i][0], accuracy[i][1]);
                ConversionTypeBox.getItems().add(types[i]);
            }
        }
        ConversionTypeBox.getSelectionModel().selectFirst();
    }

    private void setProperties() {
        AccuracySlider.setMin(unitsGroup[0].minAccuracy);
        AccuracySlider.setMax(unitsGroup[0].maxAccuracy);
        AccuracySlider.setShowTickMarks(true);
        AccuracySlider.setShowTickLabels(true);
        AccuracySlider.setMajorTickUnit(1);
        AccuracySlider.setBlockIncrement(1);

    }

    private void setValues() {

    }

    private void setLayout() {
        rootPane.setCenter(gridPane);

        // 0
        gridPane.add(ConvTypeLabel, 0, 0, 1, 1);
        gridPane.add(ConversionTypeBox, 1, 0, 1, 1);

        // 1

        gridPane.add(MetricLabel, 0,1,1,1);
        gridPane.add(MetricTextField, 1, 1, 1,1);
        gridPane.add(ImperialLabel, 2, 1, 1 ,1);
        gridPane.add(ImperialTextField, 3, 1, 1,1);

        // 2
        gridPane.add(unitsGroup[0].group1.hBox, 1, 2, 1,1);
        gridPane.add(unitsGroup[0].group2.hBox, 3, 2, 1,1);

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

    UnitsGroup unitsGroup[];

    GridPane gridPane;
}
