package com.braly.researchproject

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.transition.Transition
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

fun CoroutineScope.launchCoroutineWithHandler(
    dispatcher: CoroutineDispatcher = Dispatchers.Default,
    errorBlock: (() -> Unit)? = null,
    body: suspend (CoroutineScope) -> Unit
): Job {
    return launch(dispatcher + CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
        errorBlock?.invoke()
    }) {
        body.invoke(this)
    }
}

fun preloadImage(
    uri: Int,
    onResourceReady: (Drawable) -> Unit,
    context: Context
) {
    Glide.with(context)
        .load(uri)
        .centerCrop()
        .encodeFormat(Bitmap.CompressFormat.PNG)
        .encodeQuality(100)
        .skipMemoryCache(false)
        .into(object : CustomTarget<Drawable>() {
            override fun onResourceReady(
                resource: Drawable,
                transition: com.bumptech.glide.request.transition.Transition<in Drawable>?
            ) {
                onResourceReady.invoke(resource)
            }

            override fun onLoadCleared(placeholder: Drawable?) = Unit
        })
}