package com.totalwar.warhammer.util.dispatcher

import kotlinx.coroutines.Dispatchers

class DefaultDispatcherProvider : CoroutineDispatcherProvider {
    override val io = Dispatchers.IO
    override val default = Dispatchers.Default
    override val main = Dispatchers.Main
}