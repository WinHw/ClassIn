package com.hendrywinata.classin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

class DashboardActivity : AppCompatActivity() {
    private lateinit var accID: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        accID = intent.getStringExtra("accID").toString()
        setContentView(R.layout.activity_dashboard)
        Toast.makeText(this@DashboardActivity, accID, Toast.LENGTH_SHORT).show()
    }
}