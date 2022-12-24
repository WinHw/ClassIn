package com.hendrywinata.classin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_login.*

class MainActivity : AppCompatActivity() {
    private lateinit var db: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        db = Firebase.firestore

        btn_login.setOnClickListener { getAccounts() }
    }

    private fun getAccounts() {
        db.collection("users")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d("GET", "${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener { exception ->
                Log.w("GET", "Error getting documents.", exception)
            }
    }

    private fun addAccount() {

        // Create a new user with a first and last name
        val user = hashMapOf(
            "first" to "Ada",
            "last" to "Lovelace",
            "born" to 1815
        )

        // Add a new document with a generated ID
        db.collection("accounts")
            .add(user)
            .addOnSuccessListener { documentReference ->
                Log.d("Adding", "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w("Adding", "Error adding document", e)
            }
    }


}