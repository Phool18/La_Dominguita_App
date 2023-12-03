package la.dominga.utils;

import android.text.Editable;
import android.text.TextWatcher;
import com.google.android.material.textfield.TextInputEditText;

public class FechaVencimientoTextWatcher implements TextWatcher {
    private String current = "";
    private final int maxLongitud = 5;
    private final TextInputEditText editText;

    public FechaVencimientoTextWatcher(TextInputEditText editText) {
        this.editText = editText;
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        String text = s.toString();
        if (!text.equals(current)) {
            String clean = text.replaceAll("[^\\d.]", "");
            String formatted = "";

            if (clean.length() >= 2) {
                String month = clean.substring(0, 2);
                int monthInt = Integer.parseInt(month);
                if (monthInt > 12) month = "12";
                if (monthInt == 00) month = "01";

                if (clean.length() >= 4) {
                    String year = clean.substring(2, 4);
                    int yearInt = Integer.parseInt(year);
                    if (yearInt == 0) year = "24";
                    if (month.equals("12") && yearInt < 24 && yearInt == 00) year = "24";

                    formatted = month + "/" + year;
                } else if (clean.length() > 2) {
                    formatted = month + "/" + clean.substring(2);
                } else {
                    formatted = month;
                }
            } else if (clean.equals("0")) {
                formatted = "01/";
            } else {
                formatted = clean;
            }

            current = formatted;
            editText.setText(formatted);
            editText.setSelection(Math.min(formatted.length(), maxLongitud));
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

    @Override
    public void afterTextChanged(Editable s) {}
}
