package com.example.sepic.Room

import android.database.sqlite.SQLiteConstraintException
import androidx.lifecycle.LiveData
import com.example.sepic.Utils.AppExecutors

class ItemRepository private constructor(private val itemDao: ItemDao, private val appExecutors: AppExecutors) {
    //mendapatkan semua data dari database
    fun getAllItem(): LiveData<List<ItemDatabase>> = itemDao.getAllItem()

    fun insertItem(item: ItemDatabase): Result<Unit> {
        return try {
            appExecutors.diskIO().execute { itemDao.insertItem(item) }
            Result.success(Unit)
        } catch (e: SQLiteConstraintException) {
            Result.failure(e)
        }
    }

    fun updateItem(item: ItemDatabase): Result<Unit> {
        return try {
            appExecutors.diskIO().execute {
                itemDao.updateItem(item)
            }
            Result.success(Unit)
        } catch (e: SQLiteConstraintException) {
            Result.failure(e)
        }
    }
    //mendapatkan data kedalam database
    fun deleteItem(item: ItemDatabase) {
        appExecutors.diskIO().execute { itemDao.deleteItem(item) }
    }
    //variable untuk menyimpan instance dari app resip
    companion object {
        @Volatile
        private var instance: ItemRepository? = null

        fun getInstance(
            itemDao: ItemDao,
            appExecutors: AppExecutors
        ): ItemRepository = instance ?: synchronized(this) {
                instance ?: ItemRepository(itemDao, appExecutors)
            }.also { instance = it }
    }
}