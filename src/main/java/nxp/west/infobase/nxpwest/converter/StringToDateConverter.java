package nxp.west.infobase.nxpwest.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class StringToDateConverter implements Converter<String, Date> {

    @Override
    public Date convert(String source) {
        Date target = null;
        if(!StringUtils.isEmpty(source)) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            try {
                target =  format.parse(source);
            } catch (ParseException e) {
                throw new RuntimeException(String.format("parser %s to Date fail", source));
            }
        }
        return target;
    }
}