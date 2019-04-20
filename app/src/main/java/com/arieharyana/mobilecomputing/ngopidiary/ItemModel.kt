package com.arieharyana.mobilecomputing.ngopidiary

import java.io.Serializable

class ItemModel : Serializable {

    var title: String? = null
    var link: String? = null
    var media: Media? = null
    var date_taken: String? = null
    var description: String? = null
    var published: String? = null
    var author: String? = null
    var tags: String? = null

    class Media : Serializable {
        var m: String? = null
    }

}