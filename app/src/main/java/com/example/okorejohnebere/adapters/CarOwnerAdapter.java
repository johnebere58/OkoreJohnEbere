package com.example.okorejohnebere.adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

import static com.example.okorejohnebere.adapters.FilterListAdapter.getViewColor;

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
        final CarOwnerModel carOwnerModel = carOwnerModels.get(position);

        String colorsString = carOwnerModel.getCar_color();
        int color = getViewColor(mContext,colorsString);

        holder.name_tv.setText(String.format("%s %s",carOwnerModel.getFirst_name(),carOwnerModel.getLast_name()));
        holder.job_tv.setText(carOwnerModel.getJob_title());
        holder.gender_tv.setText(carOwnerModel.getGender());
        holder.email_tv.setText(carOwnerModel.getEmail());
        holder.country_tv.setText(carOwnerModel.getCountry());
        holder.car_tv.setText(String.format("%s (%s) ~ %s",
                carOwnerModel.getCar_model(),carOwnerModel.getCar_model_year(),colorsString));
        holder.bio_tv.setReadMoreText(carOwnerModel.getBio(),true);

        if(color==-1){
            holder.car_color.setVisibility(View.GONE);
        }else{
            holder.car_color.setBackgroundColor(color);
        }

    }


    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.name_tv) TextView name_tv;
        @BindView(R.id.gender_tv) TextView gender_tv;
        @BindView(R.id.job_tv) TextView job_tv;
        @BindView(R.id.email_tv) TextView email_tv;
        @BindView(R.id.country_tv) TextView country_tv;
        @BindView(R.id.car_tv) TextView car_tv;
        @BindView(R.id.car_color) ImageView car_color;
        @BindView(R.id.bio_tv) ReadMoreTextView bio_tv;


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