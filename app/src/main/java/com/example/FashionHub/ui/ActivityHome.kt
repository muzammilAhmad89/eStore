package com.example.FashionHub.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.FashionHub.adapters.CustomAdapter
import com.facebook.shimmer.ShimmerFrameLayout
import androidx.fragment.app.Fragment
import com.example.FashionHub.data.Items
import com.example.FashionHub.fragments.fragment_like
import com.example.fashionhub.R
import com.google.android.material.bottomnavigation.BottomNavigationView
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
    private lateinit var shimmer: ShimmerFrameLayout
    lateinit var bottomNav : BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        bottomNav = findViewById(R.id.bottomNav) as BottomNavigationView
        bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    startActivity(Intent(this, ActivityHome::class.java))
                    true
                }
                R.id.like -> {
                    loadFragment(fragment_like())
                    true
                }
                R.id.cart -> {
                    startActivity(Intent(this,ActivityCart::class.java))
                    true
                }
                R.id.notification -> {
                    true
                }
                else -> {
                    Toast.makeText(this,"error",Toast.LENGTH_SHORT).show()
                    true
                }
            }
        }

        val cardViewWomen: MaterialCardView = findViewById(R.id.user)

        cardViewWomen.setOnClickListener {

            val intent = Intent(this@ActivityHome, ActivityCart::class.java)
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
        actionBarToggle = ActionBarDrawerToggle(this, drawerLayout,
            R.string.open_drawer,
            R.string.close_drawer
        )
        drawerLayout.addDrawerListener(actionBarToggle)

        // Set up the navigation item click listener
        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home -> {
                    // Handle Home click
                    startActivity(Intent(this@ActivityHome, ActivityHome::class.java))
                    true
                }
                R.id.nav_profile -> {
                    // Handle Profile click
                    startActivity(Intent(this@ActivityHome, ActivityCurrentUser::class.java))
                    true
                }
                R.id.nav_settings -> {
                    // Handle Settings click

                    startActivity(Intent(this@ActivityHome, ActivityAdminPanel::class.java))
                    true
                }
                else -> false
            }
        }


        val layoutManager=GridLayoutManager(this,2)

        // getting the recyclerview by its id
        recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        shimmer=findViewById(R.id.shimmer)
        shimmer.startShimmer()

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
                    shimmer.stopShimmer()
                    shimmer.visibility=View.GONE
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

    private fun loadFragment(fragment: Fragment) {

        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container,fragment)
        transaction.commit()
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