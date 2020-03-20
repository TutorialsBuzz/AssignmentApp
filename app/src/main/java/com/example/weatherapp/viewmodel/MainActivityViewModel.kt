package com.example.weatherapp.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.api.Repository
import com.example.weatherapp.model.JSONResponse
import com.example.weatherapp.model.persistence.Database
import com.google.gson.Gson
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(
    val repository: Repository
) : ViewModel() {

    var jsonResponse = MutableLiveData<JSONResponse>()

    var isLoading = MutableLiveData<Boolean>()


    fun fetchFromData() {

        isLoading.postValue(true)

        viewModelScope.launch {
            val data = repository.getALL()
            if (!data?.isEmpty()) {
                val model: JSONResponse = Gson().fromJson(
                    data?.get(0)!!.data,
                    JSONResponse::class.java
                )
                isLoading.postValue(false)
                jsonResponse.value = model
            }
        }
    }
}