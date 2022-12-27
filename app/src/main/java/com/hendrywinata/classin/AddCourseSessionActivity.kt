package com.hendrywinata.classin

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.hendrywinata.classin.data.CourseItem
import kotlinx.android.synthetic.main.activity_add_course_session.*
import kotlinx.android.synthetic.main.activity_add_course_session.add_session
import kotlinx.android.synthetic.main.activity_add_course_session.btn_back
import kotlinx.android.synthetic.main.activity_add_course_session.tv_title
import java.text.SimpleDateFormat
import java.util.*

class AddCourseSessionActivity : AppCompatActivity() {
    private lateinit var course: CourseItem
    private var startYear: Int? = null
    private var startMonth: Int? = null
    private var startDayOfMonth: Int? = null
    private var endYear: Int? = null
    private var endMonth: Int? = null
    private var endDayOfMonth: Int? = null
    private var startDateTime: String? = null
    private var endDateTime: String? = null
    private val startCalendar = Calendar.getInstance()
    private val endCalendar = Calendar.getInstance()
    private val datetimeFormat = "yyyy-MM-dd HH:mm:ss"
    private val dateFormatter = SimpleDateFormat(datetimeFormat, Locale.US)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_course_session)
        course = intent.getParcelableExtra("course_detail")!!
        tv_title.text = course.course_name + " " + course.course_class
        btn_back.setOnClickListener { this.finish() }

        datetime_picker_start.setOnClickListener {
            DatePickerDialog(this, startDatePicker,
                startCalendar.get(Calendar.YEAR),
                startCalendar.get(Calendar.MONTH),
                startCalendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        datetime_picker_end.setOnClickListener {
            DatePickerDialog(this, endDatePicker,
                endCalendar.get(Calendar.YEAR),
                endCalendar.get(Calendar.MONTH),
                endCalendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        add_session.setOnClickListener { submitNewSession() }
    }

    private fun submitNewSession() {
        if (startDateTime.isNullOrEmpty()) Toast.makeText(this, "Please set session start datetime", Toast.LENGTH_LONG).show()
        else if (endDateTime.isNullOrEmpty()) Toast.makeText(this, "Please set session end datetime", Toast.LENGTH_LONG).show()
        else {
            val startTimestamp = SimpleDateFormat(datetimeFormat).parse(startDateTime).time
            val endTimestamp = SimpleDateFormat(datetimeFormat).parse(endDateTime).time
            if ((endTimestamp - startTimestamp) > 0) {
                Toast.makeText(this, (endTimestamp - startTimestamp).toString(), Toast.LENGTH_LONG).show()
            } else Toast.makeText(this, "Session start datetime must come before end datetime", Toast.LENGTH_LONG).show()
        }
    }

    private val startDatePicker =
        DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            startYear = year
            startMonth = month
            startDayOfMonth = dayOfMonth
            TimePickerDialog(
                this@AddCourseSessionActivity,
                startTimePicker,
                startCalendar.get(Calendar.HOUR_OF_DAY),
                startCalendar.get(Calendar.MINUTE),
                true
            ).show()
        }

    private val startTimePicker =
        TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
            startCalendar.set(startYear!!, startMonth!!, startDayOfMonth!!, hourOfDay, minute, 0)
            startDateTime = dateFormatter.format(startCalendar.timeInMillis)
            datetime_picker_start.text = startDateTime
        }

    private val endDatePicker =
        DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            endYear = year
            endMonth = month
            endDayOfMonth = dayOfMonth
            TimePickerDialog(
                this@AddCourseSessionActivity,
                endTimePicker,
                endCalendar.get(Calendar.HOUR_OF_DAY),
                endCalendar.get(Calendar.MINUTE),
                true
            ).show()
        }

    private val endTimePicker =
        TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
            endCalendar.set(endYear!!, endMonth!!, endDayOfMonth!!, hourOfDay, minute, 0)
            endDateTime = dateFormatter.format(endCalendar.timeInMillis)
            datetime_picker_end.text = endDateTime
        }
}