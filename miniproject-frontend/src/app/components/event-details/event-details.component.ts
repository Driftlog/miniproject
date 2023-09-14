
import { PlaceDetails } from './../models/PlaceDetails';
import { GoogleMapService } from 'src/app/google-map.service';
import { UserService } from './../../user.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Component, ViewChild, inject, AfterViewInit, Input } from '@angular/core';
import { Event } from '../models/Event';
import { Email } from '../models/Email';
import { GoogleMap } from '@angular/google-maps';


@Component({
  selector: 'app-event-details',
  templateUrl: './event-details.component.html',
  styleUrls: ['./event-details.component.css']
})
export class EventDetailsComponent {

  @Input() event!: Event


  activatedRoute = inject(ActivatedRoute)
  placeDetails!:  PlaceDetails
  router = inject(Router)
  userSvc = inject(UserService)
  googleMapSvc = inject(GoogleMapService)
  center!: google.maps.LatLngLiteral
  zoom = 12
  options: google.maps.MapOptions = {
    mapTypeId: google.maps.MapTypeId.ROADMAP,
    zoomControl: false,
    disableDoubleClickZoom: false,
    maxZoom: 15,
    minZoom: 8,
  }

  calendarID !: string
  imageURL !: string | undefined
  @ViewChild('googleMap')
  map !: GoogleMap

  constructor() {}

  ngOnInit() {
    this.center = {lat : 1.3521 , lng: 103.8198}

    if (this.event != null) {this.initMap(this.event.location)}

    if ((this.activatedRoute.snapshot.params != null || undefined) && (this.event == null)) {
    const eventID = this.activatedRoute.snapshot.params['eventID']
    this.userSvc.getEvent(eventID).then(event => {this.event = event as Event

      this.initMap(this.event.location)
        }


      ).catch((error) =>
    {
      console.log(error)
    })
  }
  }

  initMap(location : string) {

    const request = {
      query: location,
      fields: ['photos', 'name', 'formatted_address', 'icon']
    }

    console.log("running")

    const googleMapSvc = new google.maps.places.PlacesService(this.map.googleMap!)
    googleMapSvc.findPlaceFromQuery(request, (results, status) => {

      if (status == google.maps.places.PlacesServiceStatus.OK && results != undefined) {

        console.log(results[0])

        const result = results[0]
        if (result.geometry?.location) {
          this.center.lat = result.geometry?.location.lat()
          this.center.lng = result.geometry.location.lng()
          const marker = new google.maps.Marker({
            position: this.center,
            map: this.map.googleMap
          });
        }




        this.placeDetails = {

          name: results[0].name as string,
          address: results[0].formatted_address as string,
          icon: results[0].icon as string,
        }

        if (result.photos !=undefined) {
          this.placeDetails.photoURL = result.photos[0].getUrl()
        }

      } else { console.log(status)
      }


    })

  }


  ngAfterViewInit() {
    this.initMap(this.event.location)}




}
