package com.marinchevmanolov.fisher

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
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


class FeedActivity : AppCompatActivity() {
    lateinit var viewPager: ViewPager
    lateinit var myAdapter: MyAdapter
    lateinit var db: FirebaseFirestore
    lateinit var database: DatabaseReference
    lateinit var post :Post
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)
        val db = FirebaseFirestore.getInstance();
        val dbRef = Firebase.database.getReference("posts")
        val postsRef = db.collection("posts")
        Log.d("REFERENCEEEEEEE", dbRef.toString())
//        val postsRef = db.collection("posts")
        var images= intArrayOf(R.drawable.bg,R.drawable.ic_password)
        viewPager = findViewById<ViewPager>(R.id.viewPager) as ViewPager
        myAdapter = MyAdapter(this,images)
        viewPager!!.adapter=myAdapter
//        postsRef.document("kdDA2JZKAURUzwGf1Iqs").get().addOnSuccessListener { document ->
//            if (document != null) {
//                Log.d("ne e null", "DocumentSnapshot data: ${document.data}")
//            } else {
//                Log.d("Null e", "No such document")
//            }
//        }
//            .addOnFailureListener { exception ->
//                Log.d("MALKO GREDA", "get failed with ", exception)
//            }
//
//        Log.w("PROBAAAAAAA" , "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")


//        val childEventListener = object : ChildEventListener{
//            override fun onChildAdded(dataSnapshot: DataSnapshot, previousChildName: String?) {
//                Log.d("SHIBAINU", "onChildAdded:" + dataSnapshot.key!!)
//
//                // A new comment has been added, add it to the displayed list
//                val post = dataSnapshot.getValue(Post::class.java)
//                Log.w("DOGE","OPA TIGREEEE")
//                Log.w("RESULTATA", post.toString())
//
//                // ...
//            }
//
//            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
//                TODO("Not yet implemented")
//            }
//
//            override fun onChildRemoved(snapshot: DataSnapshot) {
//                TODO("Not yet implemented")
//            }
//
//            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
//                TODO("Not yet implemented")
//            }
//
//            override fun onCancelled(databaseError: DatabaseError) {
//                Log.w("ERORCHE", "postComments:onCancelled", databaseError.toException())
//
//            }
//        }

   // dbRef.addChildEventListener(childEventListener);

        postsRef.get().addOnSuccessListener { result ->
            for(document in result){
//                var post = document.get(Post.class)
                Log.d("DBBBBBBBBBBB", document.data.toString())
                val pos = document.data.to(Post::class.java)
                post= document.toObject(Post::class.java)
                Log.d("STANA LI WE",post.title)
            }
        }

        val goAddPostBtn = findViewById<Button>(R.id.goAddPostBtn) as Button

        goAddPostBtn.setOnClickListener {
            val intent = Intent(this, AddPostActivity::class.java);
            startActivity(intent);
        }
    }


}