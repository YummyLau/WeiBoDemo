package yummylau.feature.data.local.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import io.reactivex.Flowable;
import yummylau.feature.data.local.db.entity.UserEntity;


/**
 * Created by g8931 on 2017/12/4.
 */
@Dao
public interface UserDao {

    @Query("SELECT * FROM " + UserEntity.TABLE_NAME + " WHERE " + UserEntity.COLUMN_ID_NAME + " = :uid")
    Flowable<UserEntity> getUserById(long uid);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertUser(UserEntity user);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insertUsers(List<UserEntity> user);
}
