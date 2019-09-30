package com.wego.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.widget.Toast;

import com.wego.model.HomeModel;
import com.wego.model.pojo.CategoriesResults;
import com.wego.model.pojo.Result;

import java.util.List;

public class CategoryResultsViewModel extends AndroidViewModel implements IHomeViewModel{

    private HomeModel mHomeModel;

    // Using LiveData and caching what getAlphabetizedWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    private MutableLiveData<List<Result>> mCategoriesResults;


    public CategoryResultsViewModel(Application application) {
        super(application);
        mCategoriesResults = new MutableLiveData<>();
        mHomeModel = new HomeModel(this);
    }

    public void getCategories(String location, String radius, String type, String apiKey){
        mHomeModel.fetchCategories(location, radius, type, apiKey);
    }

    public MutableLiveData<List<Result>> getCategoryResults(){
        return mCategoriesResults;
    }

    @Override
    public void updateDocuments(List<Result> results) {

        if(results == null || results.size() == 0){
            Toast.makeText(getApplication(), "Categories not available.", Toast.LENGTH_LONG).show();
        }

        if(mCategoriesResults!=null && mCategoriesResults.getValue()!=null){
            mCategoriesResults.getValue().clear();
        }
        mCategoriesResults.postValue(results);
    }
}
