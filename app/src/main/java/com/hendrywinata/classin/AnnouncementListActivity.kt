package com.hendrywinata.classin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.hendrywinata.classin.adapter.AnnouncementListAdapter
import com.hendrywinata.classin.data.AnnouncementItem
import com.hendrywinata.classin.rest.RetrofitClient
import kotlinx.android.synthetic.main.activity_announcement_list.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AnnouncementListActivity : AppCompatActivity() {
    private lateinit var accID: String
    private lateinit var accName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        accID = intent.getStringExtra("accID").toString()
        accName = intent.getStringExtra("accName").toString()

        retrieveAnnouncementDetail()
        setContentView(R.layout.activity_announcement_list)

        btn_back_announcement.setOnClickListener { this@AnnouncementListActivity.finish() }
//        Add announcement intent function
        add_announcement.setOnClickListener { addAnnouncement() }
    }

    private fun announcementItemClicked(announcement: AnnouncementItem) {
        startActivity(
            Intent(this@AnnouncementListActivity, AnnouncementViewActivity::class.java)
                .putExtra("announcement_detail", announcement)
        )
    }

    private fun buildAnnouncementList(announcements: ArrayList<AnnouncementItem>) {
        rv_announcements.apply {
            this.adapter = AnnouncementListAdapter(announcements) { announcement: AnnouncementItem ->
                announcementItemClicked(announcement)
            }
            this.layoutManager = LinearLayoutManager(this@AnnouncementListActivity)
        }
    }

    private fun retrieveAnnouncementDetail() {
        RetrofitClient.instance.getAnnouncementList(accID)
            .enqueue(object: Callback<ArrayList<AnnouncementItem>> {
                override fun onResponse(call: Call<ArrayList<AnnouncementItem>>, response: Response<ArrayList<AnnouncementItem>>) {
                    if (response.code() == 200) {
                        val list = response.body()
                        Log.d("GET ANNOUNCEMENT ITEMS", list.toString())

                        if (list!!.isEmpty()) {
                            tv_no_announcements.visibility = View.VISIBLE
                            Toast.makeText(this@AnnouncementListActivity, "There is no announcement made by you", Toast.LENGTH_LONG).show()
                        } else {
                            tv_no_announcements.visibility = View.INVISIBLE
                            buildAnnouncementList(list)
                        }
                    } else {
                        Toast.makeText(this@AnnouncementListActivity, "Fail fetching from database", Toast.LENGTH_LONG).show()
                        Log.d("GET ANNCMNT ITEMS FAIL ${response.code()}", response.body().toString())
                    }
                }

                override fun onFailure(call: Call<ArrayList<AnnouncementItem>>, t: Throwable) {
                    Toast.makeText(this@AnnouncementListActivity, "Fail fetching from database...", Toast.LENGTH_LONG).show()
                    Log.d("GET ANNCMNT ITEMS FAIL", t.toString())
                }
            })
    }

    private fun addAnnouncement() {
        startActivity(
            Intent(this@AnnouncementListActivity, AddAnnouncementActivity::class.java)
                .putExtra("accID", accID)
                .putExtra("accName", accName)
        )
    }
}