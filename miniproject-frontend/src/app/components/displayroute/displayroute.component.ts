import { PlaceDetails } from './../models/PlaceDetails';
import { GoogleMapService } from 'src/app/google-map.service';
import { UserService } from './../../user.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Component, ViewChild, inject, AfterViewInit } from '@angular/core';
import { Event } from '../models/Event';
import { Email } from '../models/Email';
import { GoogleMap } from '@angular/google-maps';

@Component({
  selector: 'app-displayroute',
  templateUrl: './displayroute.component.html',
  styleUrls: ['./displayroute.component.css']
})
export class DisplayrouteComponent {

  activatedRoute = inject(ActivatedRoute)
  placeDetails!:  PlaceDetails
  router = inject(Router)
  userSvc = inject(UserService)


  calendarID !: string
  newEvent !: Event
  userID!: string
  imageURL !: string | undefined

  constructor() {}

  ngOnInit() {
    this.userID = "1"
    this.userID = localStorage.getItem("userID") as string
    const eventID = this.activatedRoute.snapshot.params['eventID']
    this.userSvc.getEvent(eventID).then(event => this.newEvent = event as Event
      ).catch((error) =>
    {
      console.log(error)

    })


    console.log(this.placeDetails)

  }





}
