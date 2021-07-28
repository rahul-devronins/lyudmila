package com.devronins.lyudmilatesttask.helper

import com.devronins.lyudmilatesttask.data.ClassType
import com.devronins.lyudmilatesttask.data.Gym
import com.devronins.lyudmilatesttask.data.PopularClass

object AppUtils {

    fun removeUnselectedType(classType: ClassType?) {
        classType?.run {
            var selectedTypes = PreferenceUtils.getString(PreferenceUtils.KEY_SELECTED_TYPES)
            if (selectedTypes != null && selectedTypes.contains("-${name}-")) {
                selectedTypes = selectedTypes.replace("-${name}-", "")
                PreferenceUtils.saveString(PreferenceUtils.KEY_SELECTED_TYPES, selectedTypes)
            }
        }
    }

    fun saveSelectedType(classType: ClassType?) {
        classType?.run {
            var selectedTypes = PreferenceUtils.getString(PreferenceUtils.KEY_SELECTED_TYPES)
            if (selectedTypes == null || !selectedTypes.contains("-${name}-")) {
                selectedTypes = if (selectedTypes != null) {
                    "$selectedTypes-${name}-"
                } else {
                    "-${name}-"
                }
                PreferenceUtils.saveString(PreferenceUtils.KEY_SELECTED_TYPES, selectedTypes)
            }
        }
    }

    fun removeGymItemFromFavorite(gym: Gym?) {
        gym?.run {
            var favoriteGymItems = PreferenceUtils.getString(PreferenceUtils.KEY_FAVORITE_GYM_ITEMS)
            if (favoriteGymItems != null && favoriteGymItems.contains("-${id}-")) {
                favoriteGymItems = favoriteGymItems.replace("-${id}-", "")
                PreferenceUtils.saveString(PreferenceUtils.KEY_FAVORITE_GYM_ITEMS, favoriteGymItems)
            }
        }
    }

    fun saveGymItemAsFavorite(gym: Gym?) {
        gym?.run {
            var favoriteGymItems = PreferenceUtils.getString(PreferenceUtils.KEY_FAVORITE_GYM_ITEMS)
            if (favoriteGymItems == null || !favoriteGymItems.contains("-${id}-")) {
                favoriteGymItems = if (favoriteGymItems != null) {
                    "$favoriteGymItems-${id}-"
                } else {
                    "-${id}-"
                }
                PreferenceUtils.saveString(PreferenceUtils.KEY_FAVORITE_GYM_ITEMS, favoriteGymItems)
            }
        }
    }

    fun saveClassAsFavorite(popularClass: PopularClass?) {
        popularClass?.run {
            var favoriteClass = PreferenceUtils.getString(PreferenceUtils.KEY_FAVORITE_CLASS)
            if (favoriteClass == null || !favoriteClass.contains("-${id}-")) {
                favoriteClass = if (favoriteClass != null) {
                    "$favoriteClass-${id}-"
                } else {
                    "-${id}-"
                }
                PreferenceUtils.saveString(PreferenceUtils.KEY_FAVORITE_CLASS, favoriteClass)
            }
        }
    }

    fun removeClassAsFavorite(popularClass: PopularClass?) {
        popularClass?.run {
            var favoriteClasses = PreferenceUtils.getString(PreferenceUtils.KEY_FAVORITE_CLASS)
            if (favoriteClasses != null && favoriteClasses.contains("-${id}-")) {
                favoriteClasses = favoriteClasses.replace("-${id}-", "")
                PreferenceUtils.saveString(PreferenceUtils.KEY_FAVORITE_CLASS, favoriteClasses)
            }
        }
    }

}