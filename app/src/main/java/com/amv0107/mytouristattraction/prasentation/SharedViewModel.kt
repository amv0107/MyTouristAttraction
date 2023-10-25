package com.amv0107.mytouristattraction.prasentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amv0107.mytouristattraction.data.Places
import com.amv0107.mytouristattraction.data.Repository
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.PolyUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(private val repo: Repository) : ViewModel() {

    /**
     * List on ALL locations for RecyclerView
     */
    private var _locations = MutableLiveData<MutableList<Places>>()
    val locations: LiveData<MutableList<Places>> = _locations

    /**
     * List of intermediate waypoints
     */
    private var _decodePathPolylinePoints = MutableLiveData<MutableList<LatLng>>()
    val decodePathPolylinePoints: LiveData<MutableList<LatLng>> = _decodePathPolylinePoints

    private var _localCity = MutableLiveData<String>()
    val localCity: LiveData<String> = _localCity

    fun getLocalCity(city: String) {
        _localCity.value = city
    }

    fun getAllLocations(location: String = "", radius: String = "") {
        val tempLocation = mutableListOf<Places>()
        viewModelScope.launch(Dispatchers.IO) {

            val result = if (location.isNotEmpty() || radius.isNotEmpty())
                repo.getNearbyPlaces(location, radius)
            else
                repo.getNearbyPlaces()
            if (result.isSuccessful) {
                result.body()?.let {
                    it.results.forEach { place ->
                        tempLocation.add(place)
                    }
                }
                _locations.postValue(tempLocation)
            }
        }
    }

    fun getPolyLinePoints(originId: String, destinationId: String, waypoints: String) {
        viewModelScope.launch(Dispatchers.IO) {
            var decodePath = mutableListOf<LatLng>()
            decodePath.clear()
            val result = repo.getComplexRoute(originId, destinationId, waypoints)
            if (result.isSuccessful) {
                result.body()?.let {
                    val polylinePoints = it.routes[0].overviewPolyline.points
                    decodePath = PolyUtil.decode(polylinePoints)

                    _decodePathPolylinePoints.postValue(decodePath)
                }
            }
        }
    }
}