package yummylau.modulea.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.database.Cursor;

import java.util.List;

import yummylau.modulea.bean.Address;
import yummylau.modulea.bean.User;
import yummylau.modulea.bean.UserItem;

/**
 * Insert/Update默认发生冲突时终止操作
 * Query，编译器检查查询语句
 * Created by g8931 on 2017/11/22.
 */
@Dao
public interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public long[] insertUsers(User... users);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public long insertUser(User user);

    @Update
    public void updateUsers(User... users);

    @Delete
    public void deleteUsers(User... users);

    @Query("SELECT * from users")
    public User[] loadAllUser();

    @Query("SELECT * FROM users WHERE userAge > :minAge")
    public User[] loadAllUsersOlderThan(int minAge);

    @Query("SELECT * FROM users WHERE userAge BETWEEN :minAge AND :maxAge")
    public User[] loadAllUsersBetweenAges(int minAge, int maxAge);

    @Query("SELECT * FROM users WHERE userName LIKE :search")
    public List<User> findUserWithName(String search);

    @Query("SELECT userId,userName FROM users")
    public List<UserItem> loadFullName();

    @Query("SELECT street,state,city FROM users")
    public List<Address> loadAllAddress();

    @Query("SELECT userId FROM users WHERE userAge IN (:userAges)")
    public List<UserItem> loadUsersFromAges(List<Integer> userAges);

    @Query("SELECT userId FROM users WHERE userAge IN (:userAges)")
    public LiveData<List<UserItem>> loadUsersFromAgesSync(List<Integer> userAges);

    @Query("SELECT * FROM users WHERE userAge > :minAge LIMIT 5")
    public Cursor loadRawUsersOlderThan(int minAge);
}
