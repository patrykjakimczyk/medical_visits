import { Component, OnInit } from '@angular/core';
import { ApiService } from 'src/app/service/api.service';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-doctors-list',
  templateUrl: './doctors-list.component.html',
  styleUrls: ['./doctors-list.component.css', '../../home-page/home-page.component.css', '../../form/registation-form/registation-form.component.css']
})
export class DoctorsListComponent implements OnInit{

  constructor(private apiService: ApiService) {}

  ngOnInit(): void {
    this.apiService.getDoctors().subscribe({
      next: (response: any) => {
        console.log(response);
      },
      error: (error: HttpErrorResponse) => {
        console.log(error)
      }
    });
  }

  insertDoctors() {
    this.apiService.getDoctors().subscribe({
      next: (response: any) => {
        console.log(response);
      },
      error: (error: HttpErrorResponse) => {
        console.log(error)
      }
    });
    // //subscribe (lista obiektow)
    // //hook do listy
    // //tworzyc element listy
    // const User = {
    //   name: "name"
    // };


    // for (user item : lista obiektow) {
    // const liTemplate: DomListItem = new DomListItem(`<li class="li-element">
    // <div class="li-element-div">${item.name}</div>
    // <div class="li-element-div">Zbychowski</div>
    // <div class="li-element-div">Gynecologist</div>
    // <div class="li-element-div">213769420</div>
    // <div class="li-element-div">zbyszekkozak@zut.edu.pl</div>
    // <div class="li-element-div">
    //     <a>Details</a>&nbsp;&nbsp;&nbsp;
    //     <a>Edit</a>
    // </div>
    // </li>`);
    //   //hook.add(litemplate)
    // }
  }
}
