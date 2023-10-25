package com.amv0107.mytouristattraction.data

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("/maps/api/place/nearbysearch/json?")
    suspend fun getNearbyPlaces(
        @Query("location") location: String = "48.6208,22.287883",
        @Query("radius") radius: String = "2000",
        @Query("type") type: String = "tourist_attractions",
        @Query("key") key: String = "AIzaSyDlmthMDpc7f1D2vOxJdWif_7Hfc86Ks24",
    ): Response<PlacesResponse>


    @GET("/maps/api/directions/json?")
    suspend fun getComplexRoute(
        @Query("origin") originId: String,
        @Query("destination") destinationId: String,
        @Query("waypoints") waypoints: String,
        @Query("key") key: String = "AIzaSyDlmthMDpc7f1D2vOxJdWif_7Hfc86Ks24"
    ): Response<DirectionsResponse>

}