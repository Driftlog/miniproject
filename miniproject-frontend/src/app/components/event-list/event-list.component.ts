import { Component, OnInit, inject } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { UserService } from 'src/app/user.service';
import { Event } from '../models/Event';

@Component({
  selector: 'app-event-list',
  templateUrl: './event-list.component.html',
  styleUrls: ['./event-list.component.css']
})
export class EventListComponent implements OnInit{

  activatedRoute = inject(ActivatedRoute)
  router = inject(Router)
  userSvc = inject(UserService)
  userID !: string
  eventsList : Event[] = []

  ngOnInit() {

    this.userID = window.localStorage.getItem("userID") as string
    this.userID = this.activatedRoute.snapshot.queryParams['userID']
    this.userSvc.loadUserEvents(this.userID).then(events => this.eventsList = events).catch(error => console.log("Error has occured: " + error))
  }

  deleteEvent( index : number) {

    const confirmation = confirm("delete event?")
    if (confirmation) {
     const eventToDelete  = this.eventsList[index]
     this.userSvc.deleteEvent(eventToDelete.eventID).then(
      resp => console.log(resp)
     )}

  }

  moreDetails(index : number) {
    const event : Event = this.eventsList[index]

    this.router.navigate(['/eventdetails', event.eventID])
  }

}
