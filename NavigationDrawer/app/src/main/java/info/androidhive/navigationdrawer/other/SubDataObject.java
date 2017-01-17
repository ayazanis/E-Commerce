package info.androidhive.navigationdrawer.other;

import java.util.ArrayList;

/**
 * Created by Admin on 19-10-2016.
 */

public class SubDataObject {

    private String mText1;
    private String mText2;
    private String mImage;

    public String getmImage() {
        return mImage;
    }

    public void setmImage(String mImage) {
        this.mImage = mImage;
    }

    public String getmText2() {
        return mText2;
    }

    public void setmText2(String mText2) {
        this.mText2 = mText2;
    }

    public String getmText1() {
        return mText1;
    }

    public void setmText1(String mText1) {
        this.mText1 = mText1;
    }





    public SubDataObject(String text1, String text2 , String image){
        mText1 = text1;
        mText2 = text2;
        mImage = image;
    }

}
