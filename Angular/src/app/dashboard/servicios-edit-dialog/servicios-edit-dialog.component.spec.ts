import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ServiciosEditDialogComponent } from './servicios-edit-dialog.component';

describe('ServiciosEditDialogComponent', () => {
  let component: ServiciosEditDialogComponent;
  let fixture: ComponentFixture<ServiciosEditDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ServiciosEditDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ServiciosEditDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
