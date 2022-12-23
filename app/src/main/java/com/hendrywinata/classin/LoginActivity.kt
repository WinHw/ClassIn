package com.hendrywinata.classin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.database.*
import com.hendrywinata.classin.data.Account
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btn_login.setOnClickListener { retrieveAccounts() }
    }

    private fun retrieveAccounts() {
//        database = FirebaseDatabase.getInstance("https://classin-mobile005-default-rtdb.firebaseio.com/").getReference("Accounts")
//        val accID = database.push().key.toString()
//        val acc = Account(accID, "dsa", "dsa")
//        database.child(accID).setValue(acc)
//            .addOnCompleteListener {
//                Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
//            }.addOnFailureListener { err ->
//                Toast.makeText(this, "Error: ${err.message}", Toast.LENGTH_SHORT).show()
//            }

        Log.d("button", "CLICKED")
        database = FirebaseDatabase.getInstance().getReference("accounts")
//        val accounts: List<Account> = mutableListOf(
//            Account("001", "001"),
//            Account("002", "002")
//        )
//
//        accounts.forEach {
//            val key = database.child("acc").push().key
//            if (key != null) {
//                database.child("acc").child(key).setValue(it)
//            } else {
//                Log.d("key", "NONE")
//            }
//        }
        database.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val children = snapshot.children
                children.forEach {
                    Log.d("CHILD", it.toString())
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("oncancelled", error.message)
            }
        })
    }


    private fun addAccount() {
//        database = FirebaseDatabase.getInstance()
//        val accRef = database.getReference("accounts")
//        Log.d("instance", accRef.toString())
//
//        val username = et_username.text.toString()
//        val password = et_password.text.toString()
//        val userID = accRef.push().key.toString()
//
//        val account = Account(userID, username, password)
//
//        Log.d("data class", account.id.toString() + account.username.toString() + account.password.toString())
////        accRef.child("acc").child(userID).setValue(account).addOnSuccessListener {
////            et_username.text.clear()
////            et_password.text.clear()
////
////            Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
////        }.addOnFailureListener {
////            Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
////        }
//        accRef.addValueEventListener(object: ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                accRef.child(userID).setValue("ASDASDADASASDASDASDADASDADASD")
//                Toast.makeText(this@LoginActivity, "Success", Toast.LENGTH_SHORT).show()
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                Toast.makeText(this@LoginActivity, "Failure $error", Toast.LENGTH_SHORT).show()
//            }
//
//        })
    }
}