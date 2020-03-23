export class EditLocationDto {
    country: string;
    isoCode: string;
    city: string;
    street: string;
    postalCode: string;
    constructor(c: string, ic: string, ci: string, s: string, pc: string) {
        this.country = c;
        this.isoCode = ic;
        this.city = ci;
        this.street = s;
        this.postalCode = pc;
    } 
}