package com.example.foodies

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import model.Time
import model.TAdapter
import android.icu.text.NumberFormat
import android.icu.util.Currency
import android.os.Build
import com.google.android.material.slider.RangeSlider

class MainPageActivity : AppCompatActivity() {
    private lateinit var dbREF: DatabaseReference
    private lateinit var timeRV: RecyclerView
    private lateinit var timeAL: ArrayList<Time>
    private lateinit var rangeSlider: RangeSlider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_page)

        timeRV = findViewById(R.id.recyclerTimes)
        timeRV.layoutManager = LinearLayoutManager(this)
        timeRV.setHasFixedSize(true)

        timeAL = arrayListOf<Time>()
        getTimeData()

    }

    private fun getTimeData() {
        dbREF = FirebaseDatabase.getInstance().getReference("Times")

        dbREF.addValueEventListener(object: ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()){
                    for (menuSnapshot in snapshot.children){

                        val time = menuSnapshot.getValue(Time::class.java)
                        timeAL.add(time!!)
                    }
                    var adapter = TAdapter(timeAL)
                    timeRV.adapter = adapter
                    adapter.setOnItemClickListener(object: TAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {
                            //Toast.makeText(this@MenusListActivity,"Presionaste $position", Toast.LENGTH_SHORT).show()
                            if (position == 0){
                                val intent = Intent(this@MainPageActivity, BreakfastMenusActivity::class.java)
                                startActivity(intent)
                            }
                            if (position == 1){
                                val intent = Intent(this@MainPageActivity, DinnerMenusActivity::class.java)
                                startActivity(intent)
                            }
                        }

                    })
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })

        /**get Id*/
        rangeSlider = findViewById(R.id.priceInputSlider)

        rangeSlider.setLabelFormatter { value: Float ->
            val format = NumberFormat.getCurrencyInstance()
            format.maximumFractionDigits = 0
            format.currency = Currency.getInstance("GTQ")
            format.format(value.toDouble())
        }
    }
}