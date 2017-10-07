package sample;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

class Variables {

    void init() {
        width = 850;
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
        historyEntries = new ArrayList<>();
        historyList = new ListView<>();
        ConvertBtn = new Button("Convert");
        ClearBtn = new Button("Clear");
        ClearHistoryBtn = new Button("Clear History");
        SpaceGrow = new Pane();
        BtnsHbox = new HBox();

        actualConversionUnit = "Imperial";


        initAndSetRadioButtonsGroups();
        setProperties();
        setValues();
        setLayout();
    }

    private void initAndSetRadioButtonsGroups() {
        UnitsGroup = new UnitsGroup[] {
                new UnitsGroup(this,
                        "Length",
                        new RadioChoices[] {
                                new RadioChoices("m", 1.0),
                                new RadioChoices("cm", 0.01),
                                new RadioChoices("mm", 0.001),
                        },
                        new RadioChoices[] {
                                new RadioChoices("in", 1 / 0.0254),
                                new RadioChoices("foot", 12.0),
                                new RadioChoices("yard", 36.0),
                        },
                        new double[] { 1.0, 4, 2, 0, 0, 6 }
                ),
                new UnitsGroup(this,
                        "Mass",
                        new RadioChoices[] {
                                new RadioChoices("g", 1.0),
                                new RadioChoices("kg", 1000.0),
                                new RadioChoices("ton", 1000000.0),
                        },
                        new RadioChoices[] {
                                new RadioChoices("oz", 1 / 28.3495),
                                new RadioChoices("lb", 16.0),
                                new RadioChoices("stone", 224.0),
                        },
                        new double[] { 0.5, 3, 1, 1, 0, 5 }
                ),
                new UnitsGroup(this,
                        "Temperature",
                        new RadioChoices[] {
                                new RadioChoices("Celsius", 1.0)
                        },
                        new RadioChoices[] {
                                new RadioChoices("Fahrenheit", 1.8, 32.0)
                        },
                        new double[] { 32, 1, 0, 0, 0, 3 }
                ),
                new UnitsGroup(this,
                        "Volume",
                        new RadioChoices[] {
                                new RadioChoices("Litre", 1.0)
                        },
                        new RadioChoices[] {
                                new RadioChoices("Pint", 1.7598)
                        },
                        new double[] { 1, 2, 0, 0, 0, 10 }
                ),
        };

        for (UnitsGroup unit: UnitsGroup) {
            ConversionTypeBox.getItems().add(unit.type);
        }
        ConversionTypeBox.getSelectionModel().selectFirst();
        ActualGroup = UnitsGroup[0];
        ActualGroup.setDefault();
    }

    private void setProperties() {
        ConvertBtn.setMinSize(Button.USE_PREF_SIZE, Button.USE_PREF_SIZE);
        ClearBtn.setMinSize(Button.USE_PREF_SIZE, Button.USE_PREF_SIZE);
        ClearHistoryBtn.setMinSize(Button.USE_PREF_SIZE, Button.USE_PREF_SIZE);
        HBox.getHgrow(SpaceGrow);
        SpaceGrow.setMinSize(130, 1);
        BtnsHbox.getChildren().addAll(ClearBtn, ClearHistoryBtn, SpaceGrow, ConvertBtn);
        BtnsHbox.setPadding(new Insets(6, 10, 5, 0));
        ConvertBtn.setStyle("-fx-border-color: forestgreen");
        gridPane.setHgap(5);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(3, 3, 1, 3));
        AccuracySlider.setMin(UnitsGroup[0].minAccuracy);
        AccuracySlider.setMax(UnitsGroup[0].maxAccuracy);
        AccuracySlider.setShowTickMarks(true);
        AccuracySlider.setShowTickLabels(true);
        AccuracySlider.setMajorTickUnit(1);
        AccuracySlider.setBlockIncrement(1);
        BtnsHbox.setSpacing(15);
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
        gridPane.add(AccuracyTextField, 2, 3,1,1);
        gridPane.add(BtnsHbox, 3, 3, 2, 1);


        // history column
        gridPane.add(historyList, 4, 0, 2, 3);
    }

    BorderPane rootPane;
    private Label ConvTypeLabel, ImperialLabel, MetricLabel, AccuracyLabel, UnitTypeLabel;

    TextField ImperialTextField, MetricTextField, AccuracyTextField;

    ComboBox<String> ConversionTypeBox;

    Slider AccuracySlider;

    UnitsGroup UnitsGroup[], ActualGroup;

    GridPane gridPane;

    private HBox BtnsHbox;
    private Pane SpaceGrow;
    Button ConvertBtn, ClearBtn, ClearHistoryBtn;

    ComboBox<String> UnitTypeConversion;

    int accuracy;
    String actualConversionUnit;

    ArrayList<String> historyEntries;
    ListView<String> historyList;
    int height, width;
}
