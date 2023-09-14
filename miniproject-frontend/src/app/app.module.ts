import { GoogleMapService } from './google-map.service';
import { NgModule, isDevMode } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ReactiveFormsModule } from '@angular/forms';
import {HttpClientModule} from '@angular/common/http';
import { HomepageComponent } from './components/homepage/homepage.component';
import { LoginComponent } from './components/login/login.component'
import { RouterModule } from '@angular/router';
import { SignupComponent } from './components/signup/signup.component';
import { UserHomeComponent } from './components/user-home/user-home.component';
import { PlacesSearchComponent } from './components/places-search/places-search.component';
import { GoogleMapsModule } from '@angular/google-maps';
import { GoogleMapAngularComponent } from './components/google-map-angular/google-map-angular.component';
import { MaterialModule } from '../material/material.module';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { OAuthModule } from 'angular-oauth2-oidc';
import { DisplayrouteComponent } from './components/displayroute/displayroute.component';
import { EventListComponent } from './components/event-list/event-list.component';
import { EventDetailsComponent } from './components/event-details/event-details.component';
import { ServiceWorkerModule } from '@angular/service-worker';


@NgModule({
  declarations: [
    AppComponent,
    HomepageComponent,
    LoginComponent,
    SignupComponent,
    UserHomeComponent,
    PlacesSearchComponent,
    GoogleMapAngularComponent,
    DisplayrouteComponent,
    EventListComponent,
    EventDetailsComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule,
    RouterModule,
    ReactiveFormsModule,
    GoogleMapsModule,
    MaterialModule,
    BrowserAnimationsModule,
    OAuthModule.forRoot(),
    ServiceWorkerModule.register('ngsw-worker.js', {
      enabled: !isDevMode(),
      // Register the ServiceWorker as soon as the application is stable
      // or after 30 seconds (whichever comes first).
      registrationStrategy: 'registerWhenStable:30000'
    })
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
