package com.dxfeed.statuspage.type.converter;

import com.dxfeed.statuspage.type.IncidentSeverity;
import com.dxfeed.statuspage.type.IncidentStatus;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

public class IncidentSeverityDeserializer extends StdDeserializer {

    public IncidentSeverityDeserializer() {
        super(IncidentStatus.class);
    }
    public IncidentSeverityDeserializer(Class t) {
        super(t);
    }

    @Override
    public Object deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        return IncidentSeverity.of(node.asText());
    }

}
