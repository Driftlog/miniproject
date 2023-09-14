import { Injectable } from '@angular/core';
import { GoogleMap } from '@angular/google-maps';
import { Event } from './components/models/Event';

@Injectable({
  providedIn: 'root'
})
export class GoogleMapService {

  directionsObj!: google.maps.DirectionsResult
  markers: google.maps.Marker[] = []

  constructor() { }

  




}
