package com.wego.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wego.R;
import com.wego.databinding.GoogleMapsResultItemBinding;
import com.wego.model.pojo.Result;
import com.wego.viewmodel.IMainActivity;

import java.text.DecimalFormat;
import java.util.List;

public class GoogleMapsResultsAdapter extends RecyclerView.Adapter<GoogleMapsResultsAdapter.BindingHolder> {

    private List<Result> mCategoriesList;
    private Location mCurrentLocation;
    private Context mContext;
    private static DecimalFormat df2 = new DecimalFormat("#.##");


    public GoogleMapsResultsAdapter(List<Result> categories, Context mContext, Location currentLocation) {
        this.mCategoriesList = categories;
        this.mContext = mContext;
        this.mCurrentLocation = currentLocation;
    }

    @NonNull
    @Override
    public BindingHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        GoogleMapsResultItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.google_maps_result_item, viewGroup, false);
        return new BindingHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull BindingHolder bindingHolder, final int i) {

        Result category = mCategoriesList.get(i);
        bindingHolder.binding.setPosition(i);
        bindingHolder.binding.setResult(category);
        bindingHolder.binding.setDistance(distance(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude(), category.getGeometry().getLocation().getLat(), category.getGeometry().getLocation().getLng()));
        bindingHolder.binding.setIMainActivity((IMainActivity) mContext);
        bindingHolder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        if(mCategoriesList!=null){
            return mCategoriesList.size();
        }
        else {
            return 0;
        }
    }

    public class BindingHolder extends RecyclerView.ViewHolder{

        GoogleMapsResultItemBinding binding;
        public BindingHolder(View itemView){
            super(itemView);

            binding = DataBindingUtil.bind(itemView);
        }
    }

    /**
    * Here getting distance in kilometers (km)
    **/
    private String distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return "Distance : "+df2.format(dist)+" KM";
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }
}
