package nxp.west.infobase.nxpwest.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

@Component
public class JsonUtil{
    @Autowired
    ObjectMapper objectMapper;
    Logger logger = LoggerFactory.getLogger(this.getClass());
    public Map decodeUTF8JsonToMap(String jsonString) throws UnsupportedEncodingException, JsonProcessingException {
        String decode = URLDecoder.decode(jsonString, "utf-8");
        return objectMapper.readValue(decode, HashMap.class);
    }
}
