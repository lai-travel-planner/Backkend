package com.example.flagcamp.entity.Request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginRequestBody { //将前端post过来的body自动转换成LoginRequestBody这样的一个class object
    private final String userId;
    private final String password;

    @JsonCreator  // deserialization
    public LoginRequestBody(@JsonProperty("user_id") String userId, @JsonProperty("password") String password) { // 让jason自动去找到对应的值赋值给userId和password
        this.userId = userId;
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }
}