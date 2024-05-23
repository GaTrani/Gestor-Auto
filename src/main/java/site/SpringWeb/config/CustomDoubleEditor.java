package site.SpringWeb.config;

import java.beans.PropertyEditorSupport;

public class CustomDoubleEditor extends PropertyEditorSupport {
    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (text == null || text.isEmpty()) {
            setValue(null);
        } else {
            String replaced = text.replace(",", ".");
            setValue(Double.parseDouble(replaced));
        }
    }

    @Override
    public String getAsText() {
        Object value = getValue();
        return (value != null ? value.toString().replace(".", ",") : "");
    }
}
