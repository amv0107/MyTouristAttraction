package com.amv0107.mytouristattraction.prasentation.locationList

import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.amv0107.mytouristattraction.R
import com.amv0107.mytouristattraction.databinding.LocationListFragmentBinding
import com.amv0107.mytouristattraction.prasentation.SharedViewModel
import com.amv0107.mytouristattraction.prasentation.map.MapFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LocationListFragment : Fragment() {

//    var cityName = ""

    private var _binding: LocationListFragmentBinding? = null
    private val binding: LocationListFragmentBinding
        get() = _binding ?: throw RuntimeException("FragmentLocationList == null")

    private lateinit var viewModel: SharedViewModel
    private var myAdapter: RecyclerViewAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = LocationListFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]
        viewModel.localCity.observe(viewLifecycleOwner){
            binding.edCity.setText(it)
            Log.d("MyLog", "???? -> $it")
        }

        binding.btnFindAttraction.setOnClickListener {
            val geoCoder = Geocoder(requireContext())
            val city = geoCoder.getFromLocationName(binding.edCity.text.toString(), 1)
            val latLngCity = "${city?.first()?.latitude},${city?.first()?.longitude}"
            val radius = binding.edRadius.text.toString()
            viewModel.getAllLocations(latLngCity, radius)
        }

        binding.showMap.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .add(R.id.container, MapFragment())
                .addToBackStack("attraction_list_fragment")
                .commit()
        }

        viewModel.locations.observe(viewLifecycleOwner) { location ->
            myAdapter = RecyclerViewAdapter(location)
            binding.list.adapter = myAdapter
            binding.list.layoutManager = LinearLayoutManager(requireContext())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}