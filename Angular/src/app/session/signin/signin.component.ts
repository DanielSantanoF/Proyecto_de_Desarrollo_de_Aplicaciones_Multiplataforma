import { Component, OnInit } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  Validators
} from '@angular/forms';

import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';
import { UserGoogleDto } from 'src/app/models/userGoogle.dto';
import { MatSnackBar } from '@angular/material';

@Component({
  selector: 'app-signin',
  templateUrl: './signin.component.html',
  styleUrls: ['./signin.component.scss']
})
export class SigninComponent implements OnInit {
  
  username: string;
  password: string;

  public form: FormGroup;
  
  constructor(
    private fb: FormBuilder,
    private router: Router,
    private authService: AuthService,
    private _snackBar: MatSnackBar
    ) {}

  ngOnInit() {
    this.form = this.fb.group({
      uname: [null, Validators.compose([Validators.required])],
      password: [null, Validators.compose([Validators.required])]
    });
  }

  onSubmit() {
    
  }

  googleSignIn() {
    this.authService.googleLogin().then(resp => {
      this.authService.setLocalData(
        resp.user.uid,
        resp.user.displayName,
        resp.user.email,
        resp.user.photoURL
      );
      this.authService.getUser().subscribe(usuarioEncontrado => {
        if(usuarioEncontrado){
          const dto = new UserGoogleDto(resp.user.email, resp.user.uid);
          this.authService.updateUserLogged(resp.user.uid, dto).then(resp2 => {
            this.apirestSignInByGoogle(usuarioEncontrado.username, usuarioEncontrado.password);
          }).catch(err => {
            this._snackBar.open("Error al iniciar sesión", err);
          });
        } else {
            this.authService.logout()
            .then(x => {
              this._snackBar.open("No tienes acceso permitido");
            })
            .catch(err => {});
        }
      });
    }).catch(err =>{
      this._snackBar.open("Error al iniciar sesión", err);
    })
  }

  apirestSignInByGoogle(username: string, password: string){
    this.authService.apiRestSignIn(username, password).subscribe(resp => {
      localStorage.setItem('token', resp.token)
      localStorage.setItem('username', resp.username)
      localStorage.setItem('role', resp.role)
      if(resp.role != "ADMIN"){
        this.signOut();
      }
      this.router.navigate(['/']);
    });
  }

  apirestSignIn(){
    this.authService.apiRestSignIn(this.username, this.password).subscribe(resp => {
      localStorage.setItem('token', resp.token)
      localStorage.setItem('username', resp.username)
      localStorage.setItem('role', resp.role)
      if(resp.role != "ADMIN"){
        this.signOut();
      }
      this.router.navigate(['/']);
    });
  }

  signOut(){
    this.authService.logout().then(resp => {
      this._snackBar.open("No tienes acceso permitido");
    }).catch(err => {
      this._snackBar.open("Error al cerrar sesión", err);
    });
  }

}
