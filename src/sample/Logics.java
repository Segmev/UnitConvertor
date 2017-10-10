package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;

class Logics {

    private Variables Vars;

    private final ChangeListener<Toggle> radioBtnListener
            = (ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue)
            -> ApplyConversionUnit(true);

    private final ChangeListener<Number> accuracySliderListener = (observable, oldValue, newValue) -> {
        Vars.AccuracyTextField.setText("" + (double)newValue.intValue());
        int newAccuracy = (int)Double.parseDouble(Vars.AccuracyTextField.getText().replaceAll(",", "."));
        if (newAccuracy != Vars.accuracy) {
            Vars.accuracy = newAccuracy;
            ApplyConversionUnit(true);
        }
    };

    private final EventHandler<ActionEvent> accuracyFieldListener = (ActionEvent event) -> {
        try {
            double val = Double.parseDouble(Vars.AccuracyTextField.getText().replaceAll(",", "."));
            Vars.accuracy = (int)val;
            if (Vars.accuracy > (int)Vars.AccuracySlider.getMax()) {
                Vars.accuracy = (int)Vars.AccuracySlider.getMax();
            } else if (Vars.accuracy < 0) {
                Vars.accuracy = 0;
            }
            Vars.AccuracySlider.setValue(val);
            Vars.AccuracyTextField.positionCaret(100);
            ApplyConversionUnit(true);
        } catch (NumberFormatException e) {
            Vars.AccuracySlider.setValue(0.0);
            Vars.accuracy = 0;
        }
        Vars.AccuracyTextField.setText("" + (double)Vars.accuracy);
    };

    Logics(Variables _variables) {
        Vars = _variables;
        behaviors();
    }

    private void behaviors() {
        Vars.AccuracySlider.valueProperty().addListener(accuracySliderListener);

        Vars.AccuracyTextField.setOnAction(accuracyFieldListener);

        Vars.ConversionTypeBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            UnitsGroup selectedGroup = null;
            UnitsGroup oldGroup = null;

            for (int i = 0; i < Vars.UnitsGroup.length; i++) {
                if (Vars.UnitsGroup[i] != null) {
                    if (newValue.equals(Vars.UnitsGroup[i].type)) {
                        selectedGroup = Vars.UnitsGroup[i];
                    } else if (oldValue.equals(Vars.UnitsGroup[i].type)) {
                        oldGroup = Vars.UnitsGroup[i];
                    }
                }
            }
            if (selectedGroup != null && oldGroup != null) {
                Vars.GridPane.getChildren().removeAll(oldGroup.group1.hBox, oldGroup.group2.hBox);
                Vars.GridPane.add(selectedGroup.group1.hBox, 1, 2, 1, 1);
                Vars.GridPane.add(selectedGroup.group2.hBox, 3, 2, 1, 1);

                selectedGroup.setDefault();

                Vars.ActualGroup = selectedGroup;
                Vars.historyList.getItems().clear();
                Vars.historyEntries.clear();
                ApplyConversionUnit(false);
            }
        });

        Vars.UnitTypeConversion.valueProperty().addListener((observable, oldValue, newValue) -> {
            Vars.ActualConversionUnit = newValue;
            ChangeConversionUnit();
        });

        Vars.MetricTextField.setOnAction((ActionEvent event) ->
            ApplyConversionUnit(true)
        );

        Vars.ImperialTextField.setOnAction((ActionEvent event) ->
            ApplyConversionUnit(true)
        );

        Vars.ConvertBtn.setOnAction((ActionEvent event) ->
            ApplyConversionUnit(true)
        );

        Vars.ClearBtn.setOnAction((ActionEvent event) ->
            Vars.ActualGroup.setDefault()
        );

        Vars.ClearHistoryBtn.setOnAction((ActionEvent) -> {
            Vars.historyList.getItems().clear();
            Vars.historyEntries.clear();
        });


        for (int i = 0; i < Vars.UnitsGroup.length; i++) {
            if (Vars.UnitsGroup[i] != null) {
                Vars.UnitsGroup[i].group1.toggleGroup.selectedToggleProperty().addListener(radioBtnListener);
                Vars.UnitsGroup[i].group2.toggleGroup.selectedToggleProperty().addListener(radioBtnListener);
            }
        }

        Vars.historyList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                for (Toggle toggle : Vars.ActualGroup.group1.toggleGroup.getToggles()) {
                    if (((RadioButton)toggle).getText().equals(newValue.metricUnit)) {
                        Vars.ActualGroup.group1.toggleGroup.selectedToggleProperty().removeListener(radioBtnListener);
                        toggle.setSelected(true);
                        Vars.ActualGroup.group1.toggleGroup.selectedToggleProperty().addListener(radioBtnListener);
                    }
                }
                for (Toggle toggle : Vars.ActualGroup.group2.toggleGroup.getToggles()) {
                    if (((RadioButton)toggle).getText().equals(newValue.imperialUnit)) {
                        Vars.ActualGroup.group2.toggleGroup.selectedToggleProperty().removeListener(radioBtnListener);
                        toggle.setSelected(true);
                        Vars.ActualGroup.group2.toggleGroup.selectedToggleProperty().addListener(radioBtnListener);
                    }
                }
                Vars.MetricTextField.setText(newValue.metricEntry);
                Vars.AccuracyTextField.setOnAction(null);
                Vars.AccuracySlider.valueProperty().removeListener(accuracySliderListener);

                Vars.ImperialTextField.setText(newValue.imperialEntry);
                Vars.AccuracySlider.setValue(newValue.accuracy);

                Vars.AccuracyTextField.setOnAction(accuracyFieldListener);
                Vars.AccuracySlider.valueProperty().addListener(accuracySliderListener);

                Vars.AccuracyTextField.setText("" + (double)newValue.accuracy);
            }
        });
    }

    private boolean fromMetricConversion() {
        try {
            String entry = Vars.MetricTextField.getText().replaceAll(",", ".");
            double val = Double.parseDouble(entry);
            double startConvFactor = 1, endConvFactor = 1;

            String radioMSelected = ((RadioButton)Vars.ActualGroup.group1.toggleGroup.getSelectedToggle()).getText();
            String radioISelected = ((RadioButton)Vars.ActualGroup.group2.toggleGroup.getSelectedToggle()).getText();

            for (int i = 1; i < Vars.ActualGroup.group1.choices.length; i++) {
                if (radioMSelected.equals(Vars.ActualGroup.group1.choices[i].unit)) {
                    startConvFactor = Vars.ActualGroup.group1.choices[i].value;
                    break ;
                }
            }

            for (int i = 1; i < Vars.ActualGroup.group2.choices.length; i++) {
                if (radioISelected.equals(Vars.ActualGroup.group2.choices[i].unit)) {
                    endConvFactor = Vars.ActualGroup.group2.choices[i].value;
                    break ;
                }
            }
            val *= (Vars.ActualGroup.group2.choices[0].value * startConvFactor) / endConvFactor;
            val += Vars.ActualGroup.group2.choices[0].baseDifference;
            Vars.ImperialTextField.setText("" + applyAccuracy(val));
            return true;
        } catch (Exception e) {
            Vars.ImperialTextField.setText("");
            Vars.MetricTextField.setText("Enter a valid number");
            Vars.MetricTextField.selectAll();
            return false;
        }
    }

    private boolean fromImperialConversion() {
        try {
            String entry = Vars.ImperialTextField.getText().replaceAll(",", ".");
            double val = Double.parseDouble(entry);
            double startConvFactor = 1, endConvFactor = 1;

            String radioMSelected = ((RadioButton)Vars.ActualGroup.group1.toggleGroup.getSelectedToggle()).getText();
            String radioISelected = ((RadioButton)Vars.ActualGroup.group2.toggleGroup.getSelectedToggle()).getText();

            for (int i = 1; i < Vars.ActualGroup.group1.choices.length; i++) {
                if (radioMSelected.equals(Vars.ActualGroup.group1.choices[i].unit)) {
                    startConvFactor = Vars.ActualGroup.group1.choices[i].value;
                    break ;
                }
            }

            for (int i = 1; i < Vars.ActualGroup.group2.choices.length; i++) {
                if (radioISelected.equals(Vars.ActualGroup.group2.choices[i].unit)) {
                    endConvFactor = Vars.ActualGroup.group2.choices[i].value;
                    break ;
                }
            }
            val -= Vars.ActualGroup.group2.choices[0].baseDifference;
            val /= (Vars.ActualGroup.group2.choices[0].value * startConvFactor) / endConvFactor;
            Vars.MetricTextField.setText("" + applyAccuracy(val));
            return true;
        } catch (Exception e) {
            Vars.MetricTextField.setText("");
            Vars.ImperialTextField.setText("Enter a valid number");
            Vars.ImperialTextField.selectAll();
            return false;
        }
    }

    private String applyAccuracy(double entry) {
        for (int i = 0; i < Vars.accuracy; i++) {
            entry *= 10;
        }
        entry = Math.round(entry);
        for (int i = 0; i < Vars.accuracy; i++) {
            entry /= 10;
        }
        return (String.format("%." + Vars.accuracy + "f", entry));
    }

    void ApplyConversionUnit(boolean updateHistory) {
        boolean resultOk;
        if (Vars.ActualConversionUnit.equals("Metric")) {
            resultOk = fromMetricConversion();
        } else {
            resultOk = fromImperialConversion();
        }
        if (resultOk && updateHistory) {
            AddEntryToHistory();
        }
    }

    void ChangeConversionUnit() {
        TextField editableField, displayField;

        if (Vars.ActualConversionUnit.equals("Metric")) {
            editableField = Vars.MetricTextField;
            displayField = Vars.ImperialTextField;
        } else {
            editableField = Vars.ImperialTextField;
            displayField = Vars.MetricTextField;
        }
        displayField.setEditable(false);
        displayField.setStyle("-fx-opacity: 0.65;");
        displayField.promptTextProperty().setValue("");

        editableField.setEditable(true);
        editableField.setStyle("-fx-opacity: 1;");
        editableField.promptTextProperty().setValue("Enter a valid number.");
    }

    private void AddEntryToHistory() {
        Vars.historyEntries.add(0,
                new HistoryEntry(
                        Vars.MetricTextField.getText(),
                        ((RadioButton) Vars.ActualGroup.group1.toggleGroup.getSelectedToggle()).getText(),
                        Vars.ImperialTextField.getText(),
                        ((RadioButton) Vars.ActualGroup.group2.toggleGroup.getSelectedToggle()).getText(),
                        Vars.accuracy
                )
        );

        if (Vars.historyEntries.size() >= 25) {
            Vars.historyEntries.remove(25 - 1);
        }

        Vars.historyList.getItems().clear();
        for (HistoryEntry entry : Vars.historyEntries) {
            Vars.historyList.getItems().add(entry);
        }
    }
}
