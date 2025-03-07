package mobile.dev.androidfinalproject.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.android.volley.BuildConfig
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.VolleyError
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

class MapsFragment() : Fragment() {

    private lateinit var googleMap: GoogleMap
    private lateinit var location: LatLng
    private lateinit var requestQueue:RequestQueue

    @SuppressLint("MissingPermission")
    private val callback = OnMapReadyCallback { googleMap ->
        this.googleMap = googleMap

        location = LatLng(238.217174, -122.532560)

        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        fusedLocationClient.lastLocation.addOnSuccessListener{ result -> run{
            Log.i("location", result.toString())
            location = LatLng(result.latitude,result.longitude)
            googleMap.addMarker(MarkerOptions().position(location).title("your location"))
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 12.0f))
            fetchLocations()
        }}
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
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    private fun fetchLocations() {
        val radius = 1500

        // TODO save it in local properties
        val apiKey = "Q3h0fGEz-KQh4G2T0QhOqyeFpIry9xS4Nzx601bKNQkMaWWgfFmZG7DoryEonSQGH3P2DzryHGh0JzMvEVVhlM_ItZ-g89zbGvUhnQ_mdTM71u4ug8di_x6hv-XIZ3Yx"
        val url = "https://api.yelp.com/v3/businesses/search?term=fitness&latitude=${location.latitude}&longitude=${location.longitude}&radius=${radius}"

        requestQueue = Volley.newRequestQueue(context)
        val jsonObjectRequest = object : JsonObjectRequest(
            Method.GET, url, null,
            { response-> addMarks(response) },
            { error-> Log.e("log1", "Error fetching data: ${error.message}") }
        ) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = mutableMapOf<String, String>()
                headers["Authorization"] = "Bearer $apiKey"
                return headers
            }
        }

        requestQueue.add(jsonObjectRequest)
    }

    private fun addMarks(response: JSONObject?) {
        try {
            val businesses: JSONArray = response?.getJSONArray("businesses") ?: JSONArray()
            if (businesses.length() == 0) {
                Log.i("log1", "addMarks: No results found")
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
                    googleMap.addMarker(MarkerOptions().position(latLng).title(name).snippet("Address: $address\nRating: $rating ⭐").icon( BitmapDescriptorFactory.defaultMarker(
                        BitmapDescriptorFactory.HUE_CYAN)))
                }
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        requestQueue.cancelAll(null);
    }
}



