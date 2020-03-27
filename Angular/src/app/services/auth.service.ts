import { Injectable } from '@angular/core';
import { AngularFireAuth } from '@angular/fire/auth';
import { auth } from 'firebase/app';
import { AngularFirestore } from '@angular/fire/firestore';
import { UserGoogleDto } from '../models/userGoogle.dto';
import { UsersFirebase } from '../models/userFirebase.interface';
import { HttpHeaders, HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { LoginResponse } from '../models/loginResponse.interface';
import { LoginDto } from '../models/login.dto';

export const collectionNameUsers = 'users';
//export const API_REST_URL = 'http://localhost:3000/';
export const API_REST_URL = 'https://dsantanoproyectodam.herokuapp.com/';

const httpOptions = {
  headers: new HttpHeaders({
    'Content-type': 'application/json'
  })
};

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(public afAuth: AngularFireAuth, 
    private dbFireStore: AngularFirestore,
    private http: HttpClient) { }

  googleLogin() {
    return this.afAuth.auth.signInWithPopup(new auth.GoogleAuthProvider());
  }

  logout(){
    localStorage.removeItem('uid');
    localStorage.removeItem('nombre');
    localStorage.removeItem('email');
    localStorage.removeItem('photo');
    localStorage.removeItem('token');
    localStorage.removeItem('username');
    localStorage.removeItem('role');
    return this.afAuth.auth.signOut();
  }

  setLocalData(uid: string, nombre: string, email: string, photo: string) {
    localStorage.setItem('uid', uid);
    localStorage.setItem('nombre', nombre);
    localStorage.setItem('email', email);
    localStorage.setItem('photo', photo);
  }

  getLocalData(key: string) {
    return localStorage.getItem(key);
  }

  public getUser(){
    const uid = localStorage.getItem('uid');
    return this.dbFireStore.collection(collectionNameUsers).doc<UsersFirebase>(uid).valueChanges();
  }

  public saveUserLogged(id, dto: UserGoogleDto){
    return this.dbFireStore.collection<UsersFirebase>(collectionNameUsers).doc(id).set(dto.transformarDto());
  }

  public updateUserLogged(id, dto: UserGoogleDto){
    return this.dbFireStore.collection<UsersFirebase>(collectionNameUsers).doc(id).update(dto.transformarDto());
  }

  apiRestSignIn(username: string, password: string): Observable<LoginResponse>{
    const dto = new LoginDto(username, password);
    return this.http.post<LoginResponse>(API_REST_URL + 'api/login', dto, httpOptions)
  }

  getHttpOptionsWithToken(){
    const httpOptionsWithToken = {
      headers: new HttpHeaders({
        'Content-type': 'application/json'
        ,'Authorization': 'Bearer '+ localStorage.getItem('token')
    })};
    return httpOptionsWithToken;
  }

}
