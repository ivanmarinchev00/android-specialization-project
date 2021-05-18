package com.marinchevmanolov.fisher

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
<<<<<<< HEAD
import android.util.Log
=======
import android.view.View
import android.widget.Button
>>>>>>> ca912c1bb859e99de8386615a79052adba8a092d
import androidx.viewpager.widget.ViewPager
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class FeedActivity : AppCompatActivity() {
    lateinit var viewPager: ViewPager
    lateinit var myAdapter: MyAdapter
<<<<<<< HEAD
    lateinit var db: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)
        val db = FirebaseFirestore.getInstance();
        val postsRef = db.collection("posts")
=======
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)

>>>>>>> ca912c1bb859e99de8386615a79052adba8a092d
        var images= intArrayOf(R.drawable.bg,R.drawable.ic_password)
        viewPager = findViewById<ViewPager>(R.id.viewPager) as ViewPager
        myAdapter = MyAdapter(this,images)
        viewPager!!.adapter=myAdapter
<<<<<<< HEAD
        postsRef.document("kdDA2JZKAURUzwGf1Iqs").get().addOnSuccessListener { document ->
            if (document != null) {
                Log.d("ne e null", "DocumentSnapshot data: ${document.data}")
            } else {
                Log.d("Null e", "No such document")
            }
        }
            .addOnFailureListener { exception ->
                Log.d("MALKO GREDA", "get failed with ", exception)
            }

        Log.w("PROBAAAAAAA" , "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")

=======

        val goAddPostBtn = findViewById<Button>(R.id.goAddPostBtn) as Button

        goAddPostBtn.setOnClickListener {
            val intent = Intent(this, AddPostActivity::class.java);
            startActivity(intent);
        }
>>>>>>> ca912c1bb859e99de8386615a79052adba8a092d
    }


}