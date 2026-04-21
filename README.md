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
  
   - 📸 Screenshots
   - <img width="305" height="536" alt="image" src="https://github.com/user-attachments/assets/0273f338-85d4-43d1-b0d8-1b0083e40ee7" />
   <img width="305" height="526" alt="image" src="https://github.com/user-attachments/assets/13d72cb8-4a4d-4171-89a2-e490ec10ed24" />
   <img width="315" height="530" alt="image" src="https://github.com/user-attachments/assets/ff6b033c-b437-4051-8403-d5b06b25cae8" />
   <img width="294" height="528" alt="image" src="https://github.com/user-attachments/assets/182fef71-107e-4934-86cb-ca73927b7ec5" />
   <img width="294" height="533" alt="image" src="https://github.com/user-attachments/assets/40a9acd4-653a-4f85-ad5c-54848bfd878c" />






