package sample;

import javafx.event.ActionEvent;

public class Logics {

    Variables Vars;

    Logics(Variables _variables) {
        Vars = _variables;
        behaviors();
    }

    void behaviors() {
        Vars.AccuracySlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            Vars.AccuracyTextField.setText("" + newValue.intValue());
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
            }
        });

        Vars.AccuracyTextField.setOnAction((ActionEvent event) -> {
            try {
                int val = Integer.parseInt(Vars.AccuracyTextField.getText());
                Vars.AccuracySlider.setValue(val * 1.0);
                Vars.AccuracyTextField.positionCaret(100);
            } catch (NumberFormatException e) {
                Vars.AccuracySlider.setValue(0.0);
            }
        });
    }
}
