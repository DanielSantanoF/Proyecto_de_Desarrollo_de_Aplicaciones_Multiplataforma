'use strict'

const mongoose = require('mongoose');
const Schema = mongoose.Schema;

const userSchema = new mongoose.Schema({
    type_service: {type: String, enum: ['HACER_COMPRA', 'TRANSPORTE', 'COCINAR', 'REPARACIONES']},
    title: {type: String},
    description: {type: String},
    available: {type: Boolean, default: true},
    user_offering_service: { type: Schema.Types.ObjectId, ref: 'User' }
});

module.exports = mongoose.model('Servicio', userSchema);