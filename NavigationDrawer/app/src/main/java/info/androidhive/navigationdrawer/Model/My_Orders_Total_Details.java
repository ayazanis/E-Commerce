package info.androidhive.navigationdrawer.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Admin on 26-12-2016.
 */

public class My_Orders_Total_Details implements Parcelable{

    String order_total_id;
    String order_id;
    String code;
    String title;
    String value;
    String sort_order;


    public String getSort_order() {
        return sort_order;
    }

    public void setSort_order(String sort_order) {
        this.sort_order = sort_order;
    }

    public String getOrder_total_id() {
        return order_total_id;
    }

    public void setOrder_total_id(String order_total_id) {
        this.order_total_id = order_total_id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(order_total_id);
        parcel.writeString(order_id);
        parcel.writeString(code);
        parcel.writeString(title);
        parcel.writeString(value);
        parcel.writeString(sort_order);
    }
}
