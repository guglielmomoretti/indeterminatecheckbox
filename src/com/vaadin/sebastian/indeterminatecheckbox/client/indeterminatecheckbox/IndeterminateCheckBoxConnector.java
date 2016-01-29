package com.vaadin.sebastian.indeterminatecheckbox.client.indeterminatecheckbox;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.ui.AbstractComponentConnector;
import com.vaadin.sebastian.indeterminatecheckbox.IndeterminateCheckBox;
import com.vaadin.shared.ui.Connect;

@Connect(IndeterminateCheckBox.class)
public class IndeterminateCheckBoxConnector extends AbstractComponentConnector {

    private static final long serialVersionUID = -2321682985906664914L;

    @Override
    protected void init() {
        getWidget().setConnector(this);
    }

    @Override
    protected Widget createWidget() {
        return GWT.create(IndeterminateCheckBoxWidget.class);
    }

    @Override
    public IndeterminateCheckBoxWidget getWidget() {
        return (IndeterminateCheckBoxWidget) super.getWidget();
    }

    @Override
    public IndeterminateCheckBoxState getState() {
        return (IndeterminateCheckBoxState) super.getState();
    }

    @Override
    public void onStateChanged(StateChangeEvent stateChangeEvent) {

        super.onStateChanged(stateChangeEvent);
        if (stateChangeEvent.hasPropertyChanged("value")
                && (getState().value != getWidget().getValue())) {
            Boolean value = getState().value;
            getWidget().setValue(value, false);
        }

    }

    public void sendValue(Boolean value) {
        getRpcProxy(IndeterminateCheckBoxServerRpc.class).setValue(value);
    }

    @Override
    protected void updateWidgetStyleNames() {
        getWidget().removePossibleValoClassName();
        super.updateWidgetStyleNames();
    }

}
