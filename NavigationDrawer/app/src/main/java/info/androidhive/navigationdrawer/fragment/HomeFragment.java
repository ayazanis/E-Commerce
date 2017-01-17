package info.androidhive.navigationdrawer.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import info.androidhive.navigationdrawer.Model.Categories_Model;
import info.androidhive.navigationdrawer.Model.ProductModel;
import info.androidhive.navigationdrawer.R;
import info.androidhive.navigationdrawer.activity.MainActivity;
import info.androidhive.navigationdrawer.other.CardFragmentPagerAdapter;
import info.androidhive.navigationdrawer.other.CustomPagerEnum;
import info.androidhive.navigationdrawer.other.DataHelper;
import info.androidhive.navigationdrawer.other.GlobalClass;
import info.androidhive.navigationdrawer.other.Items;
import info.androidhive.navigationdrawer.other.ItemsAdapter;
import info.androidhive.navigationdrawer.other.RecyclerTouchListener;
import info.androidhive.navigationdrawer.other.ShadowTransformer;
import info.androidhive.navigationdrawer.other.VolleyHelper;


public class HomeFragment extends Fragment{

    ImageButton iv1, iv3;
    ImageView iv2, iv4;
    private RecyclerView recyclerView;
    private ArrayList<String> horizontalList;
    private ItemsAdapter Adapter;
    int count;
    private static List<Items> ItemsList = new ArrayList<>();
    private static List<Categories_Model> CATEGORY_LIST = new ArrayList<>();
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    String TITLES[] = {""};
    String ICONS[] = {""};
    private  Integer imagesArray[]={R.drawable.pic1,R.drawable.pic2,R.drawable.pic3,R.drawable.pic4};
    String NAME = "Ecommerce";
    String EMAIL = "www.ecommerce.com";
    int PROFILE = R.drawable.ic_profile;
    final String Category_url = GlobalClass.URL + "category_list.php";
    RecyclerView mRecyclerView;                           // Declaring RecyclerView
    RecyclerView.Adapter mAdapter;                        // Declaring Adapter For Recycler View
    RecyclerView.LayoutManager mLayoutManager;
    private int currImage=0;
    private int index = 0;
    ImageSwitcher below_img;

    // Declaring Layout Manager as a linear layout manager

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    SharedPreferences sharedPreferences,sharedpref;
    private OnFragmentInteractionListener mListener;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.horizontal_recycler_view);
        sharedPreferences =getContext().getSharedPreferences("credentials_new", Context.MODE_PRIVATE);
        sharedpref  = getContext().getSharedPreferences("credentials", Context.MODE_PRIVATE);
        below_img = (ImageSwitcher) rootView.findViewById(R.id.below_img);

        MainActivity.pageIndex=1;

        below_img.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView imageView = new ImageView(getContext());
                return imageView;
            }
        });

        below_img.setInAnimation(AnimationUtils.loadAnimation(getContext(), android.R.anim.slide_in_left));
        below_img.setOutAnimation(AnimationUtils.loadAnimation(getContext(), android.R.anim.slide_out_right));

        below_img.postDelayed(new Runnable()
        {
            public void run() {
            below_img.setImageResource(imagesArray[index]);
            if(index==(imagesArray.length-1))
                index = 0;
            else
                index++;
            below_img.postDelayed(this, 3000);
        } }, 1000);


        //ViewPager viewPager = (ViewPager) rootView.findViewById(R.id.viewpager);
        //viewPager.setAdapter(new CustomPagerAdapter(getContext()));

       /* ViewPager viewPager = (ViewPager) rootView.findViewById(R.id.viewPager);

        CardFragmentPagerAdapter pagerAdapter = new CardFragmentPagerAdapter(getFragmentManager(), dpToPixels(2, getContext()));
        ShadowTransformer fragmentCardShadowTransformer = new ShadowTransformer(viewPager, pagerAdapter);
        fragmentCardShadowTransformer.enableScaling(true);

        viewPager.setAdapter(pagerAdapter);
        viewPager.setPageTransformer(false, fragmentCardShadowTransformer);
        viewPager.setOffscreenPageLimit(3);
*/



        DataHelper dataHelper = new DataHelper(getContext());
        ArrayList<ProductModel> dbData = new ArrayList();
        dbData=dataHelper.getDataFromDB();




        if(sharedPreferences.getString("Firstname",null)==null){

            //MainActivity.username_text.setText("Guest user");
            if(sharedpref.getString("Firstname",null)==null)
            {
                //MainActivity.username_text.setText("Guest user");
            }
            else
            {
               // MainActivity.username_text.setText(sharedpref.getString("Firstname",null));
            }
        }
        else
        {
            // MainActivity.username_text.setText(sharedPreferences.getString("Firstname",null));
        }

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(),recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Categories_Model items = MainActivity.category_lists.get(position);
                MainActivity.textView.setText(items.getCategory_name());
                MainActivity.mDrawerToggle.setDrawerIndicatorEnabled(false);
                Sub_list_category sub_list_category = new Sub_list_category();
                Sub_list_category.Category_Index = items.getCategory_id();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.frame,sub_list_category).addToBackStack("HomeFragment");
                ft.commit();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        CategoryAdapter categoryAdapter;
       categoryAdapter = new CategoryAdapter(MainActivity.category_lists);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setAdapter(categoryAdapter);
        categoryAdapter.notifyDataSetChanged();

       /* iv1 = (ImageButton) rootView.findViewById(R.id.previous_btn);
        iv2 = (ImageView) rootView.findViewById(R.id.center_image1);
        iv4 = (ImageView) rootView.findViewById(R.id.center_image2);
        iv3 = (ImageButton) rootView.findViewById(R.id.forward_btn);

        iv2.setVisibility(View.VISIBLE);
        iv4.setVisibility(View.INVISIBLE);
        iv2.setImageResource(imagesArray[count]);

        //iv2.setVisibility(View.INVISIBLE);
        iv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Toast.makeText(getActivity(), "Previous Image", Toast.LENGTH_LONG).show();
                    slide_previous(view);
                    slide_previous2(view);

                    if (iv2.getVisibility() == View.VISIBLE) {

                        if (count > 0 && count < imagesArray.length) {
                            count--;
                        } else {
                            count = imagesArray.length - 2;
                        }
                        iv4.setImageResource(imagesArray[count]);
                        iv2.setVisibility(View.INVISIBLE);
                        iv4.setVisibility(View.VISIBLE);
                    } else {
                        if (count > 0 && count < imagesArray.length) {
                            count = imagesArray.length - 2;
                        } else {
                            count--;
                        }
                        iv2.setImageResource(imagesArray[count]);
                        iv4.setVisibility(View.INVISIBLE);
                        iv2.setVisibility(View.VISIBLE);
                    }
                }
                catch (Exception e){
                    Log.i("",""+e);
                }
            }
        });


        iv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Toast.makeText(getActivity(), "Next Image", Toast.LENGTH_LONG).show();
                    slide_next(view);
                    slide_next2(view);

                    if (iv2.getVisibility() == View.VISIBLE) {
                        //slide_next(view);
                        if (count >= 0 && count < imagesArray.length - 1) {
                            count++;
                        } else {
                            count = 1;
                        }
                        iv4.setImageResource(imagesArray[count]);
                        iv2.setVisibility(View.INVISIBLE);
                        iv4.setVisibility(View.VISIBLE);
                    } else {
                        if (count > 0 && count < imagesArray.length) {
                            count++;
                        } else {
                            count = 1;
                        }
                        iv2.setImageResource(imagesArray[count]);
                        iv4.setVisibility(View.INVISIBLE);
                        iv2.setVisibility(View.VISIBLE);
                    }
                }
                catch(Exception e){
                    Log.i("",""+e);
                }
            }
        });
        // Inflate the layout for this fragment
*/

        return rootView;
    }






    public static float dpToPixels(int dp, Context context) {
        return dp * (context.getResources().getDisplayMetrics().density);
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
            TITLES  = new String[result.length()+5];

            ICONS = new String[result.length()+5];
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

                TITLES[i]= MainActivity.category_lists.get(i).getCategory_name();
                ICONS[i] = MainActivity.category_lists.get(i).getCategory_image();
            }
            Log.i("*********","**************************"+TITLES.length);
            int size = result.length();
            Log.i("*********","**************************"+size);
            TITLES[size] ="My Account";
            TITLES[size+1]="My Order";
            TITLES[size+2]="My Wishlist";
            TITLES[size+3]="Help Center";
            SharedPreferences sharedPreferences_signup = getContext().getSharedPreferences("credentials", Context.MODE_PRIVATE);
            String name_signup = sharedPreferences_signup.getString("Firstname",null);

            SharedPreferences sharedPreferences_login = getContext().getSharedPreferences("credentials_new",Context.MODE_PRIVATE);
            String name_login = sharedPreferences_login.getString("Firstname",null);
            if(name_signup!=null){
                TITLES[size+4]="Logout";
            }
            else{
                if(name_login!=null){
                    TITLES[size+4]="Logout";
                }
                else{
                    Toast.makeText(getContext(),"Login to continue",Toast.LENGTH_SHORT).show();
                    TITLES[size+4]="Sign up/Sign in";
                }
            }
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    public void slide_next(View view) {
        if(iv2.getVisibility()==View.VISIBLE)
        {
            Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_next);
            iv4.startAnimation(animation);
            Animation animation1 = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_next2);
            iv2.startAnimation(animation1);
        }
        if(iv4.getVisibility()==View.VISIBLE)
        {
            Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_next);
            iv2.startAnimation(animation);
            Animation animation1 = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_next2);
            iv4.startAnimation(animation1);
        }
    }


    public void slide_previous(View view) {
       /* Animation a = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_previous);
        iv2.startAnimation(a);*/
        if(iv2.getVisibility()==View.VISIBLE)
        {
            Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_previous);
            iv2.startAnimation(animation);
            Animation animation1 = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_previous2);
            iv4.startAnimation(animation1);
        }
        if(iv4.getVisibility()==View.VISIBLE)
        {
            Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_previous);
            iv4.startAnimation(animation);
            Animation animation1 = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_previous2);
            iv2.startAnimation(animation1);
        }
    }

    public void slide_next2(View view) {
       // Animation animation1 = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_next2);
        if(iv2.getVisibility()==View.VISIBLE)
        {
            Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_next);
            iv4.startAnimation(animation);
            Animation animation1 = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_next2);
            iv2.startAnimation(animation1);
        }
        if(iv4.getVisibility()==View.VISIBLE)
        {
            Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_next);
            iv2.startAnimation(animation);
            Animation animation1 = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_next2);
            iv4.startAnimation(animation1);
        }
    }

    public void slide_previous2(View view) {
      /*  Animation a = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_previous2);
        iv4.startAnimation(a);*/
        if(iv2.getVisibility()==View.VISIBLE)
        {
            Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_previous);
            iv2.startAnimation(animation);
            Animation animation1 = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_previous2);
            iv4.startAnimation(animation1);
        }
        if(iv4.getVisibility()==View.VISIBLE)
        {
            Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_previous);
            iv4.startAnimation(animation);
            Animation animation1 = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_previous2);
            iv2.startAnimation(animation1);
        }
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }



    @Override
    public void onResume() {
        super.onResume();

        if(MainActivity.login_signup_number==1 || MainActivity.login_signup_number==2)
        {

        }


        MainActivity.toolbar.setVisibility(View.VISIBLE);
        MainActivity.imgBack.setVisibility(View.GONE);
        MainActivity.Drawer.setDrawerListener(MainActivity.mDrawerToggle);
        MainActivity.mDrawerToggle.syncState();
    }

    public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {

        String src;
        Categories_Model categoriesModel;
        private List<Categories_Model> CategoryList;

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_row,parent,false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, int position) {
            categoriesModel = CategoryList.get(position);

            src = GlobalClass.URL+categoriesModel.getCategory_image();
            Log.e("src",src);
            holder.Items_Title.setText(categoriesModel.getCategory_name());
            holder.img.setImageUrl(GlobalClass.URL+CategoryList.get(position).getCategory_image(), VolleyHelper.getVolleyHelper(getActivity()).getImageLoader());
        }

        @Override
        public int getItemCount() {

            return CategoryList.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {

            public TextView Items_Title;
            public NetworkImageView img;
            public MyViewHolder(View view) {
                super(view);
                Items_Title = (TextView) itemView.findViewById(R.id.title);
                img = (NetworkImageView) itemView.findViewById(R.id.items_imageview);

            }
        }

        public CategoryAdapter(List<Categories_Model> local_categoryList)
        {
            this.CategoryList=local_categoryList;
        }
    }

    /*public class CustomPagerAdapter extends PagerAdapter {

        private Context mContext;

        public CustomPagerAdapter(Context context) {
            mContext = context;
        }

        @Override
        public Object instantiateItem(ViewGroup collection, int position) {
            CustomPagerEnum customPagerEnum = CustomPagerEnum.values()[position];
            LayoutInflater inflater = LayoutInflater.from(mContext);
            ViewGroup layout = (ViewGroup) inflater.inflate(customPagerEnum.getLayoutResId(), collection, false);
            collection.addView(layout);
            return layout;
        }

        @Override
        public void destroyItem(ViewGroup collection, int position, Object view) {
            collection.removeView((View) view);
        }

        @Override
        public int getCount() {
            return CustomPagerEnum.values().length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            CustomPagerEnum customPagerEnum = CustomPagerEnum.values()[position];
            return mContext.getString(customPagerEnum.getTitleResId());
        }

    }*/
}
