package com.szareckii.map.view.main

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.*
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.szareckii.map.R
import com.szareckii.map.model.data.AppState
import com.szareckii.map.model.data.DataModel
import com.szareckii.map.view.base.BaseActivity
import com.szareckii.map.view.marks.MarksActivity
import com.szareckii.map.view.marks.MarksViewModel
import kotlinx.android.synthetic.main.loading_layout.*
import org.koin.android.viewmodel.ext.android.viewModel

private const val LATITUDE_DEFAULT = 55.558
private const val LONGITUDE_DEFAULT = 37.378
private const val ZOOM_DEFAULT = 12f

class MainActivity : BaseActivity<AppState>(), OnMapReadyCallback,
    GoogleMap.OnMyLocationButtonClickListener,
    GoogleMap.OnMyLocationClickListener
{

    override val model: MarksViewModel by viewModel()

    private lateinit var mMap: GoogleMap
    private var currentMarker: Marker? = null
    private val markers = mutableListOf<Marker>()

    var requestPermissions = 0

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        requestPermissions()

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        model.subscribe().observe(this@MainActivity, { appState ->
            when (appState) {
                is AppState.Success -> {
                    val marks = appState.data
                    marks?.let {
                            for(index in marks.indices) {
                                var p = -1
                                for(j in markers) {
                                    if (marks[index].id == j.title?.toInt() ?: 0) {
                                        p = index
                                    }
                             }
                              if(p != -1) {
                                  markers.removeAt(index)
                                  break
                              }
                        }
                    }
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        model.getData()
    }

    // Запрашиваем Permission’ы
    private fun requestPermissions() {
        // Проверим, есть ли Permission’ы, и если их нет, запрашиваем их у
        // пользователя
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
            || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            // Запрашиваем координаты
            requestLocation()
        } else {
            // Permission’ов нет, запрашиваем их у пользователя
            requestLocationPermissions()
        }
    }

    // Запрашиваем координаты
    private fun requestLocation() {
        // Если Permission’а всё- таки нет, просто выходим: приложение не имеет
        // смысла
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) return

        if (requestPermissions == 1) {
            mMap.isMyLocationEnabled = true

            mMap.setOnMyLocationButtonClickListener(this)
            mMap.setOnMyLocationClickListener(this)

            var currentLatLng = LatLng(LATITUDE_DEFAULT, LONGITUDE_DEFAULT)
            fusedLocationClient.lastLocation.addOnSuccessListener(this) { location ->
                if (location != null) {
                    currentLatLng = LatLng(location.latitude, location.longitude)
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, ZOOM_DEFAULT))
                }
            }
            mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLatLng))

            mMap.setOnMapLongClickListener { latLng ->
                addMarker(latLng)
            }
        }
        // Получаем менеджер геолокаций
        val locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        val criteria = Criteria()
        criteria.accuracy = Criteria.ACCURACY_COARSE

        val provider = locationManager.getBestProvider(criteria, true)
        if (provider != null) {
            // Будем получать геоположение через каждые 10 секунд или каждые 10 метров
            locationManager.requestLocationUpdates(provider, 10000, 10f,
                object : LocationListener {
                    override fun onLocationChanged(location: Location) {
                        val lat: Double = location.latitude // Широта
                        val latitude = lat.toString()
                        val lng: Double = location.longitude // Долгота
                        val longitude = lng.toString()
                        val accuracy = location.accuracy.toString() // Точность
                        val currentPosition = LatLng(lat, lng)
                        currentMarker?.position = currentPosition
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentPosition, ZOOM_DEFAULT))
                    }

                    override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
                    override fun onProviderEnabled(provider: String) {}
                    override fun onProviderDisabled(provider: String) {}
            })
        }
    }

    // Запрашиваем Permission’ы для геолокации
    private fun requestLocationPermissions() {
        if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CALL_PHONE)) {
            // Запрашиваем эти два Permission’а у пользователя
            ActivityCompat.requestPermissions(
                this, arrayOf(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ),
                PERMISSION_REQUEST_CODE
            )
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
              R.id.menu_favorites -> {
                startActivity(Intent(this, MarksActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.history_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val moscow = LatLng(LATITUDE_DEFAULT, LONGITUDE_DEFAULT)
        currentMarker = mMap.addMarker( MarkerOptions().position(moscow).title(getString(R.string.current_position)))
        mMap.addMarker(MarkerOptions().position(moscow).title(getString(R.string.moskow_marker)))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(moscow))

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        if (requestPermissions == 0) {
            mMap.isMyLocationEnabled = true

            mMap.setOnMyLocationButtonClickListener(this)
            mMap.setOnMyLocationClickListener(this)

            var currentLatLng = LatLng(LATITUDE_DEFAULT, LONGITUDE_DEFAULT)
            fusedLocationClient.lastLocation.addOnSuccessListener(this) { location ->
                if (location != null) {
                    currentLatLng = LatLng(location.latitude, location.longitude)
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, ZOOM_DEFAULT))
                }
            }
            mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLatLng))

            mMap.setOnMapLongClickListener { latLng ->
                addMarker(latLng)
            }
        }
    }

    // Добавляем метки на карту
    private fun addMarker(location: LatLng) {
        val index = markers.size
        val title = markers.size.toString()
        val marker = mMap.addMarker(
            MarkerOptions()
                .position(location)
                .title(title)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker))
        )
        marker?.let {
            markers.add(it)
        }

        val lat: Double = location.latitude // Широта
        val lng: Double = location.longitude // Долгота

        model.saveData(index, title, lat, lng)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.size == 2 &&
                (grantResults[0] == PackageManager.PERMISSION_GRANTED || grantResults[1] == PackageManager.PERMISSION_GRANTED)
            ) {
                requestPermissions = 1
                requestLocation()
            }
        }
    }

    override fun onMyLocationClick(location: Location) {
        Toast.makeText(this, "Current location:\n$location", Toast.LENGTH_LONG)
            .show()
    }

    override fun onMyLocationButtonClick(): Boolean {
        Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT)
            .show()
        return false
    }

    companion object {
        private const val PERMISSION_REQUEST_CODE = 10
    }

    override fun setDataToAdapter(data: MutableList<DataModel>) {
    }

}
