package com.mx.app.base.data.dummy;

import com.mx.app.base.data.DataProvider;
import com.mx.app.base.domain.User;
import java.util.Calendar;
import java.util.Date;


/**
 * A dummy implementation for the backend API.
 */
public class DummyDataProvider implements DataProvider {
    
    private static Date lastDataUpdate;

/**
     * Initialize the data for this application.
     */
    public DummyDataProvider() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, -1);
        if (lastDataUpdate == null || lastDataUpdate.before(cal.getTime())) {
            //refreshStaticData();
            lastDataUpdate = new Date();
        }
    }

    @Override
    public User authenticate(String userName, String password) {
        User user = new User();
        user.setFirstName(DummyDataGenerator.randomFirstName());
        user.setLastName(DummyDataGenerator.randomLastName());
        user.setRole("admin");
        String email = user.getFirstName().toLowerCase() + "."
                + user.getLastName().toLowerCase() + "@"
                + DummyDataGenerator.randomCompanyName().toLowerCase() + ".com";
        user.setEmail(email.replaceAll(" ", ""));
        user.setLocation(DummyDataGenerator.randomWord(5, true));
        user.setBio("Quis aute iure reprehenderit in voluptate velit esse."
                + "Cras mattis iudicium purus sit amet fermentum.");
        return user;
    }

}
