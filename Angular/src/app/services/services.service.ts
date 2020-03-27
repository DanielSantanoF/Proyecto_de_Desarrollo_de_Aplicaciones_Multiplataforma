import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import { AuthService } from './auth.service';
import { Observable } from 'rxjs';
import { Servicio } from '../models/service.interface';
import { EditServiceDto } from '../models/editService.dto';

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
export class ServicesService {

  constructor(private http: HttpClient,
    private authService: AuthService) { }

  getAllServices(): Observable<Servicio[]> {
    return this.http.get<Servicio[]>(API_REST_URL + 'api/services', this.authService.getHttpOptionsWithToken())
  }

  deleteServiceById(id: string): Observable<Object>{
    return this.http.delete<Object>(API_REST_URL + `api/services/${id}`, this.authService.getHttpOptionsWithToken())
  }

  putServiceById(id: string, dto: EditServiceDto): Observable<Object>{
    return this.http.put<Object>(API_REST_URL + `api/services/${id}`, dto, this.authService.getHttpOptionsWithToken())
  }

}
