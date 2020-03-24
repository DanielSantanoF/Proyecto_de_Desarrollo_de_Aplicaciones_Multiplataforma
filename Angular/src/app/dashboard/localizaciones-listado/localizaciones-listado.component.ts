import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator, MatTableDataSource, MatDialog, MatSnackBar } from '@angular/material';
import { LocationUser } from 'src/app/models/locationUser.interface';
import { Router } from '@angular/router';
import { LocationsService } from 'src/app/services/locations.service';
import { ConfirmDeleteDialogComponent } from '../confirm-delete-dialog/confirm-delete-dialog.component';
import { LocalizacionesEditDialogComponent } from '../localizaciones-edit-dialog/localizaciones-edit-dialog.component';

@Component({
  selector: 'app-localizaciones-listado',
  templateUrl: './localizaciones-listado.component.html',
  styleUrls: ['./localizaciones-listado.component.scss']
})
export class LocalizacionesListadoComponent implements OnInit {

  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
  dataSource = new MatTableDataSource<LocationUser>();
  displayedColumns: string[] = ['country', 'city', 'street', 'available', 'acciones'];

  constructor(private router: Router,
    private locationsService: LocationsService,
    public dialog: MatDialog,
    private _snackBar: MatSnackBar, ) { }

  ngOnInit() {
    if (localStorage.getItem('token') || localStorage.getItem('role')) {
    } else {
      this.router.navigate(['/session/signin']);
    }
    this.getAllLocations();
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  getAllLocations() {
    this.locationsService.getAllLocations().subscribe(resp => {
      this.dataSource = new MatTableDataSource<LocationUser>(resp);
    })
  }

  editLocation(data: LocationUser) {
    const dialogRef = this.dialog.open(LocalizacionesEditDialogComponent, {
      data: {
        country: data.location_offered.country,
        isoCode: data.location_offered.iso_code,
        city: data.location_offered.city,
        street: data.location_offered.street,
        postalCode: data.location_offered.postal_code,
        id: data.location_offered._id
      }
    });
    dialogRef.afterClosed().subscribe(resp => {
      if (resp) {
        this._snackBar.open("Localización ofrecida editada correctamente", "✔️");
        this.getAllLocations();
      }
    });
  }

  deleteLocation(id: string){
    const dialogRef = this.dialog.open(ConfirmDeleteDialogComponent, {
      data: {
        title: "¿Eliminar localización?"
      }
    });
    dialogRef.afterClosed().subscribe(resp => {
      if (resp) {
        this.locationsService.deleteLocationById(id).subscribe(resp => {
          this._snackBar.open("Localización ofrecida eliminada correctamente", "✔️");
          this.getAllLocations();
        })
      }
    });
  }


}
