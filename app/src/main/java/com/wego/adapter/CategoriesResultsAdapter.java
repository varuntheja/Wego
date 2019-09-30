package com.wego.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wego.R;
import com.wego.databinding.CategoryResultItemBinding;
import com.wego.model.pojo.Result;
import com.wego.viewmodel.IMainActivity;

import java.util.List;

public class CategoriesResultsAdapter extends RecyclerView.Adapter<CategoriesResultsAdapter.BindingHolder> {

    private List<Result> mCategoriesList;
    private Context mContext;

    public CategoriesResultsAdapter(List<Result> categories, Context mContext) {
        this.mCategoriesList = categories;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public BindingHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        CategoryResultItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.category_result_item, viewGroup, false);
        return new BindingHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull BindingHolder bindingHolder, final int i) {

        Result category = mCategoriesList.get(i);
        String imageUrl = null;
        if(category.getPhotos()!=null && category.getPhotos().size()>0){
            imageUrl= "https://maps.googleapis.com/maps/api/place/photo?maxwidth=200&maxheight=200&photoreference="+category.getPhotos().get(0).getPhotoReference()+"&sensor=false&key="+mContext.getResources().getString(R.string.google_maps_key);
        }
        else if(category.getIcon()!=null && category.getIcon().length()>0){
            imageUrl = category.getIcon();
        }

        bindingHolder.binding.setPosition(i);
        bindingHolder.binding.setResult(category);
        bindingHolder.binding.setImageUrl(imageUrl);
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

        CategoryResultItemBinding binding;
        public BindingHolder(View itemView){
            super(itemView);

            binding = DataBindingUtil.bind(itemView);
        }
    }
}
