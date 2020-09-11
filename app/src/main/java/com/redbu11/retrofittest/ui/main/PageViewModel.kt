package com.redbu11.retrofittest.ui.main

import android.app.Application
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.*
import com.google.gson.JsonElement
import com.redbu11.retrofittest.api.*
import com.redbu11.retrofittest.datamodels.Post
import com.redbu11.retrofittest.ui.main.enums.TAB_TITLES
import kotlinx.coroutines.launch
import retrofit2.Response
import kotlin.random.Random

class PageViewModel(application: Application) : AndroidViewModel(application), Observable {

    private val apiInterface: ApiInterface = ApiRequestProvider.getRetrofitInstance()

    private val _index = MutableLiveData<Int>()

    fun setIndex(index: Int) {
        _index.value = index
    }

    @Bindable
    val text: LiveData<String> = Transformations.map(_index) {
//        "Hello world from section: $it"
        when (it) {
            1 -> "Simple GET request"
            2 -> "Simple POST request"
            3 -> "Simple PUT request"
            4 -> "Simple PATCH request"
            5 -> "Simple DELETE request"
            else -> "Unknown text for _index: $it"
        }

    }

    @Bindable
    val requestResponse = MutableLiveData<String>()

    @Bindable
    val buttonText: LiveData<String> = Transformations.map(_index) {
        "Execute ${application.resources.getString(TAB_TITLES[_index.value!! - 1])} request"
    }

    /**
     * Execute a request, depending on the current active tab
     */
    fun executeRequest() = viewModelScope.launch {
        requestResponse.value = "Requesting..."

        val response: Response<JsonElement> = when (_index.value) {
            1 -> apiInterface.getPosts()
            2 -> apiInterface.uploadPost(
                Post(
                    Random.nextInt(10),
                    Random.nextInt(10),
                    "title ${Random.nextInt(10)}",
                    "body ${Random.nextInt(10)}"
                )
            )
            3 -> apiInterface.updatePost(1, Post(
                Random.nextInt(10),
                Random.nextInt(10),
                "new_title ${Random.nextInt(10)}",
                "new_body ${Random.nextInt(10)}"
            ))
            4 -> apiInterface.updatePostWithPATCH(1, Post(
                null,
                null,
                "new_title ${Random.nextInt(10)}",
                null
            ))
            5 -> apiInterface.deletePost(1)
            else -> apiInterface.getPosts()
        }
        requestResponse.value = "Raw response:\n\n${response.toString()} \n\n${response.body().toString()}"
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}