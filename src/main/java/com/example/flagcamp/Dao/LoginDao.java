package com.example.flagcamp.Dao;

import com.example.flagcamp.entity.db.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class LoginDao {

    @Autowired
    private SessionFactory sessionFactory;

    // Verify if the given user Id and password are correct. Returns the user name when it passes
    public String verifyLogin(String userId, String password) {
        String name = "";

        try (Session session = sessionFactory.openSession()) {   // 自动创建Session
            User user = session.get(User.class, userId); // 返回这个User_id对应的user是谁
            // User.class User这个Object所属的class
            if(user != null && user.getPassword().equals((password))) {
                name = user.getUserName();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return name;
    }
}