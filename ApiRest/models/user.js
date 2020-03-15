'use strict'

const mongoose = require('mongoose');
const Schema = mongoose.Schema;

const imgSchema = new mongoose.Schema({
    data: {type: String}, 
    contentType: {type: String}
});

const userSchema = new mongoose.Schema({
    username: {type: String},
    email: {type: String},
    name: {type: String},
    roles: {type: String, enum: ['USER', 'ADMIN']},
    password: {type: String},
    avatar: imgSchema,
    register_date: {type: Date, default: Date.now}
});

module.exports = mongoose.model('User', userSchema);