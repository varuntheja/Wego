package com.wego.model;

import com.wego.viewmodel.CategoryResultsViewModel;

public class WegoDataModel {

    private static WegoDataModel wegoDataModel;
    private CategoryResultsViewModel categoryResultsViewModel;
    private WegoDataModel(){}
    // static method to create instance of Singleton class
    public static WegoDataModel getInstance()
    {
        if (wegoDataModel == null)
            wegoDataModel = new WegoDataModel();
        return wegoDataModel;
    }

    public CategoryResultsViewModel getCategoryResultsViewModel() {
        return categoryResultsViewModel;
    }

    public void setCategoryResultsViewModel(CategoryResultsViewModel categoryResultsViewModel) {
        this.categoryResultsViewModel = categoryResultsViewModel;
    }
}
