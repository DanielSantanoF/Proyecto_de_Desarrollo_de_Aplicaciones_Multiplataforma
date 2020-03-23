export class EditServiceDto {
    typeService: string;
    title: string;
    description: string;
    constructor(ts: string, t: string, d: string) {
        this.typeService = ts;
        this.title = t;
        this.description = d;
    } 
}