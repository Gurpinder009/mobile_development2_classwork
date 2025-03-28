package mobile.dev.androidfinalproject.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import mobile.dev.androidfinalproject.R
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class MapsFragment : Fragment() {

    private lateinit var googleMap: GoogleMap
    private lateinit var location: LatLng
    private var requestQueue: RequestQueue? = null

    companion object {
        private const val TAG = "MapsFragment"
        private const val RADIUS = 1500
        private const val ZOOM_LEVEL = 12.0f
    }

    private val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        if (permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
            permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true) {
            fetchCurrentLocation()
        } else {
            showToast("Location permission denied")
        }
    }

    private val callback = OnMapReadyCallback { googleMap ->
        this.googleMap = googleMap
        googleMap.uiSettings.isZoomControlsEnabled = true
        if (hasLocationPermission()) {
            fetchCurrentLocation()
        } else {
            requestLocationPermission()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requestQueue = Volley.newRequestQueue(requireContext())
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    @SuppressLint("MissingPermission")
    private fun fetchCurrentLocation() {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        fusedLocationClient.lastLocation.addOnSuccessListener { result ->
            if (result != null) {
                location = LatLng(result.latitude, result.longitude)
                googleMap.addMarker(MarkerOptions().position(location).title("Your Location"))
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, ZOOM_LEVEL))
                fetchLocations()
            } else {
                showToast("Unable to fetch location")
            }
        }.addOnFailureListener { exception ->
            logError("Error fetching location: ${exception.message}")
            showToast("Error fetching location")
        }
    }

    private fun fetchLocations() {
        val apiKey = mobile.dev.androidfinalproject.BuildConfig.YELP_API_KEY
        val url = "https://api.yelp.com/v3/businesses/search?term=fitness&latitude=${location.latitude}&longitude=${location.longitude}&radius=$RADIUS"

        val jsonObjectRequest = object : JsonObjectRequest(
            Method.GET, url, null,
            { response -> addMarkers(response) },
            { error ->
                logError("Error fetching data: ${error.message}")
                showToast("Error fetching data")
            }
        ) {
            override fun getHeaders(): MutableMap<String, String> {
                return mutableMapOf("Authorization" to "Bearer $apiKey")
            }
        }

        requestQueue?.add(jsonObjectRequest)
    }

    private fun addMarkers(response: JSONObject?) {
        if (response == null) {
            logInfo("No response from API")
            return
        }

        try {
            val businesses: JSONArray = response.getJSONArray("businesses")
            if (businesses.length() == 0) {
                logInfo("No results found")
                showToast("No businesses found nearby")
            } else {
                for (i in 0 until businesses.length()) {
                    val business = businesses.getJSONObject(i)
                    val name = business.getString("name")
                    val address = business.getJSONObject("location").getString("address1")
                    val coordinates = business.getJSONObject("coordinates")
                    val rating = business.getString("rating")
                    val latLng = LatLng(coordinates.getDouble("latitude"), coordinates.getDouble("longitude"))

                    googleMap.addMarker(
                        MarkerOptions()
                            .position(latLng)
                            .title(name)
                            .snippet("Address: $address\nRating: $rating ⭐")
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN))
                    )
                }
            }
        } catch (e: JSONException) {
            e.printStackTrace()
            showToast("Error parsing API response")
        }
    }

    private fun hasLocationPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(), Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
            requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestLocationPermission() {
        locationPermissionRequest.launch(
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
        )
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun logError(message: String) {
        Log.e(TAG, message)
    }

    private fun logInfo(message: String) {
        Log.i(TAG, message)
    }

    override fun onDestroy() {
        super.onDestroy()
        requestQueue?.cancelAll { true }
    }
}