import { Injectable } from '@angular/core';
import { AngularFirestore } from '@angular/fire/firestore';
import { UserDto } from '../models/user.dto';
import { UserApiRest } from '../models/user.interface';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from './auth.service';
import { UsersFirebase } from '../models/userFirebase.interface';

const collectionName = 'users';
export const API_REST_UTL = 'http://localhost:3000/';

const httpOptions = {
  headers: new HttpHeaders({
    'Content-type': 'application/json'
  })
};

@Injectable({ 
  providedIn: 'root'
})
export class UsersService {

  constructor(private db: AngularFirestore,
    private http: HttpClient,
    private authService: AuthService) { }

  createUser(uid: string, userDto: UserDto) {
    return this.db.collection<UsersFirebase>(collectionName).doc(uid).set(userDto.transformarDto());
  }

  getAllUsers(): Observable<UserApiRest[]>{
    return this.http.get<UserApiRest[]>(API_REST_UTL + 'api/users', this.authService.getHttpOptionsWithToken())
  }

}
