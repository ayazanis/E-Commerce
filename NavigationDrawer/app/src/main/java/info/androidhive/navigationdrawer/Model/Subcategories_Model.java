package info.androidhive.navigationdrawer.Model;

import android.graphics.Bitmap;

/**
 * Created by Admin on 19-10-2016.
 */

public class Subcategories_Model {
    String Category_id;
    String SubCategory_id;
    String SubCategory_name;
    String SubCategory_image;
    String SubCategory_description;
    String SubCategory_price;
    String SubCategory_quantity;
    Bitmap SubCategory_image_bitmap;

    public String getSubCategory_quantity() {
        return SubCategory_quantity;
    }

    public void setSubCategory_quantity(String subCategory_quantity) {
        SubCategory_quantity = subCategory_quantity;
    }

    public Bitmap getSubCategory_image_bitmap() {
        return SubCategory_image_bitmap;
    }

    public void setSubCategory_image_bitmap(Bitmap subCategory_image_bitmap) {
        SubCategory_image_bitmap = subCategory_image_bitmap;
    }

    public String getSubCategory_price() {
        return SubCategory_price;
    }

    public void setSubCategory_price(String subCategory_price) {
        SubCategory_price = subCategory_price;
    }

    public String getCategory_id() {
        return Category_id;
    }

    public void setCategory_id(String category_id) {
        Category_id = category_id;
    }

    public String getSubCategory_id() {
        return SubCategory_id;
    }

    public void setSubCategory_id(String subCategory_id) {
        SubCategory_id = subCategory_id;
    }

    public String getSubCategory_name() {
        return SubCategory_name;
    }

    public void setSubCategory_name(String subCategory_name) {
        SubCategory_name = subCategory_name;
    }

    public String getSubCategory_image() {
        return SubCategory_image;
    }

    public void setSubCategory_image(String subCategory_image) {
        SubCategory_image = subCategory_image;
    }

    public String getSubCategory_description() {
        return SubCategory_description;
    }

    public void setSubCategory_description(String subCategory_description) {
        SubCategory_description = subCategory_description;
    }
}
