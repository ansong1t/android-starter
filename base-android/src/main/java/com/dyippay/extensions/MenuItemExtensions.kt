package com.dyippay.extensions

import android.view.MenuItem

fun MenuItem.setActionViewExpanded(expanded: Boolean) {
    if (expanded != isActionViewExpanded) {
        if (expanded) expandActionView() else collapseActionView()
    }
}
