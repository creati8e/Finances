package serg.chuprin.finances.core.api.presentation.view.extensions

import androidx.recyclerview.widget.RecyclerView

/**
 * Created by Sergey Chuprin on 20.12.2020.
 */
fun RecyclerView.onScroll(onScroll: (recyclerView: RecyclerView, dx: Int, dy: Int) -> Unit) {
    addOnScrollListener(object : RecyclerView.OnScrollListener() {

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            onScroll(recyclerView, dx, dy)
        }
    })
}

fun RecyclerView.onScrollStateChanged(
    onScrollStateChanged: (recyclerView: RecyclerView, newState: Int) -> Unit
) {
    addOnScrollListener(object : RecyclerView.OnScrollListener() {

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            onScrollStateChanged(recyclerView, newState)
        }
    })
}