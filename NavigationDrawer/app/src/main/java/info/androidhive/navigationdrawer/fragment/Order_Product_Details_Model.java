package info.androidhive.navigationdrawer.fragment;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Admin on 26-12-2016.
 */

public class Order_Product_Details_Model{


    String product_name;
    String product_quantity;
    String product_price;
    String product_total;
    String product_weight;
    String unit;
    String product_weight_class_id;

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_quantity() {
        return product_quantity;
    }

    public void setProduct_quantity(String product_quantity) {
        this.product_quantity = product_quantity;
    }

    public String getProduct_price() {
        return product_price;
    }

    public void setProduct_price(String product_price) {
        this.product_price = product_price;
    }

    public String getProduct_total() {
        return product_total;
    }

    public void setProduct_total(String product_total) {
        this.product_total = product_total;
    }

    public String getProduct_weight() {
        return product_weight;
    }

    public void setProduct_weight(String product_weight) {
        this.product_weight = product_weight;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getProduct_weight_class_id() {
        return product_weight_class_id;
    }

    public void setProduct_weight_class_id(String product_weight_class_id) {
        this.product_weight_class_id = product_weight_class_id;
    }


}
