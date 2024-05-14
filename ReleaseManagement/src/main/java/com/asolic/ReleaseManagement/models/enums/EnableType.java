package com.asolic.ReleaseManagement.models.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum EnableType {
    @JsonProperty("ALL")
    ALL,
    @JsonProperty("PER_ACCOUNT")
    PER_ACCOUNT

}
