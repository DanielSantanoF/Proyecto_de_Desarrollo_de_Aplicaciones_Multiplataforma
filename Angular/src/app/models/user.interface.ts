import { Avatar } from "./avatar.interface";

export interface UserApiRest {
    validated: boolean;
    location_offered: string;
    favorite_users: string[];
    _id: string;
    username: string;
    email: string;
    phone: string;
    name: string;
    role: string;
    type_user: string;
    password: string;
    date_of_birth: Date;
    avatar: Avatar;
}