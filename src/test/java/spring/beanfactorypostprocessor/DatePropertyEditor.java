package spring.beanfactorypostprocessor;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author:ben.gu
 * @Date:2019/3/6 10:17 PM
 */
public class DatePropertyEditor extends PropertyEditorSupport {

    private String datePattern;

    @Override public void setAsText(String text) throws IllegalArgumentException {
        SimpleDateFormat formatter =  new SimpleDateFormat(getDatePattern());
        try {
            Date date = formatter.parse(text);
            setValue(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public String getDatePattern() {
        return datePattern;
    }

    public void setDatePattern(String datePattern) {
        this.datePattern = datePattern;
    }
}
