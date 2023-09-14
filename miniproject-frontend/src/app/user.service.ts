import { firstValueFrom, lastValueFrom } from 'rxjs';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Event } from './components/models/Event';
import { User } from './components/models/User';
import { Email } from './components/models/Email';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  http = inject(HttpClient)
  homeUser !: User
  authSvc = inject(AuthService)

  constructor() { }

  loadUserDetails(userID : string) {

    let headers = new HttpHeaders()
    headers = this.authSvc.addAuthorizationHeader(headers)

    const params = new HttpParams()
                    .set("userID", userID)

    return firstValueFrom(this.http.get('/user/getUser', {params, headers}))
  }

  loadUserEvents(userID : string) : Promise<Event[]> {

    let headers = new HttpHeaders()
    headers = this.authSvc.addAuthorizationHeader(headers)

    const params = new HttpParams()
                    .set("userID", userID)

    return lastValueFrom(this.http.get<Event[]>('/user/getEvents', {params, headers}))

  }

  getEvent(eventID : string) {
    let headers = new HttpHeaders()
    headers = this.authSvc.addAuthorizationHeader(headers)

    return firstValueFrom(this.http.get(`user/getEvent/${eventID}`, {headers}))

  }

  postEvent(newEvent : Event, userID : string) {
    let headers = new HttpHeaders()
    headers = this.authSvc.addAuthorizationHeader(headers)

    return firstValueFrom(this.http.post(`/user/postEvent/${userID}`, newEvent, { responseType: 'text', headers}))

  }

  sendMail(email: Email) {
    let headers = new HttpHeaders()
    headers = this.authSvc.addAuthorizationHeader(headers)

    return firstValueFrom(this.http.post("/user/sendMail", email, {headers}))
  }

  deleteEvent(eventID : string) {

    let headers = new HttpHeaders()
    headers = this.authSvc.addAuthorizationHeader(headers)

    const params = new HttpParams()
                    .set("eventID", eventID)

    return firstValueFrom(this.http.delete("/user/deleteEvent", {responseType: 'text', headers, params}))
  }



}
