package com.example.weatherapp

import android.content.Context
import com.example.weatherapp.api.LocationRepository
import com.example.weatherapp.api.WeatherApiService
import com.example.weatherapp.model.persistence.Database
import com.example.weatherapp.utility.PreferenceManager
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class TestLoctionRepository {

    @InjectMocks
    lateinit var locationRepository: LocationRepository

    @Mock
    lateinit var application: Context

    @Mock
    lateinit var apiService: WeatherApiService

    @Mock
    lateinit var database: Database

    @Mock
    lateinit var preferenceManager: PreferenceManager


    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        
    }


    @Test
    fun testAdd() {


        locationRepository.getLocation()


        // when (locationRepository.isGPSEnabled()).thenReturn(true)


        Assert.assertEquals(13, getSum())


    }

    fun getSum(): Int {
        return 4 + 9
    }

}