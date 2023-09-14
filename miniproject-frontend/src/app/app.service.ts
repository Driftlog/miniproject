import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { firstValueFrom } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AppService {

  http = inject(HttpClient)
  constructor() { }

  login(loginEmail : string, loginPass : string) {

    const headers = new HttpHeaders()
                      .set('Content-Type', 'application/x-www-form-urlencoded')


    return firstValueFrom(this.http.post('/login' , {headers, responseType: 'text'}))
  }

  signUp(formData : FormData) {

    return firstValueFrom(this.http.post('/signup', formData))
  }
}
