package com.example.weatherapp.api

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.util.Log
import com.example.weatherapp.BuildConfig
import com.example.weatherapp.MainActivity
import com.example.weatherapp.model.JSONResponse
import com.example.weatherapp.model.persistence.Database
import com.example.weatherapp.model.persistence.entity.WeatherInfo
import com.example.weatherapp.utility.PreferenceManager
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class LocationRepository @Inject constructor(
    val application: Context,
    val apiService: WeatherApiService,
    val database: Database,
    val preferenceManager: PreferenceManager
) {

    val TAG: String = "LocationRepository"
    val intent = Intent()

    fun getLocation() {

        if (isGPSEnabled() && checkLocationPermission()) {
            LocationServices.getFusedLocationProviderClient(application)
                ?.lastLocation
                ?.addOnSuccessListener { location: android.location.Location? ->
                    if (location != null) {
                        Log.d(
                            TAG,
                            "lat nd lng" + location.latitude + "||" + location.longitude
                        )

                        // store lat lng periodically
                        preferenceManager.setLatitude(location.latitude.toFloat())
                        preferenceManager.setLongitude(location.longitude.toFloat())

                        makeAPIRequest(location.latitude, location.longitude)

                    } else {
                        intent.putExtra("LocResult", false)
                        application.sendBroadcast(intent.setAction("com.example.Broadcast"))
                    }
                }
        } else {
            intent.putExtra("LocResult", false)
            application.sendBroadcast(intent.setAction("com.example.Broadcast"))
        }


    }


    fun makeAPIRequest(lat: Double, lng: Double) {
        GlobalScope.launch {


            val responseBody = apiService.fetchDataByLocation(
                lat.toString(),
                lng.toString(),
                BuildConfig.API_KEY
            )

            if (responseBody.isSuccessful) {
                val data = responseBody.body()

                Log.d(TAG, "data" + data)

                val gson = Gson()

                val insertDT =
                    database.locationDao().insert(WeatherInfo(1, gson.toJson(data)))
                Log.d(TAG, "insertDT: " + insertDT)

                if (insertDT > 0)
                    intent.putExtra("LocResult", true)
                else
                    intent.putExtra("LocResult", false)

            } else {
                intent.putExtra("LocResult", false)
            }
            application.sendBroadcast(intent.setAction("com.example.Broadcast"))

        }
    }

    public fun isGPSEnabled() =
        (application.getSystemService(Context.LOCATION_SERVICE) as LocationManager).isProviderEnabled(
            LocationManager.GPS_PROVIDER
        )

    public fun checkLocationPermission(): Boolean =
        application.checkCallingOrSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED


}
