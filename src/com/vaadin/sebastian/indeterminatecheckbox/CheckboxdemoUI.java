package com.vaadin.sebastian.indeterminatecheckbox;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
@Theme("checkboxdemo")
public class CheckboxdemoUI extends UI {

    @WebServlet(value = "/*", asyncSupported = true)
    @VaadinServletConfiguration(productionMode = false, ui = CheckboxdemoUI.class, widgetset = "com.vaadin.sebastian.indeterminatecheckbox.IndeterminateCheckboxWidgetSet")
    public static class Servlet extends VaadinServlet {
    }

    @Override
    protected void init(VaadinRequest request) {
        final VerticalLayout layout = new VerticalLayout();
        layout.setMargin(true);
        setContent(layout);

        layout.addComponent(new MenuBar(this));

        CheckBox basicBox = new CheckBox("Normal checkbox");
        // layout.addComponent(basicBox);

        IndeterminateCheckBox box = new IndeterminateCheckBox(
                "Indeterminate Box set to True", true);
        // layout.addComponent(box);
        box = new IndeterminateCheckBox("Indeterminate Box set to False",
                false);
        // layout.addComponent(box);

        final IndeterminateCheckBox indeterminatBox = new IndeterminateCheckBox(
                "Indeterminate Box set to Indeterminate");
        indeterminatBox.setValue(null);
        layout.addComponent(indeterminatBox);

        final Button setNextValue = new Button();
        setNextValue.setCaption("Set next state");
        setNextValue.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                if (indeterminatBox.getValue() == null) {
                    indeterminatBox.setValue(true);
                } else {
                    indeterminatBox.setValue(null);
                }
            }
        });
        layout.addComponent(indeterminatBox);
        layout.addComponent(setNextValue);

        final IndeterminateCheckBox togglebox = new IndeterminateCheckBox(
                "Toggle box");
        togglebox.setUserCanToggleIndeterminate(true);
        layout.addComponent(togglebox);

        final Button toggle = new Button();
        toggle.setCaption(
                "User toggle : " + togglebox.isUserCanToggleIndetermine());
        toggle.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                togglebox.setUserCanToggleIndeterminate(
                        !togglebox.isUserCanToggleIndetermine());
                toggle.setCaption("User toggle : "
                        + togglebox.isUserCanToggleIndetermine());
            }
        });
        layout.addComponent(toggle);

        Button showToggleValue = new Button("Show toggle value",
                new Button.ClickListener() {

                    @Override
                    public void buttonClick(ClickEvent event) {
                        Notification.show("" + togglebox.getValue());
                    }
                });
        layout.addComponent(showToggleValue);
    }

}