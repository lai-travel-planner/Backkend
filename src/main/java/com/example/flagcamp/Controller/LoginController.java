package com.example.flagcamp.Controller;

import com.example.flagcamp.Service.LoginService;
import com.example.flagcamp.entity.Request.LoginRequestBody;
import com.example.flagcamp.entity.Response.LoginResponseBody;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
public class LoginController {
    @Autowired
    private LoginService loginService;

    @RequestMapping(value = "/login", method = RequestMethod.POST) // 定义RES API 定义了做Login的时候，有一个api去serve这个请求，就是post这个method.
    public void login(@RequestBody LoginRequestBody requestBody, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userName = loginService.verifyLogin(requestBody.getUserId(), requestBody.getPassword());

        // Create a new session for the user if user ID and password are correct, otherwise return Unauthorized error.
        if (!userName.isEmpty()) {
            // Create a new session, put user ID as an attribute into the session object, and set the expiration time to 600 seconds.
            HttpSession session = request.getSession();  // 返回和当前request相关的Session，如果没有Session，创建一个新的
            session.setAttribute("user_id", requestBody.getUserId());
            session.setMaxInactiveInterval(600);
            LoginResponseBody loginResponseBody = new LoginResponseBody(requestBody.getUserId(), userName);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().print(new ObjectMapper().writeValueAsString(loginResponseBody));
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
}

