package com.dyippay.common.navigation

import androidx.core.net.toUri
import androidx.navigation.NavOptions

fun tvShowDetailsDeeplink(collectionId: Long) =
    "com.appetizercodingchallenge://tv-show/$collectionId".toUri()

fun songDetailsDeeplink(trackId: Long) =
    "com.dyippay://song/$trackId".toUri()

fun movieDetailsDeeplink(trackId: Long) =
    "com.appetizercodingchallenge://movie/$trackId".toUri()

fun audioBookDetailsDeeplink(collectionId: Long) =
    "com.appetizercodingchallenge://audiobook/$collectionId".toUri()

fun defaultNavAnimation(block: ((NavOptions.Builder) -> Unit)? = null) =
    NavOptions.Builder().apply {
        block?.invoke(this)
    }.setEnterAnim(R.anim.enter_anim)
        .setExitAnim(R.anim.exit_anim)
        .setPopEnterAnim(R.anim.pop_enter_anim)
        .setPopExitAnim(R.anim.pop_exit_anim)
        .build()
