package com.example.flagcamp.entity.db;

import com.example.flagcamp.entity.Response.Place;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Users")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "user_id")
    @JsonProperty("user_id")
    private String userId;

    @Column(name = "user_name")
    @JsonProperty("user_name")
    private String userName;

    @JsonProperty("password")
    private String password;

    @Column(name = "email_address")
    @JsonProperty("email_address")
    private String emailAddress;

    // fetch : EAGER, 得到user信息的时候，需要马上知道信息的值
    // joinTable : joinColumns = 主导 ， inversejoinColumns = 副的
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "cart_records", joinColumns = { @JoinColumn(name = "user_id")}, inverseJoinColumns = {@JoinColumn(name = "place_id")})
    Set<place> placeSet = new HashSet<>();


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
    public Set<place> getPlaceSet() {
        return placeSet;
    }

    public void setPlaceSet(Set<place> placeSet) {
        this.placeSet = placeSet;
    }

}