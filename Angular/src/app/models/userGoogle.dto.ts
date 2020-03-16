export class UserGoogleDto {

    constructor(
        public name: string,
        public email: string,
        public avatar: string
    ) {}

    transformarDto() {
        return { 
            name: this.name, 
            email: this.email,
            avatar: this.avatar
        };
    }
    
}