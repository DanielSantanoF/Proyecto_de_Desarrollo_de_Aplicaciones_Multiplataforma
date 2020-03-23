import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';

export interface DatosEntradaDialogData {
  title: string;
}

@Component({
  selector: 'app-confirm-action-dialog',
  templateUrl: './confirm-action-dialog.component.html',
  styleUrls: ['./confirm-action-dialog.component.scss']
})
export class ConfirmActionDialogComponent implements OnInit {

  title: string;

  constructor(public dialogRef: MatDialogRef<ConfirmActionDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: DatosEntradaDialogData) { }

  ngOnInit() {
    this.title = this.data.title;
  }

  cerrarDialog(){
    this.dialogRef.close(false);
  }
  
  confirmAction(){
    this.dialogRef.close(true);
  }

}
