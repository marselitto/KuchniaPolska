package com.example.kuchniapolska.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class OrderViewModel : ViewModel() {

    private val prices = mapOf(
        "Rosół" to 10.0,
        "Pomidorowa" to 12.0,
        "Ogórkowa" to 11.0,
        "Schabowy z ziemniakami" to 25.0,
        "Mielony z ziemniakami" to 22.0,
        "Pierogi ruskie" to 20.0,
        "Woda" to 5.0,
        "Cola" to 7.0,
        "Sok jabłkowy" to 6.0
    )

    private val _soup = MutableLiveData<String?>()
    val soup: LiveData<String?> = _soup

    private val _meal = MutableLiveData<String?>()
    val meal: LiveData<String?> = _meal

    private val _drink = MutableLiveData<String?>()
    val drink: LiveData<String?> = _drink

    fun setOrder(soup: String?, meal: String?, drink: String?) {
        _soup.value = soup
        _meal.value = meal
        _drink.value = drink
    }

    fun getPrice(item: String?): Double {
        return prices[item] ?: 0.0
    }

    fun calculateTotalPrice(): Double {
        return getPrice(_soup.value) + getPrice(_meal.value) + getPrice(_drink.value)
    }

    fun clearOrder() {
        _soup.value = null
        _meal.value = null
        _drink.value = null
    }
}