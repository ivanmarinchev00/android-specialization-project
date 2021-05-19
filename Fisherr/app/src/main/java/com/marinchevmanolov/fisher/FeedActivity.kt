package com.marinchevmanolov.fisher

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewpager.widget.ViewPager
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.gson.JsonParser
import com.marinchevmanolov.fisher.model.Post
import org.w3c.dom.Text


class FeedActivity : AppCompatActivity() {
    lateinit var viewPager: ViewPager
    lateinit var myAdapter: MyAdapter
    lateinit var db: FirebaseFirestore
    lateinit var posts: MutableList<Post>
    lateinit var database: DatabaseReference
    lateinit var post :Post
    private lateinit var layout: ConstraintLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)
        val db = FirebaseFirestore.getInstance();
        val dbRef = Firebase.database.getReference("posts")
        val postsRef = db.collection("posts")
        var currentPostIndex = 0
        posts = mutableListOf<Post>()
        Log.d("REFERENCEEEEEEE", dbRef.toString())
//        val postsRef = db.collection("posts")
        var images= intArrayOf(R.drawable.bg,R.drawable.ic_password)
        viewPager = findViewById<ViewPager>(R.id.viewPager) as ViewPager
        myAdapter = MyAdapter(this,images)
        viewPager!!.adapter=myAdapter

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
                }
                Log.d("STANA LI WE",posts.last().title)
            }
        }

        layout = findViewById(R.id.linearLayout)
        layout.setOnTouchListener( object : OnSwipeTouchListener(this@FeedActivity){
            override fun onSwipeUp() {
                super.onSwipeUp()
                currentPostIndex++;
                if(currentPostIndex> posts.count()-1){
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





}