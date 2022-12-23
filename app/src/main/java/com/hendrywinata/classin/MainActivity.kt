package com.hendrywinata.classin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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
        setContentView(R.layout.activity_main)

        retrieveAccounts()
//        addAccount()
    }

    private fun addAccount() {
        RetrofitClient.instance.addAccount(
            "003", "hw", "hw"
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