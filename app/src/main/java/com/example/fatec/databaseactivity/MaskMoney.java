package com.example.fatec.databaseactivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;
import java.math.BigDecimal;
import java.text.NumberFormat;

public class MaskMoney {

    //String money = NumberFormat.getCurrencyInstance().format(0.00);

//    public static TextWatcher monetario(final EditText editText) {
//         return new TextWatcher() {
//
//         private String current = "";
//
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
//
//            @Override
//            public void afterTextChanged(Editable s) { }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                BigDecimal parsed = null;
//                if (editText == null) return;
//                editText.removeTextChangedListener(this);
//
//                String cleanString = s.toString().replaceAll("[R$,.]", "");
//                parsed = new BigDecimal(cleanString).setScale(2,BigDecimal.ROUND_UNNECESSARY).divide(new BigDecimal(100), BigDecimal.ROUND_UNNECESSARY);
//                String formatted = NumberFormat.getCurrencyInstance().format((parsed));
//
//                current = formatted.replaceAll("[R$]", "");
//                editText.setText(current);
//                editText.setSelection(editText.length());
//                editText.addTextChangedListener(this);
//            }
//         };
//    }
//
//    public static TextWatcher monetarioTextView(final TextView editText) {
//        return new TextWatcher() {
//
//            private String current = "";
//
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
//
//            @Override
//            public void afterTextChanged(Editable s) { }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                    BigDecimal parsed = null;
//                    if (editText == null) return;
//                    editText.removeTextChangedListener(this);
//
//                    String cleanString = s.toString().replaceAll("[R$,.]", "");
//                    parsed = new BigDecimal(cleanString).setScale(2,BigDecimal.ROUND_UNNECESSARY).divide(new BigDecimal(100), BigDecimal.ROUND_UNNECESSARY);
//                    String formatted = NumberFormat.getCurrencyInstance().format((parsed));
//
//                    current = formatted.replaceAll("[R$]", "");
//                    editText.setText(current);
//                    editText.addTextChangedListener(this);
//            }
//        };
//    }

    public static TextWatcher monetario(final EditText ediTxt) {
        return new TextWatcher() {
            // Mascara monetaria para o preço do produto
            private String current = "";

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals(current)) {
                    ediTxt.removeTextChangedListener(this);

                    String cleanString = s.toString().replaceAll("[R$,.]", "");

                    double parsed = Double.parseDouble(cleanString);
                    String formatted = NumberFormat.getCurrencyInstance().format((parsed / 100));

                    current = formatted.replaceAll("[R$]", "");
                    ediTxt.setText(current);
                    ediTxt.setSelection(current.length());

                    ediTxt.addTextChangedListener(this);
                }
            }

        };

    }
}