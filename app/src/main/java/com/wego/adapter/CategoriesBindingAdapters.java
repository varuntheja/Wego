package com.wego.adapter;

import android.databinding.BindingAdapter;
import android.location.Location;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.wego.model.pojo.Result;
import com.wego.room.CategoryEntity;

import java.util.List;

public class CategoriesBindingAdapters {

    @BindingAdapter("categoriesList")
    public static void setCategories(RecyclerView recyclerView, List<CategoryEntity> documentList){

        if(documentList == null){
            return;
        }

        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if(layoutManager == null){
            recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        }

        CategoriesAdapter adapter = (CategoriesAdapter)recyclerView.getAdapter();
        if(adapter == null){
            adapter = new CategoriesAdapter(documentList, recyclerView.getContext());
            recyclerView.setAdapter(adapter);
        }
    }

    @BindingAdapter("categoriesResults")
    public static void setCategoriesResults(RecyclerView recyclerView, List<Result> documentList){

        if(documentList == null){
            return;
        }

        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if(layoutManager == null){
            recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        }

        CategoriesResultsAdapter adapter = (CategoriesResultsAdapter)recyclerView.getAdapter();
        if(adapter == null){
            adapter = new CategoriesResultsAdapter(documentList, recyclerView.getContext());
            recyclerView.setAdapter(adapter);
        }
    }

    @BindingAdapter({"imageUrl"})
    public static void setImageUrl(ImageView view, String url){
        Picasso.get().load(url).tag(view.getContext()).into(view);
    }

    @BindingAdapter({"googleMapsResults", "location"})
    public static void setMapsResults(RecyclerView recyclerView, List<Result> documentList, Location location){

        if(documentList == null){
            return;
        }

        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if(layoutManager == null){

            SnapHelper snapHelper = new PagerSnapHelper();
            recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext(),
                    LinearLayoutManager.HORIZONTAL, false) {
                @Override
                public boolean canScrollVertically() {
                    return false;
                }
            });
            snapHelper.attachToRecyclerView(recyclerView);
        }

        GoogleMapsResultsAdapter adapter = (GoogleMapsResultsAdapter)recyclerView.getAdapter();
        if(adapter == null){
            adapter = new GoogleMapsResultsAdapter(documentList, recyclerView.getContext(), location);
            recyclerView.setAdapter(adapter);
        }
    }
}
