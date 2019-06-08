package com.xfpay.response.user;

import com.xfpay.entity.User;
import com.xfpay.response.PayResponse;

/**
 * Created by fei on 2019/6/7.
 */
public class UserResponse extends PayResponse {

    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
