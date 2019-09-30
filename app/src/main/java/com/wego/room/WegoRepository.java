package com.wego.room;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.List;

public class WegoRepository {

    private WegoDao mWegoDao;
    private LiveData<List<CategoryEntity>> mAllCategories;
    private final int INSERT = 1, DELETE=2, UPDATE = 3;

    public WegoRepository(Application application) {
        WegoRoomDatabase db = WegoRoomDatabase.getDatabase(application);
        mWegoDao = db.wegoDao();
        mAllCategories = mWegoDao.getAllCategories();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    public LiveData<List<CategoryEntity>> getAllDocuments() {
        return mAllCategories;
    }

    public void insert() {
        new insertAsyncTask(mWegoDao, INSERT).execute();
    }

    public void delete(CategoryEntity category) {
        new insertAsyncTask(mWegoDao, DELETE).execute(category);
    }

    public void update(CategoryEntity category) {
        new insertAsyncTask(mWegoDao, UPDATE).execute(category);
    }

    private class insertAsyncTask extends android.os.AsyncTask<CategoryEntity, Void, Void> {

        private WegoDao mAsyncTaskDao;
        private int type;

        insertAsyncTask(WegoDao dao, int operation_type) {
            mAsyncTaskDao = dao;
            type = operation_type;
        }

        @Override
        protected Void doInBackground(final CategoryEntity... params) {

            if(type == INSERT){
                List<CategoryEntity> categoryEntities = new ArrayList<>();
                categoryEntities.add(new CategoryEntity("Airports", 0));
                categoryEntities.add(new CategoryEntity("Travel agencies", 0));
                mAsyncTaskDao.insertCategory(categoryEntities);
            }
            else if(type == DELETE){
                mAsyncTaskDao.deleteCategory(params[0]);
            }
            else if(type == UPDATE){
                mAsyncTaskDao.updateCategory(params[0]);
            }
            return null;
        }
    }
}
