import { Routes } from '@angular/router';

import { DashboardComponent } from './dashboard.component';
import { UsuariosListadoComponent } from './usuarios-listado/usuarios-listado.component';

export const DashboardRoutes: Routes = [
  { path: '', component: DashboardComponent},
  { path: 'users', component: UsuariosListadoComponent },
];
