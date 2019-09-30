package com.wego.viewmodel;

import com.wego.room.CategoryEntity;

public interface IMainActivity {
    void openCategoryResults(CategoryEntity categoryEntity);
    void openGoogleMap(int position);
}
