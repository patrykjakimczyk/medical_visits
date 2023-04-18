import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HomePageLoggedComponent } from './home-page-logged.component';

describe('HomePageLoggedComponent', () => {
  let component: HomePageLoggedComponent;
  let fixture: ComponentFixture<HomePageLoggedComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ HomePageLoggedComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(HomePageLoggedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
