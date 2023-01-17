package com.hendrywinata.classin.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AnnouncementItem(
    val announcementID: String?,
    val courseID: String?,
    val courseCode: String?,
    val courseClass: String?,
    val title: String?,
    val content: String?,
    val dateTime: String?
): Parcelable
