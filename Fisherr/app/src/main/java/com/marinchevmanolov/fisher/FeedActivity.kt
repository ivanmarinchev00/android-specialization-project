package com.marinchevmanolov.fisher

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager

class FeedActivity : AppCompatActivity() {
    var viewPager: ViewPager? = null
    var images= intArrayOf(R.drawable.button_background,R.drawable.ic_password)
    var myAdapter: MyAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)
        viewPager = findViewById<ViewPager>(R.id.viewPager) as ViewPager
        myAdapter = MyAdapter(this,images)
        viewPager!!.adapter=myAdapter
    }
}