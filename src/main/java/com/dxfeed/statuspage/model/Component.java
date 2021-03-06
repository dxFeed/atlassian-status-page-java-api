package com.dxfeed.statuspage.model;

import com.dxfeed.statuspage.type.ComponentStatus;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true)
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({"id", "name"})
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
public class Component {

    private String id;

    private String name;

    @JsonProperty("page_id")
    private String pageId;

    @JsonProperty("group_id")
    private String groupId;

    private String description;

    @JsonProperty("only_show_if_degraded")
    private boolean onlyShowIfDegraded;

    private boolean showcase;

    private ComponentStatus status;

    private boolean group;

}
