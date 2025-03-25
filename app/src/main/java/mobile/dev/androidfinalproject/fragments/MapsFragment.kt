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

    // Location permission request
    private val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val fineLocationGranted = permissions[Manifest.permission.ACCESS_FINE_LOCATION] ?: false
        val coarseLocationGranted = permissions[Manifest.permission.ACCESS_COARSE_LOCATION] ?: false

        if (fineLocationGranted || coarseLocationGranted) {
            fetchCurrentLocation()
        } else {
            Toast.makeText(requireContext(), "Location permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    // Callback for when the map is ready
    private val callback = OnMapReadyCallback { googleMap ->
        this.googleMap = googleMap

        // Enable zoom controls
        googleMap.uiSettings.isZoomControlsEnabled = true

        // Check for location permissions
        if (hasLocationPermission()) {
            fetchCurrentLocation()
        } else {
            requestLocationPermission()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize Volley request queue
        requestQueue = Volley.newRequestQueue(requireContext())

        // Initialize the map
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    // Fetch the user's current location
    @SuppressLint("MissingPermission")
    private fun fetchCurrentLocation() {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        fusedLocationClient.lastLocation.addOnSuccessListener { result ->
            if (result != null) {
                location = LatLng(result.latitude, result.longitude)
                googleMap.addMarker(MarkerOptions().position(location).title("Your Location"))
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 12.0f))
                fetchLocations()
            } else {
                Toast.makeText(requireContext(), "Unable to fetch location", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener { exception ->
            Log.e("log1", "Error fetching location: ${exception.message}")
            Toast.makeText(requireContext(), "Error fetching location", Toast.LENGTH_SHORT).show()
        }
    }

    // Fetch nearby fitness locations using the Yelp API
    private fun fetchLocations() {
        val radius = 1500
        val apiKey = mobile.dev.androidfinalproject.BuildConfig.YELP_API_KEY
        val url = "https://api.yelp.com/v3/businesses/search?term=fitness&latitude=${location.latitude}&longitude=${location.longitude}&radius=${radius}"

        val jsonObjectRequest = object : JsonObjectRequest(
            Method.GET, url, null,
            { response -> addMarks(response) },
            { error ->
                Log.e("log1", "Error fetching data: ${error.message}")
                Toast.makeText(requireContext(), "Error fetching data", Toast.LENGTH_SHORT).show()
            }
        ) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = mutableMapOf<String, String>()
                headers["Authorization"] = "Bearer $apiKey"
                return headers
            }
        }

        requestQueue?.add(jsonObjectRequest)
    }

    // Add markers for nearby fitness locations
    private fun addMarks(response: JSONObject?) {
        if (response == null) {
            Log.i("log1", "addMarks: No response from API")
            return
        }

        try {
            val businesses: JSONArray = response.getJSONArray("businesses")
            if (businesses.length() == 0) {
                Log.i("log1", "addMarks: No results found")
                Toast.makeText(requireContext(), "No businesses found nearby", Toast.LENGTH_SHORT).show()
            } else {
                for (i in 0 until businesses.length()) {
                    val business = businesses.getJSONObject(i)
                    val name = business.getString("name")
                    val location = business.getJSONObject("location")
                    val address = location.getString("address1")
                    val coordinates = business.getJSONObject("coordinates")
                    val rating = business.getString("rating")
                    val lat = coordinates.getDouble("latitude")
                    val lng = coordinates.getDouble("longitude")

                    val latLng = LatLng(lat, lng)
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
            Toast.makeText(requireContext(), "Error parsing API response", Toast.LENGTH_SHORT).show()
        }
    }

    // Check if location permissions are granted
    private fun hasLocationPermission(): Boolean {
        return requireContext().checkSelfPermission(
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED || requireContext().checkSelfPermission(
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    // Request location permissions
    private fun requestLocationPermission() {
        locationPermissionRequest.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        requestQueue?.cancelAll { true }
    }
}