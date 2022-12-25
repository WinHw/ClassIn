package com.hendrywinata.classin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.hendrywinata.classin.adapter.ResponseAdapter
import com.hendrywinata.classin.data.Account
import com.hendrywinata.classin.rest.RetrofitClient
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private val list = ArrayList<Account>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sharedPref = getSharedPreferences("ClassIn", MODE_PRIVATE)
        if (sharedPref.contains("aid")) {
            val accID = sharedPref.getString("aid", null)
            RetrofitClient.instance.idExistAccount(accID)
                .enqueue(object: Callback<String> {
                    override fun onResponse(call: Call<String>, response: Response<String>) {
                        if (response.code() == 200) {
                            val accIDisExist = response.body()
                            if (accIDisExist == "1") {
                                startActivity(
                                    Intent(this@MainActivity, DashboardActivity::class.java)
                                        .putExtra("accID", accID)
                                )
                                finish()
                            } else {
                                sharedPref.edit().remove("aid").apply()
                                startActivity(Intent(this@MainActivity, LoginActivity::class.java))
                                finish()
                            }
                        } else {
                            Toast.makeText(this@MainActivity, "Something wrong on server", Toast.LENGTH_LONG).show()
                            Log.d("CHECK ACC ID EXIST (${response.code()})", response.body().toString())
                        }
                    }

                    override fun onFailure(call: Call<String>, t: Throwable) {
                        Toast.makeText(this@MainActivity, "Something wrong on server...", Toast.LENGTH_LONG).show()
                        Log.d("CHECK ACC ID EXIST", t.toString())
                    }
                })
        } else {
            startActivity(Intent(this@MainActivity, LoginActivity::class.java))
            finish()
        }
        setContentView(R.layout.activity_main)
    }

    private fun addAccount() {
        RetrofitClient.instance.addAccount(
            null, "admin", "admin"
        ).enqueue(object: Callback<Account> {
            override fun onResponse(call: Call<Account>, response: Response<Account>) {
                val responseText = "Response code: ${response.code()}\nResponse body: ${response.body()}"
                response_code.text = responseText
            }

            override fun onFailure(call: Call<Account>, t: Throwable) {
                response_code.text = t.message
            }

        })
    }

    private fun retrieveAccounts() {
        response_list.setHasFixedSize(true)
        response_list.layoutManager = LinearLayoutManager(this)
        RetrofitClient.instance.getAccounts().enqueue(object: Callback<ArrayList<Account>> {
            override fun onResponse(
                call: Call<ArrayList<Account>>,
                response: Response<ArrayList<Account>>
            ) {
                val responseCode = response.code().toString()
                response_code.text = responseCode
                response.body()?.let { list.addAll(it) }
                val adapter = ResponseAdapter(list)
                response_list.adapter = adapter
            }

            override fun onFailure(call: Call<ArrayList<Account>>, t: Throwable) {
                response_code.text = t.message
            }

        })
    }
}