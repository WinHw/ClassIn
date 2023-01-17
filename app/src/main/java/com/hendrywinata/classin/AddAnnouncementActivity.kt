package com.hendrywinata.classin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.hendrywinata.classin.data.CourseItem
import com.hendrywinata.classin.rest.RetrofitClient
import kotlinx.android.synthetic.main.course_item.view.*
import kotlinx.android.synthetic.main.activity_add_announcement.*
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.EmptyCoroutineContext.plus


class AddAnnouncementActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private lateinit var accID: String
    private lateinit var accName: String

    private var sendToCourseCode: String? = null
    private var sendToCourseClass: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        accID = intent.getStringExtra("accID").toString()
        accName = intent.getStringExtra("accName").toString()

        setContentView(R.layout.activity_add_announcement)

        retrieveClassItems()

        spinner_class_to_send.onItemSelectedListener = this

        btn_add_announcement.setOnClickListener { announce() }
        btn_back_add_announcement.setOnClickListener { this@AddAnnouncementActivity.finish() }
    }

    private fun buildSpinnerItems(courses: Array<String>) {
        ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            courses
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner_class_to_send.adapter = adapter
        }
    }

    private fun retrieveClassItems() {
        val accLevel = "lecturer"

        RetrofitClient.instance.getActiveCoursesByLevelAndID(accID, accLevel)
            .enqueue(object: Callback<ArrayList<CourseItem>> {
                override fun onResponse(call: Call<ArrayList<CourseItem>>, response: Response<ArrayList<CourseItem>>) {
                    if (response.code() == 200) {
                        val list = response.body()
                        Log.d("GET COURSE ITEMS", list.toString())

                        if (list!!.isEmpty()) {
                            val coursesOption: Array<String> = arrayOf("No active class")
                            buildSpinnerItems(coursesOption)
                        } else {
                            var coursesOption: Array<String> = arrayOf("Select a class")

                            list.forEach{
                                coursesOption = coursesOption.plus("${it.course_code} ${it.course_class}")
                            }

                            buildSpinnerItems(coursesOption)
                        }
                    } else {
                        Toast.makeText(this@AddAnnouncementActivity, "Fail fetching from database", Toast.LENGTH_LONG).show()
                        Log.d("GET COURSE ITEMS FAIL ${response.code()}", response.body().toString())
                    }
                }

                override fun onFailure(call: Call<ArrayList<CourseItem>>, t: Throwable) {
                    Toast.makeText(this@AddAnnouncementActivity, "Fail fetching from database...", Toast.LENGTH_LONG).show()
                    Log.d("GET COURSE ITEMS FAIL", t.toString())
                }
            })
    }

    private fun announce() {
        val title = announcement_title.text.toString()
        val content = announcement_content.text.toString()

        if (title.isEmpty()) {
            announcement_title.error = "Please fill the title"
            return
        } else if (content.isEmpty()) {
            announcement_content.error = "Please fill the content"
            return
        } else if (sendToCourseCode.isNullOrEmpty()) {
            Toast.makeText(this@AddAnnouncementActivity, "Please select a class", Toast.LENGTH_LONG).show()
        } else {
            RetrofitClient.instance.addAnnouncement(sendToCourseCode, sendToCourseClass, title, content)
                .enqueue(object: Callback<com.hendrywinata.classin.data.Response> {
                    override fun onResponse(
                        call: Call<com.hendrywinata.classin.data.Response>,
                        response: Response<com.hendrywinata.classin.data.Response>
                    ) {
                        if (response.code() == 200) {
                            val resp = response.body()
                            if (resp!!.error) Toast.makeText(this@AddAnnouncementActivity, resp.message + ", please try again later", Toast.LENGTH_LONG).show()
                            else {
                                Toast.makeText(this@AddAnnouncementActivity, resp.message, Toast.LENGTH_SHORT).show()
                                startActivity(
                                    Intent(this@AddAnnouncementActivity, AnnouncementListActivity::class.java)
                                        .putExtra("accID", accID)
                                        .putExtra("accName", accName)
                                )
                                this@AddAnnouncementActivity.finish()
                            }
                        } else {
                            Toast.makeText(this@AddAnnouncementActivity, "Something wrong on server", Toast.LENGTH_LONG).show()
                            Log.d("ADD ANNOUNCEMENT (${response.code()})", response.body().toString())
                        }
                    }

                    override fun onFailure(call: Call<com.hendrywinata.classin.data.Response>, t: Throwable) {
                        Toast.makeText(this@AddAnnouncementActivity, "Something wrong on server...", Toast.LENGTH_LONG).show()
                        Log.d("ADD ANNOUNCEMENT FAIL", t.toString())
                    }
                })
        }

    }

    override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
        var courseSelected: String = parent.getItemAtPosition(pos).toString()

        if ((courseSelected != "No active class") && (courseSelected != "Select a class")) {
            sendToCourseCode = courseSelected.split(" ")[0]
            sendToCourseClass = courseSelected.split(" ")[1]
        } else {
            sendToCourseCode = null
            sendToCourseClass = null
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
//        TODO("Not yet implemented")
    }
}