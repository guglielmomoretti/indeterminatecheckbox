package com.vaadin.sebastian.indeterminatecheckbox;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;

@SuppressWarnings("serial")
public class MenuBar extends HorizontalLayout {

    private UI ui;

    public MenuBar(UI ui) {
        this.ui = ui;
        setMargin(new MarginInfo(false, true, false, true));
        setWidth("100%");
        createComponents();
    }

    private void createComponents() {
        final ComboBox theme = new ComboBox();
        theme.addItem(ui.getTheme());
        theme.addItem("reindeer");
        theme.addItem("valo");
        theme.addItem("chameleon");
        theme.addItem("base");

        theme.select(ui.getTheme());
        theme.addValueChangeListener(new ValueChangeListener() {

            @Override
            public void valueChange(ValueChangeEvent event) {
                ui.setTheme(theme.getValue().toString());
            }
        });
        addComponent(theme);
    }

}
