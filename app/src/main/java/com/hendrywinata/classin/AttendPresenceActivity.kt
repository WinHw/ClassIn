package com.hendrywinata.classin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hendrywinata.classin.data.PresenceItem
import kotlinx.android.synthetic.main.activity_lecturer_course.*

class AttendPresenceActivity : AppCompatActivity() {
    private lateinit var presence: PresenceItem
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_attend_presence)
        presence = intent.getParcelableExtra("presence_detail")!!
    }
}