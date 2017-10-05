package sample;

import javafx.event.ActionEvent;
import javafx.scene.control.RadioButton;

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
            } catch (Exception e) { }
        });

        Vars.ConversionTypeBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            UnitsGroup selectedGroup = null;
            UnitsGroup oldGroup = null;

            for (int i = 0; i < Vars.unitsGroup.length; i++) {
                if (Vars.unitsGroup[i] != null) {
                    if (newValue.equals(Vars.unitsGroup[i].type)) {
                        selectedGroup = Vars.unitsGroup[i];
                    } else if (oldValue.equals(Vars.unitsGroup[i].type)) {
                        oldGroup = Vars.unitsGroup[i];
                    }
                }
            }
            if (selectedGroup != null && oldGroup != null) {
                Vars.gridPane.getChildren().removeAll(oldGroup.group1.hBox, oldGroup.group2.hBox);
                Vars.gridPane.add(selectedGroup.group1.hBox, 1, 2, 1, 1);
                Vars.gridPane.add(selectedGroup.group2.hBox, 3, 2, 1, 1);

                Vars.AccuracySlider.setMin(selectedGroup.minAccuracy);
                Vars.AccuracySlider.setMax(selectedGroup.maxAccuracy);

                Vars.actualGroup = selectedGroup;
            }
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
            fromMetricConversion();
        });

        Vars.ImperialTextField.setOnAction((ActionEvent event) -> {
            fromImperialConversion();
        });
    }

    void fromMetricConversion() {
        try {
            double val = Double.parseDouble(Vars.MetricTextField.getText());
            double startConvFactor = 1, endConvFactor = 1;

            String radioMSelected = ((RadioButton)Vars.actualGroup.group1.toggleGroup.getSelectedToggle()).getText();
            String radioISelected = ((RadioButton)Vars.actualGroup.group2.toggleGroup.getSelectedToggle()).getText();

            for (int i = 1; i < Vars.actualGroup.group1.choices.length; i++) {
                if (radioMSelected.equals(Vars.actualGroup.group1.choices[i].name)) {
                    startConvFactor = Vars.actualGroup.group1.choices[i].value;
                }
            }

            for (int i = 1; i < Vars.actualGroup.group2.choices.length; i++) {
                if (radioISelected.equals(Vars.actualGroup.group2.choices[i].name)) {
                    endConvFactor = Vars.actualGroup.group2.choices[i].value;
                }
            }
            val *= (Vars.actualGroup.group2.choices[0].value * startConvFactor) / endConvFactor;
            Vars.ImperialTextField.setText("" + applyAccuracy(val));
        } catch (Exception e) {

        }
    }

    void fromImperialConversion() {
        try {
            double val = Double.parseDouble(Vars.ImperialTextField.getText());
            double startConvFactor = 1, endConvFactor = 1;

            String radioMSelected = ((RadioButton)Vars.actualGroup.group1.toggleGroup.getSelectedToggle()).getText();
            String radioISelected = ((RadioButton)Vars.actualGroup.group2.toggleGroup.getSelectedToggle()).getText();

            for (int i = 1; i < Vars.actualGroup.group1.choices.length; i++) {
                if (radioMSelected.equals(Vars.actualGroup.group1.choices[i].name)) {
                    startConvFactor = Vars.actualGroup.group1.choices[i].value;
                }
            }

            for (int i = 1; i < Vars.actualGroup.group2.choices.length; i++) {
                if (radioISelected.equals(Vars.actualGroup.group2.choices[i].name)) {
                    endConvFactor = Vars.actualGroup.group2.choices[i].value;
                }
            }
            val /= (Vars.actualGroup.group2.choices[0].value * startConvFactor) / endConvFactor;
            Vars.MetricTextField.setText("" + applyAccuracy(val));
        } catch (Exception e) {

        }
    }

    String applyAccuracy(double entry) {
        for (int i = 0; i < Vars.accuracy; i++) {
            entry *= 10;
        }
        entry = Math.round(entry);
        for (int i = 0; i < Vars.accuracy; i++) {
            entry /= 10;
        }
        return (String.format("%." + Vars.accuracy + "f", entry));
    }
}
