package com.picturecolor.client

fun debugLog(text: String) {
  println(text)
}

fun <T> List<T>.replace(newValue: T, block: (T) -> Boolean): List<T> {
  return map {
    if (block(it)) newValue else it
  }
}

fun <T> List<T>.addOrReplace(newValue: T, block: (T) -> Boolean): List<T> {
  val firstOrNull = this.firstOrNull(block)
  return if (firstOrNull != null) {
    debugLog("going to replace")
    replace(newValue, block)
  } else {
    debugLog("going to add $newValue")
    val mutableList = toMutableList()
    mutableList.add(newValue)
    mutableList
  }
}