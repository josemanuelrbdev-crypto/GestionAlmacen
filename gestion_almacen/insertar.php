<?php
$conexion = mysqli_connect("localhost", "root", "", "gestion_almacen");

if (!$conexion) {
    echo "Error de conexión";
    exit;
}

$nombre = $_POST['nombre'];
$precio = $_POST['precio'];

$consulta = "INSERT INTO productos (nombre, precio) VALUES ('$nombre', '$precio')";
$resultado = mysqli_query($conexion, $consulta);

if ($resultado) {
    echo "Producto insertado con éxito";
} else {
    echo "Error al insertar";
}

mysqli_close($conexion);
?>