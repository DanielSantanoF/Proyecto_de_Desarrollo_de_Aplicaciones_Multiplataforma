import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { LocationsService } from 'src/app/services/locations.service';
import { EditLocationDto } from 'src/app/models/editLocation.dto';

export interface DatosEntradaDialogData {
  country: string;
  isoCode: string;
  city: string;
  street: string;
  postalCode: string;
  id: string;
}

@Component({
  selector: 'app-localizaciones-edit-dialog',
  templateUrl: './localizaciones-edit-dialog.component.html',
  styleUrls: ['./localizaciones-edit-dialog.component.scss']
})
export class LocalizacionesEditDialogComponent implements OnInit {

  country: string;
  isoCode: string;
  city: string;
  street: string;
  postalCode: string;
  id: string;

  constructor(public dialogRef: MatDialogRef<LocalizacionesEditDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: DatosEntradaDialogData,
    private locationsService: LocationsService,) { }

  ngOnInit() {
    this.country = this.data.country;
    this.isoCode = this.data.isoCode;
    this.city = this.data.city;
    this.street = this.data.street;
    this.postalCode = this.data.postalCode;
    this.id = this.data.id;
  }

  cerrarDialog(){
    this.dialogRef.close(false);
  }

  editLocation(){
    const dto = new EditLocationDto(this.country, this.isoCode, this.city, this.street, this.postalCode);
    this.locationsService.putLocationById(this.id, dto).subscribe(resp => {
      this.dialogRef.close(true);
    })
  }

}
