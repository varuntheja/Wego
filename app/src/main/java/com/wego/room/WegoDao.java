package com.wego.room;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface WegoDao {

    @Query("SELECT * FROM categories_table ORDER BY selection_frequency DESC")
    LiveData<List<CategoryEntity>> getAllCategories();

    @Insert
    void insertCategory(List<CategoryEntity> categoryEntity);

    @Delete
    void deleteCategory(CategoryEntity categoryEntity);

    @Update
    void updateCategory(CategoryEntity categoryEntity);
}
