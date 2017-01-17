package info.androidhive.navigationdrawer.other;

/**
 * Created by Admin on 27-09-2016.
 */
public class Items {
    String Item_Title;
    int Item_Image;

    public Items(String Item_Title,int Item_Image){
        this.Item_Title = Item_Title;
        this.Item_Image = Item_Image;
    }

    public String getItem_Title() {
        return Item_Title;
    }

    public void setItem_Title(String item_Title) {
        Item_Title = item_Title;
    }

    public int getItem_Image() {
        return Item_Image;
    }

    public void setItem_Image(int item_Image) {
        Item_Image = item_Image;
    }


}
