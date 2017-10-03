package sample;

import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;

class RadioButtonsGroup {
    HBox hBox;
    RadioButton[] radioButtons;
    Label[] labels;
    ToggleGroup toggleGroup;

    RadioButtonsGroup(RadioChoices[] choices) {
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