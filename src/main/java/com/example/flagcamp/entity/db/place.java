package com.example.flagcamp.entity.db;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Places")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class place implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @JsonProperty("place_id")
    private String placeId;

    @Column(name = "place_name")
    @JsonProperty("place_name")
    private String placeName;

    @Column(name = "address")
    @JsonProperty("address")
    private String address;

    @Column(name = "content")
    @JsonProperty("content")
    private String content;

    @Column(name = "category")
    @JsonProperty("category")
    private String category;

    @JsonIgnore
    @ManyToMany(mappedBy = "placeSet")
    private Set<User> users= new HashSet<>();

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}