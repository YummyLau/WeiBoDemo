package yummylau.feature.data.local.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import java.util.List;

import yummylau.feature.data.local.db.entity.UserEntity;


/**
 * Created by g8931 on 2017/12/4.
 */
@Dao
public interface UserDao {

    @Query("SELECT * FROM user_table")
    LiveData<List<UserEntity>> getUsers();
}
