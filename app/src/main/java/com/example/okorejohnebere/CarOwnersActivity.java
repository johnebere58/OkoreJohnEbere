package com.example.okorejohnebere;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.okorejohnebere.adapters.CarOwnerAdapter;
import com.example.okorejohnebere.adapters.FilterListAdapter;
import com.example.okorejohnebere.models.CarOwnerModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CarOwnersActivity extends AppCompatActivity {

    Context context;
    @BindView(R.id.car_owners_listView) RecyclerView car_owners_listView;
    CarOwnerAdapter carOwnerAdapter;
    ArrayList<CarOwnerModel> carOwnerModels = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.car_owners_page);
        ButterKnife.bind(this);
        context = this;

        carOwnerModels.add(new CarOwnerModel());
        carOwnerModels.add(new CarOwnerModel());
        carOwnerModels.add(new CarOwnerModel());
        carOwnerModels.add(new CarOwnerModel());
        carOwnerModels.add(new CarOwnerModel());
        carOwnerAdapter = new CarOwnerAdapter(this,carOwnerModels);
        car_owners_listView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
        car_owners_listView.setAdapter(carOwnerAdapter);

    }

}
