'use strict'

const mongoose = require('mongoose');
const Schema = mongoose.Schema;

const locationSchema = new mongoose.Schema({
    country: {type: String},
    iso_code: {type: String},
    city: {type: String},
    street: {type: String},
    postal_code: {type: String},
    available: {type: Boolean, default: true}
});

module.exports = mongoose.model('LocationOffered', locationSchema);