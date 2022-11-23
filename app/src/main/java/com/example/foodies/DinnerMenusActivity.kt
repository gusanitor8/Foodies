package com.example.foodies

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import model.Menu
import model.MyAdapter

class DinnerMenusActivity : AppCompatActivity() {
    private lateinit var dbREF: DatabaseReference
    private lateinit var menuRV: RecyclerView
    private lateinit var menuAL: ArrayList<Menu>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menus_list)

        menuRV = findViewById(R.id.recyclerMenus)
        menuRV.layoutManager = LinearLayoutManager(this)
        menuRV.setHasFixedSize(true)

        menuAL = arrayListOf<Menu>()
        getMenuData()

    }

    private fun getMenuData() {
        dbREF = FirebaseDatabase.getInstance().getReference("Menus")

        dbREF.addValueEventListener(object: ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()){
                    for (menuSnapshot in snapshot.children){

                        val menu = menuSnapshot.getValue(Menu::class.java)
                        if (menu != null) {
                            if (menu.Time == "Almuerzo"){
                                menuAL.add(menu!!)
                            }
                        }
                    }
                    var adapter = MyAdapter(menuAL)
                    menuRV.adapter = adapter
                    adapter.setOnItemClickListener(object: MyAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {
                            //Toast.makeText(this@MenusListActivity,"Clickeaste. $position", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this@DinnerMenusActivity, ProvidersActivity::class.java)
                            startActivity(intent)
                        }

                    })
                }
            }

            override fun onCancelled(error: DatabaseError) {
                menuRV.adapter = MyAdapter(menuAL)
                Toast.makeText(this@DinnerMenusActivity, error.toString(), Toast.LENGTH_SHORT).show()
            }
        })

    }
}