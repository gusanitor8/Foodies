package com.example.foodies

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import model.MyAdapter
import model.PAdapter
import model.Provider

class ProvidersActivity : AppCompatActivity() {
    private lateinit var dbREF: DatabaseReference
    private lateinit var providerRV: RecyclerView
    private lateinit var providerAL: ArrayList<Provider>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_providers)

        providerRV = findViewById(R.id.recyclerProviders)
        providerRV.layoutManager = LinearLayoutManager(this)
        providerRV.setHasFixedSize(true)

        providerAL = arrayListOf<Provider>()
        getProviderData()

    }

    private fun getProviderData() {
        dbREF = FirebaseDatabase.getInstance().getReference("Providers")

        dbREF.addValueEventListener(object: ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()){
                    for (providerSnapshot in snapshot.children){

                        val provider = providerSnapshot.getValue(Provider::class.java)
                        providerAL.add(provider!!)
                    }
                    providerRV.adapter = PAdapter(providerAL)
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })

    }
}