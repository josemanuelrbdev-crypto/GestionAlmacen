<?php
$conexion = mysqli_connect("localhost", "root", "", "gestion_almacen");

$user = $_POST['usuario'];
$pass = $_POST['password'];

$consulta = "SELECT * FROM usuarios WHERE usuario = '$user' AND password = '$pass'";
$resultado = mysqli_query($conexion, $consulta);

if($fila = mysqli_fetch_assoc($resultado)){
    echo "success";
} else {
    echo "error";
}

mysqli_close($conexion);
?>