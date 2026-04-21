<?php
$conexion = mysqli_connect("localhost", "root", "", "gestion_almacen");

$id = $_POST['id'];

$consulta = "DELETE FROM productos WHERE id='$id'";
$resultado = mysqli_query($conexion, $consulta);

echo ($resultado) ? "Eliminado con éxito" : "Error al eliminar";
mysqli_close($conexion);
?>