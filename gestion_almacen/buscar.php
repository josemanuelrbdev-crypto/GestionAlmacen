<?php
$conexion = mysqli_connect("localhost", "root", "", "gestion_almacen");

// Usamos GET para buscar
$id = $_GET['id'];

$consulta = "SELECT * FROM productos WHERE id = '$id'";
$resultado = mysqli_query($conexion, $consulta);

if($fila = mysqli_fetch_assoc($resultado)){
    // Convertimos el precio a número para Android
    $fila['precio'] = (float)$fila['precio'];
    echo json_encode($fila);
} else {
    // Si no existe, devolvemos null
    echo json_encode(null);
}

mysqli_close($conexion);
?>