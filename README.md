## 🛠️ Configuración del Backend (XAMPP + MySQL)

Para que la aplicación pueda comunicarse con la base de datos, sigue estos pasos:

1. **Base de Datos:**
   - El archivo `.txt` (o `.sql`) incluido en el repositorio contiene la estructura y los datos de la base de datos **MySQL**.
   - Importa este contenido en tu gestor (phpMyAdmin) para crear las tablas necesarias.

2. **Servidor Local (PHP):**
   - La carpeta llamada `gestiona_almacen` (que contiene los scripts PHP) debe copiarse en la ruta de instalación de XAMPP:
     `C:\xampp\htdocs\gestiona_almacen`
   - Asegúrate de que los servicios de **Apache** y **MySQL** estén activos en el Panel de Control de XAMPP.

3. **Conexión:**
   - La aplicación Android utiliza **Retrofit** para conectarse a los scripts PHP en esta carpeta, sirviendo como puente hacia la base de datos MySQL.
