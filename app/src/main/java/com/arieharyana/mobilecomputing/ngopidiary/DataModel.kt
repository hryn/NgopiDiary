package com.arieharyana.mobilecomputing.ngopidiary

import java.io.Serializable

class DataModel : Serializable {

    var title: String? = null
    var link: String? = null
    var description: String? = null
    var modified: String? = null
    var generator: String? = null
    var items: ArrayList<ItemModel>? = null

}