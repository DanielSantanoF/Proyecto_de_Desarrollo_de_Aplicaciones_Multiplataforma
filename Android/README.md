# Proyecto_de_Desarrollo_de_Aplicaciones_Multiplataforma_Android
Este proyecto consiste en la implementación de una app para Android que da acceso a los usuarios para asi poder interactuar entre ellos y de esta manera llegar al objetivo del proyecto, que es tratar de hacer algo en contra de la soledad que día a día afecta a las personas mayores de nuestro país.

#### Instalar la aplicación
* O bien instalarla mediante el ide
* O usar el apk de la aplicación localizado en `/app/release` el archivo `app-release.apk`

***

### Datos de acceso de prueba
#### Usuario
* Username: username
* Contraseña: 123456
#### Admin
* Username: admin
* Contraseña: 123456

***

#### ApiRest:
* La aplicación cuenta con el perfil de usuario desde donde se puede gestionar todo lo necesario
* Listados de usuarios, servicios y localizaciones
* Todas las opciones CRUD de cada listado asi como modelo implementado en android

***

#### Firebase:
* La aplicación cuenta con el login de Google con Firebase

***

#### Google Maps:
* Se usa para mostrar la ciudad donde se encuentra la localización ofrecida por el usuario.

***

#### Configurar URL del api en la app
La url del api se encuentra en el fichero `/app/src/main/java/com/dsantano/proyectodam/common/Constants.java` la variable `NODE_API_URL` [ver fichero](https://github.com/DanielSantanoF/Proyecto_de_Desarrollo_de_Aplicaciones_Multiplataforma/blob/master/Android/app/src/main/java/com/dsantano/proyectodam/common/Constants.java), en este caso se encuentra desplegada una api en heroku para poder usarse con la aplicación de android en la url: `https://dsantanoproyectodam.herokuapp.com`