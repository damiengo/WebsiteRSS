package com.damiengo.websiterss.category

import com.damiengo.websiterss.R
import javax.inject.Inject

class CategoriesBuilder @Inject constructor() {

    fun build(): Map<Int, Category> {
        return mapOf(
            R.id.nav_actu       to Category("Actualité",  "http://www.lequipe.fr/rss/actu_rss.xml"),
            R.id.nav_foot       to Category("Football",   "http://www.lequipe.fr/rss/actu_rss_Football.xml"),
            R.id.nav_transferts to Category("Transferts", "http://www.lequipe.fr/rss/actu_rss_Transferts.xml"),
            R.id.nav_basket     to Category("Basket",     "http://www.lequipe.fr/rss/actu_rss_Basket.xml"),
            R.id.nav_tennis     to Category("Tennis",     "http://www.lequipe.fr/rss/actu_rss_Tennis.xml"),
            R.id.nav_rugby      to Category("Rugby",      "http://www.lequipe.fr/rss/actu_rss_Rugby.xml"),
            R.id.nav_cyclisme   to Category("Cyclisme",   "http://www.lequipe.fr/rss/actu_rss_Cyclisme.xml")
        )
    }

}