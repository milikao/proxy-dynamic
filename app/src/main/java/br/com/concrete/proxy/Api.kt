package br.com.concrete.proxy

interface Api {
    @GET
    fun myMethod(@Query("type") name : String, @Query("paras") paras : Int,
                 @Query("start-with-lorem") start : Int)
}