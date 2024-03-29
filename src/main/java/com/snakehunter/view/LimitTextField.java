package com.snakehunter.view;

import javax.swing.JFormattedTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

class LimitTextField
        extends JFormattedTextField {

    LimitTextField() {
        setColumns(2);
        setDocument(new PlainDocument() {
            @Override
            public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
                if (str == null) {
                    return;
                }

                try {
                    Integer.parseInt(str);
                } catch (NumberFormatException e) {
                    return;
                }

                if ((getLength() + str.length()) <= 2) {
                    super.insertString(offs, str, a);
                }
            }
        });
    }
}


