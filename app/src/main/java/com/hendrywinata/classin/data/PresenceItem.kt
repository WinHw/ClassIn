package com.hendrywinata.classin.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PresenceItem(
    val course_id: String?,
    val course_name: String?,
    val course_class: String?,
    val course_lecturer: String?,
    val presence_id: String?,
    val presence_start: String?,
    val presence_end: String?,
    val presence_description: String?,
): Parcelable
