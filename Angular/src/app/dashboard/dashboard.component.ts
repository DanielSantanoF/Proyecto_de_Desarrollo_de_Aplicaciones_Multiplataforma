import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { MatDialog, MatSnackBar } from '@angular/material';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit{

  constructor(private router: Router,
    public dialog: MatDialog,
    private _snackBar: MatSnackBar) { }

  ngOnInit(){
    if (localStorage.getItem('token') || localStorage.getItem('role')) {
    } else {
      this.router.navigate(['/session/signin']);
    }
  }

}
