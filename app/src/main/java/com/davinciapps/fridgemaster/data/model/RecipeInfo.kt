package com.davinciapps.fridgemaster.data.model

import java.io.Serializable

data class RecipeInfo(
    val Calorie: String = "", //350Kcal
    val Quantity: String = "",  //4인분
    val cookingTime: String = "", //80분
    val createdAt: String = "", //2022-03-28T10:29:15+09:00
    val description: String = "", //잔칫상에 갈비찜이 빠질 수 없죠!
    val id: String = "", //50
    val imageUrl: String = "", //file.okdab.com...
    val name: String = "", //갈비찜
    val nation: String = "", //한식
    val type: String = "", //찜
    val updatedAt: String = "", //2022-03-28T10:29:15+09:00
    val link: String = "", //웹검색인 경우 Url
    val thumbnailImage: String = "" //웹검색 썸네일 이미지
) : Serializable