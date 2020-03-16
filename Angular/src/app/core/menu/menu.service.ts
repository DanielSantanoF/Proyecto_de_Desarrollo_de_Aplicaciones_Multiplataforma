import { Injectable } from '@angular/core';
import {TranslateService} from '@ngx-translate/core';

@Injectable()
export class MenuService {

  constructor(public translate: TranslateService) {}

  getAllUser() {
    return [
      {
        link: '/',
        label: 'Menú principal',
        icon: 'explore'
      },
      {
        link: '/users',
        label: 'Users',
        icon: 'supervised_user_circle'
      }
    ];
  }

  getAllAdmin() {
    return [
      {
        link: '/',
        label: 'Menú principal',
        icon: 'explore'
      },
      {
        link: '/users',
        label: 'Users',
        icon: 'supervised_user_circle'
      },
      {
        link: '/adminUsers',
        label: 'Gestionar usuarios',
        icon: 'group_add'
      }
    ];
  }

}
