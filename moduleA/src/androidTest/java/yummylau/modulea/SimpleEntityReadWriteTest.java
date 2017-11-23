package yummylau.modulea;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import yummylau.modulea.bean.Address;
import yummylau.modulea.bean.User;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Created by g8931 on 2017/11/23.
 */

@RunWith(AndroidJUnit4.class)
public class SimpleEntityReadWriteTest {

    private TestDao mUserDao;
    private TestDataBase mDb;

    @Before
    public void createDb() {
        Context context = InstrumentationRegistry.getTargetContext();
        mDb = Room.inMemoryDatabaseBuilder(context, TestDataBase.class).build();
        mUserDao = mDb.userDao();
    }

    @After
    public void closeDb() throws IOException {
        mDb.close();
    }

    @Test
    public void writeUserAndReadInList() throws Exception {
        User user = new User();
        user.id = 1;
        user.name = "name1";
        user.age = 1;
        Address address = new Address();
        address.state = "state1";
        address.street = "street1";
        address.city = "city1";
        user.address = address;
        mUserDao.insertUser(user);
        User[] byName = mUserDao.loadAllUser();
        assertThat(byName[0], equalTo(user));
    }
}
