package sample;

import javafx.event.ActionEvent;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

public class Logics {

    Variables Vars;

    Logics(Variables _variables) {
        Vars = _variables;
        behaviors();
    }

    void behaviors() {
        Vars.AccuracySlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            Vars.AccuracyTextField.setText("" + newValue.intValue());
            try {
                Vars.accuracy = Integer.parseInt(Vars.AccuracyTextField.getText());
                ApplyConversionUnit(true);
            } catch (Exception e) { }
        });

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
                Vars.gridPane.getChildren().removeAll(oldGroup.group1.hBox, oldGroup.group2.hBox);
                Vars.gridPane.add(selectedGroup.group1.hBox, 1, 2, 1, 1);
                Vars.gridPane.add(selectedGroup.group2.hBox, 3, 2, 1, 1);

                selectedGroup.setDefault();

                Vars.ActualGroup = selectedGroup;
                ApplyConversionUnit(false);
            }
        });

        Vars.UnitTypeConversion.valueProperty().addListener((observable, oldValue, newValue) -> {
            Vars.actualConversionUnit = newValue;
            ChangeConversionUnit();
        });

        Vars.AccuracyTextField.setOnAction((ActionEvent event) -> {
            try {
                int val = Integer.parseInt(Vars.AccuracyTextField.getText());
                Vars.accuracy = val;
                Vars.AccuracySlider.setValue(val * 1.0);
                Vars.AccuracyTextField.positionCaret(100);
            } catch (NumberFormatException e) {
                Vars.AccuracySlider.setValue(0.0);
            }
        });

        Vars.MetricTextField.setOnAction((ActionEvent event) -> {
            ApplyConversionUnit(true);
        });

        Vars.ImperialTextField.setOnAction((ActionEvent event) -> {
            ApplyConversionUnit(true);
        });

        Vars.ConvertBtn.setOnAction((ActionEvent event) -> {
            ApplyConversionUnit(true);
        });

        Vars.ClearBtn.setOnAction((ActionEvent event) -> {
            Vars.ActualGroup.setDefault();
        });

        Vars.ClearHistoryBtn.setOnAction((ActionEvent) -> {
            Vars.historyList.getItems().clear();
            Vars.historyEntries.clear();
        });

        for (int i = 0; i < Vars.UnitsGroup.length; i++) {
            if (Vars.UnitsGroup[i] != null) {
                Vars.UnitsGroup[i].group1.toggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) ->
                    ApplyConversionUnit(true)
                );
                Vars.UnitsGroup[i].group2.toggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) ->
                    ApplyConversionUnit(true)
                );
            }
        }
    }

    private boolean fromMetricConversion() {
        try {
            String entry = Vars.MetricTextField.getText().replaceAll(",", ".");
            double val = Double.parseDouble(entry);
            double startConvFactor = 1, endConvFactor = 1;

            String radioMSelected = ((RadioButton)Vars.ActualGroup.group1.toggleGroup.getSelectedToggle()).getText();
            String radioISelected = ((RadioButton)Vars.ActualGroup.group2.toggleGroup.getSelectedToggle()).getText();

            for (int i = 1; i < Vars.ActualGroup.group1.choices.length; i++) {
                if (radioMSelected.equals(Vars.ActualGroup.group1.choices[i].name)) {
                    startConvFactor = Vars.ActualGroup.group1.choices[i].value;
                }
            }

            for (int i = 1; i < Vars.ActualGroup.group2.choices.length; i++) {
                if (radioISelected.equals(Vars.ActualGroup.group2.choices[i].name)) {
                    endConvFactor = Vars.ActualGroup.group2.choices[i].value;
                }
            }
            val *= (Vars.ActualGroup.group2.choices[0].value * startConvFactor) / endConvFactor;
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
                if (radioMSelected.equals(Vars.ActualGroup.group1.choices[i].name)) {
                    startConvFactor = Vars.ActualGroup.group1.choices[i].value;
                }
            }

            for (int i = 1; i < Vars.ActualGroup.group2.choices.length; i++) {
                if (radioISelected.equals(Vars.ActualGroup.group2.choices[i].name)) {
                    endConvFactor = Vars.ActualGroup.group2.choices[i].value;
                }
            }
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
        if (Vars.actualConversionUnit.equals("Metric")) {
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

        if (Vars.actualConversionUnit.equals("Metric")) {
            editableField = Vars.MetricTextField;
            displayField = Vars.ImperialTextField;
        } else {
            editableField = Vars.ImperialTextField;
            displayField = Vars.MetricTextField;
        }
        displayField.setEditable(false);
        displayField.setStyle("-fx-opacity: 0.75;");
        displayField.promptTextProperty().setValue("");

        editableField.setEditable(true);
        editableField.setStyle("-fx-opacity: 1;");
        editableField.promptTextProperty().setValue("Enter a valid number.");
    }

    private void AddEntryToHistory() {
        Vars.historyEntries.add(0,
                Vars.MetricTextField.getText()
                        + " "
                        + ((RadioButton) Vars.ActualGroup.group1.toggleGroup.getSelectedToggle()).getText()
                        + " <-> "
                        + Vars.ImperialTextField.getText()
                        + " "
                        + ((RadioButton) Vars.ActualGroup.group2.toggleGroup.getSelectedToggle()).getText()
        );

        if (Vars.historyEntries.size() >= Vars.height / 25) {
            Vars.historyEntries.remove(Vars.height / 25);
        }

        Vars.historyList.getItems().clear();
        Vars.historyList.getItems().addAll(Vars.historyEntries);
    }
}
