'use strict'

const bcrypt = require('bcryptjs');
const User = require('./models/user');

//INSERTAMOS USUARIOS DE EJEMPLO
//Contraseña de todos los usuarios
let hash = bcrypt.hashSync("1234", parseInt(process.env.BCRYPT_ROUNDS));

let userAdmin = new User({
    _id: "5df919997853105891baf7b0",
    username: "admin",
    email: "admin@admin.com",
    name: "Admin",
    roles: 'ADMIN',
    password: hash,
    avatar: {
        data: "", 
        contentType: ""
    }
});
userAdmin.save();