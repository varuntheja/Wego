package com.wego.room;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "categories_table")
public class CategoryEntity implements Serializable{

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "category_id")
    public String mCategoryId;

    @ColumnInfo(name = "category_name")
    public String mCategoryName;

    @ColumnInfo(name = "selection_frequency")
    public int mSelectionFrequency;

    public CategoryEntity(String mCategoryName, int mSelectionFrequency) {
        this.mCategoryName = mCategoryName;
        this.mSelectionFrequency = mSelectionFrequency;
    }
}
