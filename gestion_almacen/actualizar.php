<?php
$conexion = mysqli_connect("localhost", "root", "", "gestion_almacen");

$id = $_POST['id'];
$nombre = $_POST['nombre'];
$precio = $_POST['precio'];

$consulta = "UPDATE productos SET nombre='$nombre', precio='$precio' WHERE id='$id'";
$resultado = mysqli_query($conexion, $consulta);

echo ($resultado) ? "Actualizado con éxito" : "Error al actualizar";
mysqli_close($conexion);
?>