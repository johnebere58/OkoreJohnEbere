package com.example.okorejohnebere.adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.okorejohnebere.R;
import com.example.okorejohnebere.custom_views.CircleImageView;
import com.example.okorejohnebere.custom_views.ReadMoreTextView;
import com.example.okorejohnebere.models.CarOwnerModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by John Ebere on 5/13/2016.
 */
public class CarOwnerAdapter extends BaseAdapter<CarOwnerAdapter.ViewHolder> {

    ArrayList<CarOwnerModel> carOwnerModels;

    public CarOwnerAdapter(Activity activity, ArrayList<CarOwnerModel> carOwnerModels) {
        super(activity);
        this.carOwnerModels = carOwnerModels;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int type) {
        return new ViewHolder(mInflater.inflate(R.layout.car_owners_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final CarOwnerModel filterModel = carOwnerModels.get(position);

        holder.bio_tv.setReadMoreText("Hello i am a very big giant with big " +
                "fangs Hello i am a very big giant " +
                "with big fangs Hello i am a very big giant with big fangs" +
                " Hello i am a very big giant with big fangs Hello i am a very " +
                "big giant with big fangs Hello i am a very big giant with big fangs ",true);


    }


    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        @Nullable @BindView(R.id.name_tv) TextView name_tv;
        @Nullable @BindView(R.id.time_tv) TextView time_tv;
        @Nullable @BindView(R.id.gender_tv) TextView gender_tv;
        @Nullable @BindView(R.id.job_tv) TextView job_tv;
        @Nullable @BindView(R.id.email_tv) TextView email_tv;
        @Nullable @BindView(R.id.country_tv) TextView country_tv;
        @Nullable @BindView(R.id.car_tv) TextView car_tv;
        @Nullable @BindView(R.id.car_color) CircleImageView car_color;
        @Nullable @BindView(R.id.bio_tv) ReadMoreTextView bio_tv;


        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this,v);

        }
    }

    @Override
    public int getItemCount() {
        return carOwnerModels.size();
    }


}