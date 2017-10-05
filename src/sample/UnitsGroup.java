package sample;

import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;

class UnitsGroup {
    Variables Vars;
    RadioGroupByUnit group1, group2;
    String type;
    double defaultAccuracy, minAccuracy, maxAccuracy;
    double imperialDefaultVal;

    UnitsGroup(Variables _vars, String _type, RadioChoices[] choices1, RadioChoices[] choices2, double defaultsVals[]) {
        Vars = _vars;
        type = _type;

        imperialDefaultVal = defaultsVals[0];
        defaultAccuracy = defaultsVals[1];

        group1 = new RadioGroupByUnit(choices1, defaultsVals[2]);
        group2 = new RadioGroupByUnit(choices2, defaultsVals[3]);

        minAccuracy = defaultsVals[4];
        maxAccuracy = defaultsVals[5];
    }

    void setDefault() {
        group1.setDefaults();
        group2.setDefaults();

        Vars.AccuracySlider.setMin(minAccuracy);
        Vars.AccuracySlider.setMax(maxAccuracy);
        Vars.AccuracySlider.setValue(defaultAccuracy);
        Vars.AccuracyTextField.setText("" + defaultAccuracy);
        Vars.accuracy = (int)defaultAccuracy;

        Vars.MetricTextField.setText("");
        Vars.ImperialTextField.setText("" + imperialDefaultVal);
    }
}

class RadioGroupByUnit {
    HBox hBox;
    RadioButton[] radioButtons;
    RadioChoices[] choices;
    ToggleGroup toggleGroup;
    int defaultChoice;

    RadioGroupByUnit(RadioChoices[] _choices, double defaultChoice) {
        choices = _choices;
        hBox = new HBox();
        radioButtons = new RadioButton[choices.length];
        toggleGroup = new ToggleGroup();
        this.defaultChoice = (int)defaultChoice;

        for (int i = 0; i < choices.length; i++) {
            radioButtons[i] = new RadioButton(choices[i].name);
            hBox.getChildren().addAll(radioButtons[i]);
        }
        toggleGroup.getToggles().addAll(radioButtons);
        setDefaults();
    }

    void setDefaults() {
        radioButtons[defaultChoice].setSelected(true);
    }
}

class RadioChoices {
    String name;
    Double value;

    RadioChoices(String _name, Double _value) {
        name = _name;
        value = _value;
    }
}