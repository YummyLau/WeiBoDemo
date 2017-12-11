package yummylau.common.net.download;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 下载apk
 * Email yummyl.lau@gmail.com
 * Created by yummylau on 2017/12/11.
 */

public class ApkInfo implements Parcelable {

    public String url;

    public String name;

    public String pkgName;

    public String apkName;

    public int notificationId;

    public ApkInfo(String url, String name, String pkgName, String apkName, int id) {
        this.url = url;
        this.name = name;
        this.pkgName = pkgName;
        this.apkName = apkName;
        this.notificationId = id;
    }

    protected ApkInfo(Parcel in) {
        url = in.readString();
        name = in.readString();
        pkgName = in.readString();
        apkName = in.readString();
        notificationId = in.readInt();
    }

    public static final Creator<ApkInfo> CREATOR = new Creator<ApkInfo>() {
        @Override
        public ApkInfo createFromParcel(Parcel in) {
            return new ApkInfo(in);
        }

        @Override
        public ApkInfo[] newArray(int size) {
            return new ApkInfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(url);
        dest.writeString(name);
        dest.writeString(pkgName);
        dest.writeString(apkName);
        dest.writeInt(notificationId);
    }

}
