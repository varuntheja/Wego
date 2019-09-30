package com.wego.model;

import com.wego.api.APIWego;
import com.wego.api.WegoApiInterface;
import com.wego.model.pojo.CategoriesResults;
import com.wego.viewmodel.IHomeViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeModel {

    private IHomeViewModel iHomeViewModel;
    public HomeModel(IHomeViewModel mIDashboardPresenter) {
        this.iHomeViewModel = mIDashboardPresenter;
    }

    public void fetchCategories(String location, String radius, String type, String apiKey) {

        WegoApiInterface apiService = APIWego.getClient().create(WegoApiInterface.class);
        Call<CategoriesResults> call = apiService.getCategories(location, radius, type, apiKey);
        call.enqueue(new Callback<CategoriesResults>() {
            @Override
            public void onResponse(Call<CategoriesResults>call, final Response<CategoriesResults> response) {
                if(response.body()!=null && response.code() == 200){
                    iHomeViewModel.updateDocuments(response.body().getResults());
                }
            }

            @Override
            public void onFailure(Call<CategoriesResults>call, Throwable t) {
                // Log error here since request failed
                iHomeViewModel.updateDocuments(null);
            }
        });
    }
}
