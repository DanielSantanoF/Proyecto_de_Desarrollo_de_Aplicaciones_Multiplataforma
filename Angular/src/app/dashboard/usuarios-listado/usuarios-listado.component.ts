import { Component, OnInit, ViewChild } from '@angular/core';
import { UsersService } from 'src/app/services/users.service';
import { UserApiRest } from '../../models/user.interface';
import { Router } from '@angular/router';
import { MatPaginator, MatTableDataSource, MatDialog, MatSnackBar } from '@angular/material';
import { ConfirmDeleteDialogComponent } from '../confirm-delete-dialog/confirm-delete-dialog.component';
import { UsuariosEditDialogComponent } from '../usuarios-edit-dialog/usuarios-edit-dialog.component';
import { ConfirmActionDialogComponent } from '../confirm-action-dialog/confirm-action-dialog.component';

@Component({
  selector: 'app-usuarios-listado',
  templateUrl: './usuarios-listado.component.html',
  styleUrls: ['./usuarios-listado.component.scss']
})
export class UsuariosListadoComponent implements OnInit {

  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
  dataSource = new MatTableDataSource<UserApiRest>();
  displayedColumns: string[] = ['name', 'typeUser', 'email', 'role', 'acciones'];

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

  deleteUser(id: string) {
    const dialogRef = this.dialog.open(ConfirmDeleteDialogComponent, {
      data: {
        title: "¿Eliminar usuario?"
      }
    });
    dialogRef.afterClosed().subscribe(resp => {
      if (resp) {
        this.usersService.deleteUserById(id).subscribe(resp => {
          this._snackBar.open("Usuario eliminado correctamente", "✔️");
          this.getAllUsers();
        })
      }
    });
  }

  editUser(data: UserApiRest){
    let dateOfUser = new Date(data.date_of_birth);
    let day = dateOfUser.getDate();
    let month = dateOfUser.getMonth() + 1;
    let year = dateOfUser.getFullYear();
    const date = year.toString() + "-" + month.toString() + "-" + day.toString();
    const dialogRef = this.dialog.open(UsuariosEditDialogComponent, {
      data: {
        username: data.username,
        email: data.email,
        phone: data.phone,
        name: data.name,
        typeUser: data.type_user,
        dateOfBirth: date,
        id: data._id
      }
    });
    dialogRef.afterClosed().subscribe(resp => {
      if (resp) {
        this._snackBar.open("Usuario editado correctamente", "✔️");
        this.getAllUsers();
      }
    });
  }

  resetPassword(id: string){
    const dialogRef = this.dialog.open(ConfirmActionDialogComponent, {
      data: {
        title: "¿Reiniciar contraseña del usuario?"
      }
    });
    dialogRef.afterClosed().subscribe(resp => {
      if (resp) {
        this.usersService.putResetPassword(id).subscribe(resp => {
          this._snackBar.open("Contraseña restablecida correctamente", "✔️");
        })
      }
    });
  }

}
