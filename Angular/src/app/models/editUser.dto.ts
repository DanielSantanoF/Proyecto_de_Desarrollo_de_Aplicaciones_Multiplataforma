export class EditUserDto {
    username: string;
    email: string;
    phone: string;
    name: string;
    typeUser: string;
    dateOfBirth: string;
    constructor(u: string, e: string, p: string, n: string, tu: string, dob: string) {
        this.username = u;
        this.email = e;
        this.phone = p;
        this.name = n;
        this.typeUser = tu;
        this.dateOfBirth = dob;
    } 
}