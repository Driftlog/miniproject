import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomepageComponent } from './components/homepage/homepage.component';
import { LoginComponent } from './components/login/login.component';
import { SignupComponent } from './components/signup/signup.component';
import { GoogleMapAngularComponent } from './components/google-map-angular/google-map-angular.component';
import { UserHomeComponent } from './components/user-home/user-home.component';
import { DisplayrouteComponent } from './components/displayroute/displayroute.component';
import { EventListComponent } from './components/event-list/event-list.component';
import { EventDetailsComponent } from './components/event-details/event-details.component';

const routes: Routes = [
  {path: '', component: HomepageComponent},
  {path: 'login', component: LoginComponent},
  {path:'signUp', component: SignupComponent},
  {path:'googlemaps', component: GoogleMapAngularComponent},
  {path:'userhome/:id', component: UserHomeComponent},
  {path: 'displayroute/:eventID', component: DisplayrouteComponent},
  {path: 'eventlist', component: EventListComponent},
  {path: 'eventdetails/:eventID', component: EventDetailsComponent}
];
@NgModule({
  imports: [RouterModule.forRoot(routes, {useHash:true})],
  exports: [RouterModule]
})
export class AppRoutingModule { }
