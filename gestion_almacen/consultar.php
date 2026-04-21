<?php
$conexion = mysqli_connect("localhost", "root", "", "gestion_almacen");

if (!$conexion) {
    die("Error de conexión");
}

$consulta = "SELECT * FROM productos";
$resultado = mysqli_query($conexion, $consulta);

$productos = array();

while($fila = mysqli_fetch_assoc($resultado)){
    // Cast de precio a float para que Android lo reconozca como número
    $fila['precio'] = (float)$fila['precio'];
    $productos[] = $fila;
}

echo json_encode($productos);

mysqli_close($conexion);
?>