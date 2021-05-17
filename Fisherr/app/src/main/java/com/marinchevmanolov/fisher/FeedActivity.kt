package com.marinchevmanolov.fisher

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.viewpager.widget.ViewPager

class FeedActivity : AppCompatActivity() {
    lateinit var viewPager: ViewPager
    lateinit var myAdapter: MyAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)

        var images= intArrayOf(R.drawable.bg,R.drawable.ic_password)
        viewPager = findViewById<ViewPager>(R.id.viewPager) as ViewPager
        myAdapter = MyAdapter(this,images)
        viewPager!!.adapter=myAdapter

        val goAddPostBtn = findViewById<Button>(R.id.goAddPostBtn) as Button

        goAddPostBtn.setOnClickListener {
            val intent = Intent(this, AddPostActivity::class.java);
            startActivity(intent);
        }
    }


}