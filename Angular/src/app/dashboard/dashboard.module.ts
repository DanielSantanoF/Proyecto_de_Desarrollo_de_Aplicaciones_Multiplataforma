import {
  MatButtonModule,
  MatCardModule,
  MatIconModule,
  MatListModule,
  MatMenuModule,
  MatProgressBarModule,
  MatTableModule,
  MatFormFieldModule,
  MatInputModule,
  MatDialogModule,
  MatCheckboxModule,
  MatSnackBarModule,
  MatChipsModule,
  MAT_SNACK_BAR_DEFAULT_OPTIONS,
  MAT_CHIPS_DEFAULT_OPTIONS,
  MatSelectModule,
  MatDatepickerModule,
  MatNativeDateModule,
  MAT_DIALOG_DEFAULT_OPTIONS,
} from '@angular/material';

import { ChartsModule } from 'ng2-charts';
import { CommonModule } from '@angular/common';
import { DashboardComponent } from './dashboard.component';
import { DashboardRoutes } from './dashboard.routing';
import { FlexLayoutModule } from '@angular/flex-layout';
import { NgModule } from '@angular/core';
import { NgxDatatableModule } from '@swimlane/ngx-datatable';
import { RouterModule } from '@angular/router';
import { AngularFireModule } from '@angular/fire';
import { AngularFirestoreModule } from '@angular/fire/firestore';
import { AngularFireAuthModule } from '@angular/fire/auth';
import { environment } from 'src/environments/environment';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AuthService } from '../services/auth.service';
import { UsersService } from '../services/users.service';
import { ServicesService } from '../services/services.service';
import { LocationsService } from '../services/locations.service';
import { UsuariosListadoComponent } from './usuarios-listado/usuarios-listado.component';
import {MatPaginatorModule} from '@angular/material/paginator';
import { ConfirmDeleteDialogComponent } from './confirm-delete-dialog/confirm-delete-dialog.component';
import { UsuariosEditDialogComponent } from './usuarios-edit-dialog/usuarios-edit-dialog.component';
import { ConfirmActionDialogComponent } from './confirm-action-dialog/confirm-action-dialog.component';
import { ServiciosListadoComponent } from './servicios-listado/servicios-listado.component';
import { ServiciosEditDialogComponent } from './servicios-edit-dialog/servicios-edit-dialog.component';
import { LocalizacionesListadoComponent } from './localizaciones-listado/localizaciones-listado.component';
import { LocalizacionesEditDialogComponent } from './localizaciones-edit-dialog/localizaciones-edit-dialog.component';
import { AdministrarUsuariosListadoComponent } from './administrar-usuarios-listado/administrar-usuarios-listado.component';



@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(DashboardRoutes),
    MatIconModule,
    MatCardModule,
    MatButtonModule,
    MatListModule,
    MatProgressBarModule,
    MatMenuModule,
    MatTableModule,
    MatFormFieldModule,
    MatInputModule,
    MatDialogModule,
    MatCheckboxModule,
    MatSnackBarModule,
    MatChipsModule,
    ChartsModule,
    FormsModule,
    NgxDatatableModule,
    FlexLayoutModule,
    AngularFireModule.initializeApp(environment.firebase),
    AngularFirestoreModule,
    AngularFireAuthModule,
    MatDialogModule,
    FormsModule, 
    ReactiveFormsModule,
    MatSelectModule,
    MatSnackBarModule,
    MatTableModule,
    MatFormFieldModule,
    MatInputModule,
    MatCheckboxModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatPaginatorModule
  ],
  declarations: [DashboardComponent, UsuariosListadoComponent, ConfirmDeleteDialogComponent, UsuariosEditDialogComponent, ConfirmActionDialogComponent, ServiciosListadoComponent, ServiciosEditDialogComponent, LocalizacionesListadoComponent, LocalizacionesEditDialogComponent, AdministrarUsuariosListadoComponent],
  entryComponents: [
    ConfirmDeleteDialogComponent,
    ConfirmActionDialogComponent,
    UsuariosEditDialogComponent,
    ServiciosEditDialogComponent,
    LocalizacionesEditDialogComponent
  ],
  providers: [
    MatDatepickerModule,
    {provide: MAT_DIALOG_DEFAULT_OPTIONS, useValue: {hasBackdrop: true}},
    {provide: MAT_SNACK_BAR_DEFAULT_OPTIONS, useValue: {duration: 2000}},
    AuthService,
    UsersService,
    ServicesService,
    LocationsService
  ]
})
export class DashboardModule {}
