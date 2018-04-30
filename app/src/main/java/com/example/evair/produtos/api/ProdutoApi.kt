package com.example.evair.produtos.api

import com.example.evair.produtos.model.Produto
import retrofit2.Call
import retrofit2.http.*

/**
 * Created by evair on 07/04/2018.
 */
interface ProdutoApi {

    @GET("/produto")
    fun getAll(): Call<List<Produto>>

    @GET("/produto/{nome}/aproximado")
    fun getContaining(@Path("nome") id: String): Call<List<Produto>>

    @POST("/produto")
    fun salvar(@Body produto: Produto): Call<Void>

    @PUT("/produto")
    fun update(@Body produto: Produto): Call<Void>

    @DELETE("/produto/{id}/deletar")
    fun delete(@Path("id") id: String): Call<Void>

}