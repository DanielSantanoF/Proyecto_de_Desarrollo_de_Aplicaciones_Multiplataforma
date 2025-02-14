'use strict'

const mongoose = require('mongoose');
const Schema = mongoose.Schema;

//The user avatar
const imgSchema = new mongoose.Schema({
    data: {type: String}, 
    contentType: {type: String}
});

const userSchema = new mongoose.Schema({
    username: {type: String},
    email: {type: String},
    phone: {type: String},
    name: {type: String},
    role: {type: String, enum: ['USER', 'ADMIN']},
    type_user: {type: String, enum: ['BUSCA_COMPANIA', 'OFRECE_ALOJAMIENTO', 'JOVEN', 'OFRECE_SERVICIO']},
    password: {type: String},
    avatar: imgSchema,
    date_of_birth: {type: Date},
    register_date: {type: Date, default: Date.now},
    validated: {type: Boolean, default: false},
    active: {type: Boolean, default: true},
    location_offered: { type: Schema.Types.ObjectId, ref: 'LocationOffered', default: null },
    living_with: { type: Schema.Types.ObjectId, ref: 'User' },
    favorite_users: [{ type: Schema.Types.ObjectId, ref: 'User' }]
});

module.exports = mongoose.model('User', userSchema);