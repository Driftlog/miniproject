import { Component, OnInit, inject } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/auth.service';
import { User } from '../models/User';
import { UserService } from 'src/app/user.service';
import { Event } from '../models/Event';

@Component({
  selector: 'app-user-home',
  templateUrl: './user-home.component.html',
  styleUrls: ['./user-home.component.css']
})
export class UserHomeComponent implements OnInit{

  router = inject(Router)
  authSvc = inject(AuthService)
  userSvc = inject(UserService)
  homeUser !: User
  eventList : Event[] = []

  constructor() {}

  ngOnInit() {
    this.userSvc.loadUserDetails(window.localStorage.getItem("userID") as string).then(
      value => {
        this.homeUser = <User> value
        localStorage.setItem("calendarID", this.homeUser.calendarID)
        localStorage.setItem("email", this.homeUser.email)
      }
    )

    this.userSvc.loadUserEvents(this.homeUser.calendarID)
        .then(events => {
          this.eventList = events}).catch((error) => {console.log("Error has occured: " + error)})

  }

  logout() {
    this.authSvc.logout()
    this.router.navigate(['/'])
  }

}
