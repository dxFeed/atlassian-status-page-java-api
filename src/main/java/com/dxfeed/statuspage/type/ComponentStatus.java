package com.dxfeed.statuspage.type;

import com.dxfeed.statuspage.type.converter.ComponentStatusDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = ComponentStatusDeserializer.class)
public enum ComponentStatus {

     OPERATIONAL("operational")
    ,MAINTENANCE("under_maintenance")
    ,DEGRADED("degraded_performance")
    ,PARTIAL_OUTAGE("partial_outage")
    ,MAJOR_OUTAGE("major_outage")
    ,NONE("")
    ;

    private String value;
    ComponentStatus(String value) {
        this.value = value;
    }
    public String value() {
        return this.value;
    }
    public static ComponentStatus of(String input){
        for(ComponentStatus v : values()){
            if( v.value().equalsIgnoreCase(input) ){
                return v;
            }
        }
        return null;
    }

}
