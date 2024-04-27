package com.example.mynotesapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Parcelize
data class Note(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val description: String,
) : Parcelable {

//    override fun equals(other: Any?): Boolean {
//        return super.equals(other)
//    }
//
//    override fun hashCode(): Int {
//        return super.hashCode()
//    }
//
//    override fun toString(): String {
//        return super.toString()
//    }

}