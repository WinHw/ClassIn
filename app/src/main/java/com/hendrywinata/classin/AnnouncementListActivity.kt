package com.hendrywinata.classin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
//import android.util.Log
//import android.view.View
//import android.widget.ImageView
//import android.widget.Toast
//import androidx.recyclerview.widget.LinearLayoutManager
//import com.hendrywinata.classin.adapter.CourseListAdapter
//import com.hendrywinata.classin.adapter.PresenceListAdapter
//import com.hendrywinata.classin.data.CourseItem
//import com.hendrywinata.classin.data.PresenceItem
//import com.hendrywinata.classin.rest.RetrofitClient
import kotlinx.android.synthetic.main.activity_announcement_list.*
//import kotlinx.android.synthetic.main.activity_dashboard.*
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response

class AnnouncementListActivity : AppCompatActivity() {
    private lateinit var accID: String
    private lateinit var accName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        accID = intent.getStringExtra("accID").toString()
        accName = intent.getStringExtra("accName").toString()

        setContentView(R.layout.activity_announcement_list)

        btn_back_announcement.setOnClickListener { this@AnnouncementListActivity.finish() }
//        Add announcement intent function
        add_announcement.setOnClickListener { addAnnouncement() }
    }

    private fun addAnnouncement() {
        startActivity(
            Intent(this@AnnouncementListActivity, AddAnnouncementActivity::class.java)
                .putExtra("accID", accID)
                .putExtra("accName", accName)
        )
    }
}