import { Routes } from '@angular/router';

import { DashboardComponent } from './dashboard.component';
import { UsuariosListadoComponent } from './usuarios-listado/usuarios-listado.component';
import { ServiciosListadoComponent } from './servicios-listado/servicios-listado.component';
import { LocalizacionesListadoComponent } from './localizaciones-listado/localizaciones-listado.component';
import { AdministrarUsuariosListadoComponent } from './administrar-usuarios-listado/administrar-usuarios-listado.component';

export const DashboardRoutes: Routes = [
  { path: '', component: DashboardComponent},
  { path: 'users', component: UsuariosListadoComponent },
  { path: 'services', component: ServiciosListadoComponent },
  { path: 'locations', component: LocalizacionesListadoComponent },
  { path: 'adminUsers', component: AdministrarUsuariosListadoComponent },
];
