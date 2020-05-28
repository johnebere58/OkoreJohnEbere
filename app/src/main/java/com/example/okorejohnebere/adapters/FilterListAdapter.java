package com.example.okorejohnebere.adapters;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.okorejohnebere.R;
import com.example.okorejohnebere.custom_views.CircleImageView;
import com.example.okorejohnebere.models.FilterModel;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by John Ebere on 5/13/2016.
 */
public class FilterListAdapter extends BaseAdapter<FilterListAdapter.ViewHolder> {

    ArrayList<FilterModel> filterModels;

    public FilterListAdapter(Activity activity, ArrayList<FilterModel> filterModels) {
        super(activity);
        this.filterModels = filterModels;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int type) {
        return new ViewHolder(mInflater.inflate(R.layout.filter_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final FilterModel filterModel = filterModels.get(position);

        String name = filterModel.getFullName();
        String gender = filterModel.getGender();
        String time = filterModel.getCreatedAt();
        String avatar = filterModel.getAvatar();
        final JSONArray colors = filterModel.getColors();
        JSONArray countries = filterModel.getCountries();

        holder.name_tv.setVisibility((name==null || name.isEmpty())?View.GONE:View.VISIBLE);
        holder.name_tv.setText(name);

        holder.gender_tv.setVisibility((gender==null || gender.isEmpty())?View.GONE:View.VISIBLE);
        holder.gender_tv.setText(gender);

        holder.time_tv.setVisibility((time==null || time.isEmpty())?View.GONE:View.VISIBLE);
        holder.time_tv.setText(time);

        if(gender!=null)holder.user_image_icon.setImageResource(
                filterModel.getGender().equalsIgnoreCase("male")?
                        (R.mipmap.ic_male):(R.mipmap.ic_female));

        if(avatar==null || avatar.isEmpty()){
        holder.user_image_holder.setVisibility(View.GONE);
        }else{
        holder.user_image_holder.setVisibility(View.VISIBLE);
        holder.user_image.displayImage(avatar);
        }

        if((colors==null || colors.length()==0)){
        holder.color_scroll.setVisibility(View.GONE);
        }else{
        holder.color_scroll.setVisibility(View.VISIBLE);
        holder.color_holder.removeAllViews();
           //inflate color views
           for(int i=0;i<colors.length();i++){
               try {
                   String colorsString = colors.getString(i);
                   int color = getViewColor(colorsString);

                   if(color!=-1) {
                       View view = View.inflate(mContext, R.layout.color_item, null);
                       CircleImageView color_view = view.findViewById(R.id.color_view);
                       color_view.setBackgroundColor(color);
                       holder.color_holder.addView(view);
                   }else{
                       View view = View.inflate(mContext, R.layout.color_item_textview, null);
                       TextView color_tv = view.findViewById(R.id.color_tv);
                       color_tv.setText(colorsString);
                       holder.color_holder.addView(view);
                   }

               } catch (JSONException e) {
                   e.printStackTrace();
               }

           }

        }
        if((countries==null || countries.length()==0)){
        holder.country_scroll.setVisibility(View.GONE);
        }else{
        holder.country_scroll.setVisibility(View.VISIBLE);
        holder.country_holder.removeAllViews();
           //inflate country and flags
            for(int i=0;i<countries.length();i++){
                try {
                    String countryString = countries.getString(i);
                    Drawable flag = getCountryFlag(countryString);

                    View view = View.inflate(mContext, R.layout.country_item, null);
                    TextView country_tv = view.findViewById(R.id.country_tv);
                    ImageView country_flag = view.findViewById(R.id.country_flag);
                    country_flag.setVisibility(flag==null?View.GONE:View.VISIBLE);
                    country_tv.setText(countryString);
                    country_flag.setImageDrawable(flag);
                    holder.country_holder.addView(view);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }

        holder.main_layout.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        mContext.startActivity(new Intent(mContext,CarOwnersActivity.class));

                    }
                });
    }

    private int getViewColor(String color){
        if(color.equalsIgnoreCase("Green"))return mContext.getResources().getColor(R.color.green);
        if(color.equalsIgnoreCase("Violet"))return mContext.getResources().getColor(R.color.violet);
        if(color.equalsIgnoreCase("Blue"))return mContext.getResources().getColor(R.color.blue);
        if(color.equalsIgnoreCase("Teal"))return mContext.getResources().getColor(R.color.teal);
        if(color.equalsIgnoreCase("Maroon"))return mContext.getResources().getColor(R.color.maroon);
        if(color.equalsIgnoreCase("Aquamarine"))return mContext.getResources().getColor(R.color.aquamarine);
        if(color.equalsIgnoreCase("Orange"))return mContext.getResources().getColor(R.color.orange);
        if(color.equalsIgnoreCase("Mauv"))return mContext.getResources().getColor(R.color.mauv);
        if(color.equalsIgnoreCase("Yellow"))return mContext.getResources().getColor(R.color.yellow);
        if(color.equalsIgnoreCase("Red"))return mContext.getResources().getColor(R.color.red);
        return -1;
    }

    private Drawable getCountryFlag(String country){
        if(country.equalsIgnoreCase("China"))return mContext.getResources().getDrawable(R.mipmap.flag_china);
        if(country.equalsIgnoreCase("france"))return mContext.getResources().getDrawable(R.mipmap.flag_france);
        if(country.equalsIgnoreCase("Mexico"))return mContext.getResources().getDrawable(R.mipmap.flag_mexico);
        if(country.equalsIgnoreCase("Japan"))return mContext.getResources().getDrawable(R.mipmap.flag_japan);
        if(country.equalsIgnoreCase("Estonia"))return mContext.getResources().getDrawable(R.mipmap.flag_estonia);
        if(country.equalsIgnoreCase("Colombia"))return mContext.getResources().getDrawable(R.mipmap.flag_columbia);
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.main_layout) View main_layout;
        @BindView(R.id.user_image_holder) View user_image_holder;
        @BindView(R.id.user_image_icon) ImageView user_image_icon;
        @BindView(R.id.user_image) CircleImageView user_image;
        @BindView(R.id.name_tv) TextView name_tv;
        @BindView(R.id.time_tv) TextView time_tv;
        @BindView(R.id.gender_tv) TextView gender_tv;
        @BindView(R.id.color_scroll) HorizontalScrollView color_scroll;
        @BindView(R.id.color_holder) LinearLayout color_holder;
        @BindView(R.id.country_scroll) HorizontalScrollView country_scroll;
        @BindView(R.id.country_holder) LinearLayout country_holder;


        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this,v);

        }
    }

    @Override
    public int getItemCount() {
        return filterModels.size();
    }


}