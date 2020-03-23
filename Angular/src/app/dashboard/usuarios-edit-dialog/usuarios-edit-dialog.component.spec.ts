import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UsuariosEditDialogComponent } from './usuarios-edit-dialog.component';

describe('UsuariosEditDialogComponent', () => {
  let component: UsuariosEditDialogComponent;
  let fixture: ComponentFixture<UsuariosEditDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UsuariosEditDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UsuariosEditDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
