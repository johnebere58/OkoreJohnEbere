package com.example.okorejohnebere;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;

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
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    Context context;
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
                            readCsv();
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

    private void readCsv() throws Exception {
        // Read the raw csv file
        File file = new File(Environment.getExternalStorageDirectory()+"/Decagon/car_ownsers_data.csv");
        if(!file.exists()){
            noFileFound = true;
            filter_listView.hideLoading();
            return;
        }
        InputStream is =  new FileInputStream(file);
        // Reads text from character-input stream, buffering characters for efficient reading
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(is, Charset.forName("UTF-8")));

        // Initialization
        String line = "";

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
                Log.d("MyActivity","Line: " + line);
                // use comma as separator columns of CSV
                String[] data = line.split(",");
                // Read the data
                CarOwnerModel carOwnerModel = new CarOwnerModel();
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
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        filter_listView.hideLoading();
    }

    private void tryAddingToCarList(CarOwnerModel carOwnerModel){
        for(int i=0;i<filterList.size();i++){
            FilterModel filterModel = new FilterModel();

        }
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


}
