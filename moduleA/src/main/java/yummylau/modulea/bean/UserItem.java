package yummylau.modulea.bean;

import android.arch.persistence.room.ColumnInfo;

/**
 * Created by g8931 on 2017/11/22.
 */

public class UserItem {

    @ColumnInfo(name = "userId")
    public int id;

    @ColumnInfo(name = "userName")
    public String name;
}
