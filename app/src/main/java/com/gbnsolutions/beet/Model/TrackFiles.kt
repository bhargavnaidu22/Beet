package com.gbnsolutions.beet.Model

class TrackFiles {
    private var title: String = ""
    private var singer: String = ""
    private var thumbnail: Int?= null

    constructor()
    constructor(title: String, singer: String, thumbnail: Int?) {
        this.title = title
        this.singer = singer
        this.thumbnail = thumbnail
    }
    fun getTitle(): String?{
        return title
    }
    fun setTitle(title: String){
        this.title = title
    }
    fun getSinger(): String?{
        return singer
    }
    fun setSinger(singer: String){
        this.singer = singer
    }
    fun getThumbnail(): Int?{
        return thumbnail
    }
    fun setThumbnail(thumbnail: Int?){
        this.thumbnail = thumbnail
    }
}