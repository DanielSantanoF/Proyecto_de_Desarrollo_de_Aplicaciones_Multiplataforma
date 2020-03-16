export class UserDto {

    constructor(
        public avatar: string,
        public email: string,
        public name: string,
        public roles: string,
        public uid: string,
        public username: string
    ) {}

    transformarDto() {
        return { 
            avatar: this.avatar,
            email: this.email,
            name: this.name,
            roles: this.roles,
            uid: this.uid,
            username: this.username
        };
    }
    
}