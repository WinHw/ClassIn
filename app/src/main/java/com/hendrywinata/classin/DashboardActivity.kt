package com.hendrywinata.classin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.hendrywinata.classin.adapter.CourseListAdapter
import com.hendrywinata.classin.data.CourseItem
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

        btn_logout.setOnClickListener { logoutAccount() }
    }

    private fun buildCourseList(courses: ArrayList<CourseItem>) {
        rv_courses.apply {
            this.adapter = CourseListAdapter(courses)
            this.layoutManager = LinearLayoutManager(this@DashboardActivity)
        }
    }

    private fun retrieveDashboardDetail() {
//        RetrofitClient.instance.getAccountDetailByID(accID)
//            .enqueue(object: Callback<Account> {
//                override fun onResponse(call: Call<Account>, response: Response<Account>) {
//                    if (response.code() == 200) {
//                        val acc = response.body()
//                        if (acc != null) {
//                            name = acc.name.toString()
//                            level = acc.level.toString()
//                            username = acc.username.toString()
//                            setAccountDetail()
//                        } else {
//                            Toast.makeText(this@DashboardActivity, "Account ID invalid", Toast.LENGTH_LONG).show()
//                            Log.d("GET ACCOUNT DETAIL NULL", acc.toString())
//                            logoutAccount()
//                        }
//                    } else {
//                        Toast.makeText(this@DashboardActivity, "Fail fetching from database", Toast.LENGTH_LONG).show()
//                        Log.d("GET ACCOUNT DETAIL ${response.code()}", response.body().toString())
//                        logoutAccount()
//                    }
//                }
//
//                override fun onFailure(call: Call<Account>, t: Throwable) {
//                    Toast.makeText(this@DashboardActivity, "Fail fetching from database...", Toast.LENGTH_LONG).show()
//                    Log.d("GET ACCOUNT DETAIL FAIL", t.toString())
//                    logoutAccount()
//                }
//            })

        RetrofitClient.instance.getActiveCoursesByLevelAndID(accID, accLevel)
            .enqueue(object: Callback<ArrayList<CourseItem>> {
                override fun onResponse(call: Call<ArrayList<CourseItem>>, response: Response<ArrayList<CourseItem>>) {
                    if (response.code() == 200) {
                        val list = response.body()
                        Log.d("GET COURSE ITEMS", list.toString())

                        if (list!!.isEmpty()) Toast.makeText(this@DashboardActivity, "There is no active course linked to your account", Toast.LENGTH_LONG).show()
                        else buildCourseList(list)
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
    }

    private fun logoutAccount() {
        val sharedPref = getSharedPreferences("ClassIn", MODE_PRIVATE)
        sharedPref.edit().remove("aid").apply()
        startActivity(Intent(this@DashboardActivity, LoginActivity::class.java))
        finish()
    }
}