package com.hendrywinata.classin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_dashboard.*

class DashboardActivity : AppCompatActivity() {
    private lateinit var accID: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        accID = intent.getStringExtra("accID").toString()
        setContentView(R.layout.activity_dashboard)

        btn_logout.setOnClickListener { logoutAccount() }
//        Toast.makeText(this@DashboardActivity, accID, Toast.LENGTH_SHORT).show()
    }

    private fun logoutAccount() {
        val sharedPref = getSharedPreferences("ClassIn", MODE_PRIVATE)
        sharedPref.edit().remove("aid").apply()
        startActivity(Intent(this@DashboardActivity, LoginActivity::class.java))
        finish()
    }
}