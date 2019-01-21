package homepunk.github.com.presentation.core.ext

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import io.reactivex.*

/**Created by Homepunk on 18.01.2019. **/
fun <T> Flowable<T>.toLiveData() : LiveData<T> {
    return LiveDataReactiveStreams.fromPublisher(this)
}

fun <T> Observable<T>.toLiveData(backPressureStrategy: BackpressureStrategy) : LiveData<T> {
    return LiveDataReactiveStreams.fromPublisher(this.toFlowable(backPressureStrategy))
}

fun <T> Single<T>.toLiveData() :  LiveData<T> {
    return LiveDataReactiveStreams.fromPublisher(this.toFlowable())
}

fun <T> Maybe<T>.toLiveData() :  LiveData<T> {
    return LiveDataReactiveStreams.fromPublisher(this.toFlowable())
}

fun <T> Completable.toLiveData() :  LiveData<T> {
    return LiveDataReactiveStreams.fromPublisher(this.toFlowable())
}