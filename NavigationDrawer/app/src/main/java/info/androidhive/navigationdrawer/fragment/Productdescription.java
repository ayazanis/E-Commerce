package info.androidhive.navigationdrawer.fragment;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ShareCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;
import java.util.List;

import info.androidhive.navigationdrawer.Model.CartOrderModel;
import info.androidhive.navigationdrawer.Model.ProductModel;
import info.androidhive.navigationdrawer.Model.Subcategories_Model;
import info.androidhive.navigationdrawer.R;
import info.androidhive.navigationdrawer.activity.MainActivity;
import info.androidhive.navigationdrawer.other.DataHelper;
import info.androidhive.navigationdrawer.other.DataHelper_FavoriteList;
import info.androidhive.navigationdrawer.other.GlobalClass;
import info.androidhive.navigationdrawer.other.OnBackPressedListener;
import info.androidhive.navigationdrawer.other.VolleyHelper;

/**
 * A simple {@link Fragment} subclass.
 */
public class Productdescription extends Fragment implements OnBackPressedListener {

    private SQLiteDatabase s;

    NetworkImageView productdescription_productimage;
    ImageView productdescription_addtocartimage,productdescription_addtowishlistimage;
    public  static  TextView productdescription_costtext,productdescription_descriptiontext,productdescription_prodname,productdescription_quanText;
    public static Button productdescription_decButton,productdescription_incButton,productdescription_addButton;
    public static int index;
    public static String title;
    TextView title_txt;
    boolean isFavorite;
    public static int product_index;
    ImageView fav_img,checkout_hide,back_btn;
    public static LinearLayout add_layout;
    ArrayAdapter<String> aa;
    Button addtocart;
    Subcategories_Model subcategories_model;
    public static DataHelper dataHelper;
    Bitmap bitmap;
    public  static String str1,str2,str3,str4;
    int count = 0;
    int product_id;
    List<ProductModel> dbData;
    ArrayList<ProductModel> FavoriteList;

    DataHelper_FavoriteList dataHelper_favoriteList;

    Cursor cursor;
    public static ArrayList<Subcategories_Model> array_prod_desc_list;
    public static  ArrayList<CartOrderModel> array_cartlist;
    public static Integer pagenumber;     //it will check from where the control is coming to this page
    public Productdescription() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_productdescription, container, false);
        MainActivity.pageIndex=3;
        subcategories_model  = new Subcategories_Model();
        productdescription_addButton = (Button) v.findViewById(R.id.productdesc_addbutton);
        productdescription_incButton = (Button) v.findViewById(R.id.productdesc_incbutton);
        productdescription_decButton = (Button) v.findViewById(R.id.productdesc_decbutton);
        productdescription_quanText =(TextView) v.findViewById(R.id.productdesc_counttext);

        array_prod_desc_list = new ArrayList<>();
        addtocart = (Button) v.findViewById(R.id.productdesc_addtocart);
        array_prod_desc_list = Sub_list_category.array_subcategory_list;
        add_layout  = (LinearLayout) v.findViewById(R.id.productdesc_addlayout);
        checkout_hide = (ImageView)v.findViewById(R.id.checkout_text) ;
        title_txt = (TextView)v.findViewById(R.id.toolbar_3_main_title);
        fav_img = (ImageView)v.findViewById(R.id.productdesc_wishlistimage);
        array_cartlist = new ArrayList<>();

        dbData = new ArrayList<>();
        FavoriteList=new ArrayList<>();

        title_txt.setText("Product Description");
        back_btn = (ImageView)v.findViewById(R.id.toolbar_3_back_image);
        productdescription_productimage = (NetworkImageView) v.findViewById(R.id.productdesc_productimage);
        productdescription_addtowishlistimage = (ImageView) v.findViewById(R.id.productdesc_wishlistimage);
        productdescription_costtext = (TextView) v.findViewById(R.id.productdesc_costtextview);
        productdescription_descriptiontext = (TextView) v.findViewById(R.id.productdesc_desctextview);
        productdescription_prodname = (TextView) v.findViewById(R.id.productdesc_prodnametextview);

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                fm.popBackStack();
            }
        });

        loadproduct();

        count=getcount();

        Log.i("!!!!!!!!!!!","!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! in onCreate count="+count);
        productdescription_quanText.setText(""+count);

        fav_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataHelper_favoriteList=new DataHelper_FavoriteList(getContext());
                FavoriteList=dataHelper_favoriteList.getDataFromDB();
                ProductModel productModel = new ProductModel();
                productModel.setProd_id(Integer.parseInt(subcategories_model.getSubCategory_id()));
                productModel.setProd_price(subcategories_model.getSubCategory_price());
                productModel.setProd_name(subcategories_model.getSubCategory_name());
                productModel.setProd_quantity(subcategories_model.getSubCategory_quantity());
                productModel.setProd_image(subcategories_model.getSubCategory_image());
                productModel.setProd_desc(subcategories_model.getSubCategory_description());

                if(FavoriteList.size()!=0){

                        int ii=0;
                        int i;
                        for(i=0;i<FavoriteList.size();i++){
                            Log.i("inside for loop","inside for loop subcategories_model.getSubCategory_id="+subcategories_model.getSubCategory_id() +" FavoriteList.get(i).getProd_id"+ FavoriteList.get(i).getProd_id());
                            if(Integer.parseInt(subcategories_model.getSubCategory_id())==(FavoriteList.get(i).getProd_id())){
                                Log.i("inside if ","inside if condition ");
                                ii=1;
                                break;
                            }
                        }
                        if(ii==1)
                        {
                            Log.i("inside second if ","inside second if condition ");
                            fav_img.setImageResource(R.drawable.wish_list_blank);
                            isFavorite = false;
                            FavoriteList.remove(i);
                            ii=0;
                            dataHelper_favoriteList.deleteData(productModel.getProd_id());
                        }

                    else
                    {
                        fav_img.setImageResource(R.drawable.wish_list_red);
                        isFavorite=true;
                        FavoriteList.add(productModel);
                        dataHelper_favoriteList.insertprod_details(productModel.getProd_id(),productModel.getProd_name(),productModel.getProd_price(),productModel.getProd_quantity(),productModel.getProd_image());
                    }
                }
                else
                {
                    fav_img.setImageResource(R.drawable.wish_list_red);
                    isFavorite=true;
                    FavoriteList.add(productModel);
                    dataHelper_favoriteList.insertprod_details(productModel.getProd_id(),productModel.getProd_name(),productModel.getProd_price(),productModel.getProd_quantity(),productModel.getProd_image());
                }

                /*for(int i=0;i<dataHelper.getDataFromDB().size();i++)
                {
                    Log.i("Favorite List","Favorite List from database="+dataHelper.getDataFromDB().get(i).getProd_id()+" size="+MainActivity.FavoriteList.size());
                }*/
            }
        });

        productdescription_addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productdescription_addButton.setVisibility(View.GONE);
                add_layout.setVisibility(View.VISIBLE);
                if(count==0)
                    count++;
                    productdescription_quanText.setText(""+count);
                str3 = productdescription_quanText.getText().toString();
                Log.i("1111111111","1111111111111111111111112222222222222222"+str3);
                loadproduct();
            }
        });

        productdescription_incButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                productdescription_quanText.setText("" + count);
                Log.i("~~~~~~~~~%%%%%%", "~~~~~~~~~~~~~~~~" + count);
                str3 = productdescription_quanText.getText().toString();
                Log.i("1111111111", "1111111111111111111111112222222222222222" + str3);

                loadproduct();
            }
        });

        productdescription_decButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(count==1){
                    count--;
                    add_layout.setVisibility(View.GONE);
                    productdescription_addButton.setVisibility(View.VISIBLE);
                    productdescription_quanText.setText(""+count);
                   int i=0;
                    int ii=0;
                    for(i=0;i<dbData.size();i++){
                        if(dbData.get(i).getProd_id()==Integer.parseInt(subcategories_model.getSubCategory_id())){
                            ii=1;
                            break;
                        }
                    }
                    if(ii==1)
                    {
                        dataHelper = new DataHelper(getContext());
                        dbData=dataHelper.getDataFromDB();

                        dataHelper.deleteData(dbData.get(i).getProd_id());

                        dbData = dataHelper.getDataFromDB();
                        Log.i("############","############# dbData.size  in decrement button listener = "+dbData.size());
                    }

                   /* Log.i("~~~~~~~~~@@@@@","~~~~~~ count == 1 ~~~~~~~~~~"+count);
                    str3 = productdescription_quanText.getText().toString();
                    Log.i("1111111111","1111111111111111111111112222222222222222"+str3);*/

                  //loadproduct();
                }
                else{
                    count--;
                    productdescription_quanText.setText(""+count);
                    Log.i("~~~~~~~~~######","~~~~~~else count != 1 ~~~~~~~~~~"+count);
                    str3 = productdescription_quanText.getText().toString();
                    Log.i("1111111111","1111111111111111111111112222222222222222"+str3);
                    loadproduct();
                }
            }
        });

        addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"Product added to cart",Toast.LENGTH_SHORT).show();
                dataHelper = new DataHelper(getContext());
                dbData=dataHelper.getDataFromDB();
                Log.i("GGGGGGGGGGGGG","GGGGGGGGGGGGGGGGGGGG"+dbData.size());
                if(dbData.size()==0){
                    Log.i("MMMMMMMMMMM","MMMMMMMMMMMMM11111111111111"+count);
                    Log.i("MMMMMMMMMMM","MMMMMMMMMMMMM11111111111111 subcategories_model.getSubCategory_quantity()"+subcategories_model.getSubCategory_quantity());
                    if(Integer.parseInt(subcategories_model.getSubCategory_quantity())==0)
                    {
                        Log.i("addtocart listener","1 if condition addtocart setOnClickListener subcategories_model.getsubcategory_quantity()="+subcategories_model.getSubCategory_quantity());
                        count=1;
                        subcategories_model.setSubCategory_quantity(""+count);

                        Log.i("addtocart listener"," 2 if condition addtocart setOnClickListener subcategories_model.getsubcategory_quantity()="+subcategories_model.getSubCategory_quantity());


                    }
                    dataHelper.insertprod_details(Integer.parseInt(subcategories_model.getSubCategory_id()),subcategories_model.getSubCategory_name(),subcategories_model.getSubCategory_price(),subcategories_model.getSubCategory_quantity(),subcategories_model.getSubCategory_image());
                    dbData.clear();
                    dbData=dataHelper.getDataFromDB();
                }
                else {
                    int i,ii=0;
                    for (i = 0; i < dbData.size(); i++) {
                        if (Integer.parseInt(subcategories_model.getSubCategory_id()) == dbData.get(i).getProd_id()) {
                            ii=1;
                            break;
                        }
                    }
                    if (ii==1) {
                        Log.i("NNNNNNNNNNNN","NNNNNNNNNNNNNNNNNNNN" + Integer.parseInt(subcategories_model.getSubCategory_id()));
                        Log.i("AAAAAAAAAAAA","CCCCCCCCCCCCCCCCCCCC");
                        dataHelper.updateDetails(Integer.parseInt(subcategories_model.getSubCategory_id()), subcategories_model.getSubCategory_quantity());
                        ii=0;
                        dbData.clear();
                        dbData=dataHelper.getDataFromDB();
                    } else {
                        Log.i("OOOOOOOOOOOO", "OOOOOOOOOOOOOOOOOOOO");
                        Log.i("MMMMMMMMMMMM", "MMMMMMMMMMMMMMMMMMMM" + subcategories_model.getSubCategory_quantity());
                        if(Integer.parseInt(subcategories_model.getSubCategory_quantity())==0)
                        {
                            Log.i("addtocart listener","1 if condition addtocart setOnClickListener subcategories_model.getsubcategory_quantity()="+subcategories_model.getSubCategory_quantity());
                            count=1;
                            subcategories_model.setSubCategory_quantity(""+count);

                            Log.i("addtocart listener"," 2 if condition addtocart setOnClickListener subcategories_model.getsubcategory_quantity()="+subcategories_model.getSubCategory_quantity());

                        }
                        dataHelper.insertprod_details(Integer.parseInt(subcategories_model.getSubCategory_id()), subcategories_model.getSubCategory_name(), subcategories_model.getSubCategory_price(), subcategories_model.getSubCategory_quantity(), subcategories_model.getSubCategory_image());
                        ii=0;
                        dbData.clear();
                        dbData=dataHelper.getDataFromDB();
                    }

                    Log.i("PPPPPP", "PPPPPPPPPPPPPPPPPPPPP");
                }
                array_cartlist.clear();
                CartOrderModel cartOrderModel;

                    cartOrderModel = new CartOrderModel();
                    for(int i=0;i<dbData.size();i++) {
                        cartOrderModel.setProductId(dbData.get(i).getProd_id().toString());
                        cartOrderModel.setProductName(dbData.get(i).getProd_name());
                        cartOrderModel.setProductCostPerUnit(dbData.get(i).getProd_price());
                        cartOrderModel.setProductImage(dbData.get(i).getProd_image());
                            cartOrderModel.setProductQuantity(""+count);
                    }

                        array_cartlist.add(cartOrderModel);

                for(int i=0;i<array_cartlist.size();i++)
                {
                    Log.i("add_to_cart","^^^^^^^^^^^^^^^^^^^^ size of cart="+array_cartlist.size()+" Name = "+"="+array_cartlist.get(i).getProductName());
                }

                MainActivity.cart_textView.setText(""+dbData.size());
            }
        });

        checkout_hide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction ft = fragmentManager.beginTransaction();
                ft.replace(R.id.frame,new CartListFragment()).addToBackStack("Productdescription");
                ft.commit();
            }
        });
        return v;
    }

    private int getcount() {
        int c;
        int i=0;
        int ii=0;
        for(i=0;i<dbData.size();i++) {
            if (Integer.parseInt(subcategories_model.getSubCategory_id()) == dbData.get(i).getProd_id()) {
                ii=1;
                break;
            }
        }
        if(ii==1){
            c = Integer.parseInt(dbData.get(i).getProd_quantity());
            Log.i("~~~~~~~~~@@@@@","~~~~~~~~if ~~~~~~~~"+c);
        }
        else{
            c=0;
            Log.i("~~~~~~~~~@@@@@","~~~~~~~else ~~~~~~~~~"+c);
        }
        return c;
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(getContext(), "hello there", Toast.LENGTH_SHORT).show();
    }

    public void loadproduct(){
        dataHelper = new DataHelper(getContext());
        dbData=dataHelper.getDataFromDB();
        if(pagenumber==1) {
            subcategories_model.setSubCategory_id(Sub_list_category.array_subcategory_list.get(product_index).getSubCategory_id());
            subcategories_model.setSubCategory_name(Sub_list_category.array_subcategory_list.get(product_index).getSubCategory_name());
            subcategories_model.setSubCategory_price(Sub_list_category.array_subcategory_list.get(product_index).getSubCategory_price());
            subcategories_model.setSubCategory_description(Sub_list_category.array_subcategory_list.get(product_index).getSubCategory_description());
            subcategories_model.setSubCategory_image(Sub_list_category.array_subcategory_list.get(product_index).getSubCategory_image());
        }
        else{
            if(pagenumber==2){
                subcategories_model.setSubCategory_id(Search.text_sort.get(product_index).getProduct_id());
                subcategories_model.setSubCategory_name(Search.text_sort.get(product_index).getProduct_name());
                subcategories_model.setSubCategory_price(Search.text_sort.get(product_index).getPrice());
                subcategories_model.setSubCategory_description(Search.text_sort.get(product_index).getProduct_description());
                subcategories_model.setSubCategory_image(Search.text_sort.get(product_index).getProduct_image());
            }
            else{
                if (pagenumber==3){
                    FavoriteList.clear();
                    dataHelper_favoriteList=new DataHelper_FavoriteList(getContext());
                    FavoriteList=dataHelper_favoriteList.getDataFromDB();
                    subcategories_model.setSubCategory_id(FavoriteList.get(product_index).getProd_id().toString());
                    subcategories_model.setSubCategory_name(FavoriteList.get(product_index).getProd_name());
                    subcategories_model.setSubCategory_price(FavoriteList.get(product_index).getProd_price());
                    subcategories_model.setSubCategory_description(FavoriteList.get(product_index).getProd_desc());
                    subcategories_model.setSubCategory_image(FavoriteList.get(product_index).getProd_image());

                }
            }

        }

        if(add_layout.VISIBLE==View.GONE)
        {
            subcategories_model.setSubCategory_quantity(""+1);
        }
        else
        {
            Log.i("count","count="+count);
            subcategories_model.setSubCategory_quantity(""+count);
        }
        Log.i("quantity","quantity="+productdescription_quanText.getText().toString());
        int i=0;
        int ii=0;
        for(i=0;i<dbData.size();i++) {
            if (Integer.parseInt(subcategories_model.getSubCategory_id()) == dbData.get(i).getProd_id()) {
                ii=1;
                break;
            }
        }
        if(ii==1){
            productdescription_prodname.setText(subcategories_model.getSubCategory_name());
            productdescription_descriptiontext.setText(subcategories_model.getSubCategory_description());
            productdescription_productimage.setImageUrl(GlobalClass.URL+subcategories_model.getSubCategory_image(), VolleyHelper.getVolleyHelper(getActivity()).getImageLoader());
            productdescription_costtext.setText(subcategories_model.getSubCategory_price());
            if(Integer.parseInt(dbData.get(i).getProd_quantity())==count)
            {
                Log.i("!!!!!!!!!!!","!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! dbData.get(i).getProd_quantity()="+dbData.get(i).getProd_quantity());
                productdescription_quanText.setText(dbData.get(i).getProd_quantity());
            }
            else
            {
                Log.i("!!!!!!!!!!!","!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! in loadProducts count="+count);
                if(count==0){
                    Log.i("!!!!!!!!!!!","!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! in loadProducts {if(count==0) then count=1} count="+count);
                    count=1;
                }
                productdescription_quanText.setText(""+count);
            }
            productdescription_addButton.setVisibility(View.GONE);
            add_layout.setVisibility(View.VISIBLE);
        }
        else{
            productdescription_prodname.setText(subcategories_model.getSubCategory_name());
            productdescription_descriptiontext.setText(subcategories_model.getSubCategory_description());
            productdescription_productimage.setImageUrl(GlobalClass.URL+subcategories_model.getSubCategory_image(), VolleyHelper.getVolleyHelper(getActivity()).getImageLoader());
            productdescription_costtext.setText(subcategories_model.getSubCategory_price());
        }
        isFavorite(Integer.parseInt(subcategories_model.getSubCategory_id()));
    }

    void isFavorite(int product_id) {
        FavoriteList.clear();
        dataHelper_favoriteList=new DataHelper_FavoriteList(getContext());
        FavoriteList=dataHelper_favoriteList.getDataFromDB();
        int ii = 0;
        int i;
        for (i = 0; i < FavoriteList.size(); i++) {
            if (product_id==FavoriteList.get(i).getProd_id()) {
                ii = 1;
                break;
            }
        }
        if(ii==1){
            fav_img.setImageResource(R.drawable.wish_list_red);
            isFavorite=false;
        }
        else
        {
            fav_img.setImageResource(R.drawable.wish_list_blank);
            isFavorite=true;
        }
    }
}

