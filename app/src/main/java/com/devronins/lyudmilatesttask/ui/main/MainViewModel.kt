package com.devronins.lyudmilatesttask.ui.main

import android.content.Context
import android.util.Log
import androidx.databinding.ObservableArrayList
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devronins.lyudmilatesttask.R
import com.devronins.lyudmilatesttask.data.ClassType
import com.devronins.lyudmilatesttask.data.Gym
import com.devronins.lyudmilatesttask.data.GymResponse
import com.devronins.lyudmilatesttask.data.PopularClass
import com.devronins.lyudmilatesttask.helper.PreferenceUtils
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import java.io.IOException

class MainViewModel : ViewModel() {

    val showProgressBar = MutableLiveData(false)
    val errorMessage = MutableLiveData<String?>()

    val gyms = ArrayList<Gym>()
    val gymItems = ObservableArrayList<Gym>()

    val popularClasses = ObservableArrayList<PopularClass>()

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        showProgressBar.postValue(false)
        errorMessage.postValue(exception.localizedMessage)
    }

    fun parseAssetJsonData(context: Context) {
        viewModelScope.launch(exceptionHandler) {
            showProgressBar.postValue(true)
            try {
                val json = context.assets.open("data.json").bufferedReader().use { it.readText() }
                val response = Gson().fromJson(json, GymResponse::class.java)
                response?.gyms?.run {
                    gyms.clear()
                    gyms.addAll(this)

                    val favoriteGymItems =
                        PreferenceUtils.getString(PreferenceUtils.KEY_FAVORITE_GYM_ITEMS)
                    gyms.forEach {
                        val title = it.id
                        if (favoriteGymItems != null && favoriteGymItems.contains("-${title}-")) {
                            it.favorite = true
                        }
                    }

                    gymItems.clear()
                    gymItems.addAll(gyms)

                    popularClasses.clear()
                    var i = 1
                    gymItems[0].popular_clasess.forEach {
                        it.id = i
                        val favoriteClasses =
                            PreferenceUtils.getString(PreferenceUtils.KEY_FAVORITE_CLASS)
                        if (favoriteClasses != null && favoriteClasses.contains("-${i}-")) {
                           it.favorite = true
                        }
                        popularClasses.add(it)
                        i++
                    }
                }
            } catch (ex: IOException) {
                ex.printStackTrace()
            }
            showProgressBar.postValue(false)
        }
    }

    val classTypes = ObservableArrayList<ClassType>()
    fun prepareClassesType(context: Context) {
        val types = context.resources.getStringArray(R.array.types)
        classTypes.clear()
        types.forEach {
            val type = ClassType()
            type.name = it
            val selectedTypes = PreferenceUtils.getString(PreferenceUtils.KEY_SELECTED_TYPES)
            type.selected = selectedTypes != null && selectedTypes.contains("-${it}-")
            classTypes.add(type)
        }
    }

    fun addMoreGymItems() {
        showProgressBar.postValue(true)
        gyms.forEach {
            val item = Gym()
            item.id = (gymItems[gymItems.size - 1].id ?: 0) + 1
            item.title = it.title
            item.rating = it.rating
            item.price = it.price
            item.date = it.date

            val favoriteGymItems = PreferenceUtils.getString(PreferenceUtils.KEY_FAVORITE_GYM_ITEMS)
            val id = item.id
            item.favorite = favoriteGymItems != null && favoriteGymItems.contains("-${id}-")

            gymItems.add(item)
        }
        showProgressBar.postValue(false)
    }

}