import { firstValueFrom } from 'rxjs';
import { Injectable, inject } from '@angular/core';
import { Login } from './components/models/Login';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { User } from './components/models/User';



@Injectable({
  providedIn: 'root'
})
export class AuthService {


  http = inject(HttpClient)

  constructor() {
  }

  signUp(formData : FormData) {
    return firstValueFrom(this.http.post('/api/register', formData))
  }

  login(loginDetails: Login) {

   return firstValueFrom(this.http.post('/api/login', loginDetails))
  }

  logout() {
    window.localStorage.clear()
  }

  public addAuthorizationHeader(headers: HttpHeaders) {

    if (window.localStorage.getItem("token") != null) {
      return headers.set("Authorization", "Bearer " + window.localStorage.getItem("token"))
    }

    return headers

  }





}
