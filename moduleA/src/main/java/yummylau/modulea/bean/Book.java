package yummylau.modulea.bean;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by g8931 on 2017/11/21.
 */
@Entity(tableName = "book",foreignKeys = @ForeignKey(entity = UserTest.class,parentColumns = "id",childColumns = "user_id"))
public class Book {

    @PrimaryKey
    public int bookId;

    @ColumnInfo(name = "user_id")
    public int userId;

}
