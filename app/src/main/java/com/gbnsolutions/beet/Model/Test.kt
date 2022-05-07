package com.gbnsolutions.beet.Model

class Test {
    private var name: String = ""
    private var songsCategory: String = ""
    private var url: String = ""
    constructor()
    constructor(name: String,songsCategory: String,url: String){
        this.name = name
        this.songsCategory = songsCategory
        this.url = url
    }
    fun getName(): String? {
        return name
    }
    fun setName(name: String){
        this.name = name
    }
    fun getSongscategory(): String?{
        return songsCategory
    }
    fun setSongscategory(songsCategory: String){
        this.songsCategory = songsCategory
    }
    fun getUrl(): String?{
        return url
    }
    fun setUrl(url: String){
        this.url = url
    }
}