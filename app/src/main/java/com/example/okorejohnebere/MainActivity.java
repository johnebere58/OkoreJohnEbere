package com.example.okorejohnebere;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.okorejohnebere.adapters.FilterListAdapter;
import com.example.okorejohnebere.custom_views.CustomListView;
import com.example.okorejohnebere.models.CarOwnerModel;
import com.example.okorejohnebere.models.FilterModel;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    Context context;
    @BindView(R.id.no_file_tv) TextView no_file_tv;
    @BindView(R.id.filter_listView) CustomListView filter_listView;
    FilterListAdapter filterListAdapter;
    ArrayList<FilterModel> filterList = new ArrayList<>();
    boolean noFileFound = false;
    boolean errorInCsv = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filter_page);
        ButterKnife.bind(this);
        context = this;
        filterListAdapter = new FilterListAdapter(this,filterList);
        filter_listView.getRecyclerView().setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
        filter_listView.getRecyclerView().setAdapter(filterListAdapter);

        loadItems();

        requestForPermissions();
    }

    public void clickBack(View v){
        onBackPressed();
    }

    private void loadItems() {
        filter_listView.showLoading();
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://android-json-test-api.herokuapp.com/accounts";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            handleResponse(response);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try {
                            new FilterTask().execute();
//                            filterListAdapter.notifyDataSetChanged();
//                            filter_listView.hideLoading();
                        } catch (Exception e) {
                            e.printStackTrace();
                            errorInCsv = true;
                            filter_listView.hideLoading();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                filter_listView.hideLoadingAndShowError(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        loadItems();;
                    }
                });
            }
        });
        queue.add(stringRequest);
    }

    private void handleResponse(String response)throws Exception{
        final JSONArray myResponse = new JSONArray(response);
        for ( int i = 0; i < myResponse.length(); i++) {
            JSONObject entry = myResponse.getJSONObject(i);
            FilterModel filterModel = new FilterModel();
            filterModel.setFullName(entry.getString("fullName"));
            filterModel.setCreatedAt(entry.getString("createdAt"));
            filterModel.setGender(entry.getString("gender"));
            filterModel.setAvatar(entry.getString("avatar"));

            JSONArray colors = entry.getJSONArray("colors");
            JSONArray countries = entry.getJSONArray("countries");

            filterModel.setColors(colors);
            filterModel.setCountries(countries);
            filterList.add(filterModel);
        }
        filterListAdapter.notifyDataSetChanged();
    }

    private void requestForPermissions(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                        PackageManager.PERMISSION_GRANTED||
                ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) !=
                        PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.M) {
                ActivityCompat.requestPermissions((Activity) context,new String[]{
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                },34);
            }
        }
    }

    class FilterTask extends AsyncTask<Void, Integer,Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            File file = new File(Environment.getExternalStorageDirectory()+"/Decagon/car_ownsers_data.csv");
            if(!file.exists()){
                noFileFound = true;
                filter_listView.hideLoading();
                return null;
            }
            InputStream is = null;
            try {
                is = new FileInputStream(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            // Reads text from character-input stream, buffering characters for efficient reading
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(is, Charset.forName("UTF-8")));

            // Initialization
            String line = "";
            int lineCount = 0;
            // Initialization
            try {
                // Step over headers
                try {
                    reader.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // If buffer is not empty
                while ((line = reader.readLine()) != null) {
                    String[] data = line.split(",");
                    // Read the data
                    final CarOwnerModel carOwnerModel = new CarOwnerModel();
                    carOwnerModel.setFirst_name(data[1]);
                    carOwnerModel.setLast_name(data[2]);
                    carOwnerModel.setEmail(data[3]);
                    carOwnerModel.setCountry(data[4]);
                    carOwnerModel.setCar_model(data[5]);
                    carOwnerModel.setCar_model_year(data[6]);
                    carOwnerModel.setCar_color(data[7]);
                    carOwnerModel.setGender(data[8]);
                    carOwnerModel.setJob_title(data[9]);
                    carOwnerModel.setBio(data[10]);
                    tryAddingToCarList(carOwnerModel);
                    lineCount++;
                    double percent = ((double)lineCount/65500) *100;
                    publishProgress((int)percent);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(final Integer... values) {
            super.onProgressUpdate(values);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    int progress = values[0];
                    progress = Math.min(progress, 100);
                    filter_listView.setLoadingText(String.format("Loading %s%s",progress,"%"));
                }
            });
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            filterListAdapter.notifyDataSetChanged();
            filter_listView.hideLoading();
            if(noFileFound)no_file_tv.setVisibility(View.VISIBLE);
        }

        private void tryAddingToCarList(final CarOwnerModel carOwnerModel){
            for(int i=0;i<filterList.size();i++){
                FilterModel filterModel = filterList.get(i);
                JSONArray countries = filterModel.getCountries();
                JSONArray colors = filterModel.getColors();
                String fullName = filterModel.getFullName();
                String gender = filterModel.getGender();

                if(fullName!=null) {
                    String[] nameParts = fullName.split(" ");
                    String firstName = carOwnerModel.getFirst_name().toLowerCase();
                    String lastName = carOwnerModel.getLast_name().toLowerCase();
                    boolean nameExist = false;
                    for(String name : nameParts){
                        if(name.trim().toLowerCase().equalsIgnoreCase(firstName)
                           || name.trim().toLowerCase().equalsIgnoreCase(lastName)){
                            nameExist=true;
                            break;
                        }
                    }
                    if(!nameExist)continue;
                }

                if(gender!=null) {
                    if (!gender.toLowerCase().
                            equalsIgnoreCase(carOwnerModel.getGender().toLowerCase())) continue;
                }

                if(countries!=null && countries.length()!=0) {
                    boolean countryExist = false;
                    for (int x = 0; x < countries.length(); x++) {
                        try {
                            String country = countries.getString(x);
                            if (country.toLowerCase().equalsIgnoreCase(carOwnerModel.getCountry())) {
                                countryExist = true;
                                break;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    if (!countryExist) {
                        continue;
                    }
                }

                if(colors!=null && colors.length()!=0) {
                    boolean colorExist = false;
                    for (int x = 0; x < colors.length(); x++) {
                        try {
                            String color = colors.getString(x);
                            if (color.toLowerCase().equalsIgnoreCase(carOwnerModel.getCar_color())) {
                                colorExist = true;
                                break;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    if (!colorExist) {
                        continue;
                    }
                }

                ArrayList<CarOwnerModel> carList = filterModel.getCarList();
                carList = carList==null?new ArrayList<CarOwnerModel>():carList;
                carList.add(carOwnerModel);
                filterModel.setCarList(carList);
                return;
            }
        }
    }

}
