import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AdministrarUsuariosListadoComponent } from './administrar-usuarios-listado.component';

describe('AdministrarUsuariosListadoComponent', () => {
  let component: AdministrarUsuariosListadoComponent;
  let fixture: ComponentFixture<AdministrarUsuariosListadoComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AdministrarUsuariosListadoComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AdministrarUsuariosListadoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
