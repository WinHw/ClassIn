package com.hendrywinata.classin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.hendrywinata.classin.data.Account
import com.hendrywinata.classin.data.CourseItem
import com.hendrywinata.classin.data.PresenceItem
import com.hendrywinata.classin.rest.RetrofitClient
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.activity_lecturer_course.*
import kotlinx.android.synthetic.main.activity_profile.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileActivity : AppCompatActivity() {
    private lateinit var accID: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        accID = intent.getStringExtra("accID").toString()
        retrieveAccountDetail()
        setContentView(R.layout.activity_profile)
        btn_back_mhs.setOnClickListener { this@ProfileActivity.finish() }
    }

    private fun retrieveAccountDetail() {
        RetrofitClient.instance.getAccountDetailByID(accID)
            .enqueue(object : Callback<Account> {
                override fun onResponse(call: Call<Account>, response: Response<Account>) {
                    if (response.code() == 200) {
                        val list = response.body()
                        Log.d("GET ACCOUNT DETAIL", list.toString())

                        if (list != null) {
                            mhs_name.text = list.name
                            mhs_nim.text = " NIM " + list.username
                            mhs_email.text = list.email
                            mhs_telephone.text = list.phone
                        }

                    } else {
                        Toast.makeText(
                            this@ProfileActivity,
                            "Fail fetching from database",
                            Toast.LENGTH_LONG
                        ).show()
                        Log.d("GET ACCOUNT FAIL ${response.code()}", response.body().toString())
                    }
                }

                override fun onFailure(call: Call<Account>, t: Throwable) {
                    Toast.makeText(
                        this@ProfileActivity,
                        "Fail fetching from database...",
                        Toast.LENGTH_LONG
                    ).show()
                    Log.d("GET ACCOUNT FAIL", t.toString())
                }
            })
    }
}