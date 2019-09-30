package com.wego.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wego.R;
import com.wego.databinding.FragmentHomeBinding;
import com.wego.room.CategoryEntity;
import com.wego.viewmodel.HomeFragmentViewModel;

import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding mBinding;
    private HomeFragmentViewModel mHomeFragmentViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((MainActivity)getActivity()).getSupportActionBar().setTitle(getActivity().getResources().getString(R.string.select_category));
        mBinding = FragmentHomeBinding.inflate(inflater);
        // Get a new or existing ViewModel from the ViewModelProvider.
        mHomeFragmentViewModel = ViewModelProviders.of(this).get(HomeFragmentViewModel.class);
        mHomeFragmentViewModel.getCategories().observe(this, new Observer<List<CategoryEntity>>() {
            @Override
            public void onChanged(@Nullable final List<CategoryEntity> categories) {

                if (categories != null && categories.size() > 0) {
                    mBinding.setCategories(categories);
                } else {
                    mHomeFragmentViewModel.insert();
                }
            }
        });
        return mBinding.getRoot();
    }
}
