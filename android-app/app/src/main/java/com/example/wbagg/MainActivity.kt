package com.example.wbagg

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    lateinit var rv: RecyclerView
    lateinit var adapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rv = findViewById(R.id.rv)
        rv.layoutManager = LinearLayoutManager(this)
        adapter = ProductAdapter()
        rv.adapter = adapter

        val baseUrl = getString(R.string.backend_base_url)
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val api = retrofit.create(ApiService::class.java)
        api.discounts().enqueue(object: Callback<List<Product>> {
            override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>) {
                if (response.isSuccessful) {
                    val list = response.body() ?: emptyList()
                    adapter.submitList(list)
                } else {
                    Toast.makeText(this@MainActivity, "Server error: ${'$'}{response.code()}", Toast.LENGTH_LONG).show()
                }
            }
            override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Network error: ${'$'}{t.localizedMessage}", Toast.LENGTH_LONG).show()
            }
        })
    }
}
