import { Injectable } from '@angular/core';
import { AngularFirestore } from '@angular/fire/firestore';
import { UserDto } from '../models/user.dto';
import { UserApiRest } from '../models/user.interface';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from './auth.service';
import { UsersFirebase } from '../models/userFirebase.interface';
import { EditUserDto } from '../models/editUser.dto';
import { PasswordsDto } from '../models/passwords.dto';
import { ValidatedDto } from '../models/validated.dto';
import { ActiveDto } from '../models/Active.dto';

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

  putUserById(id: string, dto: EditUserDto): Observable<UserApiRest>{
    return this.http.put<UserApiRest>(API_REST_UTL + `api/users/${id}`, dto, this.authService.getHttpOptionsWithToken())
  }

  deleteUserById(id: string): Observable<Object>{
    return this.http.delete<Object>(API_REST_UTL + `api/users/${id}`, this.authService.getHttpOptionsWithToken())
  }

  putResetPassword(id: string): Observable<UserApiRest>{
    const dto = new PasswordsDto("12345678", "12345678");
    return this.http.put<UserApiRest>(API_REST_UTL + `api/users/password/${id}`, dto, this.authService.getHttpOptionsWithToken())
  }

  putValidated(id: string, state: boolean): Observable<UserApiRest>{
    const dto = new ValidatedDto(state);
    return this.http.put<UserApiRest>(API_REST_UTL + `api/users/admin/validate/${id}`, dto, this.authService.getHttpOptionsWithToken())
  }

  putActive(id: string, state: boolean): Observable<UserApiRest>{
    const dto = new ActiveDto(state);
    return this.http.put<UserApiRest>(API_REST_UTL + `api/users/admin/active/${id}`, dto, this.authService.getHttpOptionsWithToken())
  }

}
