package yummylau.feature;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.reactivex.functions.Predicate;
import yummylau.feature.data.local.db.AppDataBase;
import yummylau.feature.data.local.db.entity.UserEntity;

/**
 * 测试数据库
 * Created by g8931 on 2017/12/8.
 */

@RunWith(AndroidJUnit4.class)
public class UserDaoTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule =
            new InstantTaskExecutorRule();

    private AppDataBase mAppDataBase;

    private static UserEntity USER;

    @Before
    public void initDB() throws Exception {
        mAppDataBase = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(), AppDataBase.class)
                .allowMainThreadQueries()
                .build();
        USER = new UserEntity();
        USER.name = "test_name";
        USER.id = 1;
    }

    @After
    public void closeDb() throws Exception {
        mAppDataBase.close();
    }

    @Test
    public void insertAndGetUserById() {
        // Given that we have a user in the data source
        mAppDataBase.userDao().insertUser(USER);
        // When subscribing to the emissions of user

        // When subscribing to the emissions of the user
        mAppDataBase.userDao().getUser()
                .test()
                // assertValue asserts that there was only one emission of the user
                .assertValue(new Predicate<UserEntity>() {
                    @Override
                    public boolean test(UserEntity userEntity) throws Exception {
                        return userEntity != null && userEntity.id == USER.id && userEntity.name.equals(USER.name);
                    }
                });

    }

    @Test
    public void updateAndGetUser() {
        // Given that we have a user in the data source
        mAppDataBase.userDao().insertUser(USER);

        // When we are updating the name of the user
        UserEntity updatedUser = new UserEntity();
        updatedUser.id = 1;
        updatedUser.name = "new username";
        mAppDataBase.userDao().insertUser(updatedUser);

        // When subscribing to the emissions of the user
        mAppDataBase.userDao().getUser()
                .test()
                // assertValue asserts that there was only one emission of the user
                .assertValue(new Predicate<UserEntity>() {
                    @Override
                    public boolean test(UserEntity userEntity) throws Exception {
                        return userEntity != null && userEntity.id == USER.id && userEntity.name.equals(USER.name);
                    }
                });
    }

    @Test
    public void deleteAndGetUser() {
        // Given that we have a user in the data source
        mAppDataBase.userDao().insertUser(USER);

        //When we are deleting all users
        mAppDataBase.userDao().deleteAllUsers();
        // When subscribing to the emissions of the user
        mAppDataBase.userDao().getUser()
                .test()
                // check that there's no user emitted
                .assertNoValues();
    }
}
