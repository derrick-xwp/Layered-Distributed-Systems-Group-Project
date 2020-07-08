/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package converter;

/**
 *
 * @author xingwenp
 */
import static com.sun.faces.util.MessageUtils.CONVERSION_ERROR_MESSAGE_ID;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

@FacesConverter("conv")

public class converter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        long num;
        if (value.isEmpty()) {
            return null;
        } else {
            try {
                num = Long.parseLong(value, 16);
            } catch (NumberFormatException nfa) {
                // value is not a hex string, throw error
                FacesMessage errMsg = new FacesMessage(CONVERSION_ERROR_MESSAGE_ID);
                FacesContext.getCurrentInstance().addMessage(null, errMsg);
                throw new ConverterException(errMsg.getSummary());
            }
        }
        return num;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        String result = ""; // default value is empy string
        if (value instanceof Long) {
            // object is an integer, we can convert
            result = Long.toHexString((Long) value);
        } else if (value instanceof Integer) {
            // object is an integer, we can convert
            result = Integer.toHexString((Integer) value);
        } else {
            // object is not an integer, throw error
            FacesMessage errMsg = new FacesMessage(CONVERSION_ERROR_MESSAGE_ID);
            FacesContext.getCurrentInstance().addMessage(null, errMsg);
            throw new ConverterException(errMsg.getSummary());
        }
        return result;
    }

}
