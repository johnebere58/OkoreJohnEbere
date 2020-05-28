package com.example.okorejohnebere;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.okorejohnebere.adapters.CarOwnerAdapter;
import com.example.okorejohnebere.adapters.FilterListAdapter;
import com.example.okorejohnebere.custom_views.CustomListView;
import com.example.okorejohnebere.models.CarOwnerModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CarOwnersActivity extends AppCompatActivity {

    Context context;
    @BindView(R.id.car_owners_listView) CustomListView car_owners_listView;
    CarOwnerAdapter carOwnerAdapter;
    ArrayList<CarOwnerModel> carOwnerModels = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.car_owners_page);
        ButterKnife.bind(this);
        context = this;

        Intent i = this.getIntent();
        ArrayList<CarOwnerModel> carList =i.getParcelableArrayListExtra("data");
        if (carList != null) {
            carOwnerModels.addAll(carList);
            if(carOwnerModels.isEmpty()){
                car_owners_listView.showEmpty(null);
            }
        }
        carOwnerAdapter = new CarOwnerAdapter(this,carOwnerModels);
        car_owners_listView.getRecyclerView().setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
        car_owners_listView.getRecyclerView().setAdapter(carOwnerAdapter);

    }

}
