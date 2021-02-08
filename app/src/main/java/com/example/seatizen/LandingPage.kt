package com.example.seatizen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.amplifyframework.AmplifyException
import com.amplifyframework.api.ApiException
import com.amplifyframework.api.aws.AWSApiPlugin
import com.amplifyframework.api.graphql.model.ModelMutation
import com.amplifyframework.api.graphql.model.ModelPagination
import com.amplifyframework.api.graphql.model.ModelQuery
import com.amplifyframework.core.Amplify
import com.amplifyframework.datastore.generated.model.Todo
import kotlinx.android.synthetic.main.activity_landing_page.*
import java.time.LocalDateTime
import java.util.concurrent.CountDownLatch

class LandingPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing_page)
        supportActionBar?.hide()
        textView4.text = LocalDateTime.now().toString()
        imageButton.setOnClickListener {
            val intent= Intent(this,LandingPage::class.java)
            startActivity(intent)
        }
        try {
            Amplify.addPlugin(AWSApiPlugin())
            Amplify.configure(applicationContext)
            Log.i("MyAmplifyApp", "Initialized Amplify")
        } catch (error: AmplifyException) {
            Log.e("MyAmplifyApp", "Could not initialize Amplify", error)
        }

        //to get the count of entries.
        var count_entries=0
        val latch1 = CountDownLatch(1)
        Amplify.API.query(
                ModelQuery.list(Todo::class.java, ModelPagination.firstPage().withLimit(1_000)),
                { response ->
                    for (todo in response.data) {
                        count_entries++
                        Log.i("MyAmplifyApp", todo.name)
                        latch1.countDown()
                    }
                },
                { error -> latch1.countDown()
                    Log.e("MyAmplifyApp", "Query failure", error) }
        )
        latch1.await()
        Log.i("to display count", "number of entries:$count_entries")

        val Bus_count = ArrayList<String>()
        val Bus_ID = ArrayList<String>()
        val latch = CountDownLatch(count_entries)
        Amplify.API.query(
                ModelQuery.list(Todo::class.java, ModelPagination.firstPage().withLimit(1_000)),
                { response ->
                    for (todo in response.data) {
                        latch.countDown()
                        Bus_count.add(todo.name)
                        Bus_ID.add(todo.id)
                        Log.i("MyAmplifyApp", todo.name)
                        Log.i("MyAmplifyApp", todo.id)
                        /*Log.i("response_count", "$Bus_count")
                        Log.i("response_id", "$Bus_ID")*/

                    }
                },
                {
                    error ->
                    latch.countDown()
                    Log.e("MyAmplifyApp", "Query failure", error)
                }
        )
        latch.await()
        Log.i("arraylist_count", "$Bus_count")
        Log.i("arraylist_id", "$Bus_ID")
        var n = Bus_ID.size
        for (i in 0 until n){
            for (j in 0 until n - (i+1)) {
                if (Bus_ID[j] > Bus_ID[j + 1]) {
                    var temp = Bus_ID[j]
                    Bus_ID[j] = Bus_ID[j + 1]
                    Bus_ID[j + 1] = temp

                    temp = Bus_count[j]
                    Bus_count[j] = Bus_count[j + 1]
                    Bus_count[j + 1] = temp

                }
            }
        }
        Log.i("arraylist_count_after", "$Bus_count")
        Log.i("arraylist_id_after", "$Bus_ID")

        rv_recycler.layoutManager = LinearLayoutManager(this)
        rv_recycler.adapter = RecyclerAdaptor(Bus_ID,Bus_count)
    }

}