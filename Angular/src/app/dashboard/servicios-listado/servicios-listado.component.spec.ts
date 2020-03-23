import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ServiciosListadoComponent } from './servicios-listado.component';

describe('ServiciosListadoComponent', () => {
  let component: ServiciosListadoComponent;
  let fixture: ComponentFixture<ServiciosListadoComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ServiciosListadoComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ServiciosListadoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
