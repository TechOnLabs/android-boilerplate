package com.techonlabs.androidboilerplate.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.techonlabs.androidboilerplate.FoodEntity
import com.techonlabs.androidboilerplate.R
import com.techonlabs.androidboilerplate.databinding.FoodFragmentBinding
import com.techonlabs.androidboilerplate.datalayer.local.FoodDao
import com.techonlabs.androidboilerplate.datalayer.network.ApiInterface
import com.techonlabs.androidboilerplate.utils.baseComponents.BaseFragment
import com.techonlabs.androidboilerplate.utils.baseComponents.BaseViewModel
import com.techonlabs.androidboilerplate.utils.extensions.bindView
import com.techonlabs.androidboilerplate.utils.extensions.submitListV2
import com.techonlabs.androidboilerplate.utils.extensions.toast
import com.techonlabs.androidboilerplate.utils.recyclerView.PagedRecyclerAdapter
import com.techonlabs.androidboilerplate.utils.recyclerView.RecyclerAdapter
import com.techonlabs.androidboilerplate.utils.recyclerView.StableId
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class FoodFragment : BaseFragment() {
    override val vm by viewModel<FoodVM>()
    private lateinit var binding: FoodFragmentBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vm.fillDb()
        addRecyclerAdapter()
    }

    override fun onCreateBaseView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = container!!.bindView(R.layout.food_fragment)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.vm = vm
    }


    fun addRecyclerAdapter() {
        RecyclerAdapter(mutableMapOf(FoodEntity::class to R.layout.list_cell), this).let { adapter ->
            binding.foodListView.adapter = adapter
            vm.foodDao.get().observe(this, Observer {
                adapter.swapItems(it)
            })
        }
    }

    fun addPagedRecyclerAdapter() {
        PagedRecyclerAdapter(mutableMapOf(FoodEntity::class to R.layout.list_cell), this).let {
            binding.foodListView.adapter = it
            vm.foodList.observe(this, Observer(it::submitListV2))
        }
    }

    override fun onRecyclerItemClick(obj: StableId) {
        super.onRecyclerItemClick(obj)
        if (obj is FoodEntity)
            toast(obj.name)
    }
}

class FoodVM(val foodDao: FoodDao,
             val apiInterface: ApiInterface) : BaseViewModel() {
    fun fillDb() {
        load {
            Timber.tag("Foood").i("Working")
            foodDao.insert(foodTypes.mapIndexed { index, s -> FoodEntity(id = index, name = s) })
        }
    }

    val foodList = getPagedList(foodDao.getPaged())


    private val foodTypes = arrayListOf("pecans", "dates", "dried leeks", "chicken liver", "beans", "blueberries",
            "cayenne pepper", "orange peels", "romaine lettuce", "butter", "split peas", "dill", "lamb", "breadcrumbs", "coffee",
            "nectarines", "margarine", "rum", "sherry", "peanut butter", "red snapper", "ricotta cheese", "pine nuts", "ice cream",
            "lemon Peel", "milk", "black-eyed peas", "brandy", "bacon", "swordfish", "bard", "beets", "jicama", "cookies", "raisins",
            "salsa", "Goji berry", "peaches", "flounder", "ale", "mushrooms", "almonds", "figs", "apricots", "Romano cheese", "yogurt",
            "barbecue sauce", "chai", "won ton skins", "liver", "tofu", "bean threads", "huckleberries", "lettuce", "turnips",
            "canola oil", "prunes", "anchovies", "black beans", "cream of tartar", "celery seeds", "sour cream", "cinnamon", "macaroni",
            "Tabasco sauce", "strawberries", "bruschetta", "soy sauce", "oatmeal", "crayfish", "grouper", "celery", "half-and-half",
            "raspberries", "capers", "cranberries", "pickles", "eggplants", "blue cheese", "red pepper flakes", "snapper", "cumin",
            "crabs", "plum tomatoes", "cremini mushrooms", "feta cheese", "fennel", "olive oil", "apples", "artificial sweetener",
            "mackerel", "flour", "Mandarin oranges", "lentils", "watermelons", "garlic powder", "buttermilk", "guavas", "bass",
            "turkeys", "herring", "chutney", "mozzarella", "fish sauce", "green beans", "bourbon", "poppy seeds", "navy beans",
            "cabbage", "baguette", "moo shu wrappers", "rice wine", "plantains", "sazon", "English muffins", "brunoise", "octopus",
            "thyme", "graham crackers", "duck", "cauliflower", "corn", "sugar", "grapefruits", "wasabi", "Cappuccino Latte", "geese",
            "sea cucumbers", "rice vinegar", "powdered sugar", "apple pie spice", "pancetta", "walnuts", "colby cheese", "cottage cheese",
            "bagels", "brazil nuts", "parsley", "halibut", "ginger", "Marsala", "oranges", "vanilla", "andouille sausage",
            "red chile powder", "trout", "bouillon", "remoulade", "chambord", "oregano", "zinfandel wine", "cashew nut", "pistachios",
            "brown sugar", "horseradish", "paprika", "rice", "pumpkin seeds", "pig's feet", "couscous", "lemons", "french fries",
            "coriander", "shitakes", "bean sauce", "hoisin sauce", "potato chips", "fennel seeds", "passion fruit", "bean sprouts",
            "melons", "rhubarb", "sunflower seeds", "shallots", "tomato puree", "barley sugar", "asiago cheese", "squid", "tonic water",
            "pears", "cider vinegar", "bok choy", "spearmint", "jelly beans", "tarragon", "brussels sprouts", "pomegranates", "wine",
            "wild rice", "okra", "habanero chilies", "red beans", "curry leaves", "almond paste", "beer", "alligator", "adobo", "applesauce",
            "chili powder", "marshmallows"
    );

}
