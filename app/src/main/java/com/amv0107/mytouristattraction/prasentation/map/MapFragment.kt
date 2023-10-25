package com.amv0107.mytouristattraction.prasentation.map

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.amv0107.mytouristattraction.R
import com.amv0107.mytouristattraction.prasentation.SharedViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MapFragment : SupportMapFragment() {

    private lateinit var viewModel: SharedViewModel
    private lateinit var mapFragment: SupportMapFragment
    private var wayPointCoordinatesString: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]
        mapFragment = parentFragmentManager.findFragmentById(R.id.container) as SupportMapFragment
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapFragment.getMapAsync { googleMap ->
            val placeCoordinates = mutableListOf<String>()
            googleMap.clear()

            viewModel.locations.observe(viewLifecycleOwner) { location ->
                val coordinatesCity = LatLng(location[0].geometry.location.lat, location[0].geometry.location.lng)
                googleMap.addMarker(MarkerOptions().position(coordinatesCity))
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(coordinatesCity, 15F))

                placeCoordinates.clear()
                location.drop(0).take(10).forEach {
                    val coordinates = LatLng(it.geometry.location.lat, it.geometry.location.lng)
                    placeCoordinates.add("${coordinates.latitude},${coordinates.longitude}")
                    googleMap.addMarker(MarkerOptions().position(coordinates))
                }
                wayPointCoordinatesString = placeCoordinates.joinToString(separator = "|")
            }

            viewModel.getPolyLinePoints(placeCoordinates[0], placeCoordinates.last(), wayPointCoordinatesString)

            viewModel.decodePathPolylinePoints.observe(viewLifecycleOwner) {

                googleMap.addPolyline(PolylineOptions().color(Color.BLUE).width(12F).addAll(it))
            }
        }
    }
}