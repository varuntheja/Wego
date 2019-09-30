package com.wego.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wego.R;
import com.wego.databinding.CategoryItemBinding;
import com.wego.room.CategoryEntity;
import com.wego.viewmodel.IMainActivity;

import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.BindingHolder> {

    private List<CategoryEntity> mCategoriesList;
    private Context mContext;

    public CategoriesAdapter(List<CategoryEntity> categories, Context mContext) {
        this.mCategoriesList = categories;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public BindingHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        CategoryItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.category_item, viewGroup, false);
        return new BindingHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull BindingHolder bindingHolder, final int i) {

        CategoryEntity category = mCategoriesList.get(i);
        bindingHolder.binding.setPosition(i);
        bindingHolder.binding.setCategory(category);
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

        CategoryItemBinding binding;
        public BindingHolder(View itemView){
            super(itemView);

            binding = DataBindingUtil.bind(itemView);
        }
    }
}
