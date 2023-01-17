package com.hendrywinata.classin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hendrywinata.classin.data.AnnouncementItem

class AnnouncementViewActivity : AppCompatActivity() {
    private lateinit var announcement: AnnouncementItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_announcement_view)
        announcement = intent.getParcelableExtra("announcement_detail")!!
    }
}