package com.lukianbat.urbanist.urbanist_guide.feature.list.presentation

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.lukianbat.urbanist.urbanist_guide.feature.list.domain.model.Body
import com.lukianbat.urbanist.urbanist_guide.feature.list.domain.repository.PlaceListRepository
import com.lukianbat.urbanist.urbanist_guide.сore.domain.PreferenceRepository
import com.lukianbat.urbanist.urbanist_guide.сore.presentation.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class PlaceListViewModel @Inject constructor(
    private val placeListRepository: PlaceListRepository,
    private val preferenceRepository: PreferenceRepository
) : BaseViewModel() {
    lateinit var eventsListener: EventsListener
    val liveData = MutableLiveData<List<Body>>()

    init {
        getPlaces()
    }

    @SuppressLint("CheckResult")
    fun getPlaces() {
        val city = preferenceRepository.getCity()
        placeListRepository.getPlaces(city.lat, city.lng)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe({ result ->
                liveData.value = result.body
            }, { error ->
                eventsListener.showMessage(error.message.toString())
            })
    }

    interface EventsListener {
        fun routeToMap()
        fun showMessage(message: String)
    }
}