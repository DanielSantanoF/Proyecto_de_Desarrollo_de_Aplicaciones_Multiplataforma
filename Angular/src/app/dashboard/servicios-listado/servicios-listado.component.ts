import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator, MatTableDataSource, MatDialog, MatSnackBar } from '@angular/material';
import { Servicio } from 'src/app/models/service.interface';
import { Router } from '@angular/router';
import { ServicesService } from 'src/app/services/services.service';
import { ConfirmDeleteDialogComponent } from '../confirm-delete-dialog/confirm-delete-dialog.component';
import { ServiciosEditDialogComponent } from '../servicios-edit-dialog/servicios-edit-dialog.component';

@Component({
  selector: 'app-servicios-listado',
  templateUrl: './servicios-listado.component.html',
  styleUrls: ['./servicios-listado.component.scss']
})
export class ServiciosListadoComponent implements OnInit {

  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
  dataSource = new MatTableDataSource<Servicio>();
  displayedColumns: string[] = ['title', 'typeService', 'available', 'acciones'];

  constructor(private router: Router,
    private servicesService: ServicesService,
    public dialog: MatDialog,
    private _snackBar: MatSnackBar,) { }

    ngOnInit() {
      if (localStorage.getItem('token') || localStorage.getItem('role')) {
      } else {
        this.router.navigate(['/session/signin']);
      }
      this.getAllServices();
    }
  
    applyFilter(event: Event) {
      const filterValue = (event.target as HTMLInputElement).value;
      this.dataSource.filter = filterValue.trim().toLowerCase();
    }
  
    getAllServices() {
      this.servicesService.getAllServices().subscribe(resp => {
        this.dataSource = new MatTableDataSource<Servicio>(resp);
      })
    }

    editService(data: Servicio){
      const dialogRef = this.dialog.open(ServiciosEditDialogComponent, {
        data: {
          typeService: data.type_service,
          title: data.title,
          description: data.description,
          id: data._id
        }
      });
      dialogRef.afterClosed().subscribe(resp => {
        if (resp) {
          this._snackBar.open("Servicio editado correctamente", "✔️");
          this.getAllServices();
        }
      });
    }

    deleteService(id: string){
      const dialogRef = this.dialog.open(ConfirmDeleteDialogComponent, {
        data: {
          title: "¿Eliminar servicio?"
        }
      });
      dialogRef.afterClosed().subscribe(resp => {
        if (resp) {
          this.servicesService.deleteServiceById(id).subscribe(resp => {
            this._snackBar.open("Servicio eliminado correctamente", "✔️");
            this.getAllServices();
          })
        }
      });
    }

}
