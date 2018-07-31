package com.mz.mreal.util.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.stereotype.Service;

@Service
public class JsonMapper {
    private ObjectWriter ow;

    public JsonMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ow = mapper.writer().withDefaultPrettyPrinter();
    }

    /**
     * Convierte un POJO en un json string.
     *
     * @param pojo Pojo a mapear como un json string.
     * @return Json string.
     * @throws JsonProcessingException Si el pojo no puede ser mapeado como json.
     */
    public String map(Object pojo) throws JsonProcessingException {
        return ow.writeValueAsString(pojo);
    }

}
