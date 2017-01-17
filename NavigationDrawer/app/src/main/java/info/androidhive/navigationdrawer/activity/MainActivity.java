package info.androidhive.navigationdrawer.activity;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import info.androidhive.navigationdrawer.Model.Categories_Model;
import info.androidhive.navigationdrawer.Model.ProductModel;
import info.androidhive.navigationdrawer.R;
import info.androidhive.navigationdrawer.fragment.CartListFragment;
import info.androidhive.navigationdrawer.fragment.Category;
import info.androidhive.navigationdrawer.fragment.HelpCenter;
import info.androidhive.navigationdrawer.fragment.HomeFragment;
import info.androidhive.navigationdrawer.fragment.LoginPage;
import info.androidhive.navigationdrawer.fragment.MyAccount;
import info.androidhive.navigationdrawer.fragment.MyOrders;
import info.androidhive.navigationdrawer.fragment.MyWallet;
import info.androidhive.navigationdrawer.fragment.MyWishList;
import info.androidhive.navigationdrawer.fragment.Search;
import info.androidhive.navigationdrawer.fragment.ShowAddress;
import info.androidhive.navigationdrawer.fragment.Sub_list_category;
import info.androidhive.navigationdrawer.other.DataHelper;
import info.androidhive.navigationdrawer.other.GlobalClass;
import info.androidhive.navigationdrawer.other.ParseJSON;
import info.androidhive.navigationdrawer.other.RecyclerTouchListener;
import info.androidhive.navigationdrawer.other.VolleyHelper;

public class MainActivity extends AppCompatActivity {

    private NavigationView navigationView;
    private DrawerLayout drawer;
    private View navHeader;
    private ImageView imgNavHeaderBg, imgSearch;
    public static ImageView imgBack;
    private TextView txtName, txtWebsite;
    public static int login_signup_number;
    public static Toolbar toolbar;
    public static LinearLayout mtoolbar_layout;
    AppBarLayout appBarLayout;
    int REQUEST_PHONE_STATE=0;
    String name = null;
    private static final int PERMISSIONS_REQUEST_READ_PHONE_STATE = 999;
    private TelephonyManager mTelephonyManager;
    boolean doublebackpressed = false;
    public static TextView textView,cart_textView;
    public static TextView username_text;
    String navTitles[];
    String navIcons[];
    // urls to load navigation header background image
    // and profile image
    private static final String urlNavHeaderBg = "http://api.androidhive.info/images/nav-menu-header-bg.jpg";
    private static final String urlProfileImg = "https://lh3.googleusercontent.com/eCtE_G34M9ygdkmOpYvCag1vBARCmZwnVS6rS5t4JLzJ6QgQSBquM0nuTsCpLhYbKljoyS-txg";
    final String Category_url = GlobalClass.URL + "category_list.php";
    public static int pageIndex = 0;
    // index to identify current nav menu item
    public static int navItemIndex = 0;
    VolleyHelper volleyHelper;
    SharedPreferences sharedPreferences, sharedpref;
    RecyclerView.Adapter recyclerViewAdapter;
    RecyclerView recyclerView;
    int position_of_navigationDrawer;
    //
    String TITLES[] = {""};
    String ICONS[] = {""};

    //Similarly we Create a String Resource for the name and email in the header view
    //And we also create a int resource for profile picture in the header view

    String NAME = "Ecommerce";
    String EMAIL = "www.ecommerce.com";
    int PROFILE = R.drawable.ic_profile;

    //private Toolbar toolbar;                              // Declaring the Toolbar Object

    public static RecyclerView mRecyclerView;                           // Declaring RecyclerView
    RecyclerView.Adapter mAdapter;                        // Declaring Adapter For Recycler View
    RecyclerView.LayoutManager mLayoutManager;            // Declaring Layout Manager as a linear layout manager
    public static DrawerLayout Drawer;                                  // Declaring DrawerLayout

    public static ActionBarDrawerToggle mDrawerToggle;

    // tags used to attach the fragments
    private static final String TAG_HOME = "Home";
    private static final String TAG_ELECTRONICS = "Electronics";
    private static final String TAG_CLOTHES = "Clothes";
    private static final String TAG_HOMEANDLIVING = "Home and Living";
    private static final String TAG_BOOKS = "Books";
    private static final String TAG_MOVIESNMUSIC = "Movies And Music";
    private static final String TAG_SHOES = "Shoes";
    private static final String TAG_BEAUTYANDPERSONALCARE = "Beauty Products";
    private static final String TAG_LOGIN = "Login";
    private static final String TAG_MYACCOUNT = "My Account";
    private static final String TAG_MYORDER = "My Order";
    private static final String TAG_MYWALLET = "My Wallet";
    private static final String TAG_HELPCENTER = "Help Center";
    public static String CURRENT_TAG = TAG_HOME;
    public static String IMEI_NO;
    Categories_Model categories_model;
    JSONObject req;
    RelativeLayout cartLayout;
    public static ArrayList<Categories_Model> category_lists;
    // toolbar titles respected to selected nav menu item
    private String[] activityTitles = {TAG_HOME, TAG_ELECTRONICS, TAG_CLOTHES, TAG_HOMEANDLIVING, TAG_BOOKS, TAG_MOVIESNMUSIC, TAG_SHOES, TAG_BEAUTYANDPERSONALCARE, TAG_MYACCOUNT, TAG_MYWALLET, TAG_MYORDER, TAG_HELPCENTER, TAG_LOGIN};

    // flag to load home fragment when user presses back key
    private boolean shouldLoadHomeFragOnBackPress = true;
    private Handler mHandler;
    DataHelper dataHelper;
    List<ProductModel> db_prod_list;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mtoolbar_layout = (LinearLayout) findViewById(R.id.container_toolbar);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (ActivityCompat.checkSelfPermission(MainActivity.this,Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.READ_PHONE_STATE},
                    PERMISSIONS_REQUEST_READ_PHONE_STATE);
        } else {
            getDeviceImei();
        }

        Drawer = (DrawerLayout) findViewById(R.id.drawer_layout);        // Drawer object Assigned to the view
        mDrawerToggle = new ActionBarDrawerToggle(this, Drawer, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerOpened(View drawerView) {

                super.onDrawerOpened(drawerView);
               }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                // Code here will execute once drawer is closed
            }


        }; // Drawer Toggle Object Made
        Drawer.setDrawerListener(mDrawerToggle); // Drawer Listener set to the Drawer toggle
        mDrawerToggle.syncState();// Finally we set the drawer toggle sync State

        categories_model = new Categories_Model();

        cartLayout = (RelativeLayout) findViewById(R.id.cart_icon);
        mHandler = new Handler();
        //drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        recyclerView = (RecyclerView) findViewById(R.id.nav_recyclerView);
        textView = (TextView) findViewById(R.id.toolbar_textview);
        imgSearch = (ImageView) findViewById(R.id.toolbar2_search);
        imgBack = (ImageView) findViewById(R.id.toolbar_back_image);
        cart_textView = (TextView) findViewById(R.id.cart_text);
        volleyHelper = VolleyHelper.getVolleyHelper(MainActivity.this);
        category_lists = new ArrayList<>();

        sharedPreferences = this.getSharedPreferences("credentials_new", Context.MODE_PRIVATE);
        sharedpref = this.getSharedPreferences("credentials", Context.MODE_PRIVATE);

        dataHelper = new DataHelper(getApplicationContext());
        db_prod_list = new ArrayList<>();
        db_prod_list  = dataHelper.getDataFromDB();

        Log.i("&&&&&&&&&&&","&&&&&&&&&&&&&&&&&&&&&&"+db_prod_list+ "   "+db_prod_list.size());
        cart_textView.setText(""+db_prod_list.size());
        textView.setText("Home");

        Log.i("---------", "---------------" + sharedPreferences.getString("Firstname", null));

        if (GlobalClass.isNetworkAvailable(MainActivity.this)) {
            final StringRequest stringRequest_category = new StringRequest(Request.Method.POST, Category_url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("response", "" + response);
                    try {
                        MainActivity.category_lists.clear();
                        JSONObject json = new JSONObject(response);
                        getCategoryList(json);
                        Log.i("$$$$$$response", "" + response);
                        mRecyclerView = (RecyclerView) findViewById(R.id.nav_recyclerView); // Assigning the RecyclerView Object to the xml View

                        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), mRecyclerView, new RecyclerTouchListener.ClickListener() {
                            @Override

                            public void onClick(View view, int position) {
                                Drawer.closeDrawers();
                                FragmentManager fm = getSupportFragmentManager();
                                navigatoroptions(position,fm);
                            }

                            @Override
                            public void onLongClick(View view, int position) {

                            }
                        }));
                        //mRecyclerView.setHasFixedSize(true);                            // Letting the system know that the list objects are of fixed size

                        mAdapter = new MyAdapter(TITLES, ICONS, NAME, EMAIL, PROFILE,MainActivity.this);       // Creating the Adapter of MyAdapter class(which we are going to see in a bit)
                        // And passing the titles,icons,header view name, header view email,
                        // and header view profile picture

                        mRecyclerView.setAdapter(mAdapter);
                        final GestureDetector mGestureDetector = new GestureDetector(MainActivity.this, new GestureDetector.SimpleOnGestureListener() {

                            @Override public boolean onSingleTapUp(MotionEvent e) {
                                return true;
                            }

                        });


// Setting the adapter to RecyclerView

                        mLayoutManager = new LinearLayoutManager(MainActivity.this);                 // Creating a layout Manager

                        mRecyclerView.setLayoutManager(mLayoutManager);                 // Setting the layout Manager


                        //loadNavHeader();
                        // initializing navigation menu
                        ///setUpNavigationView();
                        if (savedInstanceState == null) {
                           /* navItemIndex = 0;
                            CURRENT_TAG = TAG_HOME;
                            textView.setText(CURRENT_TAG);
*/
                            HomeFragment homeFragment = new HomeFragment();
                            FragmentManager fm = getSupportFragmentManager();
                            FragmentTransaction ft = fm.beginTransaction();
                            ft.replace(R.id.frame,homeFragment).addToBackStack("HomeFragment");
                            ft.commit();
                            //loadHomeFragment();


                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    for (int i = 0; i < MainActivity.category_lists.size(); i++) {
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });

            Log.i("String Request", "" + stringRequest_category);

            RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
            requestQueue.add(stringRequest_category);
            Log.i("Strin Request2", "" + stringRequest_category);
        } else {
            Toast.makeText(MainActivity.this, "Check Internet", Toast.LENGTH_SHORT).show();
        }

        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toolbar.setVisibility(View.GONE);
                Fragment fragment = new Search();
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.frame, fragment).addToBackStack("HomeFragment");
                ft.commit();
            }
        });

        cartLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment frag = new CartListFragment();
                toolbar.setVisibility(View.GONE);
                FragmentManager fragman = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragman.beginTransaction();
                fragmentTransaction.replace(R.id.frame, frag, null).addToBackStack("HomeFragment");
                fragmentTransaction.commit();
            }
        });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_READ_PHONE_STATE
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            getDeviceImei();
        }
    }

    private void getDeviceImei() {

        mTelephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String deviceid = mTelephonyManager.getDeviceId();
        IMEI_NO = deviceid;
        Log.d("msg", "DeviceImei " + deviceid);
    }

    private void navigatoroptions(int position, FragmentManager fm) {
        if (position == 0) {
            HomeFragment homeFragment = new HomeFragment();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.frame,homeFragment);
            ft.commit();
        } else {
            if (position <= MainActivity.category_lists.size()) {
                Categories_Model items = MainActivity.category_lists.get(position - 1);
                // MainActivity.textView.setText(items.getCategory_name());
                Sub_list_category sub_list_category = new Sub_list_category();
                Sub_list_category.Category_Index = items.getCategory_id();
                MainActivity.textView.setText(items.getCategory_name());
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.frame, sub_list_category);
                ft.commit();
            } else
            {
                int size = MainActivity.category_lists.size();
                if (position == size + 1) {
                    if(sharedPreferences.getString("Firstname", null)==null && sharedpref.getString("Firstname",null)==null){
                        if(sharedPreferences.getString("Firstname", null)==null ){
                            LoginPage loginPage = new LoginPage();
                            MainActivity.textView.setText("Login");
                            FragmentTransaction ft = fm.beginTransaction();
                            ft.replace(R.id.frame,loginPage).addToBackStack("HomeFragment");;
                            ft.commit();
                        }
                    }
                    else{
                        MyAccount myAccount = new MyAccount();
                        MainActivity.textView.setText("My Account");
                        FragmentTransaction ft = fm.beginTransaction();
                        ft.replace(R.id.frame, myAccount).addToBackStack("HomeFragment");
                        ft.commit();
                    }
                } else {

                    if (position == size + 2) {
                        if(sharedPreferences.getString("Firstname", null)==null && sharedpref.getString("Firstname",null)==null){
                            if(sharedPreferences.getString("Firstname", null)==null ){
                                LoginPage loginPage = new LoginPage();
                                MainActivity.textView.setText("Login");
                                FragmentTransaction ft = fm.beginTransaction();
                                ft.replace(R.id.frame,loginPage).addToBackStack("HomeFragment");;
                                ft.commit();
                            }
                        }
                        else{
                            MyOrders myOrders = new MyOrders();
                            MainActivity.textView.setText("My Orders");
                            FragmentTransaction ft = fm.beginTransaction();
                            ft.replace(R.id.frame, myOrders).addToBackStack("HomeFragment");
                            ft.commit();
                        }
                    } else {
                        if (position == size + 3) {
                            MyWishList helpCenter = new MyWishList();
                            MainActivity.textView.setText("My Wishlist");
                            FragmentTransaction ft = fm.beginTransaction();
                            ft.replace(R.id.frame,helpCenter).addToBackStack("MyWishList");;
                            ft.commit();
                        }
                        else {
                            if(position == size + 4){
                                HelpCenter helpCenter = new HelpCenter();
                                MainActivity.textView.setText("Help Center");
                                FragmentTransaction ft = fm.beginTransaction();
                                ft.replace(R.id.frame,helpCenter).addToBackStack("HomeFragment");;
                                ft.commit();
                            }
                        }
                    }
                }
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
        // getMenuInflater().inflate(R.menu.main, menu);
        // return true;
    }

    private void loadNavHeader() {
        // name, website
        txtName.setText("Ecommerce App");
        txtWebsite.setText("www.ecommerce.com");
        if(sharedPreferences.getString("Firstname",null)==null){

            username_text.setText("Guest user");
            Log.i("---------","$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$"+"Guest");
        }
        else{
            username_text.setText(sharedPreferences.getString("Firstname",null));
            //username_txt.setText(sharedpref.getString("Firstname",null));
            Log.i("---------","#########################################################"+username_text.getText());
        }

        // loading header background image
        Glide.with(this).load(urlNavHeaderBg)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgNavHeaderBg);
    }


    private void setToolbarTitle() {

        textView.setText(activityTitles[navItemIndex]);
    }


    public void getCategoryList(JSONObject json)
    {
        MainActivity.category_lists.clear();
        JSONArray result=null;
        JSONObject jsonObject = json;
                try {
                    result = jsonObject.getJSONArray("category_list");
                    String[] title=new String[result.length()];
                    String[] icons=new String[result.length()];
                    TITLES  = new String[result.length()+4];

                    ICONS = new String[result.length()+4];
                    for(int i=0;i<result.length();i++) {
                        JSONObject category_data = result.getJSONObject(i);
                        Categories_Model catoCategories_model123;
                        catoCategories_model123 =new Categories_Model();
                        catoCategories_model123.setCategory_id(category_data.getString("category_id"));
                        catoCategories_model123.setCategory_name(category_data.getString("name"));
                        catoCategories_model123.setCategory_image(category_data.getString("image"));
                        catoCategories_model123.setCategory_status(category_data.getString("status"));
                        MainActivity.category_lists.add(catoCategories_model123);
                        Log.i("category _list", "category list1234 " + MainActivity.category_lists.get(i).getCategory_name());

                        TITLES[i]= category_lists.get(i).getCategory_name();
                       ICONS[i] = category_lists.get(i).getCategory_image();
                    }
                    Log.i("*********","**************************"+TITLES.length);
                    int size = result.length();
                    Log.i("*********","**************************"+size);
                    TITLES[size] ="My Account";
                    TITLES[size+1]="My Order";
                    TITLES[size+2]="My Wishlist";
                    TITLES[size+3]="Help Center";

                  recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

                } catch (JSONException e) {
                    e.printStackTrace();
                }

    }


    @Override
    protected void onResume() {
        super.onResume();
        FragmentManager fm = getSupportFragmentManager();
        fm.popBackStack();
        Log.i("@@@@@@@@@@@@@","9999999999999999999999999999999999999999999999999999999999999999999");
        cart_textView.setText(""+db_prod_list.size());
        MainActivity.mDrawerToggle.setDrawerIndicatorEnabled(true);


        //loadNavHeader();
    }

    @Override
    public void onBackPressed() {

        FragmentManager fm = getSupportFragmentManager();


        if(MainActivity.pageIndex==2 || MainActivity.pageIndex==3 || MainActivity.pageIndex==4 || MainActivity.pageIndex==5){
            while(MainActivity.pageIndex==1){

                fm.popBackStack();
            }
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.frame,new HomeFragment(),"");
            ft.commit();
        }
        setToolbarTitle();
        Drawer.setDrawerListener(mDrawerToggle);
        MainActivity.mDrawerToggle.setDrawerIndicatorEnabled(true);

        SplashActivity.aBoolean=true;

                if (MainActivity.pageIndex == 1) {

                    finish();
                }
               /* else
                {
                    fm.popBackStack();
                }*/
        if (Drawer.isDrawerOpen(GravityCompat.START)) {
            Drawer.closeDrawers();
            return;
        }
        super.onBackPressed();
    }

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{

        private static final int TYPE_HEADER = 0;  // Declaring Variable to Understand which View is being worked on
        // IF the view under inflation and population is header or Item
        private static final int TYPE_ITEM = 1;

        private String mNavTitles[]; // String Array to store the passed titles Value from MainActivity.java
        private String mIcons[];       // Int Array to store the passed icons resource value from MainActivity.java

        private String name;        //String Resource for header View Name
        private int profile;        //int Resource for header view profile picture
        private String email;
        Context context;

        //String Resource for header view email

         class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

            int Holderid;
            TextView textView;
           ImageView imageView;
            ImageView profile;
            TextView Name;
            TextView email;
             Context contxt;

            public ViewHolder(View itemView,int ViewType,Context c) {
                super(itemView);
                contxt = c;
                itemView.setClickable(true);
                itemView.setOnClickListener(this);

                if (ViewType == TYPE_ITEM) {
                    textView = (TextView) itemView.findViewById(R.id.tv_NavTitle); // Creating TextView object with the id of textView from item_row.xml
                    imageView = (ImageView) itemView.findViewById(R.id.iv_NavIcon);// Creating ImageView object with the id of ImageView from item_row.xml
                    Holderid = 1;                                               // setting holder id as 1 as the object being populated are of type item row
                } else {


                    Name = (TextView) itemView.findViewById(R.id.name);         // Creating Text View object from header.xml for name
                    email = (TextView) itemView.findViewById(R.id.email);       // Creating Text View object from header.xml for email
                    profile = (ImageView) itemView.findViewById(R.id.circleView);// Creating Image view object from header.xml for profile pic
                    Holderid = 0;                                                // Setting holder id = 0 as the object being populated are of type header view
                }
            }


             @Override
             public void onClick(View v) {
              }
         }

        public MyAdapter(String Titles[],String Icons[],String Name,String Email, int Profile,Context passedContext){ // MyAdapter Constructor with titles and icons parameter
            // titles, icons, name, email, profile pic are passed from the main activity as we
            mNavTitles = Titles;                //have seen earlier
            mIcons = Icons;
            name = Name;
            email = Email;
            profile = Profile;
            this.context = passedContext;
            //here we assign those passed values to the values we declared here
            //in adapter
        }

        @Override
        public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == TYPE_ITEM) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.nav_row,parent,false); //Inflating the layout

                ViewHolder vhItem = new ViewHolder(v,viewType,context); //Creating ViewHolder and passing the object of type view

                return vhItem; // Returning the created object

                //inflate your layout and pass it to view holder

            } else if (viewType == TYPE_HEADER) {

                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.nav_header_main,parent,false); //Inflating the layout

                ViewHolder vhHeader = new ViewHolder(v,viewType,context); //Creating ViewHolder and passing the object of type view

                return vhHeader; //returning the object created


            }
            return null;
        }

        @Override
        public void onBindViewHolder(MyAdapter.ViewHolder holder, int position) {
            if(holder.Holderid ==1) {                              // as the list view is going to be called after the header view so we decrement the
                // position by 1 and pass it to the holder while setting the text and image
                holder.textView.setText(mNavTitles[position - 1]); // Setting the Text with the array of our Titles
                holder.imageView.setImageResource(R.drawable.bullet);
                //holder.imageView.setImageUrl(GlobalClass.URL+ category_lists.get(position-1).getCategory_image(), VolleyHelper.getVolleyHelper(getApplicationContext()).getImageLoader());// Settimg the image with array of our icons
            }
            else{

                holder.profile.setImageResource(profile);           // Similarly we set the resources for header view
                holder.Name.setText(name);
                holder.email.setText(email);
            }
        }

        @Override
        public int getItemCount() {

            return mNavTitles.length+1;
        }

        public int getItemViewType(int position) {
            if (isPositionHeader(position))
                return TYPE_HEADER;

            return TYPE_ITEM;
        }

        private boolean isPositionHeader(int position) {

            return position == 0;
        }
    }

}
