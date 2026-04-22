package com.example.gestionalmacen

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @FormUrlEncoded
    @POST("login.php")
    fun login(
        @Field("usuario") usuario: String,
        @Field("password") password: String
    ): Call<RespuestaServidor> // CAMBIADO

    @FormUrlEncoded
    @POST("insertar.php")
    fun insertarProducto(
        @Field("nombre") nombre: String,
        @Field("precio") precio: Double
    ): Call<RespuestaServidor> // CAMBIADO

    @GET("consultar.php")
    fun obtenerProductos(): Call<List<Producto>>

    @FormUrlEncoded
    @POST("actualizar.php")
    fun actualizarProducto(
        @Field("id") id: Int,
        @Field("nombre") nombre: String,
        @Field("precio") precio: Double
    ): Call<RespuestaServidor> // CAMBIADO

    @FormUrlEncoded
    @POST("eliminar.php")
    fun eliminarProducto(
        @Field("id") id: Int
    ): Call<RespuestaServidor> // CAMBIADO

    @GET("buscar.php")
    fun buscarProducto(@Query("id") id: Int): Call<Producto>
}