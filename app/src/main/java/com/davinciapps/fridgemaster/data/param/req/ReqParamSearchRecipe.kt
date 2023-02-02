package com.davinciapps.fridgemaster.data.param.req

import com.davinciapps.fridgemaster.data.model.FreezerItem

class ReqParamSearchRecipe constructor(
    searchItems: List<FreezerItem>
): RequestParam() {
    init {
        if(searchItems.isNotEmpty()) {
            addParam("searchItems", searchItems)
        }
    }
}