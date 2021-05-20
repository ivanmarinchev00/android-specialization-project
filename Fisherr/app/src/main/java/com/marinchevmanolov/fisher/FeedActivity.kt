package com.marinchevmanolov.fisher

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.drawerlayout.widget.DrawerLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.material.navigation.NavigationView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.marinchevmanolov.fisher.model.Post


class FeedActivity : AppCompatActivity() {
    //lateinit var viewPager: ViewPager
    lateinit var myAdapter: MyAdapter
    lateinit var db: FirebaseFirestore
    lateinit var posts: MutableList<Post>
    lateinit var database: DatabaseReference
    lateinit var post :Post
    lateinit var toggle: ActionBarDrawerToggle
    private lateinit var layout: ConstraintLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawerLayoutFeed)
        val navView: NavigationView = findViewById(R.id.navViewFeed)
        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true);

        navView.setNavigationItemSelectedListener {
            navigateSideMenu(it.itemId);

        }

        val db = FirebaseFirestore.getInstance();
        val dbRef = Firebase.database.getReference("posts")
        val postsRef = db.collection("posts")
        var currentPostIndex = 0
        posts = mutableListOf<Post>()
        Log.d("REFERENCEEEEEEE", dbRef.toString())
//        val postsRef = db.collection("posts")
        var images= intArrayOf(R.drawable.bg, R.drawable.ic_password)
       // viewPager = findViewById<ViewPager>(R.id.viewPager) as ViewPager
        myAdapter = MyAdapter(this, images)
      //  viewPager!!.adapter=myAdapter

        var tvDate = findViewById<TextView>(R.id.tvDate)
        var tvLatitude = findViewById<TextView>(R.id.tvLatitude)
        var tvLogitude = findViewById<TextView>(R.id.tvLogitude)
        var tvTitle = findViewById<TextView>(R.id.tvTitle)
        var tvDescription = findViewById<TextView>(R.id.tvDescription)




        postsRef.get().addOnSuccessListener { result ->
            for(document in result){
//                var post = document.get(Post.class)
                Log.d("DBBBBBBBBBBB", document.data.toString())
                val pos = document.data to Post::class.java
                post= document.toObject(Post::class.java)
                posts.add(post)
                if(posts.count() == 1){
                    tvDate.setText(posts[currentPostIndex].date.toString())
                    tvLatitude.setText(posts[currentPostIndex].coordinates[0].toString())
                    tvLogitude.setText(posts[currentPostIndex].coordinates[1].toString())
                    tvDescription.setText(posts[currentPostIndex].description.toString())
                    tvTitle.setText(posts[currentPostIndex].title.toString())

                    var storageRef = FirebaseStorage.getInstance().reference
                    var imageLocation = posts[currentPostIndex].images[0].replace("gs://fisherr-3bf90.appspot.com/", "") + ".jpg"
                    Log.d("E TUKA IMAGETO WE LUD",imageLocation)
                    storageRef.child("posts/tedi.jpg").getDownloadUrl().addOnSuccessListener(OnSuccessListener<Any> {
                        uri -> Glide.with(applicationContext).load(uri.toString()).into(findViewById(R.id.iv)) }).addOnFailureListener(OnFailureListener {
                        // Handle any errors
                    })
                }
                Log.d("STANA LI WE", posts.last().title)


            }
        }

        layout = findViewById(R.id.linearLayout)
        layout.setOnTouchListener(object : OnSwipeTouchListener(this@FeedActivity) {
            override fun onSwipeUp() {
                super.onSwipeUp()
                currentPostIndex++;
                if (currentPostIndex > posts.count() - 1) {
                    currentPostIndex = 0
                }
                tvDate.setText(posts[currentPostIndex].date.toString())
                tvLatitude.setText(posts[currentPostIndex].coordinates[0].toString())
                tvLogitude.setText(posts[currentPostIndex].coordinates[1].toString())
                tvDescription.setText(posts[currentPostIndex].description.toString())
                tvTitle.setText(posts[currentPostIndex].title.toString())
            }
        })

        val goAddPostBtn = findViewById<Button>(R.id.goAddPostBtn) as Button

        goAddPostBtn.setOnClickListener {
            val intent = Intent(this, AddPostActivity::class.java);
            startActivity(intent);
        }




    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun navigateSideMenu(id: Int): Boolean {
        if(id == R.id.miItem1){
            val intent = Intent(this, FeedActivity::class.java)
            startActivity(intent)
            return true
        } else if(id == R.id.miItem2){

        } else if(id == R.id.miItem3){

        }
        return false
    }





}
