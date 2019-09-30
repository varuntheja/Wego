package com.wego.view;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.wego.R;
import com.wego.databinding.FragmentMapBinding;
import com.wego.model.WegoDataModel;
import com.wego.model.pojo.Result;
import com.wego.viewmodel.CategoryResultsViewModel;

import java.util.ArrayList;
import java.util.List;

public class GoogleMapFragment extends Fragment implements OnMapReadyCallback {

    private FragmentMapBinding mBinding;
    private GoogleMap mGoogleMap;
    private FusedLocationProviderClient fusedLocationClient;
    private List<Result> categoriesResults = new ArrayList<>();
    private int mCurrentPosition = 0;
    private Location mCurrentLocation;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = FragmentMapBinding.inflate(inflater);
        if(getArguments()!=null){
            mCurrentPosition = getArguments().getInt("position");
        }
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.mGoogleMap = googleMap;
        getDeviceLocation();
    }

    /**
     * Get the best and most recent location of the device, which may be null in rare
     * cases when a location is not available.
     **/
    private void getDeviceLocation() {
        try {
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            // Got last known location. In some rare situations this can be null.
                            if (location != null) {
                                // Logic to handle location object
                                mCurrentLocation = location;
                                mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                        new LatLng(location.getLatitude(),
                                                location.getLongitude()), 14));
                                getCategoriesList();
                            }
                        }
                    });
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    /**
     * Method to fetch categories list from existing view model.
    * */
    private void getCategoriesList(){
        CategoryResultsViewModel mCategoryResultsViewModel = WegoDataModel.getInstance().getCategoryResultsViewModel();
        mCategoryResultsViewModel.getCategoryResults().observe(this, new Observer<List<Result>>() {
            @Override
            public void onChanged(@Nullable List<Result> results) {
                categoriesResults.clear();
                if (results != null && results.size() > 0) {
                    categoriesResults.addAll(results);
                    mBinding.setCurrentLocation(mCurrentLocation);
                    mBinding.setResults(results);
                    setScrollListener();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mBinding.recyclerViewResults.scrollToPosition(mCurrentPosition);
                            drawPolyLine();
                        }
                    }, 200);
                }
            }
        });
    }


    /**
    * Method to draw polyline between user current location to target location
     * */
    private void drawPolyLine(){

        mGoogleMap.clear();

        LatLng from = new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());
        LatLng to = new LatLng(categoriesResults.get(mCurrentPosition).getGeometry().getLocation().getLat(),
                categoriesResults.get(mCurrentPosition).getGeometry().getLocation().getLng());

        mGoogleMap.addMarker(new MarkerOptions()
                .position(from)
                .icon(bitmapDescriptorFromVector(getActivity(), R.drawable.ic_location_green))
                .title("My Location"));

        mGoogleMap.addMarker(new MarkerOptions()
                .position(to)
                .icon(bitmapDescriptorFromVector(getActivity(), R.drawable.ic_location_red))
                .title(categoriesResults.get(mCurrentPosition).getName()));

        PolylineOptions line= new PolylineOptions()
                .add(from,to)
                .width(5).color(Color.RED);
        mGoogleMap.addPolyline(line);
    }

    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    /**
     * Method to listen scroll change of recyclerView to re-draw polylilne
    * */
    private void setScrollListener(){
        mBinding.recyclerViewResults.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE){
                    mCurrentPosition = ((LinearLayoutManager)recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
                    drawPolyLine();
                }
            }
        });
    }
}
