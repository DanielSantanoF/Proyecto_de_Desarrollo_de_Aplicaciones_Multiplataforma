# Proyecto DAM
Api Rest en NodeJs con MongoDB y Mongoose para el Proyecto de Desarrollo de Aplicaciones Multiplataforma.
* Se encuentra despleguada en la url `https://dsantanoproyectodam.herokuapp.com/`

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
| Form-data      | Datos de un nuevo usuario          |
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

## Usuarios

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