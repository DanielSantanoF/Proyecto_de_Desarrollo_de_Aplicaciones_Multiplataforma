import { Injectable } from '@angular/core';
import {TranslateService} from '@ngx-translate/core';

@Injectable()
export class MenuService {

  constructor(public translate: TranslateService) {}

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
        icon: 'verified_user'
      },
      {
        link: '/services',
        label: 'Servicios',
        icon: 'room_service'
      },
      {
        link: '/locations',
        label: 'Localizaciones ofrecidas',
        icon: 'map'
      }
    ];
  }

}
