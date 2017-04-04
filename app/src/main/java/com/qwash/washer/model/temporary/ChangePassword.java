package com.qwash.washer.model.temporary;

/**
 * Created by Amay on 3/8/2017.
 */

public class ChangePassword {
    private final String password;
    private boolean status = false;

    public ChangePassword(boolean status, String password) {
        this.status = status;
        this.password = password;
    }


    public boolean getStatus() {
        return status;
    }

    public String getPassword() {
        return password;
    }
}
