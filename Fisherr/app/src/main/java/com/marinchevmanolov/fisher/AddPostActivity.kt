package com.marinchevmanolov.fisher

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.os.Looper
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.marinchevmanolov.fisher.model.Post
import java.util.*

class AddPostActivity : AppCompatActivity() {

        lateinit var fusedLocationProviderClient: FusedLocationProviderClient
        lateinit var locationRequest: LocationRequest
        lateinit var filepath : Uri
        lateinit var postTitle: String
        lateinit var postDescription: String
        lateinit var postDateTime: Date
        var longtitude: Double = 0.1
        var latitude: Double = 0.1
        var locationTxt : TextView? = null
        var imageUpload : ImageView? = null
            private var PERMISSION_ID = 1000

        override fun onCreate(savedInstanceState: Bundle?){
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_post);

            val addPostBtn = findViewById<Button>(R.id.AddPostBtn) as Button
            imageUpload = findViewById<ImageView>(R.id.imageUpload) as ImageView
            locationTxt = findViewById<View>(R.id.locationTxt) as TextView
            var editTextDescription = findViewById<TextInputEditText>(R.id.editTextDescription) as TextInputEditText
            var textViewTitle = findViewById<TextInputEditText>(R.id.textViewTitle) as TextInputEditText
            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
            locationTxt!!.setOnClickListener {
            getLastLocation()
            }
            imageUpload!!.setOnClickListener {
                startFileChooser()
            }
            addPostBtn.setOnClickListener {
                postTitle = textViewTitle.text.toString()
                postDescription = editTextDescription.text.toString()
                uploadFile()
                val intent = Intent(this, FeedActivity::class.java)
                startActivity(intent);
            }
        }

    private fun uploadFile() {
        val calendar = Calendar.getInstance()
        postDateTime = calendar.time;
        val coordinates = listOf<Double>(longtitude, latitude)
        val db = FirebaseFirestore.getInstance()
        if(postDescription != null && postTitle != null && longtitude != 0.1 && filepath != null){
            val images = listOf<String>(filepath.toString())
            val post = Post(postTitle, postDescription, postDateTime, coordinates, images)

            var pd = ProgressDialog(this)
            pd.setTitle("Uploading")
            pd.show()

            var imageRef = FirebaseStorage.getInstance().reference.child("posts/catch.jpg")
            imageRef.putFile(filepath)
                    .addOnSuccessListener {
                        pd.dismiss()
                        Toast.makeText(applicationContext, "Post uploaded", Toast.LENGTH_LONG).show()
                        System.out.println("SUCESSS!!!!")
                    }
                    .addOnFailureListener { p0->
                        pd.dismiss()
                        Toast.makeText(applicationContext, p0.message, Toast.LENGTH_LONG).show()
                        System.out.println("FAAAAIIIILLL!!!!")
                    }
                    .addOnProgressListener { p0 ->
                        var progress = (100 * p0.bytesTransferred) / p0.totalByteCount
                        pd.setMessage("Uploaded ${progress.toInt()}%")
                    }
            db.collection("posts")
                    .add(post)
                    .addOnSuccessListener {
                        Toast.makeText(this@AddPostActivity, "Post added successfully", Toast.LENGTH_LONG).show()
                    }
                    .addOnFailureListener {
                        Toast.makeText(this@AddPostActivity, "Post failed to upload", Toast.LENGTH_LONG).show()
                    }
        } else {
            Toast.makeText(this@AddPostActivity, "Please fill all fields and select a picture and coordinates", Toast.LENGTH_LONG).show()
        }
    }

    private fun startFileChooser() {
        var i = Intent()
        i.setType("image/*")
        i.setAction(Intent.ACTION_GET_CONTENT)
        startActivityForResult(Intent.createChooser(i, "Choose Picture"), 111)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode===111 && resultCode == Activity.RESULT_OK && data != null){
            filepath = data.data!!
            var bitmap = MediaStore.Images.Media.getBitmap(contentResolver, filepath)
            imageUpload?.setImageBitmap(bitmap)
        }
    }


    fun getLastLocation(){
        if(CheckPermission()){
            if(isLocationEnabled()){
                if (ActivityCompat.checkSelfPermission(
                        this,
                        android.Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        this,
                        ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return
                }
                fusedLocationProviderClient.lastLocation.addOnCompleteListener { task->
                    var location:Location? = task.result
                    if(location == null){
                        NewLocationData()
                    }else{
                        Log.d("Debug:" ,"Your Location:"+ location.longitude)
                        locationTxt?.text = "Long: "+ location.longitude + " , Lat: " + location.latitude
                        longtitude = location.longitude
                        latitude = location.latitude

                    }
                }
            }else{
                Toast.makeText(this,"Please Turn on Your device Location",Toast.LENGTH_SHORT).show()
            }
        }else{
            RequestPermission()
        }
    }


    fun NewLocationData(){
        var locationRequest =  LocationRequest()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 0
        locationRequest.fastestInterval = 0
        locationRequest.numUpdates = 1
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        fusedLocationProviderClient!!.requestLocationUpdates(
            locationRequest,locationCallback,Looper.myLooper()
        )
    }

    private val locationCallback = object : LocationCallback(){
        override fun onLocationResult(locationResult: LocationResult) {
            var lastLocation: Location = locationResult.lastLocation
            Log.d("Debug:","your last last location: "+ lastLocation.longitude.toString())
            locationTxt?.text = "You Last Location is : Long: "+ lastLocation.longitude + " , Lat: " + lastLocation.latitude + "\n" + getCityName(lastLocation.latitude,lastLocation.longitude)
        }
    }


        private fun CheckPermission(): Boolean{
            if(
                ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
            ){
                return true
            }

            return false
        }

        private fun RequestPermission(){
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION,android.Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSION_ID
            )
        }

        fun isLocationEnabled():Boolean{

        var locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if(requestCode == PERMISSION_ID){
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Log.d("Debug", "You have the permission")
            }
        }
    }

    private fun getCityName(lat: Double,long: Double):String{
        var cityName:String = ""
        var countryName = ""
        var geoCoder = Geocoder(this, Locale.getDefault())
        var Adress = geoCoder.getFromLocation(lat,long,3)

        cityName = Adress.get(0).locality
        countryName = Adress.get(0).countryName
        Log.d("Debug:","Your City: " + cityName + " ; your Country " + countryName)
        return cityName
    }

}