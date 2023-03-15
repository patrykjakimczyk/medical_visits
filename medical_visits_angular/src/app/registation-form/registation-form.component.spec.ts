import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RegistationFormComponent } from './registation-form.component';

describe('RegistationFormComponent', () => {
  let component: RegistationFormComponent;
  let fixture: ComponentFixture<RegistationFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RegistationFormComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RegistationFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
