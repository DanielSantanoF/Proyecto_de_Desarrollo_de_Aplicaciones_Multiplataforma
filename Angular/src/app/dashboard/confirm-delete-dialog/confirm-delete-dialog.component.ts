import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';

export interface DatosEntradaDialogData {
  title: string;
}

@Component({
  selector: 'app-confirm-delete-dialog',
  templateUrl: './confirm-delete-dialog.component.html',
  styleUrls: ['./confirm-delete-dialog.component.scss']
})
export class ConfirmDeleteDialogComponent implements OnInit {

  title: string;

  constructor(public dialogRef: MatDialogRef<ConfirmDeleteDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: DatosEntradaDialogData) { }

  ngOnInit() {
    this.title = this.data.title;
  }

  cerrarDialog(){
    this.dialogRef.close(false);
  }
  
  confirmDelete(){
    this.dialogRef.close(true);
  }

}
