package com.techonlabs.androidboilerplate.utils.baseComponents

import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import androidx.lifecycle.ViewModel
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.techonlabs.androidboilerplate.FoodEntity
import kotlinx.coroutines.experimental.CoroutineStart
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.async

open class BaseViewModel : ObservableViewModel() {
    protected val parentJob = Job()

    fun <T> load(job: Job = parentJob, loader: suspend () -> T): Deferred<T> {
        return async(parent = job, start = CoroutineStart.DEFAULT) { loader() }
    }

    fun <T> getPagedList(dataSourceFactory: DataSource.Factory<Int, T>) =
            LivePagedListBuilder(dataSourceFactory, PagedList.Config.Builder()
                    .setPageSize(10)
                    .setInitialLoadSizeHint(10)
                    .setEnablePlaceholders(false)
                    .build()).build()

    override fun onCleared() {
        super.onCleared()
        parentJob.cancel()
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