package com.application.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.application.model.DeliveryItem
import com.application.repository.DeliveryRepository
import com.application.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * This class is used to interact with repository class and hold the data using live data
 */
class DeliverViewModel(private val repository:DeliveryRepository):ViewModel() {

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.readFile()
        }
    }
    val deliveryList:LiveData<Resource<MutableList<DeliveryItem>>> get() = repository.deliveryList

}