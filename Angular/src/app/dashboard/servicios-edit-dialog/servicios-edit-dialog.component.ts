import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { UsuariosEditDialogComponent } from '../usuarios-edit-dialog/usuarios-edit-dialog.component';
import { ServicesService } from 'src/app/services/services.service';
import { EditServiceDto } from 'src/app/models/editService.dto';

export interface DatosEntradaDialogData {
  typeService: string;
  title: string;
  description: string;
  id: string;
}

@Component({
  selector: 'app-servicios-edit-dialog',
  templateUrl: './servicios-edit-dialog.component.html',
  styleUrls: ['./servicios-edit-dialog.component.scss']
})
export class ServiciosEditDialogComponent implements OnInit {

  typeService: string;
  title: string;
  description: string;
  id: string;

  constructor(public dialogRef: MatDialogRef<UsuariosEditDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: DatosEntradaDialogData,
    private servicesService: ServicesService,) { }

  ngOnInit() {
    this.typeService = this.data.typeService;
    this.title = this.data.title;
    this.description = this.data.description;
    this.id = this.data.id;
  }

  cerrarDialog(){
    this.dialogRef.close(false);
  }

  editService(){
    const dto = new EditServiceDto(this.typeService, this.title, this.description);
    this.servicesService.putServiceById(this.id, dto).subscribe(resp => {
      this.dialogRef.close(true);
    })
  }

}
