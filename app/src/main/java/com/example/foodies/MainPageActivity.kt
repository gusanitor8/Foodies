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
import android.widget.Button
import androidx.databinding.DataBindingUtil
import com.example.foodies.databinding.ActivityLogInBinding
import com.example.foodies.databinding.ActivityMainPageBinding
import com.google.android.material.slider.RangeSlider
import com.google.android.material.slider.Slider

class MainPageActivity : AppCompatActivity() {
    private lateinit var dbREF: DatabaseReference
    private lateinit var timeRV: RecyclerView
    private lateinit var timeAL: ArrayList<Time>
    private lateinit var rangeSlider: Slider
    private lateinit var button: Button
    private lateinit var binding: ActivityMainPageBinding
    private var price: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        timeRV = findViewById(R.id.recyclerTimes)
        timeRV.layoutManager = LinearLayoutManager(this)
        timeRV.setHasFixedSize(true)

        timeAL = arrayListOf<Time>()

        //Uso del Slider
        rangeSlider = findViewById(R.id.priceInputSlider)

        rangeSlider.setLabelFormatter { value: Float ->
            val format = NumberFormat.getCurrencyInstance()
            format.maximumFractionDigits = 0
            format.currency = Currency.getInstance("GTQ")
            format.format(value.toDouble())
        }

        /*binding.priceInputSlider.addOnSliderTouchListener(object : Slider.OnSliderTouchListener {
            override fun onStartTrackingTouch(slider: Slider) {
                price = slider.value.toInt()
            }

            override fun onStopTrackingTouch(slider: Slider){

            }
        })*/

        binding.priceInputSlider.addOnChangeListener { slider, value, fromUser ->
            price = value.toInt()
        }

        button = findViewById(R.id.filterButton)
        button.setOnClickListener{
            callActivity()
        }

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
    }

    private fun callActivity() {
        val intent = Intent(this, MenusListActivity:: class.java).also {
            it.putExtra("EXTRA_MESSAGE", price.toString())
            startActivity(it)
        }

    }
}