import com.picturecolor.client.presentation.app.app
import react.dom.render
import kotlin.browser.document
import kotlin.browser.window

fun main() {
  window.onload = {
    render(document.getElementById("app")) {
      app()
    }
  }
}

fun Any.debugLog(text: String?) {
  if (text.isNullOrEmpty().not()) console.log("${this::class.simpleName?.toUpperCase()}: $text")
}