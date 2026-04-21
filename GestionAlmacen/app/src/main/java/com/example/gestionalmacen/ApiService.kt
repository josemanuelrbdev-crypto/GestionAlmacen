package com.example.gestionalmacen

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET // <--- ESTA ES LA QUE FALTA
import retrofit2.http.POST
import retrofit2.http.Query // <--- AGREGA ESTA LÍNEA




interface ApiService {

    @FormUrlEncoded
    @POST("login.php")
    fun login(
        @Field("usuario") usuario: String,
        @Field("password") password: String
    ): Call<String>

    @FormUrlEncoded
    @POST("insertar.php") // El nombre del archivo PHP que creaste
    fun insertarProducto(
        @Field("nombre") nombre: String,
        @Field("precio") precio: Double
    ): Call<String>

    // NUEVO: Método para traer la lista
    @GET("consultar.php")
    fun obtenerProductos(): Call<List<Producto>>

    @FormUrlEncoded
    @POST("actualizar.php")
    fun actualizarProducto(
        @Field("id") id: Int,
        @Field("nombre") nombre: String,
        @Field("precio") precio: Double
    ): Call<String>

    @FormUrlEncoded
    @POST("eliminar.php")
    fun eliminarProducto(
        @Field("id") id: Int
    ): Call<String>

    @GET("buscar.php")
    fun buscarProducto(
        @Query("id") id: Int
    ): Call<Producto> // Devuelve un solo objeto Producto, no una lista

}