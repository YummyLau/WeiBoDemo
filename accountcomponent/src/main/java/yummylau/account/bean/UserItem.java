package yummylau.account.bean;

import android.arch.persistence.room.ColumnInfo;

/**
 * Email yummyl.lau@gmail.com
 * Created by yummylau on 2017/12/11.
 */

public class UserItem {

    @ColumnInfo(name = "userId")
    public int id;

    @ColumnInfo(name = "userName")
    public String name;
}
