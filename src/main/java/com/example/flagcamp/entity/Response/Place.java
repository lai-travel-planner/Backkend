package com.example.flagcamp.entity.Response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonDeserialize(builder = Place.Builder.class)
public class Place {
    @JsonProperty("place_id")
    private final String id;

    @JsonProperty("place_name")
    private final String name;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    // 可以只初始值其中的几个field members
    public static class Builder {
        @JsonProperty("id")
        private String id;

        @JsonProperty("name")
        private String name;

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Place build() {
            return new Place(this);
        }
    }

    private Place(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
    }

}
