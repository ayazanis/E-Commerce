package info.androidhive.navigationdrawer.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.view.menu.ExpandedMenuView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import info.androidhive.navigationdrawer.Model.SearchModelClass;
import info.androidhive.navigationdrawer.Model.Subcategories_Model;
import info.androidhive.navigationdrawer.R;
import info.androidhive.navigationdrawer.activity.MainActivity;
import info.androidhive.navigationdrawer.other.GlobalClass;
import info.androidhive.navigationdrawer.other.OnBackPressedListener;
import info.androidhive.navigationdrawer.other.RecyclerTouchListener;
import info.androidhive.navigationdrawer.other.RecyclerViewPositionHelper;

/**
 * A simple {@link Fragment} subclass.
 */
public class Search extends Fragment{

    ImageView back_button;
    Integer textlength;
    EditText search_edit;
    ListView lv;
    ImageView search;
    public static LinearLayout layout_toolbarsearch;
    Toolbar toolbarsearch;
    ArrayAdapter<String> search_arrayAdapter;
    String[] language;
    public ArrayList<SearchModelClass> arraylist_search;
    public ArrayList<SearchModelClass> subCategories1;
    public static ArrayList<SearchModelClass> text_sort;
    int width,height;
    TextWatcher textWatcher;



    public Search() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_search, container, false);
        Productdescription.pagenumber = 2;
        MainActivity.pageIndex = 2;
        language = new String[2000];
        back_button = (ImageView) v.findViewById(R.id.toolbar_search_back_image);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fm = getFragmentManager();
                fm.popBackStack();
            }
        });
        arraylist_search = new ArrayList<>();
        subCategories1 = new ArrayList<>();
        text_sort = new ArrayList<>();
        search_edit = (EditText) v.findViewById(R.id.search_edittext);

        lv = (ListView) v.findViewById(R.id.search_listview);
        search = (ImageView) v.findViewById(R.id.toolbarsearch_search);
        layout_toolbarsearch = (LinearLayout) v.findViewById(R.id.container_toolbarsearch);
        toolbarsearch = (Toolbar) v.findViewById(R.id.toolbar_search);
        loadItems();
       search_edit.addTextChangedListener(new TextWatcher() {
           @Override
           public void beforeTextChanged(CharSequence s, int start, int count, int after) {

           }

           @Override
           public void onTextChanged(CharSequence s, int start, int before, int count) {

           }

           @Override
           public void afterTextChanged(Editable s) {

               textlength = search_edit.getText().length();

               String text = search_edit.getText().toString();

// String name = br.readLine();
// Don't mistake String object with a Character object

               String nameCapitalized = "";

               String space_string = "";

               String string_capital_all = "";

               try {
                   if (textlength > 0) {

                       String s1 = text.substring(0, 1).toUpperCase();
                       nameCapitalized = s1 + text.substring(1);

                       space_string = " " + text;

                       string_capital_all = text.toUpperCase();

                       System.out.println(nameCapitalized);
                   } else {

                   }
               } catch (Exception e) {
                   e.printStackTrace();
               }

               Log.i("", "csapital  ::" + nameCapitalized + " space  "
                       + space_string);

// ** Clear this arraylist **//
               text_sort.clear();

// *** working the search functionality ****//

// product_lista.clear();

               boolean isRunning = false;
               SearchModelClass p_search;
               //GlobalClass.homeObj123.clear();

               if (textlength > 2 && !isRunning)

               {

                   for (int i = 0; i < arraylist_search.size(); i++) {
                       isRunning = true;
                       p_search = arraylist_search.get(i);

                       String str_pname = p_search.getProduct_name();

                       if (str_pname.toLowerCase()
                               .contains(text.toLowerCase())) {


                               if (textlength >= 1 && isRunning) {
                                   text_sort.add(arraylist_search.get(i));
                               }


                       }
                   }

               } else if (textlength >= 2) {

                   //GlobalClass.homeObj123.clear();

               }

               lv.setAdapter(new CustomAdapter(text_sort));

               lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                   @Override
                   public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                       Productdescription productdescription = new Productdescription();
                       Productdescription.product_index=position;
                       FragmentManager fm = getFragmentManager();
                       FragmentTransaction ft = fm.beginTransaction();
                       ft.replace(R.id.frame,productdescription).addToBackStack("");
                       ft.commit();
                   }
               });

           }
       });

        return v;
    }
    public void loadItems(){
        {
            //bottomLayout.setVisibility(View.GONE);
            if (GlobalClass.isNetworkAvailable(getContext())) {
                String url = GlobalClass.URL + "product_search.php";
                final StringRequest stringRequest_category = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                             JSONObject json = new JSONObject(response);
                             fetchData(json);
                    } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

                Log.i("String Request", "" + stringRequest_category);

                RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                requestQueue.add(stringRequest_category);
                Log.i("Strin Request2", "" + stringRequest_category);
            } else {
                Toast.makeText(getContext(), "Check Internet", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void fetchData(JSONObject jsonObject){
            //MainActivity.category_lists.clear();
            JSONArray result = null;
            JSONObject json = jsonObject;
            try {
                result = json.getJSONArray("product_list");
                for (int i = 0; i < result.length(); i++) {
                    JSONObject subcategory_data = result.getJSONObject(i);
                    SearchModelClass searchModelClass;
                    searchModelClass  = new SearchModelClass();
                    searchModelClass.setCategory_id(subcategory_data.getString("category_id"));
                    searchModelClass.setProduct_id(subcategory_data.getString("product_id"));
                    searchModelClass.setProduct_name(subcategory_data.getString("product_name"));
                    searchModelClass.setProduct_description(subcategory_data.getString("product_description"));
                    searchModelClass.setProduct_image(subcategory_data.getString("product_image"));
                    searchModelClass.setPrice(subcategory_data.getString("price"));
                    searchModelClass.setMrp_price(subcategory_data.getString("mrp_price"));
                    searchModelClass.setQuantity(subcategory_data.getString("quantity"));
                    searchModelClass.setStock_status_id(subcategory_data.getString("stock_status_id"));
                    searchModelClass.setDate_available(subcategory_data.getString("date_available"));
                    searchModelClass.setProduct_status(subcategory_data.getString("product_status"));
                    searchModelClass.setProduct_date_added(subcategory_data.getString("product_date_added"));
                    searchModelClass.setProduct_date_modified(subcategory_data.getString("product_date_modified"));
                    searchModelClass.setUnit(subcategory_data.getString("unit"));

                    arraylist_search.add(searchModelClass);
                    Log.i("arraylist_search", "arraylist_search" + arraylist_search.get(i).getProduct_name());
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


    class CustomAdapter extends BaseAdapter {

        CustomAdapter() {

        }

        CustomAdapter(ArrayList<SearchModelClass> text) {

            subCategories1 = new ArrayList<SearchModelClass>();

            for (int i = 0; i < text.size(); i++) {
                subCategories1.add(text.get(i));
            }

        }

        public int getCount() {
            return subCategories1.size();
        }

        public String getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(final int position, View convertView,
                            ViewGroup parent) {

            LayoutInflater inflater = getActivity().getLayoutInflater();
            View row;

            row = inflater.inflate(R.layout.search_row, parent, false);

            TextView textview = (TextView) row.findViewById(R.id.search_row_text);

            textview.setText(subCategories1.get(position).getProduct_name());

            Display display = getActivity().getWindowManager().getDefaultDisplay();
            width = display.getWidth();
            height = display.getHeight();

            if(width==1080){
                textview.setTextSize(12);
            }

            return (row);
        }
    }
}
