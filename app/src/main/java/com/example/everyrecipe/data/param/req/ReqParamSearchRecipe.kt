package com.example.everyrecipe.data.param.req

import com.example.everyrecipe.data.model.FreezerItem

class ReqParamSearchRecipe constructor(
    searchItems: List<FreezerItem>
): RequestParam() {
    init {
        if(searchItems.isNotEmpty()) {
            addParam("searchItems", searchItems)
        }
    }
}