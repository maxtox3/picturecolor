package com.picturecolor.client.coroutines

import com.badoo.reaktive.single.Single
import com.badoo.reaktive.single.single
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

fun <T> singleFromCoroutine(context: CoroutineDispatcher = Dispatchers.Unconfined, block: suspend () -> T): Single<T> =
    single { emitter ->
        launchCoroutine(
          context = context,
          onSuccess = emitter::onSuccess,
          onError = emitter::onError,
          block = block
        )
            .also(emitter::setDisposable)
    }

fun <T> (suspend () -> T).asSingle(context: CoroutineDispatcher = Dispatchers.Unconfined): Single<T> =
  singleFromCoroutine(context, this)