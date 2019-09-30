package com.wego.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.wego.room.CategoryEntity;
import com.wego.room.WegoRepository;

import java.util.List;

public class HomeFragmentViewModel extends AndroidViewModel{

    private WegoRepository mRepository;
    // Using LiveData and caching what getAlphabetizedWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    private LiveData<List<CategoryEntity>> mAllCategories;

    public HomeFragmentViewModel(Application application) {
        super(application);
        mRepository = new WegoRepository(application);
        mAllCategories = mRepository.getAllDocuments();
    }

    public LiveData<List<CategoryEntity>> getCategories() {
        return mAllCategories;
    }

    public void insert() {
        mRepository.insert();
    }

    public void delete(CategoryEntity categoryEntity){
        mRepository.delete(categoryEntity);
    }

    public void update(CategoryEntity categoryEntity){
        mRepository.update(categoryEntity);
    }
}
