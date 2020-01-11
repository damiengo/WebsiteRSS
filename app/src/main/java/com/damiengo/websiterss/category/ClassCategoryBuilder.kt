package com.damiengo.websiterss.category

import com.damiengo.websiterss.R
import javax.inject.Inject

class ClassCategoriesBuilder @Inject constructor() : CategoriesBuilder  {

    override fun build(): Map<Int, Category> {
        return mapOf(
            R.id.nav_actu       to Category("Actualité",  "http://www.lequipe.fr/rss/actu_rss.xml"),
            R.id.nav_foot       to Category("Football",   "http://www.lequipe.fr/rss/actu_rss_Football.xml"),
            R.id.nav_transferts to Category("Transferts", "http://www.lequipe.fr/rss/actu_rss_Transferts.xml"),
            R.id.nav_basket     to Category("Basket",     "http://www.lequipe.fr/rss/actu_rss_Basket.xml"),
            R.id.nav_tennis     to Category("Tennis",     "http://www.lequipe.fr/rss/actu_rss_Tennis.xml"),
            R.id.nav_rugby      to Category("Rugby",      "http://www.lequipe.fr/rss/actu_rss_Rugby.xml"),
            R.id.nav_cyclisme   to Category("Cyclisme",   "http://www.lequipe.fr/rss/actu_rss_Cyclisme.xml"),
            R.id.nav_automoto   to Category("Auto-Moto",  "http://www.lequipe.fr/rss/actu_rss_Auto-Moto.xml"),
            R.id.nav_handball   to Category("Handball",   "http://www.lequipe.fr/rss/actu_rss_Hand.xml"),
            R.id.nav_athle      to Category("Athlétisme", "http://www.lequipe.fr/rss/actu_rss_Athletisme.xml"),
            R.id.nav_ski        to Category("Ski",        "http://www.lequipe.fr/rss/actu_rss_Ski.xml"),
            R.id.nav_voile      to Category("Voile",      "http://www.lequipe.fr/rss/actu_rss_Voile.xml")
        )
    }

}