package yummylau.modulea.bean;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.graphics.Bitmap;

/**
 * 1. 至少定义一个primary key
 * 2. 默认情况下，使用类名作为表名，也可以通过声明定义表名,不区分大小写
 * 3. 默认情况下，使用变量名作为列名，也可以通过声明定义列名
 * Created by g8931 on 2017/11/21.
 */
@Entity(primaryKeys = "id", tableName = "users", indices = {@Index(name = "info", value = {"userName", "userAddress"})})
class User {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "userId")
    public int id;


    @ColumnInfo(name = "userName")
    public String name;

    @ColumnInfo(name = "userAddress")
    @Embedded
    public Address address;

    @Ignore
    public Bitmap photo;
}
