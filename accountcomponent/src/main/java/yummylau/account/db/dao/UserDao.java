package yummylau.account.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.database.Cursor;

import java.util.List;

import yummylau.account.bean.Address;
import yummylau.account.bean.UserTest;
import yummylau.account.bean.UserItem;

/**
 * Insert/Update默认发生冲突时终止操作
 * Query，编译器检查查询语句
 * Created by g8931 on 2017/11/22.
 */
@Dao
public interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public long[] insertUsers(UserTest... users);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public long insertUser(UserTest user);

    @Update
    public void updateUsers(UserTest... users);

    @Delete
    public void deleteUsers(UserTest... users);

    @Query("SELECT * from UserTest")
    public UserTest[] loadAllUser();

    @Query("SELECT * FROM UserTest WHERE userAge > :minAge")
    public UserTest[] loadAllUsersOlderThan(int minAge);

    @Query("SELECT * FROM UserTest WHERE userAge BETWEEN :minAge AND :maxAge")
    public UserTest[] loadAllUsersBetweenAges(int minAge, int maxAge);

    @Query("SELECT * FROM UserTest WHERE userName LIKE :search")
    public List<UserTest> findUserWithName(String search);

    @Query("SELECT userId,userName FROM UserTest")
    public List<UserItem> loadFullName();

    @Query("SELECT street,state,city FROM UserTest")
    public List<Address> loadAllAddress();

    @Query("SELECT userId,userName FROM UserTest WHERE userId IN (:userAges)")
    public List<UserItem> loadUsersFromAges(List<Integer> userAges);

    @Query("SELECT userId,userName FROM UserTest WHERE userId IN (:userAges)")
    public LiveData<List<UserItem>> loadUsersFromAgesSync(List<Integer> userAges);

    @Query("SELECT * FROM UserTest WHERE userAge > :minAge LIMIT 5")
    public Cursor loadRawUsersOlderThan(int minAge);
}
