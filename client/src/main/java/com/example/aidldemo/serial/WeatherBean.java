package com.example.aidldemo.serial;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Serializable 序列化
 * Created by hp on 2019/9/16.
 */
public class WeatherBean implements Serializable {
    //没有此字段，其他字段变化会报错，有此字段支持增删，改不会报错但是写入的文件不会
    private static final int serialVersionUID = 1010010101;
    public String conditionId;
    public String conditionName;
    public int age;

    public WeatherBean(String conditionId, String conditionName) {
        this.conditionId = conditionId;
        this.conditionName = conditionName;
    }

    @Override
    public String toString() {
        return "WeatherBean{" +
                "conditionId='" + conditionId + '\'' +
                ", conditionName='" + conditionName + '\'' +
                ", age=" + age +
                '}';
    }

    /**
     * 序列化与反序列化，对象内容一样，但是不是同一个对象
     * @param file
     */
    private void serialWeather(File file) {
        WeatherBean bean = new WeatherBean("123", "晴天");
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file.getAbsolutePath()));
            outputStream.writeObject(bean);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void unSerialWeather(File file) {
        try {
            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(file.getAbsolutePath()));
            WeatherBean weatherBean = (WeatherBean) inputStream.readObject();
            inputStream.close();
            Log.e("tag", weatherBean.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void test(Context context) {
        String path = context.getFilesDir().getAbsolutePath();
        File file = new File(path, "weather.txt");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Log.e("tag", file.getAbsolutePath());
        serialWeather(file);
        unSerialWeather(file);
    }
}
