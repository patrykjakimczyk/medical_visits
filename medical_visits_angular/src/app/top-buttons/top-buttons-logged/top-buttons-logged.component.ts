import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: 'app-top-buttons-logged',
  templateUrl: './top-buttons-logged.component.html',
  styleUrls: ['./top-buttons-logged.component.css']
})
export class TopButtonsLoggedComponent {
  @Input() user: string;
  @Output() logoutButtonClicked: EventEmitter<boolean>;

  constructor() {
    this.logoutButtonClicked = new EventEmitter<boolean>();
  }

  buttonClicked() {
    this.logoutButtonClicked.emit(true);
  }
}
