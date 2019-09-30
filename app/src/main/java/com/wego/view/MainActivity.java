package com.wego.view;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.wego.R;
import com.wego.room.CategoryEntity;
import com.wego.room.WegoRepository;
import com.wego.viewmodel.IMainActivity;

public class MainActivity extends AppCompatActivity implements IMainActivity {

    private final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 11;
    private WegoRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        repository = new WegoRepository(getApplication());
        setToolbarTitle(getString(R.string.select_category));
        Fragment homeFragment = new HomeFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_fragment, homeFragment, "home")
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }


    /**
     * Method to update title of toolbar.
     * */
    private void setToolbarTitle(String title){
        getSupportActionBar().setTitle(title);
    }

    /**
     * Method to open list of categories fragment.
    * */
    @Override
    public void openCategoryResults(CategoryEntity categoryEntity) {

        setToolbarTitle(categoryEntity.mCategoryName);
        categoryEntity.mSelectionFrequency = categoryEntity.mSelectionFrequency + 1;
        repository.update(categoryEntity);

        Bundle bundle = new Bundle();
        bundle.putString("category", categoryEntity.mCategoryName);
        Fragment categoryListFragment = new CategoryListFragment();
        categoryListFragment.setArguments(bundle);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_fragment, categoryListFragment, "results")
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .addToBackStack(null)
                .commit();
    }



    /**
     * Method to open list of google maps fragment.
     * */
    @Override
    public void openGoogleMap(int position) {
        if (!getLocationPermission()) {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        Fragment categoryListFragment = new GoogleMapFragment();
        categoryListFragment.setArguments(bundle);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_fragment, categoryListFragment, "maps")
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .addToBackStack(null)
                .commit();
    }

    /**
     * Request location permission, so that we can get the location of the
     * device. The result of the permission request is handled by a callback,
     * onRequestPermissionsResult.
     **/
    private boolean getLocationPermission() {
        return ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openGoogleMap(1);
                }
            }
        }
    }
}
