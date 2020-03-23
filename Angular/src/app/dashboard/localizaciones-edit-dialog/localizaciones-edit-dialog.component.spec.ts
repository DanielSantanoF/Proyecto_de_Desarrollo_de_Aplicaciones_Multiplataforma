import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LocalizacionesEditDialogComponent } from './localizaciones-edit-dialog.component';

describe('LocalizacionesEditDialogComponent', () => {
  let component: LocalizacionesEditDialogComponent;
  let fixture: ComponentFixture<LocalizacionesEditDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LocalizacionesEditDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LocalizacionesEditDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
