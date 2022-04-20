package com.thiennguyen.survey.utils

import androidx.annotation.MainThread
import androidx.annotation.Nullable
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import timber.log.Timber
import java.util.concurrent.atomic.AtomicBoolean


/**
 * A lifecycle-aware observable that sends only new updates after subscription, used for events like
 * navigation or snack bar messages.
 * This avoids a common problem with events: on configuration change (like rotation) an update
 * can be emitted if the observer is active. This LiveData only calls the observable if there's an
 * explicit call to setValue() or call().
 * Note that only one observer is going to be notified of changes.
 */
class SingleLiveData<T> : MutableLiveData<T>() {
    companion object {
        const val TAG = "SingleLiveEvent"
    }

    private val mPending: AtomicBoolean = AtomicBoolean(false)

    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {

        if (hasActiveObservers()) {
            Timber.d("Multiple observers registered but only one will be notified of changes.")
        }
        // Observe the internal MutableLiveData
        super.observe(
            owner
        ) {
            val compareAndSet = mPending.compareAndSet(true, false)
            if (compareAndSet) {
                observer.onChanged(it)
            }
        }
    }

    @MainThread
    override fun setValue(@Nullable t: T?) {
        mPending.set(true)
        super.setValue(t)
    }

    override fun postValue(value: T) {
        mPending.set(true)
        super.postValue(value)
    }
}
