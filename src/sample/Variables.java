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
        GridPane = new GridPane();
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

        ActualConversionUnit = "Imperial";

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
                                new RadioChoices("L", 1.0),
                                new RadioChoices("mL", 0.001)
                        },
                        new RadioChoices[] {
                                new RadioChoices("Pint", 1.7598),
                                new RadioChoices("Quart", 2.0),
                                new RadioChoices("Gallon", 8.0),
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
        SpaceGrow.setMinSize(50, 1);
        BtnsHbox.getChildren().addAll(ClearBtn, ClearHistoryBtn, SpaceGrow, ConvertBtn);
        BtnsHbox.setPadding(new Insets(6, 10, 5, 0));
        ConvertBtn.setStyle("-fx-border-color: forestgreen");
        GridPane.setHgap(5);
        GridPane.setVgap(10);
        GridPane.setPadding(new Insets(3, 3, 1, 3));
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
        rootPane.setCenter(GridPane);

        // 0
        GridPane.add(ConvTypeLabel, 0, 0, 1, 1);
        GridPane.add(ConversionTypeBox, 1, 0, 1, 1);
        GridPane.add(UnitTypeLabel, 2, 0, 1, 1);
        GridPane.add(UnitTypeConversion, 3, 0, 1, 1);

        // 1

        GridPane.add(MetricLabel, 0,1,1,1);
        GridPane.add(MetricTextField, 1, 1, 1,1);
        GridPane.add(ImperialLabel, 2, 1, 1 ,1);
        GridPane.add(ImperialTextField, 3, 1, 1,1);

        // 2
        GridPane.add(UnitsGroup[0].group1.hBox, 1, 2, 1,1);
        GridPane.add(UnitsGroup[0].group2.hBox, 3, 2, 1,1);

        // 3
        GridPane.add(AccuracyLabel, 0, 3, 1, 1);
        GridPane.add(AccuracySlider,1,3,1,1);
        GridPane.add(AccuracyTextField, 2, 3,1,1);
        GridPane.add(BtnsHbox, 3, 3, 2, 1);


        // history column
        GridPane.add(historyList, 4, 0, 2, 3);
    }

    BorderPane rootPane;
    private Label ConvTypeLabel, ImperialLabel, MetricLabel, AccuracyLabel, UnitTypeLabel;

    TextField ImperialTextField, MetricTextField, AccuracyTextField;

    ComboBox<String> ConversionTypeBox;

    Slider AccuracySlider;

    UnitsGroup UnitsGroup[], ActualGroup;

    GridPane GridPane;

    private HBox BtnsHbox;
    private Pane SpaceGrow;
    Button ConvertBtn, ClearBtn, ClearHistoryBtn;

    ComboBox<String> UnitTypeConversion;

    int accuracy;
    String ActualConversionUnit;

    ArrayList<HistoryEntry> historyEntries;
    ListView<HistoryEntry> historyList;
    int height, width;
}

class HistoryEntry {
    String metricEntry, metricUnit, imperialEntry, imperialUnit;
    int accuracy;

    HistoryEntry(String metricEntry, String metricUnit, String imperialEntry, String imperialUnit, int accuracy) {
        this.metricEntry = metricEntry;
        this.metricUnit = metricUnit;
        this.imperialEntry = imperialEntry;
        this.imperialUnit = imperialUnit;
        this.accuracy = accuracy;
    }

    @Override
    public String toString() {
        return metricEntry + " " + metricUnit + " <-> " + imperialEntry + " " + imperialUnit;
    }
}