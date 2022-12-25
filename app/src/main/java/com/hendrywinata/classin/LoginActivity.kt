package com.hendrywinata.classin

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.hendrywinata.classin.data.Account
import com.hendrywinata.classin.rest.RetrofitClient
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        tv_reset_password.setOnClickListener {
            startActivity(Intent(this, ResetPasswordActivity::class.java))
        }

        got_issue.setOnClickListener {
            startActivity(Intent(
                Intent.ACTION_SENDTO,
                Uri.fromParts(
                    "mailto",
                    "hendrywinata@students.usu.ac.id",
                    null)
            ))
        }

        btn_login.setOnClickListener { loginAccount() }
    }

    private fun loginAccount() {
        val username = et_username.text.toString()
        val password = et_password.text.toString()

        if (username.isEmpty()) {
            et_username.error = "Please fill in your username [NIM/NIP]"
            return
        }

        if (password.isEmpty()) {
            et_password.error = "Please fill in your password"
            return
        }

        RetrofitClient.instance.loginAccount(username, password)
            .enqueue(object: Callback<Account> {
                override fun onResponse(call: Call<Account>, response: Response<Account>) {
                    if (response.code() == 200) {
                        val accID = response.body()?.id
                        Log.d("LOGIN 200", response.body().toString())
                        if (accID != null) {
                            val sharedPref = getSharedPreferences("ClassIn", MODE_PRIVATE)
                            sharedPref.edit()
                                .putString("aid", accID)
                                .apply()
                            startActivity(
                                Intent(this@LoginActivity, DashboardActivity::class.java)
                                    .putExtra("accID", accID)
                            )
                            finish()
                        } else {
                            Toast.makeText(this@LoginActivity, "Invalid credentials", Toast.LENGTH_LONG).show()
                            et_username.text.clear()
                            et_password.text.clear()
                        }
                    } else {
                        Toast.makeText(this@LoginActivity, "Invalid username or password", Toast.LENGTH_LONG).show()
                        Log.d("LOGIN " + response.code(), response.body().toString())
                        et_username.text.clear()
                        et_password.text.clear()
                    }
                }

                override fun onFailure(call: Call<Account>, t: Throwable) {
                    Toast.makeText(this@LoginActivity, "Login fail, please try again later", Toast.LENGTH_LONG).show()
                    Log.d("LOGIN FAIL", t.toString())
                }

            })
    }
}