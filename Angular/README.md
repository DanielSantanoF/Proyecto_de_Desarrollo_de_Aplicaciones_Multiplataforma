# Proyecto_de_Desarrollo_de_Aplicaciones_Multiplataforma_Angular
Aplicación de Angular de administración del proyecto, solo tienen acceso al hacer login los usuarios que sean administradores, por lo tanto es una aplicación web totalmente de gestión
* Los datos de firebase se encuentran en `/src/environments/environment.ts` son necesarios para poder usar la aplicación
* La url del ApiRest se encuentra indicada en cada Servicio de la applicación de angular en `src/app/services/*` en la constante `API_REST_URL` en este caso es `https://dsantanoproyectodam.herokuapp.com/` dado que tenemos el ApiRest desplegada en Heroku
* Esta aplicación se encuentra disponible mediante el hosting de firebase => [Ver aplicación](https://dsantanoproyectodam.firebaseapp.com/session/signin "proyectoDAM")

## Datos de acceso de prueba
#### Admin
* Username: admin
* Contraseña: 123456

Dado que Angular esta centrado solo en la parte de administrador, si no se hace el login ya sea normal o google con un usuario administrador no te dara acceso, parar poder acceder con google a angular deberas de tener un usuario enlazado a tu cuenta de google y que en el apirest tu usuario sea administrador.

***

This project was generated with [angular-cli](https://github.com/angular/angular-cli) version 1.0.0-beta.32.3.

## Development server
Run `ng serve` for a dev server. Navigate to `http://localhost:4200/`. The app will automatically reload if you change any of the source files.

## Code scaffolding

Run `ng generate component component-name` to generate a new component. You can also use `ng generate directive|pipe|service|class|guard|interface|enum|module`.

## Build

Run `ng build` to build the project. The build artifacts will be stored in the `dist/` directory. Use the `-prod` flag for a production build.

## Running unit tests

Run `ng test` to execute the unit tests via [Karma](https://karma-runner.github.io).

## Running end-to-end tests

Run `ng e2e` to execute the end-to-end tests via [Protractor](http://www.protractortest.org/).
Before running the tests make sure you are serving the app via `ng serve`.

## Further help

To get more help on the Angular CLI use `ng help` or go check out the [Angular CLI README](https://github.com/angular/angular-cli/blob/master/README.md).