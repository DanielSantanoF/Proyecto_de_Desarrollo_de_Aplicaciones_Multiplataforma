import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { MatDialog, MatSnackBar } from '@angular/material';
import { MenuDto } from '../models/Menu.dto';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit{

  usuarios = new MenuDto("supervised_user_circle", "Usuarios", "Listado de usuarios");
  administrarUsuarios = new MenuDto("verified_user", "Administrar usuarios", "Validación y activación");
  servicios = new MenuDto("room_service", "Servicios", "Listado de servicios");
  localizaciones = new MenuDto("map", "Localizaciones", "Listado de localizaciones");
  
  constructor(private router: Router,
    public dialog: MatDialog,
    private _snackBar: MatSnackBar) { }

  ngOnInit(){
    if (localStorage.getItem('token') || localStorage.getItem('role')) {
    } else {
      this.router.navigate(['/session/signin']);
    }
  }

}
