import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator, MatTableDataSource, MatDialog, MatSnackBar } from '@angular/material';
import { UserApiRest } from 'src/app/models/user.interface';
import { Router } from '@angular/router';
import { UsersService } from 'src/app/services/users.service';

@Component({
  selector: 'app-administrar-usuarios-listado',
  templateUrl: './administrar-usuarios-listado.component.html',
  styleUrls: ['./administrar-usuarios-listado.component.scss']
})
export class AdministrarUsuariosListadoComponent implements OnInit {

  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
  dataSource = new MatTableDataSource<UserApiRest>();
  displayedColumns: string[] = ['name', 'validated', 'active', 'accionesValidated', 'accionesActivate'];

  constructor(private router: Router,
    private usersService: UsersService,
    public dialog: MatDialog,
    private _snackBar: MatSnackBar, ) { }

  ngOnInit() {
    if (localStorage.getItem('token') || localStorage.getItem('role')) {
    } else {
      this.router.navigate(['/session/signin']);
    }
    this.getAllUsers();
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  getAllUsers() {
    this.usersService.getAllUsers().subscribe(resp => {
      this.dataSource = new MatTableDataSource<UserApiRest>(resp);
    })
  }

  putValidatedUser(id: string, state: boolean) {
    this.usersService.putValidated(id, state).subscribe(resp => {
      this._snackBar.open("Usuario editado correctamente", "✔️");
      this.getAllUsers();
    })
  }

  putActiveUser(id: string, state: boolean) {
    this.usersService.putActive(id, state).subscribe(resp => {
      this._snackBar.open("Usuario editado correctamente", "✔️");
      this.getAllUsers();
    })
  }

}
