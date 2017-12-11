package yummylau.account.bean;

import android.arch.persistence.room.Entity;

/**
 * Email yummyl.lau@gmail.com
 * Created by yummylau on 2017/12/11.
 */
@Entity(tableName = "address")
public class Address {

    public String street;
    public String state;
    public String city;
}
