package info.androidhive.navigationdrawer.other;

import java.util.ArrayList;

/**
 * Created by Admin on 27-09-2016.
 */
public class DataObject {
    private String mText1;
    private String mText2;
    private ArrayList<Integer> mImage;

    public DataObject(String text1, String text2 , ArrayList<Integer> image){
        mText1 = text1;
        mText2 = text2;
        mImage = image;
    }

    public String getmText1() {
        return mText1;
    }

    public void setmText1(String mText1) {
        this.mText1 = mText1;
    }

    public String getmText2() {
        return mText2;
    }

    public void setmText2(String mText2) {
        this.mText2 = mText2;
    }
    public ArrayList<Integer> getmImage() {

        return mImage;
    }

    public void setmImage(ArrayList<Integer> mImage) {

        this.mImage = mImage;
    }
}
