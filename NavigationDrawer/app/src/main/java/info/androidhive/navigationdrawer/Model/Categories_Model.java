package info.androidhive.navigationdrawer.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Admin on 14-10-2016.
 */

public class Categories_Model {

    String Category_id;
    String Category_name;
    String Category_image;
    String Category_status;



    public String getCategory_id() {
        return Category_id;
    }

    public void setCategory_id(String category_id) {
        Category_id = category_id;
    }

    public String getCategory_name() {
        return Category_name;
    }

    public void setCategory_name(String category_name) {
        Category_name = category_name;
    }

    public String getCategory_image() {
        return Category_image;
    }

    public void setCategory_image(String category_image) {
        Category_image = category_image;
    }

    public String getCategory_status() {
        return Category_status;
    }

    public void setCategory_status(String category_status) {
        Category_status = category_status;
    }


}
