package com.example.weatherlib.serial;

import android.os.Parcel;
import android.os.Parcelable;
/*
* Parcelable与Serializable的性能比较 首先Parcelable的性能要强于Serializable的原因

  1）.在内存的使用中,前者在性能方面要强于后者

  2）.后者在序列化操作的时候会产生大量的临时变量,(原因是使用了反射机制)从而导致GC的频繁调用,因此在性能上会稍微逊色

  3）.Parcelable是以Ibinder作为信息载体的.在内存上的开销比较小,因此在内存之间进行数据传递的时候,Android推荐使用Parcelable,
  既然是内存方面比价有优势,那么自然就要优先选择.

  4）.在读写数据的时候,Parcelable是在内存中直接进行读写,而Serializable是通过使用IO流的形式将数据读写入在硬盘上.

  但是：虽然Parcelable的性能要强于Serializable,但是仍然有特殊的情况需要使用Serializable,而不去使用Parcelable,因为Parcelable
  无法将数据进行持久化,因此在将数据保存在磁盘的时候,仍然需要使用后者,因为前者无法很好的将数据进行持久化.
  (原因是在不同的Android版本当中,Parcelable可能会不同,因此数据的持久化方面仍然是使用Serializable)
* */

/**
 * android独有的接口，效率高，使用麻烦点
 * Parcelable 的实现类，Intent、 Bundle、Bitmap等
 * Created by hp on 2019/9/16.
 */
public class WeatherBeanP implements Parcelable {
    public String conditionId;
    public String conditionName;
    public Book bean;

    public WeatherBeanP(String conditionId, String conditionName) {
        this.conditionId = conditionId;
        this.conditionName = conditionName;
    }

    /**
     * 从序列化后的对象中创建原始对象
     *
     * @param in
     */
    private WeatherBeanP(Parcel in) {
        conditionId = in.readString();
        conditionName = in.readString();
        bean = in.readParcelable(Thread.currentThread().getContextClassLoader());

    }

    /**
     * 序列化
     * 注：写入数据的顺序和读出数据的顺序必须是相同的.
     *
     * @param dest
     * @param flags
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(conditionId);
        dest.writeString(conditionName);
        dest.writeParcelable(bean, 0);
    }

    /**
     * 内容描述 默认返回0，仅当当前对象中存在文件描述，返回1
     *
     * @return
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * 反序列化
     */
    public static final Creator<WeatherBeanP> CREATOR = new Creator<WeatherBeanP>() {
        /**
         * 注：写入数据的顺序和读出数据的顺序必须是相同的.
         * 从序列化后的对象中创建原始对象
         * @param in
         * @return
         */
        @Override
        public WeatherBeanP createFromParcel(Parcel in) {
            return new WeatherBeanP(in);
        }

        /**
         * 创建指定长度的原始对象数据
         * @param size
         * @return
         */
        @Override
        public WeatherBeanP[] newArray(int size) {
            return new WeatherBeanP[size];
        }
    };

    @Override
    public String toString() {
        return "WeatherBeanP{" +
                "conditionId='" + conditionId + '\'' +
                ", conditionName='" + conditionName + '\'' +
                ", bean=" + bean +
                '}';
    }

    public void test() {
        //WeatherBeanP mainBean = new WeatherBeanP("123", "小雨");
//        Intent intent = new Intent(FirstParcelableActivity.this,SecondParcelableActivity.class);
//        intent.putExtra("parcelable",mainBean);
//        Bundle bundle = intent.getExtras();
//        bundle.putParcelable("parcelable1",mainBean);
//        intent.putExtras(bundle);
//        startActivity(intent);

//        Intent intent = getIntent();
//        WeatherBeanP mainBean = intent.getParcelableExtra("parcelable");
//        Bundle bundle = getIntent().getExtras();
//        WeatherBeanP mainBean1 = bundle.getParcelable("parcelable1");
    }
}
