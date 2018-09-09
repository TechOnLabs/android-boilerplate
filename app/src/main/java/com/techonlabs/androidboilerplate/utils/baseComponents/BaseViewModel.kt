package com.techonlabs.androidboilerplate.utils.baseComponents

import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.techonlabs.androidboilerplate.FoodEntity
import com.techonlabs.androidboilerplate.datalayer.network.Network
import com.techonlabs.androidboilerplate.datalayer.network.NetworkCallback
import com.techonlabs.androidboilerplate.utils.RequestState
import kotlinx.coroutines.experimental.CoroutineStart
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.async
import retrofit2.Response

open class BaseViewModel : ObservableViewModel() {
    protected val parentJob = Job()
    val requestState = MutableLiveData<RequestState?>()

    init {
        requestState.value = null
    }

    fun <T> load(job: Job = parentJob, loader: suspend () -> T): Deferred<T> {
        return async(parent = job, start = CoroutineStart.DEFAULT) { loader() }
    }

    infix fun <T> (() -> Deferred<Response<T>>).callback(callbackFunc: (() -> NetworkCallback<T>)) {
        Network.request(this, callbackFunc, parentJob)
    }


    fun <T> getPagedList(dataSourceFactory: DataSource.Factory<Int, T>) =
            LivePagedListBuilder(dataSourceFactory, PagedList.Config.Builder()
                    .setPageSize(20)
                    .setInitialLoadSizeHint(20)
                    .setEnablePlaceholders(false)
                    .build()).build()

    override fun onCleared() {
        super.onCleared()
        parentJob.cancel()
    }


    fun setStateFetching() {
        requestState.value = RequestState.Fetching
    }

    fun setStateSuccess() {
        requestState.value = RequestState.Success
    }

    fun setStateFailed() {
        requestState.value = RequestState.Failed
    }

    fun setStateNetworkFail() {
        requestState.value = RequestState.NetworkFail
    }

}

/**
 * An [Observable] [ViewModel] for Data Binding.
 */
open class ObservableViewModel : ViewModel(), Observable {

    private val callbacks: PropertyChangeRegistry by lazy { PropertyChangeRegistry() }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback) {
        callbacks.add(callback)
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback) {
        callbacks.remove(callback)
    }

    /**
     * Notifies listeners that all properties of this instance have changed.
     */
    @Suppress("unused")
    fun notifyChange() {
        callbacks.notifyCallbacks(this, 0, null)
    }

    /**
     * Notifies listeners that a specific property has changed. The getter for the property
     * that changes should be marked with [Bindable] to generate a field in
     * `BR` to be used as `fieldId`.
     *
     * @param fieldId The generated BR id for the Bindable field.
     */
    fun notifyPropertyChanged(fieldId: Int) {
        callbacks.notifyCallbacks(this, fieldId, null)
    }
}