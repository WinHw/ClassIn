package com.hendrywinata.classin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.hendrywinata.classin.adapter.CourseListAdapter
import com.hendrywinata.classin.adapter.PresenceListAdapter
import com.hendrywinata.classin.data.CourseItem
import com.hendrywinata.classin.data.PresenceItem
import com.hendrywinata.classin.rest.RetrofitClient
import kotlinx.android.synthetic.main.activity_dashboard.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DashboardActivity : AppCompatActivity() {
    private lateinit var accID: String
    private lateinit var accName: String
    private lateinit var accLevel: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        accID = intent.getStringExtra("accID").toString()
        accName = intent.getStringExtra("accName").toString()
        accLevel = intent.getStringExtra("accLevel").toString()

        retrieveDashboardDetail()
        setContentView(R.layout.activity_dashboard)
        tv_name.text = accName
        if (accLevel == "lecturer")
            announcement_icon.visibility = View.VISIBLE

        announcement_icon.setOnClickListener { announcement() }
        img_profile.setOnClickListener { profile() }
        btn_logout.setOnClickListener { logoutAccount() }
    }

    private fun courseItemClicked(course: CourseItem) {
        if (accLevel == "lecturer") {
            startActivity(
                Intent(this@DashboardActivity, LecturerCourseActivity::class.java)
                    .putExtra("course_detail", course)
            )
        } else if (accLevel == "student") {
            Toast.makeText(this, course.course_id, Toast.LENGTH_SHORT).show()
        }
    }

    private fun buildCourseList(courses: ArrayList<CourseItem>) {
        rv_courses.apply {
            this.adapter = CourseListAdapter(courses) { course: CourseItem ->
                courseItemClicked(course)
            }
            this.layoutManager = LinearLayoutManager(this@DashboardActivity, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun presenceItemClicked(presence: PresenceItem) {
        startActivity(
            Intent(this@DashboardActivity, AttendPresenceActivity::class.java)
                .putExtra("presence_detail", presence)
        )
    }

    private fun buildPresenceList(presences: ArrayList<PresenceItem>) {
        rv_presences.apply {
            this.adapter = PresenceListAdapter(presences) { presence: PresenceItem ->
                presenceItemClicked(presence)
            }
            this.layoutManager = LinearLayoutManager(this@DashboardActivity)
        }
    }

    private fun retrieveDashboardDetail() {
        RetrofitClient.instance.getActiveCoursesByLevelAndID(accID, accLevel)
            .enqueue(object: Callback<ArrayList<CourseItem>> {
                override fun onResponse(call: Call<ArrayList<CourseItem>>, response: Response<ArrayList<CourseItem>>) {
                    if (response.code() == 200) {
                        val list = response.body()
                        Log.d("GET COURSE ITEMS", list.toString())

                        if (list!!.isEmpty()) {
                            tv_no_courses.visibility = View.VISIBLE
                            Toast.makeText(this@DashboardActivity, "There is no active course linked to your account", Toast.LENGTH_LONG).show()
                        } else {
                            tv_no_courses.visibility = View.INVISIBLE
                            buildCourseList(list)
                        }
                    } else {
                        Toast.makeText(this@DashboardActivity, "Fail fetching from database", Toast.LENGTH_LONG).show()
                        Log.d("GET COURSE ITEMS FAIL ${response.code()}", response.body().toString())
                    }
                }

                override fun onFailure(call: Call<ArrayList<CourseItem>>, t: Throwable) {
                    Toast.makeText(this@DashboardActivity, "Fail fetching from database...", Toast.LENGTH_LONG).show()
                    Log.d("GET COURSE ITEMS FAIL", t.toString())
                }
            })

        RetrofitClient.instance.getOpenedPresencesByLevelAndID(accID, accLevel)
            .enqueue(object: Callback<ArrayList<PresenceItem>> {
                override fun onResponse(
                    call: Call<ArrayList<PresenceItem>>,
                    response: Response<ArrayList<PresenceItem>>
                ) {
                    if (response.code() == 200) {
                        val list = response.body()
                        Log.d("GET PRESENCE ITEMS", list.toString())

                        if (list!!.isEmpty()) {
                            tv_no_presences.visibility = View.VISIBLE
                        } else {
                            tv_no_presences.visibility = View.INVISIBLE
                            buildPresenceList(list)
                        }
                    } else {
                        Toast.makeText(this@DashboardActivity, "Fail fetching from database", Toast.LENGTH_LONG).show()
                        Log.d("GET PRESENCE ITEMS ${response.code()}", response.body().toString())
                    }
                }

                override fun onFailure(call: Call<ArrayList<PresenceItem>>, t: Throwable) {
                    Toast.makeText(this@DashboardActivity, "Fail fetching from database...", Toast.LENGTH_LONG).show()
                    Log.d("GET PRESENCE ITEMS FAIL", t.toString())
                }
            })
    }

    private fun announcement() {
        startActivity(
            Intent(this@DashboardActivity, AnnouncementListActivity::class.java)
                .putExtra("accID", accID)
                .putExtra("accName", accName)
        )
    }

    private fun profile() {
        startActivity(
            Intent(this@DashboardActivity, ProfileActivity::class.java)
                .putExtra("accID", accID)
                .putExtra("accName", accName)
        )
    }

    private fun logoutAccount() {
        val sharedPref = getSharedPreferences("ClassIn", MODE_PRIVATE)
        sharedPref.edit().remove("aid").apply()
        startActivity(Intent(this@DashboardActivity, LoginActivity::class.java))
        finish()
    }
}