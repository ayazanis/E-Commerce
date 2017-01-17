package info.androidhive.navigationdrawer.Model;

import android.graphics.Bitmap;

/**
 * Created by Admin on 03-11-2016.
 */

public class ProductModel {
    Integer prod_id;
    String prod_name,prod_price,prod_quantity;
    String prod_image,prod_desc;

    public String getProd_desc() {
        return prod_desc;
    }

    public void setProd_desc(String prod_desc) {
        this.prod_desc = prod_desc;
    }

    public Integer getProd_id() {
        return prod_id;
    }

    public void setProd_id(Integer prod_id) {
        this.prod_id = prod_id;
    }

    public String getProd_name() {
        return prod_name;
    }

    public void setProd_name(String prod_name) {
        this.prod_name = prod_name;
    }

    public String getProd_price() {
        return prod_price;
    }

    public void setProd_price(String prod_price) {
        this.prod_price = prod_price;
    }

    public String getProd_quantity() {
        return prod_quantity;
    }

    public void setProd_quantity(String prod_quantity) {
        this.prod_quantity = prod_quantity;
    }

    public String getProd_image() {
        return prod_image;
    }

    public void setProd_image(String prod_image) {
        this.prod_image = prod_image;
    }
}
