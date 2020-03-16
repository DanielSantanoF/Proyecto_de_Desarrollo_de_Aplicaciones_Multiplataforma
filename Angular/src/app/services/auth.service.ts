import { Injectable } from '@angular/core';
import { AngularFireAuth } from '@angular/fire/auth';
import { auth } from 'firebase/app';
import { AngularFirestore } from '@angular/fire/firestore';
import { UserGoogleDto } from '../models/userGoogle.dto';
import { Users } from '../models/user.interface';

export const collectionNameProfesores = 'users';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(public afAuth: AngularFireAuth, 
    private dbFireStore: AngularFirestore) { }

  googleLogin() {
    return this.afAuth.auth.signInWithPopup(new auth.GoogleAuthProvider());
  }

  logout(){UserGoogleDto
    localStorage.removeItem('uid');
    localStorage.removeItem('nombre');
    localStorage.removeItem('email');
    localStorage.removeItem('photo');
    localStorage.removeItem('es_Profesor');
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
    return this.dbFireStore.collection(collectionNameProfesores).doc<Users>(uid).valueChanges();
  }

  public saveUserLogged(id, dto: UserGoogleDto){
    return this.dbFireStore.collection<Users>(collectionNameProfesores).doc(id).set(dto.transformarDto());
  }

  public updateUserLogged(id, dto: UserGoogleDto){
    return this.dbFireStore.collection<Users>(collectionNameProfesores).doc(id).update(dto.transformarDto());
  }

}
