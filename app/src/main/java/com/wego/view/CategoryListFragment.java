package com.wego.view;

import android.app.ProgressDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.wego.R;
import com.wego.databinding.FragmentCategoryListBinding;
import com.wego.model.WegoDataModel;
import com.wego.model.pojo.Result;
import com.wego.util.Utility;
import com.wego.viewmodel.CategoryResultsViewModel;

import java.util.ArrayList;
import java.util.List;

public class CategoryListFragment extends Fragment {

    private CategoryResultsViewModel mCategoryResultsViewModel;
    private FragmentCategoryListBinding mBinding;
    private ProgressDialog progressDialog;
    private String mSelectedCategory;
    private List<Result> categoriesResults = new ArrayList<>();
    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if(view!=null){
            return view;
        }
        mBinding = FragmentCategoryListBinding.inflate(inflater);
        mCategoryResultsViewModel = ViewModelProviders.of(this).get(CategoryResultsViewModel.class);
        WegoDataModel.getInstance().setCategoryResultsViewModel(mCategoryResultsViewModel);
        if(getArguments()!=null){
            mSelectedCategory = getArguments().getString("category");
        }
        view = mBinding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(categoriesResults.size()>0){
            return;
        }
        mCategoryResultsViewModel.getCategoryResults().observe(this, new Observer<List<Result>>() {
            @Override
            public void onChanged(@Nullable List<Result> results) {
                dismissProgressDialog();
                if (results != null && results.size() > 0) {
                    categoriesResults.clear();
                    categoriesResults.addAll(results);
                    mBinding.setResults(results);
                }
            }
        });
        getDeviceLocation();
    }

    /**
     * Method to fetch device last know location.
    * */
    private void getDeviceLocation() {
        try {
            FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            // Got last known location. In some rare situations this can be null.
                            if (location != null) {
                                // Logic to handle location object
                                getCategories(location.getLatitude()+","+location.getLongitude());
                            }
                            else {
                                Toast.makeText(getActivity(), "Device location is not available.", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }


    /**
     * Method to fetch list of categories from google's nearBy api based on user selection.
     * */
    private void getCategories(String location) {

        if (Utility.isNetworkAvailable(getActivity())) {
            displayProgressDialog(getString(R.string.please_wait));
            mCategoryResultsViewModel.getCategories(location, "5000", mSelectedCategory, getString(R.string.google_maps_key));
        } else {
            Toast.makeText(getActivity(), R.string.network_error_msg, Toast.LENGTH_SHORT).show();
        }
    }

    private void displayProgressDialog(String message) {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(message);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    private void dismissProgressDialog() {

        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}
