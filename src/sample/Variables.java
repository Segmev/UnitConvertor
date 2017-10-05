package sample;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;

class Variables {

    void init() {
        width = 820;
        height = 185;
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

        UnitTypeLabel = new Label("From ");
        UnitTypeConversion = new ComboBox<>();

        AccuracySlider = new Slider();

        ConvertBtn = new Button("Convert");
        ClearBtn = new Button("Clear");
        ClearHistoryBtn = new Button("Clear History");

        actualConversionUnit = "Imperial";

        historyEntries = new ArrayList<>();
        historyList = new ListView<>();

        initAndSetRadioButtonsGroups();
        setProperties();
        setValues();
        setLayout();
    }

    private void initAndSetRadioButtonsGroups() {
        UnitsGroup = new UnitsGroup[4];

        String types[] = {
                "Length",
                "Mass",
        };

        double defaultDoubleVals[][] = {
                /* Defaults: Imperial value, accuracy, radio metric, radio imperial, min accuracy, max accuracy  */
                { 1.0, 4, 2, 0, 0, 6 },
                { 0.5, 3, 1, 1, 0, 5 },
        };

        RadioChoices[][][] choices = {
                {
                        {
                                new RadioChoices("m", 1.0),
                                new RadioChoices("cm", 0.01),
                                new RadioChoices("mm", 0.001),
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
            if (i < types.length && i < defaultDoubleVals.length) {
                UnitsGroup[i] = new UnitsGroup(this, types[i], choices[i][0], choices[i][1], defaultDoubleVals[i]);
                ConversionTypeBox.getItems().add(types[i]);
            }
        }
        ConversionTypeBox.getSelectionModel().selectFirst();
        ActualGroup = UnitsGroup[0];
        ActualGroup.setDefault();
    }

    private void setProperties() {
        gridPane.setHgap(5);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(3, 3, 1, 3));
        AccuracySlider.setMin(UnitsGroup[0].minAccuracy);
        AccuracySlider.setMax(UnitsGroup[0].maxAccuracy);
        AccuracySlider.setShowTickMarks(true);
        AccuracySlider.setShowTickLabels(true);
        AccuracySlider.setMajorTickUnit(1);
        AccuracySlider.setBlockIncrement(1);
    }

    private void setValues() {
        String Units[] = {
                "Imperial", "Metric"
        };
        UnitTypeConversion.getItems().addAll(Units);
        UnitTypeConversion.getSelectionModel().selectFirst();
    }

    private void setLayout() {
        rootPane.setCenter(gridPane);

        // 0
        gridPane.add(ConvTypeLabel, 0, 0, 1, 1);
        gridPane.add(ConversionTypeBox, 1, 0, 1, 1);
        gridPane.add(UnitTypeLabel, 2, 0, 1, 1);
        gridPane.add(UnitTypeConversion, 3, 0, 1, 1);

        // 1

        gridPane.add(MetricLabel, 0,1,1,1);
        gridPane.add(MetricTextField, 1, 1, 1,1);
        gridPane.add(ImperialLabel, 2, 1, 1 ,1);
        gridPane.add(ImperialTextField, 3, 1, 1,1);

        // 2
        gridPane.add(UnitsGroup[0].group1.hBox, 1, 2, 1,1);
        gridPane.add(UnitsGroup[0].group2.hBox, 3, 2, 1,1);

        // 3
        gridPane.add(AccuracyLabel, 0, 3, 1, 1);
        gridPane.add(AccuracySlider,1,3,1,1);
        gridPane.add(AccuracyTextField, 3, 3,1,1);

        // 4
        gridPane.add(ConvertBtn,0,4,1,1);
        gridPane.add(ClearBtn, 1, 4, 1, 1);
        gridPane.add(ClearHistoryBtn, 2,4,1,1);

        // history column
        gridPane.add(historyList, 5, 0, 1, 5);
    }

    BorderPane rootPane;
    private Label ConvTypeLabel, ImperialLabel, MetricLabel, AccuracyLabel, UnitTypeLabel;

    TextField ImperialTextField, MetricTextField, AccuracyTextField;

    ComboBox<String> ConversionTypeBox;

    Slider AccuracySlider;

    UnitsGroup UnitsGroup[], ActualGroup;

    GridPane gridPane;

    Button ConvertBtn, ClearBtn, ClearHistoryBtn;

    ComboBox<String> UnitTypeConversion;

    int accuracy;
    String actualConversionUnit;

    ArrayList<String> historyEntries;
    ListView<String> historyList;
    int height, width;
}
