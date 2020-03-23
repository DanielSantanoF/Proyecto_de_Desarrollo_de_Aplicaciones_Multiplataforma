import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { UsersService } from 'src/app/services/users.service';
import { EditUserDto } from 'src/app/models/editUser.dto';

export interface DatosEntradaDialogData {
  username: string;
  email: string;
  phone: string;
  name: string;
  typeUser: string;
  dateOfBirth: string;
  id: string;
}

@Component({
  selector: 'app-usuarios-edit-dialog',
  templateUrl: './usuarios-edit-dialog.component.html',
  styleUrls: ['./usuarios-edit-dialog.component.scss']
})
export class UsuariosEditDialogComponent implements OnInit {

  username: string;
  email: string;
  phone: string;
  name: string;
  typeUser: string;
  dateOfBirth: string;
  id: string;

  constructor(public dialogRef: MatDialogRef<UsuariosEditDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: DatosEntradaDialogData,
    private usersService: UsersService,) { }

  ngOnInit() {
    this.username =  this.data.username;
    this.email= this.data.email;
    this.phone= this.data.phone;
    this.name= this.data.name;
    this.typeUser= this.data.typeUser;
    this.dateOfBirth= this.data.dateOfBirth;
    this.id= this.data.id;
  }

  cerrarDialog(){
    this.dialogRef.close(false);
  }

  editUser(){
    const dto = new EditUserDto(this.username, this.email, this.phone, this.name, this.typeUser, this.dateOfBirth);
    this.usersService.putUserById(this.id, dto).subscribe(resp => {
      this.dialogRef.close(true);
    })
  }

}
