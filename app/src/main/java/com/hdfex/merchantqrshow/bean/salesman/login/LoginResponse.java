package com.hdfex.merchantqrshow.bean.salesman.login;

import com.hdfex.merchantqrshow.bean.Response;

/**
 * Created by harrishuang on 16/7/5.
 */
public class LoginResponse  extends Response {

   private User result;

    public User getResult() {

        return result;
    }
    public void setResult(User result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "LoginResponse{" +
                "result=" + result +
                '}';
    }
}
