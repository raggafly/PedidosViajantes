package com.combo.pedidosviajantes.util;
import android.text.method.DigitsKeyListener;
import android.text.InputType;
public class CustomDigitsKeyListener extends DigitsKeyListener
{
    public CustomDigitsKeyListener() {
        super(false, false);
    }

    public CustomDigitsKeyListener(boolean sign, boolean decimal) {
        super(sign, decimal);
    }

    public int getInputType() {
        return InputType.TYPE_CLASS_PHONE;
    }
}