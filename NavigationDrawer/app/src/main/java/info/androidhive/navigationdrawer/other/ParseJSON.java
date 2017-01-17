package info.androidhive.navigationdrawer.other;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Admin on 15-10-2016.
 */

public class ParseJSON {
    public static String[] ids;
    public static String[] names;
    public static String[] images;
    public static String[] status;

    public static final String JSON_Array = "category_list";
    public static final String KEY_ID = "category_id";
    public static final String KEY_NAME = "name";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_STATUS = "status";

    private JSONArray users = null;
    private String json;

    public ParseJSON(String json){
        this.json = json;
    }

    public void parseJSON(){
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(json);
            users = jsonObject.getJSONArray(JSON_Array);

            ids = new String[users.length()];
            names  = new String[users.length()];
            images  = new String[users.length()];
            status  = new String[users.length()];

            for(int i=0;i<users.length();i++){
                JSONObject jo = users.getJSONObject(i);
                ids[i] = jo.getString(KEY_ID);
                names[i] = jo.getString(KEY_NAME);
                images[i] = jo.getString(KEY_IMAGE);
                status[i] = jo.getString(KEY_STATUS);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
