import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class TimeUtilService {

  constructor() { }

  beforeHours(date : Date, hours: number) {
    const currentTime = date.getTime()
    const newDate = currentTime - (hours*60*60*1000)
    return new Date(newDate)
  }

  beforeMinutes(date: Date, minutes: number) {
    const currentTime = date.getTime()
    const newDate = currentTime - (minutes*60*1000)
    return new Date(newDate)
  }

  beforeDay(date : Date, day : number) {
    const currentTime = date.getTime()
    const newDate = currentTime - (day*24*60*60*1000)
    return new Date(newDate)
  }

  beforeSeconds(date : Date, seconds : number) {
    const currentTime = date.getTime()
    const newDate = currentTime - (seconds*1000)
    return new Date(newDate)
  }
}
