import { Avatar } from './avatar.interface';
import { LocationOffered } from './locationOffered.interaface';

export interface LocationUser {
    location_offered: LocationOffered;
    _id: string;
    username: string;
    date_of_birth: Date;
    avatar: Avatar;
}