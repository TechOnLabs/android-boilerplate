package com.techonlabs.androidboilerplate

import android.content.res.Resources
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.techonlabs.androidboilerplate.utils.recyclerView.OnRecyclerItemClickListener
import timber.log.Timber

class MainActivity : AppCompatActivity(), OnRecyclerItemClickListener {
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        val host = supportFragmentManager
                .findFragmentById(R.id.mainHostFragment) as NavHostFragment? ?: return
        navController = host.navController

        navController.addOnNavigatedListener { _, destination ->
            val dest: String = try {
                resources.getResourceName(destination.id)
            } catch (e: Resources.NotFoundException) {
                Integer.toString(destination.id)
            }
            Timber.v("Navigated to $dest")
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Have the NavHelper look for an action or destination matching the menu
        // item id and navigate there if found.
        // Otherwise, bubble up to the parent.
        return NavigationUI.onNavDestinationSelected(item,
                Navigation.findNavController(this, R.id.mainHostFragment))
                || super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp() =
            Navigation.findNavController(findViewById(R.id.mainHostFragment)).navigateUp()

}
