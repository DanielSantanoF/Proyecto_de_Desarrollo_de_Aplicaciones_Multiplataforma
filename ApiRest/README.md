# Proyecto DAM
Api Rest en NodeJs con MongoDB y Mongoose para el Proyecto de Desarrollo de Aplicaciones Multiplataforma.
* Se encuentra despleguada en la url `https://dsantanoproyectodam.herokuapp.com/`
* En el repositorio se encuentra una colección de postman para el uso de esta ApiRest en formato .json [Ver documento](https://github.com/DanielSantanoF/Proyecto_de_Desarrollo_de_Aplicaciones_Multiplataforma/blob/master/ProyectoDAM.postman_collection.json) que puedes importar a postman para su uso
***


#### Tecnologías usadas:
* JavaScript 6
* [NodeJs](https://nodejs.org/en/)
* [Mongoose](https://mongoosejs.com/)
* [npm](https://www.npmjs.com/)
* IDE: [Visual Studio Code](https://code.visualstudio.com/) necesario para arrancar la api rest (se debe arrancar desde el IDE)

***


#### Variables de entorno necesarias en un archivo .env para el Api Rest:
* `MONGODB_URI` url de acceso a mongodb
* `JWT_SECRET` string que es el secreto del JWT
* `BCRYPT_ROUNDS` 12 por ejemplo
* `JWT_LIFETIME` duración del JWT indicada en segundos
* `JWT_ALGORITHM` HS256 por ejemplo

***


#### Usar la Api Rest:
* En local de la siguiente manera: 

    Tener mongo disponible en local e indicar las variables de entorno del proyecto en un archivo `.env`
    Importar el proyecto en VSC abrir un nuevo terminal y ejecutar `npm start`
    Se arranca en el puerto `localhost:3000`

* La api rest en remoto

    Se encuentra despleguada en heroku su url de acceso es `https://dsantanoproyectodam.herokuapp.com/` 

***


# Endpoints

## Autenticación

### Registrarse en el Api Rest

| Tipo/URL    | POST => `/api/register`            |
| ----------- |:----------------------------------:|
| Comentarios | Petición Multiparte para registrar un usuario |
| Cabeceras   | `Content-Type: application/json`   |
| Form-data   | Datos de un nuevo usuario          |
| Respuesta/s | 201 Created => Devuelve los datos del usuario |


#### Form-data y respuesta del Endpoint
* Form-data:
```
avatar: archivo de imagen de la foto del usuario
username: username del usuario
email: email del usuario
name: nombre del usuario
password: contraseña del usuario
confirmPassword: confirmar la contraseña del usuario
phone: numero de telefono del usuario
typeUser: tipo que es el usuario ['BUSCA_COMPANIA', 'OFRECE_ALOJAMIENTO', 'JOVEN', 'OFRECE_SERVICIO']
dateOfBirth: fecha de nacimiento del usuario en formato "YYYY-MM-DD"
```
* Respuesta:
```json
{
    "id": "5dfb254d3f50834e9c920213",
    "name": "usernameFullname",
    "username": "usuario",
    "email": "usuario@email.com"
}
```

***

### Login en el Api Rest

| Tipo/URL    | POST => `/api/login` |
| ----------- |:----------------------------------:|
| Comentarios | Petición para loguear un usuario |
| Cabeceras   | `Content-Type: application/json` |
| Cuerpo      | Username y contraseña |
| Respuesta/s | 200 Ok => Devuelve datos del login |


#### Cuerpo y respuesta del Endpoint
* Cuerpo:
```json
{
	"username": "usuario",
	"password": "1234"
}
```
* Respuesta:
```json
{
    "username": "dsantano",
    "role": "ADMIN",
    "type_user": "JOVEN",
    "id": "5e7488bd454fe922eb5dc865",
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI1ZTc0ODhiZDQ1NGZlOTIyZWI1ZGM4NjUiLCJleHAiOjE1ODUyOTY5MjY0MzgsInVzZXJuYW1lIjoiZHNhbnRhbm8ifQ.09__dBPFw4e1AgSky6ByhHA_nsyZgYzrhWmvhUo5dnM"
}
```

***

### Editar validado en usuario

| Tipo/URL    | PUT => `api/users/admin/validate/`            |
| ----------- |:----------------------------------:|
| Comentarios | Petición PUT para editar validated de usuario |
| Cabeceras   | `Content-Type: application/json con role ADMIN Token JWT`   |
| Parametros  | ID del usuario |
| Respuesta/s | 200 Ok => Devuelve los nuevos datos del usuario |


#### Respuesta del Endpoint
* Respuesta:
```json
{
    "validated": true,
    "active": true,
    "location_offered": null,
    "favorite_users": [],
    "_id": "5e7483854c15af1cedb81763",
    "username": "userUpdated",
    "email": "update@gmail.com",
    "phone": "657454545",
    "name": "NameUpdated",
    "role": "USER",
    "type_user": "OFRECE_ALOJAMIENTO",
    "password": "$2a$12$j8FdACKk93lIcYuyuaJRm.tk5A67XqtcPTevKF2mtNYkskwxkpzRu",
    "date_of_birth": "1999-03-17T00:00:00.000Z",
    "register_date": "2020-03-20T08:49:09.352Z",
    "avatar": {
        "_id": "5e7483854c15af1cedb81764",
        "data": "imageData",
        "contentType": "image/jpeg"
    },
    "__v": 0
}
```

***

### Editar activo en usuario

| Tipo/URL    | PUT => `api/users/admin/active/`            |
| ----------- |:----------------------------------:|
| Comentarios | Petición PUT para editar active de usuario |
| Cabeceras   | `Content-Type: application/json con role ADMIN Token JWT`   |
| Parametros  | ID del usuario |
| Respuesta/s | 200 Ok => Devuelve los nuevos datos del usuario |


#### Respuesta del Endpoint
* Respuesta:
```json
{
    "validated": true,
    "active": true,
    "location_offered": null,
    "favorite_users": [],
    "_id": "5e7483854c15af1cedb81763",
    "username": "userUpdated",
    "email": "update@gmail.com",
    "phone": "657454545",
    "name": "NameUpdated",
    "role": "USER",
    "type_user": "OFRECE_ALOJAMIENTO",
    "password": "$2a$12$j8FdACKk93lIcYuyuaJRm.tk5A67XqtcPTevKF2mtNYkskwxkpzRu",
    "date_of_birth": "1999-03-17T00:00:00.000Z",
    "register_date": "2020-03-20T08:49:09.352Z",
    "avatar": {
        "_id": "5e7483854c15af1cedb81764",
        "data": "imageData",
        "contentType": "image/jpeg"
    },
    "__v": 0
}
```

***

## Usuarios Genericos

### Obtener todos los usuarios

| Tipo/URL    | GET => `/api/users`            |
| ----------- |:----------------------------------:|
| Comentarios | Petición para obtener todos los usuarios |
| Cabeceras   | `Content-Type: application/json con Token JWT`   |
| Respuesta/s | 200 Ok => Devuelve los datos de todos los usuarios |

#### Respuesta del Endpoint
* Respuesta:
```json
[
    {
        "validated": false,
        "active": true,
        "location_offered": null,
        "favorite_users": [],
        "_id": "5e7483854c15af1cedb81763",
        "username": "userUpdated",
        "email": "update@gmail.com",
        "phone": "657454545",
        "name": "NameUpdated",
        "role": "USER",
        "type_user": "OFRECE_ALOJAMIENTO",
        "password": "$2a$12$j8FdACKk93lIcYuyuaJRm.tk5A67XqtcPTevKF2mtNYkskwxkpzRu",
        "date_of_birth": "1999-03-17T00:00:00.000Z",
        "avatar": {
            "_id": "5e7483854c15af1cedb81764",
            "data": "imageData",
            "contentType": "image/jpeg"
        }
    },
    {
        "validated": false,
        "active": true,
        "location_offered": null,
        "favorite_users": [],
        "_id": "5e7483914c15af1cedb81765",
        "username": "dsantano4",
        "email": "santano4@email.com",
        "phone": "657464646",
        "name": "Daniel Santano4",
        "role": "USER",
        "type_user": "JOVEN",
        "password": "$2a$12$7izlzCaNS7RRQFm9TGf2Ke1SI8DY.JsOnrnepMwVrsvFuD0JdsMIm",
        "date_of_birth": "1999-11-07T00:00:00.000Z",
        "avatar": {
            "_id": "5e7483914c15af1cedb81766",
            "data": "imageData",
            "contentType": "image/jpeg"
        }
    }
]
```

***

### Obtener usuario por su id

| Tipo/URL    | GET => `/api/users/:id`            |
| ----------- |:----------------------------------:|
| Comentarios | Petición para obtener los datos de un usuario según su id |
| Cabeceras   | `Content-Type: application/json con Token JWT`   |
| Parametros  | ID del usuario |
| Respuesta/s | 200 Ok => Devuelve los datos de los usuarios|


#### Respuesta del Endpoint
* Respuesta:
```json
{
    "validated": false,
    "location_offered": null,
    "favorite_users": [],
    "_id": "5e7483914c15af1cedb81765",
    "username": "dsantano4",
    "email": "santano4@email.com",
    "phone": "657464646",
    "name": "Daniel Santano4",
    "role": "USER",
    "type_user": "JOVEN",
    "password": "$2a$12$7izlzCaNS7RRQFm9TGf2Ke1SI8DY.JsOnrnepMwVrsvFuD0JdsMIm",
    "date_of_birth": "1999-11-07T00:00:00.000Z",
    "avatar": {
        "_id": "5e7483914c15af1cedb81766",
        "data": "imageData",
        "contentType": "image/jpeg"
    }
}
```

***

### Editar un usuario por su id

| Tipo/URL    | PUT => `/api/users/:id`            |
| ----------- |:----------------------------------:|
| Comentarios | Petición PUT para editar un usuario segun su id |
| Cabeceras   | `Content-Type: application/json con role ADMIN Token JWT`   |
| Cuerpo      | Nuevos datos del usuario          |
| Parametros  | ID del usuario |
| Respuesta/s | 200 Ok => Devuelve los nuevos datos del usuario |


#### Cuerpo y respuesta del Endpoint
* Cuerpo:
```json
{
	"username": "userUpdated",
	"email": "update@gmail.com",
	"phone": "657454545",
	"name": "NameUpdated",
	"typeUser": "OFRECE_ALOJAMIENTO",
	"dateOfBirth": "1999-03-17"
}
```
* Respuesta:
```json
{
    "validated": false,
    "active": true,
    "location_offered": null,
    "favorite_users": [],
    "_id": "5e7483854c15af1cedb81763",
    "username": "userUpdated",
    "email": "update@gmail.com",
    "phone": "657454545",
    "name": "NameUpdated",
    "role": "USER",
    "type_user": "OFRECE_ALOJAMIENTO",
    "password": "$2a$12$j8FdACKk93lIcYuyuaJRm.tk5A67XqtcPTevKF2mtNYkskwxkpzRu",
    "date_of_birth": "1999-03-17T00:00:00.000Z",
    "register_date": "2020-03-20T08:49:09.352Z",
    "avatar": {
        "_id": "5e7483854c15af1cedb81764",
        "data": "imageData",
        "contentType": "image/jpeg"
    },
    "__v": 0
}
```

***

### Eliminar un usuario por su id

| Tipo/URL    | DELETE => `/api/users/:id`            |
| ----------- |:----------------------------------:|
| Comentarios | Petición DELETE para eliminar un usuario por su id |
| Cabeceras   | `Content-Type: application/json con role ADMIN y Token JWT`   |
| Parametros  | ID del usuario |
| Respuesta/s | 204 No Content => Devuelve los nuevos datos del usuario |


#### Respuesta del Endpoint
* Respuesta:
```json

```

***

### Editar contraseña de ususario por id

| Tipo/URL    | PUT => `/api/users/password/:id`            |
| ----------- |:----------------------------------:|
| Comentarios | Petición PUT para editar la contraseña de un usuario segun su id |
| Cabeceras   | `Content-Type: application/json con role ADMIN Token JWT`   |
| Cuerpo      | Nueva contraseña |
| Parametros  | ID del usuario |
| Respuesta/s | 200 Ok => Devuelve los nuevos datos del usuario |


#### Cuerpo y respuesta del Endpoint
* Cuerpo:
```json
{
	"password": "12345678",
	"confirmPassword": "12345678"
}

```
* Respuesta:
```json
{
    "validated": false,
    "active": true,
    "location_offered": null,
    "favorite_users": [],
    "_id": "5e7483854c15af1cedb81763",
    "username": "userUpdated",
    "email": "update@gmail.com",
    "phone": "657454545",
    "name": "NameUpdated",
    "role": "USER",
    "type_user": "OFRECE_ALOJAMIENTO",
    "password": "$2a$12$j8FdACKk93lIcYuyuaJRm.tk5A67XqtcPTevKF2mtNYkskwxkpzRu",
    "date_of_birth": "1999-03-17T00:00:00.000Z",
    "register_date": "2020-03-20T08:49:09.352Z",
    "avatar": {
        "_id": "5e7483854c15af1cedb81764",
        "data": "imageData",
        "contentType": "image/jpeg"
    },
    "__v": 0
}
```

***

## Tu usuario

### Obtener tu usuario

| Tipo/URL    | GET => `/api/users/me`            |
| ----------- |:----------------------------------:|
| Comentarios | Petición GET para obtener tu usuario  |
| Cabeceras   | `Content-Type: application/json con Token JWT`   |
| Respuesta/s | 200 Ok => Devuelve tu usuario |


#### Respuesta del Endpoint
* Respuesta:
```json
{
    "validated": false,
    "active": true,
    "location_offered": null,
    "favorite_users": [],
    "_id": "5e7483854c15af1cedb81763",
    "username": "userUpdated",
    "email": "update@gmail.com",
    "phone": "657454545",
    "name": "NameUpdated",
    "role": "USER",
    "type_user": "OFRECE_ALOJAMIENTO",
    "password": "$2a$12$j8FdACKk93lIcYuyuaJRm.tk5A67XqtcPTevKF2mtNYkskwxkpzRu",
    "date_of_birth": "1999-03-17T00:00:00.000Z",
    "register_date": "2020-03-20T08:49:09.352Z",
    "avatar": {
        "_id": "5e7483854c15af1cedb81764",
        "data": "imageData",
        "contentType": "image/jpeg"
    },
    "__v": 0
}
```

***

### Actualizar tu usuario

| Tipo/URL    | PUT => `/api/users/me`            |
| ----------- |:----------------------------------:|
| Comentarios | Petición PUT para actualizar tu usuario |
| Cabeceras   | `Content-Type: application/json con Token JWT`   |
| Cuerpo      | nuevos datos de tu usuario |
| Respuesta/s | 200 Ok => Devuelve los nuevos datos del usuario |


#### Cuerpo y respuesta del Endpoint
* Cuerpo:
```json
{
	"username": "userUpdated",
	"email": "update@gmail.com",
	"phone": "657454545",
	"name": "NameUpdated",
	"typeUser": "OFRECE_ALOJAMIENTO",
	"dateOfBirth": "1999-03-17"
}
```
* Respuesta:
```json
{
    "validated": false,
    "active": true,
    "location_offered": null,
    "favorite_users": [],
    "_id": "5e7483854c15af1cedb81763",
    "username": "userUpdated",
    "email": "update@gmail.com",
    "phone": "657454545",
    "name": "NameUpdated",
    "role": "USER",
    "type_user": "OFRECE_ALOJAMIENTO",
    "password": "$2a$12$j8FdACKk93lIcYuyuaJRm.tk5A67XqtcPTevKF2mtNYkskwxkpzRu",
    "date_of_birth": "1999-03-17T00:00:00.000Z",
    "register_date": "2020-03-20T08:49:09.352Z",
    "avatar": {
        "_id": "5e7483854c15af1cedb81764",
        "data": "imageData",
        "contentType": "image/jpeg"
    },
    "__v": 0
}
```

***

### Cambiar tu contraseña

| Tipo/URL    | PUT => `/api/users/me/password`            |
| ----------- |:----------------------------------:|
| Comentarios | Petición PUT para cambiar tu contraseña |
| Cabeceras   | `Content-Type: application/json con Token JWT`   |
| Cuerpo      | Nueva contraseña |
| Respuesta/s | 200 Ok => Devuelve los nuevos datos del usuario |


#### Cuerpo y respuesta del Endpoint
* Cuerpo:
```json
{
	"password": "12345678",
	"confirmPassword": "12345678"
}
```
* Respuesta:
```json
{
    "validated": false,
    "active": true,
    "location_offered": null,
    "favorite_users": [],
    "_id": "5e7483854c15af1cedb81763",
    "username": "userUpdated",
    "email": "update@gmail.com",
    "phone": "657454545",
    "name": "NameUpdated",
    "role": "USER",
    "type_user": "OFRECE_ALOJAMIENTO",
    "password": "$2a$12$j8FdACKk93lIcYuyuaJRm.tk5A67XqtcPTevKF2mtNYkskwxkpzRu",
    "date_of_birth": "1999-03-17T00:00:00.000Z",
    "register_date": "2020-03-20T08:49:09.352Z",
    "avatar": {
        "_id": "5e7483854c15af1cedb81764",
        "data": "imageData",
        "contentType": "image/jpeg"
    },
    "__v": 0
}
```

***

### Actualiza con quien estan conviviendo

| Tipo/URL    | PUT => `/api/users/me/livingWith`   |
| ----------- |:----------------------------------:|
| Comentarios | Petición PUT para actualizar con que usuario estas conviviendo |
| Cabeceras   | `Content-Type: application/json con Token JWT`   |
| Cuerpo      | id del usuario con el que convives |
| Respuesta/s | 200 Ok => Devuelve los nuevos datos del usuario |


#### Cuerpo y respuesta del Endpoint
* Cuerpo:
```json
{
	"idUser": "5e7483854c15af1cedb81763"
}
```
* Respuesta:
```json
{
    "validated": false,
    "active": true,
    "location_offered": null,
    "favorite_users": [],
    "_id": "5e7483854c15af1cedb81763",
    "username": "userUpdated",
    "email": "update@gmail.com",
    "phone": "657454545",
    "name": "NameUpdated",
    "role": "USER",
    "type_user": "OFRECE_ALOJAMIENTO",
    "password": "$2a$12$j8FdACKk93lIcYuyuaJRm.tk5A67XqtcPTevKF2mtNYkskwxkpzRu",
    "date_of_birth": "1999-03-17T00:00:00.000Z",
    "register_date": "2020-03-20T08:49:09.352Z",
    "avatar": {
        "_id": "5e7483854c15af1cedb81764",
        "data": "imageData",
        "contentType": "image/jpeg"
    },
    "living_with": "5e7483854c15af1cedb81763",
    "__v": 0
}
```

***

### Actualizar tu avatar

| Tipo/URL    | PUT => `/api/users/me/avatar`     |
| ----------- |:----------------------------------:|
| Comentarios | Petición PUT para actualizar tu avatar  |
| Cabeceras   | `Content-Type: application/json con Token JWT`   |
| Form-data   | nueva imagen para ser el avatar |
| Respuesta/s | 200 Ok => Devuelve los nuevos datos del usuario |


#### Form-data y respuesta del Endpoint
* Form-data:
```
avatar: archivo de imagen de la foto del usuario
```
* Respuesta:
```json
{
    "validated": false,
    "active": true,
    "location_offered": null,
    "favorite_users": [],
    "_id": "5e7483854c15af1cedb81763",
    "username": "userUpdated",
    "email": "update@gmail.com",
    "phone": "657454545",
    "name": "NameUpdated",
    "role": "USER",
    "type_user": "OFRECE_ALOJAMIENTO",
    "password": "$2a$12$j8FdACKk93lIcYuyuaJRm.tk5A67XqtcPTevKF2mtNYkskwxkpzRu",
    "date_of_birth": "1999-03-17T00:00:00.000Z",
    "register_date": "2020-03-20T08:49:09.352Z",
    "avatar": {
        "_id": "5e7483854c15af1cedb81764",
        "data": "imageData",
        "contentType": "image/jpeg"
    },
    "living_with": null,
    "__v": 0
}
```

***

### Darte de baja el usuario (eliminarlo)

| Tipo/URL    | DELETE => `/api/users/me`            |
| ----------- |:----------------------------------:|
| Comentarios | Petición DELETE para eliminar tu usuario |
| Cabeceras   | `Content-Type: application/json con Token JWT`   |
| Respuesta/s | 204 No Content |


#### Respuesta del Endpoint

* Respuesta:
```json
```

***

### Eliminar tu convivencia

| Tipo/URL    | DELETE => `/api/users/me/livingWith/:id`            |
| ----------- |:----------------------------------:|
| Comentarios | Petición DELETE para eliminar convivencia |
| Cabeceras   | `Content-Type: application/json con Token JWT`   |
| Parametros  | ID del usuario con el que se convive |
| Respuesta/s | 204 No Content  |


#### Respuesta del Endpoint
* Respuesta:
```json

```

***

### Eliminar tu avatar

| Tipo/URL    | DELETE => `/api/users/me/avatar`            |
| ----------- |:----------------------------------:|
| Comentarios | Petición DELETE para eliminar tu avatar |
| Cabeceras   | `Content-Type: application/json con Token JWT`   |
| Respuesta/s | 200 Ok => Devuelve los nuevos datos del usuario |


#### Respuesta del Endpoint
* Respuesta:
```json
{
    "validated": false,
    "active": true,
    "location_offered": null,
    "favorite_users": [],
    "_id": "5e7483854c15af1cedb81763",
    "username": "userUpdated",
    "email": "update@gmail.com",
    "phone": "657454545",
    "name": "NameUpdated",
    "role": "USER",
    "type_user": "OFRECE_ALOJAMIENTO",
    "password": "$2a$12$j8FdACKk93lIcYuyuaJRm.tk5A67XqtcPTevKF2mtNYkskwxkpzRu",
    "date_of_birth": "1999-03-17T00:00:00.000Z",
    "register_date": "2020-03-20T08:49:09.352Z",
    "living_with": null,
    "__v": 0
}
```

***

## Tus usuarios favoritos

### Añadir un usuario a favoritos

| Tipo/URL    | POST => `/api/users/me/favorites`      |
| ----------- |:----------------------------------:|
| Comentarios | Petición POST para añadir un uusario a favoritos  |
| Cabeceras   | `Content-Type: application/json con Token JWT`   |
| Cuerpo      | Id del usuario a añadir |
| Respuesta/s | 201 Created => Devuelve los nuevos datos del usuario |


#### Cuerpo y respuesta del Endpoint
* Cuerpo:
```json
{
	"idUser": "5e7a545e67756c6d5f57145a"
}
```
* Respuesta:
```json
{
    "validated": false,
    "active": true,
    "location_offered": null,
    "favorite_users": [
        "5e7a545e67756c6d5f57145a"
    ],
    "_id": "5e7483854c15af1cedb81763",
    "username": "userUpdated",
    "email": "update@gmail.com",
    "phone": "657454545",
    "name": "NameUpdated",
    "role": "USER",
    "type_user": "OFRECE_ALOJAMIENTO",
    "password": "$2a$12$j8FdACKk93lIcYuyuaJRm.tk5A67XqtcPTevKF2mtNYkskwxkpzRu",
    "date_of_birth": "1999-03-17T00:00:00.000Z",
    "register_date": "2020-03-20T08:49:09.352Z",
    "avatar": {
        "_id": "5e7483854c15af1cedb81764",
        "data": "imageData",
        "contentType": "image/jpeg"
    },
    "living_with": null,
    "__v": 0
}
```

***

### Obtener tus favoritos

| Tipo/URL    | GET => `/api/users/me/favorites`            |
| ----------- |:----------------------------------:|
| Comentarios | Petición GET para obtener todos tus favoritos |
| Cabeceras   | `Content-Type: application/json con Token JWT`   |
| Respuesta/s | 200 Ok => Devuelve los datos de los usuarios favoritos del usuario |


#### Respuesta del Endpoint
* Respuesta:
```json
{
    "validated": true,
    "active": true,
    "location_offered": null,
    "favorite_users": [
        {
            "validated": true,
            "active": true,
            "location_offered": "5e7a3637a75f5d40d8e9d71d",
            "favorite_users": [],
            "_id": "5e7483854c15af1cedb81763",
            "username": "userUpdated",
            "email": "update@gmail.com",
            "phone": "657454545",
            "name": "NameUpdated",
            "role": "USER",
            "type_user": "OFRECE_ALOJAMIENTO",
            "password": "$2a$12$Ph2obTm4f5YdRsyovvsnDOHLNMp4qf4nsFDr35kO1NRqhMAOtRqO6",
            "date_of_birth": "1999-03-16T23:00:00.000Z",
            "register_date": "2020-03-20T08:49:09.352Z",
            "__v": 0,
            "living_with": "5e7488bd454fe922eb5dc865"
        },
        {
            "validated": true,
            "active": true,
            "location_offered": null,
            "favorite_users": [],
            "_id": "5e7a545e67756c6d5f57145a",
            "username": "dsantano67",
            "email": "santano67@email.com",
            "phone": "657464646",
            "name": "Daniel Santano67",
            "role": "USER",
            "type_user": "JOVEN",
            "password": "$2a$12$ZAULQcopciWAN25Yrufb4OstIPWRtP62IPxebV28zyFSS1i.1c5.i",
            "date_of_birth": "1999-11-07T00:00:00.000Z",
            "register_date": "2020-03-24T18:41:34.848Z",
            "avatar": {
                "_id": "5e7a545e67756c6d5f57145b",
                "data": "imageData",
                "contentType": "image/jpeg"
            },
            "__v": 0
        }
    ],
    "_id": "5e7488bd454fe922eb5dc865",
    "username": "dsantano",
    "email": "santano@email.com",
    "phone": "657464646",
    "name": "Daniel Santano",
    "role": "ADMIN",
    "type_user": "JOVEN",
    "password": "$2a$12$4wpEii4kY0TGbgEHfFihi.0Tz/xkFU/fsvTAh2hH.MNTP17qi2wTu",
    "date_of_birth": "1999-11-07T00:00:00.000Z",
    "register_date": "2020-03-20T09:11:25.407Z",
    "avatar": {
        "_id": "5e7488bd454fe922eb5dc866",
        "data": "imageData",
        "contentType": "image/jpeg"
    },
    "__v": 1,
    "living_with": "5e7483854c15af1cedb81763"
}
```

***

### Eliminar favorito

| Tipo/URL    | DELETE => `/api/users/me/favorites/:id`            |
| ----------- |:----------------------------------:|
| Comentarios | Petición DELETE para eliminar un usuario favorito por su id |
| Cabeceras   | `Content-Type: application/json con Token JWT`   |
| Parametros  | ID del usuario |
| Respuesta/s | 200 Ok => Devuelve los nuevos datos del usuario |


#### Respuesta del Endpoint
* Respuesta:
```json
{
    "validated": false,
    "active": true,
    "location_offered": null,
    "favorite_users": [],
    "_id": "5e7483854c15af1cedb81763",
    "username": "userUpdated",
    "email": "update@gmail.com",
    "phone": "657454545",
    "name": "NameUpdated",
    "role": "USER",
    "type_user": "OFRECE_ALOJAMIENTO",
    "password": "$2a$12$j8FdACKk93lIcYuyuaJRm.tk5A67XqtcPTevKF2mtNYkskwxkpzRu",
    "date_of_birth": "1999-03-17T00:00:00.000Z",
    "register_date": "2020-03-20T08:49:09.352Z",
    "avatar": {
        "_id": "5e7483854c15af1cedb81764",
        "data": "imageData",
        "contentType": "image/jpeg"
    },
    "living_with": null,
    "__v": 0
}
```

***

## Servicios

### Añadir un servicio

| Tipo/URL    | POST => `/api/services`            |
| ----------- |:----------------------------------:|
| Comentarios | Petición POST para añadir un servicio |
| Cabeceras   | `Content-Type: application/json con role ADMIN o type_user OFRECE_SERVICIO Token JWT`   |
| Cuerpo      | datos del servicio |
| Respuesta/s | 201 Created => Devuelve los datos del servicio |


#### Cuerpo y respuesta del Endpoint
* Cuerpo:
```json
{
	"typeService": "HACER_COMPRA",
	"title": "Servicio",
    "description": "Descripcion del servicio",
}
```
* Respuesta:
```json
{
    "available": true,
    "_id": "5e749ee2770c013a4d6b1f8b",
    "type_service": "HACER_COMPRA",
    "title": "Servicio",
    "description": "Descripcion del servicio",
    "user_offering_service": "5e7488bd454fe922eb5dc865"
}
```

***

### Obtener todos los servicios

| Tipo/URL    | GET => `/api/services`            |
| ----------- |:----------------------------------:|
| Comentarios | Petición GET para obtener todos los servicios |
| Cabeceras   | `Content-Type: application/json con Token JWT`   |
| Respuesta/s | 200 Ok => Devuelve los servicios |


#### Respuesta del Endpoint
* Respuesta:
```json
[
    {
        "available": true,
        "_id": "5e749ee2770c013a4d6b1f8b",
        "type_service": "HACER_COMPRA",
        "title": "Segundo Servicio",
        "description": "Descripcion del segundo servicio",
        "user_offering_service": "5e7488bd454fe922eb5dc865"
    },
    {
        "available": true,
        "_id": "5e749f15770c013a4d6b1f8e",
        "type_service": "COCINAR",
        "title": "Tercer Servicio",
        "description": "Descripcion del tercer servicio",
        "user_offering_service": "5e7488bd454fe922eb5dc865"
    }
]
```

***

### Obtener todos los servicios disponibles

| Tipo/URL    | GET => `/api/services/available`            |
| ----------- |:----------------------------------:|
| Comentarios | Petición GET para obtener todos los servicios disponibles |
| Cabeceras   | `Content-Type: application/json con Token JWT`   |
| Respuesta/s | 200 Ok => Devuelve los servicios con available a true |


#### Respuesta del Endpoint
* Respuesta:
```json
[
    {
        "available": true,
        "_id": "5e749ee2770c013a4d6b1f8b",
        "type_service": "HACER_COMPRA",
        "title": "Segundo Servicio",
        "description": "Descripcion del segundo servicio",
        "user_offering_service": "5e7488bd454fe922eb5dc865"
    },
    {
        "available": true,
        "_id": "5e749f15770c013a4d6b1f8e",
        "type_service": "COCINAR",
        "title": "Tercer Servicio",
        "description": "Descripcion del tercer servicio",
        "user_offering_service": "5e7488bd454fe922eb5dc865"
    }
]
```

***

### Obtener un servicio

| Tipo/URL    | GET => `/api/services/:id`            |
| ----------- |:----------------------------------:|
| Comentarios | Petición GET para obtener un servicio por su id |
| Cabeceras   | `Content-Type: application/json con Token JWT`   |
| Parametros  | ID del servicio |
| Respuesta/s | 200 Ok => Devuelve los datos del servicio |


#### Respuesta del Endpoint
* Respuesta:
```json
{
    "available": true,
    "_id": "5e749ee2770c013a4d6b1f8b",
    "type_service": "HACER_COMPRA",
    "title": "Servicio",
    "description": "Descripcion del servicio",
    "user_offering_service": "5e7488bd454fe922eb5dc865"
}
```

***

### Editar un servicio

| Tipo/URL    | PUT => `/api/services/:id`            |
| ----------- |:----------------------------------:|
| Comentarios | Petición PUT para editar un servicio por su id |
| Cabeceras   | `Content-Type: application/json con role ADMIN o type_user OFRECE_SERVICIO Token JWT`   |
| Cuerpo      | nuevos datos del servicio |
| Parametros  | ID del servicio |
| Respuesta/s | 200 Ok => Devuelve los nuevos datos del servicio |


#### Cuerpo y respuesta del Endpoint
* Cuerpo:
```json
{
	"typeService": "TRANSPORTE",
	"title": "Servicio de transporte2222",
	"description": "Servicio de transporte para cualquier persona mayor que necesite desde ir a misa o ir al medico y no pueda debid a su edad o alguna condición"
}
```
* Respuesta:
```json
{
    "available": true,
    "_id": "5e749ee2770c013a4d6b1f8b",
    "type_service": "TRANSPORTE",
    "title": "Servicio de transporte2222",
    "description": "Servicio de transporte para cualquier persona mayor que necesite desde ir a misa o ir al medico y no pueda debid a su edad o alguna condición",
    "user_offering_service": "5e7488bd454fe922eb5dc865"
}
```

***

### Eliminar un servicio

| Tipo/URL    | DELETE => `/api/services/:id`            |
| ----------- |:----------------------------------:|
| Comentarios | Petición DELETE para eliminar un servicio por su id |
| Cabeceras   | `Content-Type: application/json con role ADMIN o type_user OFRECE_SERVICIO Token JWT`   |
| Parametros  | ID del servicio |
| Respuesta/s | 204 No Content  |


#### Respuesta del Endpoint
* Respuesta:
```json

```

***

## Localizaciones

### Añadir localización que ofreces

| Tipo/URL    | POST => `/api/locations`            |
| ----------- |:----------------------------------:|
| Comentarios | Petición POST para añadir tu localización ofrecida |
| Cabeceras   | `Content-Type: application/json con role ADMIN o type_user OFRECE_ALOJAMIENTO Token JWT`   |
| Cuerpo      | datos de la localización |
| Respuesta/s | 201 Created => Devuelve los nuevos datos de la localización |


#### Cuerpo y respuesta del Endpoint
* Cuerpo:
```json
{
	"country": "España",
	"isoCode": "ES",
	"city": "Sevilla",
	"street": "Ramon y Cajal",
	"postalCode": "41011"
}
```
* Respuesta:
```json
{
        "location_offered": {
            "available": true,
            "_id": "5e7a3637a75f5d40d8e9d71d",
            "country": "España",
            "iso_code": "ES",
            "city": "Sevilla",
            "street": "Ramon y Cajal",
            "postal_code": "41011",
            "__v": 0
        },
        "_id": "5e7a545e67756c6d5f57145a",
        "username": "dsantano67",
        "date_of_birth": "1999-11-07T00:00:00.000Z",
        "avatar": {
            "_id": "5e7a545e67756c6d5f57145b",
            "data": "imageData",
            "contentType": "image/jpeg"
        }
    }
```

***

### Obtener todas las localizaciones

| Tipo/URL    | GET => `/api/locations`            |
| ----------- |:----------------------------------:|
| Comentarios | Petición GET para obtener todas las localizaciones |
| Cabeceras   | `Content-Type: application/json con  Token JWT`   |
| Respuesta/s | 200 Ok => Devuelve los datos de todas las localizaciones |


#### Respuesta del Endpoint
* Respuesta:
```json
[
    {
        "location_offered": {
            "available": true,
            "_id": "5e7a3637a75f5d40d8e9d71d",
            "country": "España",
            "iso_code": "ES",
            "city": "Sevilla",
            "street": "Ramon y Cajal",
            "postal_code": "41011",
            "__v": 0
        },
        "_id": "5e7a545e67756c6d5f57145a",
        "username": "dsantano67",
        "date_of_birth": "1999-11-07T00:00:00.000Z",
        "avatar": {
            "_id": "5e7a545e67756c6d5f57145b",
            "data": "imageData",
            "contentType": "image/jpeg"
        }
    },
    {
        "location_offered": null,
        "_id": "5e7de64cc720a94e9972fa7c",
        "username": "dsantano33",
        "date_of_birth": "1999-11-07T00:00:00.000Z"
    },
    {
        "location_offered": null,
        "_id": "5e7ded21c720a94e9972fa89",
        "username": "danielsf77",
        "date_of_birth": "1987-03-26T00:00:00.000Z"
    }
]
```

***

### Obtener localización por id

| Tipo/URL    | GET => `/api/locations/:id`            |
| ----------- |:----------------------------------:|
| Comentarios | Petición GET para obtener una localización por su id |
| Cabeceras   | `Content-Type: application/json con  Token JWT`   |
| Parametros  | ID de la localización |
| Respuesta/s | 200 Ok => Devuelve los datos de la localización |


#### Respuesta del Endpoint
* Respuesta:
```json
{
    "validated": true,
    "location_offered": {
        "available": true,
        "_id": "5e7a3637a75f5d40d8e9d71d",
        "country": "España",
        "iso_code": "ES",
        "city": "Sevilla",
        "street": "Ramon y Cajal",
        "postal_code": "41011",
        "__v": 0
    },
    "favorite_users": [],
    "_id": "5e7483854c15af1cedb81763",
    "username": "userUpdated",
    "email": "update@gmail.com",
    "phone": "657454545",
    "name": "NameUpdated",
    "role": "USER",
    "type_user": "OFRECE_ALOJAMIENTO",
    "password": "$2a$12$Ph2obTm4f5YdRsyovvsnDOHLNMp4qf4nsFDr35kO1NRqhMAOtRqO6",
    "date_of_birth": "1999-03-16T23:00:00.000Z",
    "living_with": "5e7488bd454fe922eb5dc865"
}
```

***

### Editar localización

| Tipo/URL    | PUT => `/api/locations/:id`            |
| ----------- |:----------------------------------:|
| Comentarios | Petición PUT para editar los datos de una localización por su id |
| Cabeceras   | `Content-Type: application/json con role ADMIN o type_user OFRECE_ALOJAMIENTO Token JWT`   |
| Cuerpo      | nuevos datos de la localización |
| Parametros  | ID de la localización |
| Respuesta/s | 200 Ok => Devuelve los nuevos datos de la localización |


#### Cuerpo y respuesta del Endpoint
* Cuerpo:
```json
{
	"country": "Españita",
	"isoCode": "ES",
	"city": "Sevilla",
	"street": "Ramon y Cajal",
	"postalCode": "41011"
}
```
* Respuesta:
```json
{
        "location_offered": {
            "available": true,
            "_id": "5e7a3637a75f5d40d8e9d71d",
            "country": "España",
            "iso_code": "ES",
            "city": "Sevilla",
            "street": "Ramon y Cajal",
            "postal_code": "41011",
            "__v": 0
        },
        "_id": "5e7a545e67756c6d5f57145a",
        "username": "dsantano67",
        "date_of_birth": "1999-11-07T00:00:00.000Z",
        "avatar": {
            "_id": "5e7a545e67756c6d5f57145b",
            "data": "imageData",
            "contentType": "image/jpeg"
        }
}
```

***

### Editar disponibilidad en la localización

| Tipo/URL    | PUT => `/api/locations/available/:id`            |
| ----------- |:----------------------------------:|
| Comentarios | Petición PUT para editar available de una localizacion por su id |
| Cabeceras   | `Content-Type: application/json con role ADMIN o type_user OFRECE_ALOJAMIENTO Token JWT`   |
| Cuerpo      | datos |
| Parametros  | ID de la localización |
| Respuesta/s | 200 Ok => Devuelve los nuevos datos de la localización |


#### Cuerpo y respuesta del Endpoint
* Cuerpo:
```json
{
	"available": true
}
```
* Respuesta:
```json
{
        "location_offered": {
            "available": true,
            "_id": "5e7a3637a75f5d40d8e9d71d",
            "country": "España",
            "iso_code": "ES",
            "city": "Sevilla",
            "street": "Ramon y Cajal",
            "postal_code": "41011",
            "__v": 0
        },
        "_id": "5e7a545e67756c6d5f57145a",
        "username": "dsantano67",
        "date_of_birth": "1999-11-07T00:00:00.000Z",
        "avatar": {
            "_id": "5e7a545e67756c6d5f57145b",
            "data": "imageData",
            "contentType": "image/jpeg"
        }
}
```

***

### Eliminar localización

| Tipo/URL    | DELETE => `/api/locations/:id`            |
| ----------- |:----------------------------------:|
| Comentarios | Petición DELETE para eliminar localización por su id |
| Cabeceras   | `Content-Type: application/json con role ADMIN o type_user OFRECE_ALOJAMIENTO Token JWT`   |
| Parametros  | ID de la localización |
| Respuesta/s | 204 No Content |


#### Respuesta del Endpoint
* Respuesta:
```json

```
