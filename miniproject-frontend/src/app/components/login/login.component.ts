import { Router } from '@angular/router';
import { Component, OnInit, inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AppService } from 'src/app/app.service';
import { AuthService } from 'src/app/auth.service';
import { Login } from '../models/Login';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit{

  loginForm !: FormGroup
  fb = inject(FormBuilder)
  appSvc = inject(AppService)
  authSvc = inject(AuthService)
  router = inject(Router)

  ngOnInit() {
    this.loginForm = this.createForm()
  }

  createForm() {
    return this.fb.group({
      email: this.fb.control<string>('', [Validators.email, Validators.required]),
      password: this.fb.control<string>('', [Validators.required])
    })
  }

  login() {
    const loginDetails =  this.loginForm.value as Login
    this.authSvc.login(loginDetails).then( (value) => {
      const login = <Login> value
      localStorage.setItem("email", login.email)
      localStorage.setItem("token", login.token )
      localStorage.setItem("userID", login.userID)

      console.log("running into next component")
      console.log("running into next component")
      console.log("running into next component")
      console.log("running into next component")
      console.log("running into next component")
      this.router.navigate(["/homepage", login.userID])




    }).catch((error) => {
      console.log(error)
      console.log(error)
      console.log(error)
      console.log(error)

    })

  }

  googleLogin() {


  }







}
