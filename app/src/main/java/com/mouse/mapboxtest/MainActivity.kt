package com.mouse.mapboxtest

import android.Manifest
import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.graphics.drawable.toBitmap
import com.mapbox.android.gestures.MoveGestureDetector
import com.mapbox.geojson.Feature
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.Style
import com.mapbox.maps.extension.style.expressions.dsl.generated.get
import com.mapbox.maps.extension.style.expressions.dsl.generated.interpolate
import com.mapbox.maps.extension.style.layers.addLayer
import com.mapbox.maps.extension.style.layers.generated.LineLayer
import com.mapbox.maps.extension.style.layers.generated.SymbolLayer
import com.mapbox.maps.extension.style.layers.generated.lineLayer
import com.mapbox.maps.extension.style.layers.properties.generated.LineCap
import com.mapbox.maps.extension.style.layers.properties.generated.LineJoin
import com.mapbox.maps.extension.style.sources.addSource
import com.mapbox.maps.extension.style.sources.generated.GeoJsonSource
import com.mapbox.maps.extension.style.sources.generated.geoJsonSource
import com.mapbox.maps.plugin.LocationPuck2D
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.CircleAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.PolygonAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.PolylineAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createCircleAnnotationManager
import com.mapbox.maps.plugin.annotation.generated.createPointAnnotationManager
import com.mapbox.maps.plugin.annotation.generated.createPolygonAnnotationManager
import com.mapbox.maps.plugin.annotation.generated.createPolylineAnnotationManager
import com.mapbox.maps.plugin.gestures.OnMoveListener
import com.mapbox.maps.plugin.gestures.addOnMapClickListener
import com.mapbox.maps.plugin.gestures.gestures
import com.mapbox.maps.plugin.locationcomponent.OnIndicatorBearingChangedListener
import com.mapbox.maps.plugin.locationcomponent.OnIndicatorPositionChangedListener
import com.mapbox.maps.plugin.locationcomponent.location
import com.mouse.mapboxtest.ui.theme.MapboxTestTheme
import com.mouse.mapboxtest.util.LocationPermissionHelper
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.lang.ref.WeakReference

private const val LATITUDE = -6.9249233
private const val LONGITUDE = 107.6345122
private const val TAG = "MainActivity"

@ExperimentalMaterial3Api
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MapboxTestTheme {
                // A surface container using the 'background' color from the theme
                Column() {
                    Example4()
//                    MapBox()
                }
            }
        }
    }
}

@Composable
fun Example5() {
    
}
//簡單的放圖標(內建圓形、自定義圖示)、線、區塊在地圖上
@Composable
fun Example4() {
    val context = LocalContext.current
    val mapView = MapView(context)
    LaunchedEffect(Unit){
        mapView.getMapboxMap().setCamera(
            CameraOptions.Builder()
                .center(Point.fromLngLat(LONGITUDE, LATITUDE))
                .zoom(14.0)
                .build()
        )
    }
    AndroidView(
        factory = { mapView },
        modifier = Modifier.fillMaxSize()
    ) { mapView ->

        mapView.getMapboxMap().loadStyleUri(
            Style.MAPBOX_STREETS
        ){
//            addAnnotationToMap(context, mapView)//放一個自定義圖示，做得更仔細
            //放一個自定義圖示的重點程式
//            val annotationApi = mapView?.annotations
//            val pointAnnotationManager = annotationApi?.createPointAnnotationManager(mapView)
//// Set options for the resulting symbol layer.
//            val pointAnnotationOptions: PointAnnotationOptions = PointAnnotationOptions()
//                // Define a geographic coordinate.
//                .withPoint(Point.fromLngLat(LONGITUDE, LATITUDE))
//                // Specify the bitmap you assigned to the point annotation
//                // The bitmap will be added to map style automatically.
//                .withIconImage(AppCompatResources.getDrawable(context, R.drawable.android_robot)!!.toBitmap())
//// Add the resulting pointAnnotation to the map.
//            pointAnnotationManager?.create(pointAnnotationOptions)

            //放一個圓形圖示
//            val annotationApi = mapView?.annotations
//            val circleAnnotationManager = annotationApi?.createCircleAnnotationManager(mapView)
//// Set options for the resulting circle layer.
//            val circleAnnotationOptions: CircleAnnotationOptions = CircleAnnotationOptions()
//                // Define a geographic coordinate.
//                .withPoint(Point.fromLngLat(LONGITUDE, LATITUDE))
//                // Style the circle that will be added to the map.
//                .withCircleRadius(8.0)
//                .withCircleColor("#ee4e8b")
//                .withCircleStrokeWidth(2.0)
//                .withCircleStrokeColor("#ffffff")
//// Add the resulting circle to the map.
//            circleAnnotationManager?.create(circleAnnotationOptions)

            //畫一條線在地圖上
            // Create an instance of the Annotation API and get the polyline manager.

//            val annotationApi = mapView?.annotations
//            val polylineAnnotationManager = annotationApi?.createPolylineAnnotationManager(mapView)
//// Define a list of geographic coordinates to be connected.
//            val points = listOf(
//                Point.fromLngLat(LONGITUDE, LATITUDE),
//                Point.fromLngLat(LONGITUDE+0.01, LATITUDE+0.01)
//            )
//// Set options for the resulting line layer.
//            val polylineAnnotationOptions: PolylineAnnotationOptions = PolylineAnnotationOptions()
//                .withPoints(points)
//                // Style the line that will be added to the map.
//                .withLineColor("#ee4e8b")
//                .withLineWidth(10.0)
//// Add the resulting line to the map.
//            polylineAnnotationManager?.create(polylineAnnotationOptions)

            //畫一個區塊
            // Create an instance of the Annotation API and get the polygon manager.
            val annotationApi = mapView?.annotations
            val polygonAnnotationManager = annotationApi?.createPolygonAnnotationManager(mapView)
// Define a list of geographic coordinates to be connected.
            val points = listOf(
                listOf(
                    Point.fromLngLat(LONGITUDE, LATITUDE+1),
                    Point.fromLngLat(LONGITUDE, LATITUDE),

                    Point.fromLngLat(LONGITUDE+1, LATITUDE),

                    Point.fromLngLat(LONGITUDE+1, LATITUDE+1)
                )
            )
// Set options for the resulting fill layer.
            val polygonAnnotationOptions: PolygonAnnotationOptions = PolygonAnnotationOptions()
                .withPoints(points)
                // Style the polygon that will be added to the map.
                .withFillColor("#ee4e8b")
                .withFillOpacity(0.4)
// Add the resulting polygon to the map.
            polygonAnnotationManager?.create(polygonAnnotationOptions)
        }

    }
}

//打開定位權限並且在當前位置上放上水波紋圖示
@Composable
fun Example3() {
    val context = LocalContext.current
    val mapView = MapView(context)
    val locationPermissionHelper = LocationPermissionHelper(WeakReference(context as Activity))
    val onIndicatorBearingChangedListener = OnIndicatorBearingChangedListener {
//        println("####OnIndicatorBearingChangedListener")
        mapView.getMapboxMap().setCamera(CameraOptions.Builder().bearing(it).build())
    }

    val onIndicatorPositionChangedListener = OnIndicatorPositionChangedListener {
//        println("####OnIndicatorPositionChangedListener")
        mapView.getMapboxMap().setCamera(CameraOptions.Builder().center(it).build())
        mapView.gestures.focalPoint = mapView.getMapboxMap().pixelForCoordinate(it)
    }

    val onMoveListener = object : OnMoveListener {
        //移動地圖開始
        override fun onMoveBegin(detector: MoveGestureDetector) {
            println("####OnMoveListener.onMoveBegin")

            Toast.makeText(context, "####onCameraTrackingDismissed", Toast.LENGTH_SHORT).show()
            mapView.location
                .removeOnIndicatorPositionChangedListener(onIndicatorPositionChangedListener)
            mapView.location
                .removeOnIndicatorBearingChangedListener(onIndicatorBearingChangedListener)
//            mapView.gestures.removeOnMoveListener(this)
        }

        //移動地圖中
        override fun onMove(detector: MoveGestureDetector): Boolean {
            println("####OnMoveListener.onMove")

            return false
        }

        //移動地圖結束
        override fun onMoveEnd(detector: MoveGestureDetector) {
            println("####OnMoveListener.onMoveEnd")

        }
    }
    var hasPermission by remember {
        mutableStateOf(false)
    }

    val permissionRequester = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
    ) { isGranted: Boolean ->
        println("####rememberLauncherForActivityResult.isGranted=$isGranted")
        hasPermission = isGranted
    }
    LaunchedEffect(key1 = hasPermission, block = {
        if (!hasPermission) {
            println("####LaunchedEffect.if")
            permissionRequester.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
        } else {
            println("####LaunchedEffect.else")

            mapView.getMapboxMap().setCamera(
                CameraOptions.Builder()
                    .zoom(14.0)
                    .build()
            )
            mapView.getMapboxMap().loadStyleUri(
                Style.MAPBOX_STREETS
            ) {
                //initLocationComponent()
                val locationComponentPlugin = mapView.location//取的當前座標
                locationComponentPlugin.updateSettings {
                    this.enabled = true//沒給就不能定位現在的位置
                    pulsingEnabled = true//會一直出現水波紋動畫
                    //要不要使用自定義圖標，否則就是預設的藍色白框圓點
                    this.locationPuck = LocationPuck2D(
                        //主要圖示
                        bearingImage = AppCompatResources.getDrawable(
                            context,
                            R.drawable.baseline_place_24,
                        ),
                        //底圖陰影圖示
                        shadowImage = AppCompatResources.getDrawable(
                            context,
                            R.drawable.baseline_place_24,
                        ),
                        //不知道用途
                        scaleExpression = interpolate {
                            linear()
                            zoom()
                            stop {
                                literal(0.0)
                                literal(0.6)
                            }
                            stop {
                                literal(20.0)
                                literal(1.0)
                            }
                        }.toJson()
                    )
                }
                //監聽位置變化
                locationComponentPlugin.addOnIndicatorPositionChangedListener(
                    onIndicatorPositionChangedListener
                )
                //監聽方位改變
                locationComponentPlugin.addOnIndicatorBearingChangedListener(
                    onIndicatorBearingChangedListener
                )
                //setupGesturesListener()
                //監聽地圖手勢移動事件
                mapView.gestures.addOnMoveListener(onMoveListener)
            }
        }
//        locationPermissionHelper.checkPermissions {
//
//        }
    })

    if (hasPermission) {
        AndroidView(
            factory = { mapView },
            modifier = Modifier.fillMaxSize()
        ) { mapView ->
            mapView.getMapboxMap().loadStyleUri(
                Style.MAPBOX_STREETS,
                // After the style is loaded, initialize the Location component.
                object : Style.OnStyleLoaded {
                    override fun onStyleLoaded(style: Style) {
                        println("####onStyleLoaded")

                        mapView.location.updateSettings {
                            enabled = true
                            pulsingEnabled = true
                        }
                    }
                }
            )
        }
    } else {
        Text("無權限")
    }
}

//在地圖上可以動態點選目標位置，並劃出路線
@Composable
fun Example2() {
    val context = LocalContext.current
    val mapView = MapView(context)
    val points by remember {
        mutableStateOf(
            mutableStateListOf(
                Point.fromLngLat(107.6048254, -6.9218571),
                Point.fromLngLat(
                    108.6048254, -7.9218571
                )
            )
        )
    }
    AndroidView(
        factory = { mapView },
        modifier = Modifier.fillMaxSize()
    ) { mapView ->

        mapView.getMapboxMap()
            .apply {
                setCamera(
                    CameraOptions.Builder()
                        .center(Point.fromLngLat(LONGITUDE, LATITUDE))
                        .zoom(10.0)
                        .build()
                )
                addOnMapClickListener {
                    println("地圖被點擊了")
                    points.add(it)
                    true
                }
//                addOnMapLoadedListener {
                println("####地圖顯示了")
                loadStyleUri(Style.MAPBOX_STREETS) {
//                        mapView.getMapboxMap().getStyle()?.let {
                    println("####style有東西")
                    it.addImage(
                        "icon_drawble_id",
                        context.getDrawable(R.drawable.baseline_place_24)!!.toBitmap()
                    )
                    it.addLayer(
                        SymbolLayer(
                            "icon_layer_id",
                            "icon_source_id"
                        ).iconImage("icon_drawble_id")
                            .iconIgnorePlacement(true)
                            .iconAllowOverlap(true)
                            .iconSize(1.0)
                    )

                    val lineLayer = LineLayer("route_layer_id", "route source_id")
                    lineLayer.lineCap(LineCap.ROUND).lineJoin(LineJoin.ROUND).lineWidth(5.0)
                    it.addLayer(lineLayer)
                    val destination = Point.fromLngLat(107.6048254, -6.9218571)
                    val geoJsonSource = GeoJsonSource.Builder("icon_source_id")
                        .build().feature(
                            Feature.fromGeometry(
                                Point.fromLngLat(
                                    LONGITUDE,
                                    LATITUDE
                                )
                            )
                        )
                    it.addSource(geoJsonSource)
                    geoJsonSource("tt") {
                        url("asset://from_crema_to_council_crest.geojson")
                    }
                    lineLayer("linelayer", "tt") {
                        lineCap(LineCap.ROUND)
                        lineJoin(LineJoin.ROUND)
                        lineOpacity(0.7)
                        lineWidth(8.0)
                        lineColor("#888")
                    }
                    val annotationApi = mapView?.annotations
                    val polylineAnnotationManager =
                        annotationApi!!.createPolylineAnnotationManager(mapView)
// Define a list of geographic coordinates to be connected.

// Set options for the resulting line layer.
                    val polylineAnnotationOptions: PolylineAnnotationOptions =
                        PolylineAnnotationOptions()
                            .withPoints(points)
                            // Style the line that will be added to the map.
                            .withLineColor("#ee4e8b")
                            .withLineWidth(5.0)
// Add the resulting line to the map.
                    polylineAnnotationManager?.create(polylineAnnotationOptions)

//                    }

//                }

//

                }
            }
    }
}
//fun getRoute(mapBoxMap:MapboxMap,origin:Point,destination:Point){
//    val d=DirectionR
//}

//在地圖上標記一個紅色位置圖示
@Composable
fun Example1() {
    val context = LocalContext.current
    val mapView = MapView(context)

    AndroidView(
        factory = { mapView },
        modifier = Modifier.fillMaxSize()
    ) { mapView ->

        mapView.getMapboxMap()
            .apply {
                setCamera(
                    CameraOptions.Builder()
                        .center(Point.fromLngLat(LONGITUDE, LATITUDE))
                        .zoom(10.0)
                        .build()
                )
//                addOnMapLoadedListener {
                println("####地圖顯示了")
                loadStyleUri(Style.MAPBOX_STREETS) {
//                        mapView.getMapboxMap().getStyle()?.let {
                    println("####style有東西")
                    it.addImage(
                        "icon_drawble_id",
                        context.getDrawable(R.drawable.baseline_place_24)!!.toBitmap()
                    )
                    it.addLayer(
                        SymbolLayer(
                            "icon_layer_id",
                            "icon_source_id"
                        ).iconImage("icon_drawble_id")
                            .iconIgnorePlacement(true)
                            .iconAllowOverlap(true)
                            .iconSize(1.0)
                    )
                    val geoJsonSource = GeoJsonSource.Builder("icon_source_id").feature(
                        Feature.fromGeometry(
                            Point.fromLngLat(
                                LONGITUDE,
                                LATITUDE
                            )
                        )
                    ).build()
                    it.addSource(geoJsonSource)
                }

//                    }

//                }

//

            }
    }
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
//                            url("https://d2ad6b4ur7yvpq.cloudfront.net/naturalearth-3.3.0/ne_50m_populated_places_simple.geojson")
//                            url("https://api.mapbox.com/directions/v5/mapbox/driving/-73.62139%2C40.37418%3B-73.724879%2C40.494653?alternatives=false&annotations=state_of_charge%2Cduration&geometries=polyline&language=en&overview=simplified&steps=true&engine=electric&ev_initial_charge=32000&ev_max_charge=40000&ev_connector_types=ccs_combo_type1%2Cccs_combo_type2%2Ctesla%2Cchademo&energy_consumption_curve=10%2C200%3B20%2C100%3B40%2C120%3B60%2C140%3B80%2C180%3B100%2C220&ev_charging_curve=8000%2C40000%3B16000%2C45000%3B24000%2C46000%3B28000%2C32000%3B32000%2C26000%3B36000%2C20000&ev_max_ac_charging_power=3600&ev_min_charge_at_destination=6000&ev_min_charge_at_charging_station=6000&auxiliary_consumption=750&access_token=pk.eyJ1IjoieHRuYWN0NTQxIiwiYSI6ImNsZ29ra3B3ZzBpcXIzZHBvdzBzeWlycnUifQ.b-Oiw-1QDgnTauYUMhUUww")
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

@Composable
private fun MapboxMap() {
    val mapView = mapView()
    AndroidView(
        factory = { mapView },
        modifier = Modifier.fillMaxSize()
    ) { mapView ->
        mapView.getMapboxMap()
            .apply {
                loadStyleUri(Style.MAPBOX_STREETS)
                setCamera(
                    CameraOptions.Builder()
                        .center(Point.fromLngLat(LONGITUDE, LATITUDE))
                        .zoom(9.0)
                        .build()
                )
            }
    }
}

@Composable
private fun mapView(): MapView {
    val context = LocalContext.current
    return MapView(context)
}
//
//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    MapboxTestTheme {
//        MapboxMap()
//    }
//}
private fun addAnnotationToMap(context: Context, mapView: MapView) {
// Create an instance of the Annotation API and get the PointAnnotationManager.
    bitmapFromDrawableRes(
        context,
        R.drawable.android_robot
    )?.let {
        val annotationApi = mapView?.annotations
        val pointAnnotationManager = annotationApi?.createPointAnnotationManager(mapView!!)
// Set options for the resulting symbol layer.
        val pointAnnotationOptions: PointAnnotationOptions = PointAnnotationOptions()
// Define a geographic coordinate.
            .withPoint(Point.fromLngLat(LONGITUDE, LATITUDE))
// Specify the bitmap you assigned to the point annotation
// The bitmap will be added to map style automatically.
            .withIconImage(it)
// Add the resulting pointAnnotation to the map.
        pointAnnotationManager?.create(pointAnnotationOptions)
    }
}

private fun bitmapFromDrawableRes(context: Context, @DrawableRes resourceId: Int) =
    convertDrawableToBitmap(AppCompatResources.getDrawable(context, resourceId))

private fun convertDrawableToBitmap(sourceDrawable: Drawable?): Bitmap? {
    if (sourceDrawable == null) {
        return null
    }
    return if (sourceDrawable is BitmapDrawable) {
        sourceDrawable.bitmap
    } else {
// copying drawable object to not manipulate on the same reference
        val constantState = sourceDrawable.constantState ?: return null
        val drawable = constantState.newDrawable().mutate()
        val bitmap: Bitmap = Bitmap.createBitmap(
            drawable.intrinsicWidth, drawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        bitmap
    }
}