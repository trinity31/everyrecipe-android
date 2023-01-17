package com.example.everyrecipe.data.param.req

import com.example.everyrecipe.data.model.FreezerItem

class ReqParamRecommendRecipe constructor(
    freezerItems: List<FreezerItem>
): RequestParam() {
    init {
        if(freezerItems.isNotEmpty()) {
            addParam("freezerItems", freezerItems)
        }
    }
}