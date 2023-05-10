package com.mouse.mapboxtest

import android.content.pm.Capability
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import com.mouse.mapboxtest.ui.theme.MapboxTestTheme

import com.mapbox.geojson.Point
import com.mapbox.maps.extension.style.expressions.dsl.generated.get
import com.mapbox.maps.extension.style.expressions.dsl.generated.pow
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import java.lang.Exception

private const val LATITUDE = 60.239
private const val LONGITUDE = 25.005
private const val TAG = "MainActivity"

@ExperimentalMaterial3Api
class MainActivity : ComponentActivity() {
    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        println("CoroutineExceptionHandler got $exception")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MapboxTestTheme {
                // A surface container using the 'background' color from the theme
                Column() {
                    Example()
                    MapBox()
                }
            }
        }
    }
}

@Composable
fun Example() {
    var text by remember {
        mutableStateOf(0)
    }
    Text(text.toString(), modifier = Modifier.clickable {
        text = (1..10).random()
    })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapBox() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {

        var mapController: MapboxMapController? by remember {
            mutableStateOf(null)
        }

        Scaffold(
            floatingActionButton = {
                Column {
                    SmallFloatingActionButton(
                        onClick = {
                            mapController?.toggleSatelliteMode()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Refresh,
                            contentDescription = ""
                        )
                    }
                    SmallFloatingActionButton(
                        onClick = {
                            mapController?.animateCameraPosition(
                                cameraPosition = CameraPosition(
                                    center = Point.fromLngLat(
                                        120.64650820978957,
                                        24.18378728095853
                                    ),
                                    zoom = 30.0,
                                )
                            )
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Place,
                            contentDescription = ""
                        )
                    }
                }
            }
        ) {
            MapboxMap(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
                initialCameraPosition = CameraPosition(
                    center = Point.fromLngLat(120.64650820978957, 24.18378728095853),
                    zoom = 15.0,
                ),
                onMapCreated = { controller ->

                    mapController = controller
                    controller.addOnClickListeners(
                        onMapClickListener = { point ->
                            Log.d(TAG, "onMapClicked: $point")
                        },
                        onFeatureClickListener = { feature ->
                            Log.d(TAG, "onFeatureClicked: $feature")
                        }
                    )

                    controller.onStyleLoadedCallbacks.add {
                        controller.addGeoJsonSource(
                            sourceId = "sample_geojson",
                            layerId = "sample_layer",
                            circleLayer = {
                                circleColor("blue")
                                circleRadius(10.0)
                                circleStrokeWidth(2.0)
                                circleStrokeColor("#fff")
                            },
                            symbolLayer = {
                                textField(get {
                                    literal("point_count_abbreviated")
                                    textColor("#fff")
                                    textSize(10.0)
                                })
                            }
                        ) {
                            url("https://d2ad6b4ur7yvpq.cloudfront.net/naturalearth-3.3.0/ne_50m_populated_places_simple.geojson")
                            cluster(true)
                            clusterRadius(50)
                            clusterMaxZoom(14)
                        }
                    }
                },
            )
        }
    }
}

//@Composable
//private fun MapboxMap() {
//    val mapView = mapView()
//    AndroidView(
//        factory = { mapView },
//        modifier = Modifier.fillMaxSize()
//    ) { mapView ->
//        mapView.getMapboxMap()
//            .apply {
//                loadStyleUri(Style.MAPBOX_STREETS)
//                setCamera(
//                    CameraOptions.Builder()
//                        .center(Point.fromLngLat(LONGITUDE, LATITUDE))
//                        .zoom(9.0)
//                        .build()
//                )
//            }
//    }
//}
//
//@Composable
//private fun mapView(): MapView {
//    val context = LocalContext.current
//    return MapView(context)
//}
//
//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    MapboxTestTheme {
//        MapboxMap()
//    }
//}