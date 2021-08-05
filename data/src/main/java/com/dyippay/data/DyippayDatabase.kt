package com.dyippay.data

import com.dyippay.data.daos.AudioBookDao
import com.dyippay.data.daos.ItemDao
import com.dyippay.data.daos.MovieDao
import com.dyippay.data.daos.SongDao
import com.dyippay.data.daos.TokenDao
import com.dyippay.data.daos.TvShowDao
import com.dyippay.data.daos.UserDao

interface DyippayDatabase {
    fun itemDao(): ItemDao
    fun tvShowDao(): TvShowDao
    fun songDao(): SongDao
    fun movieDao(): MovieDao
    fun audioBookDao(): AudioBookDao
    fun tokenDao(): TokenDao
    fun userDao(): UserDao
}
