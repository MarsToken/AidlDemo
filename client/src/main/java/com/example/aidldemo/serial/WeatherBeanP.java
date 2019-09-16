package com.example.aidldemo.serial;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by hp on 2019/9/16.
 */
public class WeatherBeanP implements Parcelable {
    public String conditionId;
    public String conditionName;

    protected WeatherBeanP(String conditionId, String conditionName) {
        this.conditionId = conditionId;
        this.conditionName = conditionName;
    }

    private WeatherBeanP(Parcel in) {
        conditionId = in.readString();
        conditionName = in.readString();
    }

    /**
     * 序列化
     *
     * @param dest
     * @param flags
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(conditionId);
        dest.writeString(conditionName);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * 反序列化
     */
    public static final Creator<WeatherBeanP> CREATOR = new Creator<WeatherBeanP>() {
        @Override
        public WeatherBeanP createFromParcel(Parcel in) {
            return new WeatherBeanP(in);
        }

        @Override
        public WeatherBeanP[] newArray(int size) {
            return new WeatherBeanP[size];
        }
    };
}
