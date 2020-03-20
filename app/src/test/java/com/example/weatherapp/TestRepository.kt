package com.example.weatherapp

import android.content.Context
import com.example.weatherapp.api.Repository
import com.example.weatherapp.model.persistence.Database
import com.example.weatherapp.model.persistence.entity.WeatherInfo
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class TestRepository {


    @InjectMocks
    lateinit var repository: Repository

    @Mock
    lateinit var loctionRepository: TestLoctionRepository

    @Mock
    lateinit var database: Database

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

    }


    @Test
    fun TesGetAllFunction() {

      /*  given { runBlocking { repository.getALL() } }
            .willReturn { List<WeatherInfo>(1) }*/

    }

}