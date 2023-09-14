import { Component, ElementRef, OnInit, ViewChild, inject } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { AppService } from 'src/app/app.service';
import { AuthService } from 'src/app/auth.service';
import { User } from '../models/User';
import { ulid } from 'ulid';
import { Login } from '../models/Login';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit{

  @ViewChild('file') imageFile !: ElementRef;

  fb = inject(FormBuilder)
  appSvc = inject(AppService)
  signUpForm !: FormGroup
  authSvc = inject(AuthService)


  ngOnInit() {
    this.signUpForm = this.createForm()
  }

  createForm() {
    return this.fb.group(
      {userID: this.fb.control<string>(''),
      userName: this.fb.control<string>(''),
      userPassword: this.fb.control<string>(''),
      email: this.fb.control<string>(''),
      address: this.fb.control<string>(''),
      phoneNumber: this.fb.control<number>(0),
      image: this.fb.control<string>(''),
      calendarID: this.fb.control<string>('')}
    )
  }


  signUp() {
    const newUser = this.signUpForm.value as User
    newUser.userID = ulid().substring(0, 7)
    newUser.image = 'yeah'
    newUser.calendarID = ulid().substring(0, 7)

    const formData : FormData = new FormData()

    formData.set("user", JSON.stringify(newUser))

    if (this.imageFile.nativeElement.files) {
      const fileArr : File[] = Array.from(this.imageFile.nativeElement.files)
      formData.set("file", fileArr[0])}

    this.authSvc.signUp(formData).then(value => {
      console.log(value)
      const resptokenObj = <Login>value
      localStorage.setItem("token", resptokenObj.token)
      localStorage.setItem("userID", resptokenObj.email)
      localStorage.setItem("userID", resptokenObj.userID)
      //put the authentication token into storage
    }).catch(
      (error) => {
        console.log(error)
      }
      //put an error message here
    )
  }

}
