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
                Log.d("Firebase", "", t)
            }

        })
    }
}