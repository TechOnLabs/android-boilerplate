package com.techonlabs.androidboilerplate

import com.techonlabs.androidboilerplate.datalayer.local.FoodDao
import com.techonlabs.androidboilerplate.utils.baseComponents.BaseViewModel
import com.techonlabs.androidboilerplate.utils.extensions.then
import timber.log.Timber

class MainVM(val foodDao: FoodDao) : BaseViewModel() {

    fun fillDb() {
        load { Timber.tag("Foood").i("Working")
            foodDao.insert(foodTypes.mapIndexed { index, s ->   FoodEntity(id = index, name = s) }) }then {it}
    }
}


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