import { Injectable } from '@angular/core';
import { AngularFirestore } from '@angular/fire/firestore';
import { UserDto } from '../models/user.dto';
import { Users } from '../models/user.interface';

const collectionName = 'users';

@Injectable({
  providedIn: 'root'
})
export class UsersService {

  constructor(private db: AngularFirestore) { }

  createUser(uid: string, userDto: UserDto) {
    return this.db.collection<Users>(collectionName).doc(uid).set(userDto.transformarDto());
  }

}
