import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import { AuthService } from './auth.service';
import { Observable } from 'rxjs';
import { LocationUser } from '../models/locationUser.interface';
import { EditLocationDto } from '../models/editLocation.dto';

export const API_REST_UTL = 'http://localhost:3000/';

const httpOptions = {
  headers: new HttpHeaders({
    'Content-type': 'application/json'
  })
};

@Injectable({
  providedIn: 'root'
})
export class LocationsService {

  constructor(private http: HttpClient,
    private authService: AuthService) { }

  getAllLocations(): Observable<LocationUser[]> {
    return this.http.get<LocationUser[]>(API_REST_UTL + 'api/locations', this.authService.getHttpOptionsWithToken())
  }

  deleteLocationById(id: string): Observable<Object>{
    return this.http.delete<Object>(API_REST_UTL + `api/locations/${id}`, this.authService.getHttpOptionsWithToken())
  }

  putLocationById(id: string, dto: EditLocationDto): Observable<Object>{
    return this.http.put<Object>(API_REST_UTL + `api/locations/${id}`, dto, this.authService.getHttpOptionsWithToken())
  }

}
