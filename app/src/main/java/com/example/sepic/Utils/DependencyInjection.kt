package com.example.sepic.Utils

import android.content.Context
import com.example.sepic.Room.AppDatabase
import com.example.sepic.Room.ItemRepository

object DependencyInjection {
    fun provideRepository(context: Context): ItemRepository {
        val database = AppDatabase.getDatabase(context)
        val appExecutors = AppExecutors()
        val dao = database.itemDao()
        return ItemRepository.getInstance(dao, appExecutors)
    }
}