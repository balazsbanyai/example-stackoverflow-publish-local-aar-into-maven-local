package com.banyaibalazs.localarrpublishdemo;

import android.widget.TextView;

import com.jakewharton.rxbinding2.widget.RxTextView;

public class SomeClassUsingRxBindingsToVerifyCompile {

    public SomeClassUsingRxBindingsToVerifyCompile() {
        TextView textView = new TextView(null);
        RxTextView.afterTextChangeEvents(textView).skipInitialValue().subscribe();
    }
}
