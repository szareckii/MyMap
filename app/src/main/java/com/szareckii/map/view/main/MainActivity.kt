package com.szareckii.map.view.main

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Criteria
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import androidx.core.app.ActivityCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.szareckii.map.R
import com.szareckii.map.model.data.AppState
import com.szareckii.map.view.base.BaseActivity
import com.szareckii.map.view.favorites.FavoritesActivity
import org.koin.android.viewmodel.ext.android.viewModel


class MainActivity : BaseActivity<AppState>(), OnMapReadyCallback  {

    companion object {
        private val PERMISSION_REQUEST_CODE = 10
    }

    private var textLatitude: EditText? = null
    private var textLongitude: EditText? = null

    override val model: MainViewModel by viewModel()

    private lateinit var mMap: GoogleMap
    private var currentMarker: Marker? = null
    private var permissionDenied = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        initViews();
        requestPemissions();

    }

    // Инициализация Views
    private fun initViews() {
//        textLatitude = findViewById(R.id.editLat)
//        textLongitude = findViewById(R.id.editLng)
    }

    // Запрашиваем Permission’ы
    private fun requestPemissions() {
        // Проверим, есть ли Permission’ы, и если их нет, запрашиваем их у
        // пользователя
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
            || ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
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
        // Получаем менеджер геолокаций
        val locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        val criteria = Criteria()
        criteria.accuracy = Criteria.ACCURACY_COARSE

        // Получаем наиболее подходящий провайдер геолокации по критериям.
        // Но определить, какой провайдер использовать, можно и самостоятельно.
        // В основном используются LocationManager.GPS_PROVIDER или
        // LocationManager.NETWORK_PROVIDER, но можно использовать и
        // LocationManager.PASSIVE_PROVIDER - для получения координат в
        // пассивном режиме
        val provider = locationManager.getBestProvider(criteria, true)
        if (provider != null) {
            // Будем получать геоположение через каждые 10 секунд или каждые
            // 10 метров
            locationManager.requestLocationUpdates(provider, 10000, 10f, object : LocationListener {
                override fun onLocationChanged(location: Location) {
                    val lat: Double = location.getLatitude() // Широта
                    val latitude = java.lang.Double.toString(lat)
//                    textLatitude!!.setText(latitude)
                    val lng: Double = location.getLongitude() // Долгота
                    val longitude = java.lang.Double.toString(lng)
//                    textLongitude!!.setText(longitude)
                    val accuracy = java.lang.Float.toString(location.getAccuracy()) // Точность
                    val currentPosition = LatLng(lat, lng)
                    currentMarker?.setPosition(currentPosition)
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentPosition, 12.toFloat()))
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
            R.id.menu_addToFavorite -> {
                startActivity(Intent(this, FavoritesActivity::class.java))
                true
            }
            R.id.menu_favorites -> {
                startActivity(Intent(this, FavoritesActivity::class.java))
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
        mMap = googleMap;

        val sydney: LatLng = LatLng(-34.0, 151.0);
        currentMarker = mMap.addMarker( MarkerOptions().position(sydney).title("Текущая позиция"))
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
//
//        mMap = googleMap ?: return
//        googleMap.setOnMyLocationButtonClickListener(this)
//        googleMap.setOnMyLocationClickListener(this)
//        enableMyLocation()

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == PERMISSION_REQUEST_CODE) {   // Запрошенный нами
            // Permission
            if (grantResults.size == 2 &&
                (grantResults[0] == PackageManager.PERMISSION_GRANTED || grantResults[1] == PackageManager.PERMISSION_GRANTED)
            ) {
                // Все препоны пройдены и пермиссия дана
                // Запросим координаты
                requestLocation()
            }
        }
    }

}
