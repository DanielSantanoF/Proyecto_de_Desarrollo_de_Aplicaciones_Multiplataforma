import * as Screenfull from 'screenfull';

import { Component, EventEmitter, Output, OnInit } from '@angular/core';
import { AuthService } from 'src/app/services/auth.service';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html'
})
export class HeaderComponent implements OnInit {
  @Output()
  toggleSidenav = new EventEmitter<void>();
  @Output()
  toggleNotificationSidenav = new EventEmitter<void>();

  photoUsuarioLogueado: string;

  constructor(private authService: AuthService,
    private router: Router,
    private _snackBar: MatSnackBar) {}

  ngOnInit() {
    this.photoUsuarioLogueado = this.authService.getLocalData('photo');
  }

  signOut(){
    this.authService.logout().then(resp => {
      this.router.navigate(['/session/signin']);
    }).catch(err => {
      this._snackBar.open("Error al cerrar sesión", err);
    });
  }

  fullScreenToggle(): void {
    if (Screenfull.isEnabled) {
      Screenfull.toggle();
    }
  }
}
