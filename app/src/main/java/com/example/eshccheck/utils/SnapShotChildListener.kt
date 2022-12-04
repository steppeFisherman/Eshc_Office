package com.example.eshccheck.utils

import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

typealias ChildListener = (snapshot: DataSnapshot) -> Unit

class SnapShotChildListener(private val listener: ChildListener): ChildEventListener {


    //    override fun onDataChange(snapshot: DataSnapshot) {
//        listener(snapshot)
//    }
//
//    override fun onCancelled(error: DatabaseError) {
//        TODO("Not yet implemented")
//    }
    override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {

    }

    override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {

        listener(snapshot)

    }

    override fun onChildRemoved(snapshot: DataSnapshot) {

    }

    override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {

    }

    override fun onCancelled(error: DatabaseError) {

    }
}