import { Component, inject, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.css']
})
export class HomepageComponent implements OnInit{

  router = inject(Router)
  eventID = "01HA6SF"
  userID = "1"

  ngOnInit() {

  }


  login() {
    this.router.navigate(['/login'])
  }

  signUp() {
    this.router.navigate(['/signUp'])
  }

}
