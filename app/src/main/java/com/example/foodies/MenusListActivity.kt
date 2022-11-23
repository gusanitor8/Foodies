package com.example.foodies

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import model.Menu
import model.MyAdapter

class MenusListActivity : AppCompatActivity() {

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

        dbREF.addValueEventListener(object: ValueEventListener{

            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()){
                    for (menuSnapshot in snapshot.children){

                        val menu = menuSnapshot.getValue(Menu::class.java)
                        if (menu != null) {
                            if (menu.Time == "Almuerzo" || menu.Time == "Desayuno"){
                                menuAL.add(menu!!)
                            }
                        }
                    }
                    menuRV.adapter = MyAdapter(menuAL)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                menuRV.adapter = MyAdapter(menuAL)
                Toast.makeText(this@MenusListActivity, error.toString(), Toast.LENGTH_SHORT).show()
            }
        })

    }
}