import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LocalizacionesListadoComponent } from './localizaciones-listado.component';

describe('LocalizacionesListadoComponent', () => {
  let component: LocalizacionesListadoComponent;
  let fixture: ComponentFixture<LocalizacionesListadoComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LocalizacionesListadoComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LocalizacionesListadoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
