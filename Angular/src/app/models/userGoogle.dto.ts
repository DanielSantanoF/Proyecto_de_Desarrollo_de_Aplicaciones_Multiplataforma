export class UserGoogleDto {

    constructor(
        public email: string,
        public uid: string
    ) {}

    transformarDto() {
        return { 
            email: this.email,
            uid: this.uid
        };
    }
    
}