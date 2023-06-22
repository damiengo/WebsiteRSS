package com.damiengo.websiterss.category

import com.damiengo.websiterss.R
import javax.inject.Inject

class ClassCategoriesBuilder @Inject constructor() : CategoriesBuilder  {

    override fun build(): Map<Int, Category> {
        return mapOf(
            R.id.nav_actu       to Category("Actualité",  "https://dwh.lequipe.fr/api/edito/rss?path=/"),
            R.id.nav_foot       to Category("Football",   "https://dwh.lequipe.fr/api/edito/rss?path=/Football/"),
            R.id.nav_transferts to Category("Transferts", "https://dwh.lequipe.fr/api/edito/rss?path=/Football/Transferts-football/"),
            R.id.nav_basket     to Category("Basket",     "https://dwh.lequipe.fr/api/edito/rss?path=/Basket/"),
            R.id.nav_tennis     to Category("Tennis",     "https://dwh.lequipe.fr/api/edito/rss?path=/Tennis/"),
            R.id.nav_rugby      to Category("Rugby",      "https://dwh.lequipe.fr/api/edito/rss?path=/Rugby/"),
            R.id.nav_cyclisme   to Category("Cyclisme",   "https://dwh.lequipe.fr/api/edito/rss?path=/Cyclisme/"),
            R.id.nav_automoto   to Category("Auto-Moto",  "https://dwh.lequipe.fr/api/edito/rss?path=/Auto-moto/"),
            R.id.nav_handball   to Category("Handball",   "https://dwh.lequipe.fr/api/edito/rss?path=/Handball/"),
            R.id.nav_athle      to Category("Athlétisme", "https://dwh.lequipe.fr/api/edito/rss?path=/Athletisme/"),
            R.id.nav_ski        to Category("Ski",        "https://dwh.lequipe.fr/api/edito/rss?path=/Ski/"),
            R.id.nav_voile      to Category("Voile",      "https://dwh.lequipe.fr/api/edito/rss?path=/Voile/")
        )
    }

}