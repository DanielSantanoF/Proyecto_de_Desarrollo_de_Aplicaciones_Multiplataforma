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
      if(resp.user.email == "santano.fedan20@triana.salesianos.edu"){
        localStorage.setItem('role', "TRUE")
      } else {
        localStorage.setItem('role', "FALSE")
      }
      this.authService.getUser().subscribe(usuarioEncontrado => {
        if(usuarioEncontrado){
          const dto = new UserGoogleDto( resp.user.displayName, resp.user.email, resp.user.photoURL);
          this.authService.updateUserLogged(resp.user.uid, dto).then(resp2 => {
            this.router.navigate(['/']);
          }).catch(err => {
            this._snackBar.open("Error al iniciar sesión", err);
          });
        } else {
          const dto2 = new UserGoogleDto(resp.user.displayName, resp.user.email, resp.user.photoURL);
          this.authService.saveUserLogged(resp.user.uid, dto2).then(resp3 => {
            this.router.navigate(['/']);
          }).catch(err => {
            this._snackBar.open("Error al iniciar sesión", err);
          })
        }
      });
    }).catch(err =>{
      this._snackBar.open("Error al iniciar sesión", err);
    })
  }

  apirestSignIn(){
    this.authService.apiRestSignIn(this.username, this.password).subscribe(resp => {
      localStorage.setItem('token', resp.token)
      localStorage.setItem('username', resp.username)
      localStorage.setItem('role', resp.role)
      this.router.navigate(['/']);
    });
  }

  doRegister(){
    
  }

}
