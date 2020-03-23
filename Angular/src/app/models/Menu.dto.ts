export class MenuDto {
    icono: string;
    nombre: string;
    descripcion: string;

    constructor(i: string, n:string, d:string){
        this.icono = i;
        this.nombre = n;
        this.descripcion = d;
    }

}