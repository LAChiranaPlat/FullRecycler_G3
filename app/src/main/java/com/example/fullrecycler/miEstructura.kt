package com.example.fullrecycler

import android.os.Parcel
import android.os.Parcelable

data class miEstructura(var codigo: String?, var nombre:String?, var cargo:String?):Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString()) {
    }

    override fun describeContents(): Int {
        TODO("Not yet implemented")
    }

    override fun writeToParcel(p0: Parcel?, p1: Int) {
        TODO("Not yet implemented")
    }

    companion object CREATOR : Parcelable.Creator<miEstructura> {
        override fun createFromParcel(parcel: Parcel): miEstructura {
            return miEstructura(parcel)
        }

        override fun newArray(size: Int): Array<miEstructura?> {
            return arrayOfNulls(size)
        }
    }
}
