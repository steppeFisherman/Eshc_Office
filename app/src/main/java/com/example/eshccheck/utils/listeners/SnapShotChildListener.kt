package com.example.eshccheck.utils.listeners

import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError

typealias ChildListener = (snapshot: DataSnapshot) -> Unit

class SnapShotChildListener(private val listener: ChildListener) : ChildEventListener {

    override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
        listener(snapshot)
    }

    override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {}
    override fun onChildRemoved(snapshot: DataSnapshot) {}
    override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
    override fun onCancelled(error: DatabaseError) {}
}