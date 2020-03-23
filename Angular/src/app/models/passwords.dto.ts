export class PasswordsDto {
    password: string;
    confirmPassword: string;
    constructor(p: string, cp: string) {
        this.password = p;
        this.confirmPassword = cp;
    } 
}