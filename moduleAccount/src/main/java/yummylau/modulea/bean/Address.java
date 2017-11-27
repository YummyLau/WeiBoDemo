package yummylau.modulea.bean;

import android.arch.persistence.room.Entity;

/**
 * Created by g8931 on 2017/11/21.
 */
@Entity(tableName = "address")
public class Address {

    public String street;
    public String state;
    public String city;
}
