package com.vaadin.sebastian.indeterminatecheckbox.client.indeterminatecheckbox;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Event.NativePreviewEvent;
import com.google.gwt.user.client.Event.NativePreviewHandler;
import com.google.gwt.user.client.ui.CheckBox;
import com.vaadin.client.VTooltip;
import com.vaadin.client.ui.aria.AriaHelper;
import com.vaadin.client.ui.aria.HandlesAriaInvalid;
import com.vaadin.client.ui.aria.HandlesAriaRequired;

public class IndeterminateCheckBoxWidget extends CheckBox implements
        HandlesAriaInvalid, HandlesAriaRequired, NativePreviewHandler {

    /**
     * Style to make the indeterminate style work with Valo.
     */
    private static final String VALO_INDETERMINATE_CLASSNAME = "valo-indeterminate";

    private IndeterminateCheckBoxConnector connector;

    private InputElement input;

    public IndeterminateCheckBoxWidget() {
        sinkEvents(VTooltip.TOOLTIP_EVENTS | Event.MOUSEEVENTS);
        Event.addNativePreviewHandler(this);

        input = (InputElement) getElement().getFirstChildElement();
    }

    public void setConnector(IndeterminateCheckBoxConnector connector) {
        this.connector = connector;
    }

    private void removePossibleValoClassName() {
        getElement().removeClassName(VALO_INDETERMINATE_CLASSNAME);
    }

    private Element getCheckBoxElement() {
        return getElement().getFirstChildElement();
    }

    @Override
    public void setAriaRequired(boolean required) {
        AriaHelper.handleInputRequired(getCheckBoxElement(), required);
    }

    @Override
    public void setAriaInvalid(boolean invalid) {
        AriaHelper.handleInputInvalid(getCheckBoxElement(), invalid);
    }

    public Boolean handleToggle() {
        boolean indeterminate = input.getPropertyBoolean("indeterminate");
        boolean checked = input.isChecked();

        if (!checked && indeterminate) {
            input.setChecked(false);
            input.setPropertyBoolean("indeterminate", false);
            addOrRemoveValoStyleIfValo(false);
            return false;
        } else if (checked) {
            input.setPropertyBoolean("indeterminate", true);
            input.setChecked(false);
            addOrRemoveValoStyleIfValo(true);
            return null;
        } else {
            input.setChecked(true);
            return true;
        }

    }

    private native boolean isValoTheme(Element element)/*-{
                                                       var property = window.getComputedStyle(element, ':after').getPropertyValue('font-family');
                                                       return (property.indexOf("ThemeIcons") > -1) || (property.indexOf("FontAwesome") > -1);   
                                                       }-*/;

    protected void addOrRemoveValoStyleIfValo(boolean isIndeterminate) {

        boolean isValoTheme = isValoTheme(input.getNextSiblingElement());

        if (isValoTheme) {
            if (isIndeterminate) {
                getElement().addClassName(VALO_INDETERMINATE_CLASSNAME);
            } else {
                getElement().removeClassName(VALO_INDETERMINATE_CLASSNAME);
            }
        }
    }

    @Override
    public void onPreviewNativeEvent(NativePreviewEvent event) {

        Element target = event.getNativeEvent().getEventTarget().cast();

        boolean isWidgetClicked = (event.getTypeInt() == Event.ONCLICK)
                && (target == getElement()
                        || target == getElement().getFirstChild()
                        || target == getElement().getFirstChild()
                                .getNextSibling());

        if (isWidgetClicked) {

            boolean isPreviousValueNull = connector.getState().value == null;

            if ((isWidgetClicked && connector.getState().isUserToggleable)
                    || isPreviousValueNull) {
                event.cancel();

                handleToggle();
                connector.sendValue(handleToggle());
            } else {
                connector.sendValue(getValue());
                removePossibleValoClassName();
            }
        }
    }

    @Override
    public void setValue(Boolean value, boolean fireEvents) {
        if (value == null) {
            input.setPropertyBoolean("indeterminate", true);
            input.setChecked(false);
            addOrRemoveValoStyleIfValo(true);
        } else {
            super.setValue(value, fireEvents);
        }
    }
}
