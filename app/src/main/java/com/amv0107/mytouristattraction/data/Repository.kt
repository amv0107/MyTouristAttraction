package com.amv0107.mytouristattraction.data

import retrofit2.Response

class Repository(client: RetrofitClient) {

    private val apiInterface = client.retrofitClient.create(ApiInterface::class.java)

    suspend fun getNearbyPlaces(
        location: String = "",
        radius: String ="",
    ): Response<PlacesResponse> {
        return if (location.isNotEmpty() || radius.isNotEmpty()) {
            apiInterface.getNearbyPlaces(location, radius)
        } else {
            apiInterface.getNearbyPlaces()
        }
    }

    suspend fun getComplexRoute(
        originId: String,
        destinationId: String,
        waypoints: String
    ): Response<DirectionsResponse> = apiInterface.getComplexRoute(originId, destinationId, waypoints)

}