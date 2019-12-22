package com.picturecolor.client.newmvi

interface BaseView<E : BaseEvent> {
  fun dispatch(event: E)
}

interface BaseEvent