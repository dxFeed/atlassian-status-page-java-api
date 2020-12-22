package com.dxfeed.statuspage.type.converter;

import com.dxfeed.statuspage.type.IncidentStatus;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

public class IncidentStatusDeserializer extends StdDeserializer {

    public IncidentStatusDeserializer() {
        super(IncidentStatus.class);
    }
    public IncidentStatusDeserializer(Class t) {
        super(t);
    }

    @Override
    public Object deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        return IncidentStatus.of(node.asText());
    }

}
