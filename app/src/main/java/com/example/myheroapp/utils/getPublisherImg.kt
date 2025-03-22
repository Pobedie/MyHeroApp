package com.example.myheroapp.utils

import com.example.myheroapp.R

fun getPublisherImg(publisher: String): Int{
    return when(publisher){
        "Dark Horse Comics" -> R.drawable.dark_horse_comics
        "Marvel Comics" -> R.drawable.marvel_comics
        "Sharon Carter" -> R.drawable.sharon_carter
        "NBC - Heroes" -> R.drawable.nbc_heroes
        "DC Comics" -> R.drawable.dc_comics
        "Wildstorm" -> R.drawable.wildstorm
        "Goliath" -> R.drawable.goliath
        "Speedy" -> R.drawable.speedy
        "Archangel" -> R.drawable.archangel
        "Tempest" -> R.drawable.tempest
        "Image Comics" -> R.drawable.image
        "Toxin" -> R.drawable.toxin
        "Giant-Man" -> R.drawable.giant_man
        "Angel" -> R.drawable.angel
        else -> R.drawable.ic_broken_image
    }

}
