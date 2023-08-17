package com.example.fashionhub

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ActivityHome : AppCompatActivity() {
    private lateinit var database:DatabaseReference
    private lateinit var userArrayList: ArrayList<Items>
    private lateinit var recyclerView: RecyclerView

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var actionBarToggle: ActionBarDrawerToggle
    private lateinit var navView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val cardViewWomen: MaterialCardView = findViewById(R.id.user)

        cardViewWomen.setOnClickListener {

            val intent = Intent(this@ActivityHome, ActivityCurrentUser::class.java)
            startActivity(intent)
        }

        // Find the Toolbar by its ID and set it as the support action bar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_action_circled) // Replace with your navigation icon drawable



        // Find views by their IDs
        drawerLayout = findViewById(R.id.drawerLayout)
        navView = findViewById(R.id.navView)

        // Set up the ActionBarDrawerToggle
        actionBarToggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open_drawer, R.string.close_drawer)
        drawerLayout.addDrawerListener(actionBarToggle)

        // Set up the navigation item click listener
        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home -> {
                    // Handle Home click
                    startActivity(Intent(this@ActivityHome,ActivityHome::class.java))
                    true
                }
                R.id.nav_profile -> {
                    // Handle Profile click
                    startActivity(Intent(this@ActivityHome,ActivityCurrentUser::class.java))
                    true
                }
                R.id.nav_settings -> {
                    // Handle Settings click

                    startActivity(Intent(this@ActivityHome,ActivityAdminPanel::class.java))
                    true
                }
                else -> false
            }
        }


        val layoutManager=GridLayoutManager(this,2)

        // getting the recyclerview by its id
        recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        recyclerView.layoutManager = layoutManager

        userArrayList= arrayListOf()

        database=FirebaseDatabase.getInstance().getReference("items")
        database.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for (dataSnapShot in snapshot.children){
                        val user=dataSnapShot.getValue(Items::class.java)
                        if (!userArrayList.contains(user)){
                            userArrayList.add(user!!)
                        }
                    }
                    // This will pass the ArrayList to our Adapter
                    val adapter = CustomAdapter(userArrayList)

                    // Set the item click listener for the adapter
                    adapter.setOnClickListener(object : CustomAdapter.OnClickListener {
                        override fun onClick(position: Int, item: Items) {
                            // Handle the item click here
                            val intent = Intent(this@ActivityHome, ActivityItemDetails::class.java)
                            intent.putExtra("selectedItem", item)
                            startActivity(intent)
                        }
                    })


                    // Setting the Adapter with the recyclerview
                    recyclerView.adapter = adapter
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@ActivityHome,error.toString(),Toast.LENGTH_SHORT).show()
            }
        })


    }

    // Override onSupportNavigateUp to open the drawer
    override fun onSupportNavigateUp(): Boolean {
        drawerLayout.openDrawer(navView)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START)
            return true
        }
        return super.onOptionsItemSelected(item)
    }
    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        actionBarToggle.syncState()
    }

}