package com.amv0107.mytouristattraction.data

import com.google.gson.annotations.SerializedName

data class DirectionsResponse(val routes:List<Routes>)
data class Routes(@SerializedName("overview_polyline" ) val overviewPolyline: OverviewPolyline)
data class OverviewPolyline(val points:String)

data class PlacesResponse(val results:List<Places>)
data class Places(val geometry: Geometry, val photos:List<Photos>, val name:String)
data class Geometry(val location: Location)
data class Location(val lat:Double, val lng:Double)
data class Photos (@SerializedName("photo_reference") val photoReference:String? = null)
