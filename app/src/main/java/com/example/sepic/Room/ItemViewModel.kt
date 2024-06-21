package com.example.sepic.Room


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ItemViewModel(private val itemRepository: ItemRepository) : ViewModel() {

    private val _insertResult = MutableLiveData<Result<Unit>>()
    val insertResult: LiveData<Result<Unit>> get() = _insertResult

    private val _updateResult = MutableLiveData<Result<Unit>>()
    val updateResult: LiveData<Result<Unit>> get() = _updateResult

    fun insertItem(item: ItemDatabase) {
        viewModelScope.launch {
            val result = itemRepository.insertItem(item)
            _insertResult.value = result
        }
    }

    fun getAllItem(): LiveData<List<ItemDatabase>> {
        return itemRepository.getAllItem()
    }

    fun updateItem(item: ItemDatabase) {
        viewModelScope.launch {
            val result = itemRepository.updateItem(item)
            _updateResult.value = result
        }
    }

    fun deleteItem(item: ItemDatabase) {
        itemRepository.deleteItem(item)
    }
}