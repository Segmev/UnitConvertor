package sample;

import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;

class UnitsGroup {
    RadioGroupByUnit group1, group2;
    String type;
    double minAccuracy, maxAccuracy;

    UnitsGroup(String _type, RadioChoices[] choices1, RadioChoices[] choices2, double minAcc, double maxAcc) {
        type = _type;
        group1 = new RadioGroupByUnit(choices1);
        group2 = new RadioGroupByUnit(choices2);
        minAccuracy = minAcc;
        maxAccuracy = maxAcc;
    }
}

class RadioGroupByUnit {
    HBox hBox;
    RadioButton[] radioButtons;
    Label[] labels;
    ToggleGroup toggleGroup;

    RadioGroupByUnit(RadioChoices[] choices) {
        hBox = new HBox();
        radioButtons = new RadioButton[choices.length];
        labels = new Label[choices.length];
        toggleGroup = new ToggleGroup();

        for (int i = 0; i < choices.length; i++) {
            radioButtons[i] = new RadioButton();
            labels[i] = new Label(choices[i].name);
            hBox.getChildren().addAll(radioButtons[i], labels[i]);
        }
        radioButtons[0].setSelected(true);
        toggleGroup.getToggles().addAll(radioButtons);

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