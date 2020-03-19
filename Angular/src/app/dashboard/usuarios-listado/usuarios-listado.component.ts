import { Component, OnInit, ViewChild } from '@angular/core';
import { UsersService } from 'src/app/services/users.service';
import { UserApiRest } from '../../models/user.interface';
import { Router } from '@angular/router';
import { MatPaginator, MatTableDataSource } from '@angular/material';

@Component({
  selector: 'app-usuarios-listado',
  templateUrl: './usuarios-listado.component.html',
  styleUrls: ['./usuarios-listado.component.scss']
})
export class UsuariosListadoComponent implements OnInit {

  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;
  dataSource = new MatTableDataSource<UserApiRest>();
  displayedColumns: string[] = ['name', 'typeUser', 'email', 'role', 'acciones'];

  constructor(private router: Router,
    private usersService: UsersService) { }

  ngOnInit() {
    if (localStorage.getItem('token') || localStorage.getItem('role')) {
    } else {
      this.router.navigate(['/session/signin']);
    }
    this.getAllUsers();
  }

  getAllUsers(){
    this.usersService.getAllUsers().subscribe(resp => {
        //this.listaUsuarios = resp;
        this.dataSource = new MatTableDataSource<UserApiRest>(resp);
    })
  }

  deleteUser(){

  }

}
