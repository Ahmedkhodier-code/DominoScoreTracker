package com.khodier.dominoscoretracker

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform