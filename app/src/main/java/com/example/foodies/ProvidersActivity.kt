package com.example.foodies

import android.content.Intent
import android.net.Uri
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
    private lateinit var linksAL: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_providers)

        providerRV = findViewById(R.id.recyclerProviders)
        providerRV.layoutManager = LinearLayoutManager(this)
        providerRV.setHasFixedSize(true)

        providerAL = arrayListOf<Provider>()
        linksAL = arrayListOf<String>()
        getProviderData()

    }

    private fun getProviderData() {
        dbREF = FirebaseDatabase.getInstance().getReference("Providers")

        dbREF.addValueEventListener(object: ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()){
                    for (providerSnapshot in snapshot.children){

                        val provider = providerSnapshot.getValue(Provider::class.java)
                        if (provider != null) {
                            linksAL.add(provider.Link.toString())
                        }
                        providerAL.add(provider!!)
                    }
                    //providerRV.adapter = PAdapter(providerAL)
                    var adapter = PAdapter(providerAL)
                    providerRV.adapter = adapter
                    adapter.setOnItemClickListener(object: PAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {
                            //Toast.makeText(this@MenusListActivity,"Clickeaste. $position", Toast.LENGTH_SHORT).show()
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(linksAL.get(position)))
                            startActivity(intent)
                        }

                    })
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })

    }
}