package com.mx.app.base.data;

import com.mx.app.base.domain.User;

public interface DataProvider {

    /**
     * @param userName
     * @param password
     * @return Authenticated used.
     */
    User authenticate(String userName, String password);

}
