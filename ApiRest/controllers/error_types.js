'use strict'

const error_types = {
    Error400: (msg) => { //bad request
        let err = Error.apply(this, [msg]);
        this.name = err.name = "Error400";
        this.message = err.message;
        this.stack = err.stack;
        return this;
    },
    Error401: (msg) => { //no autorizado
        let err = Error.apply(this, [msg]);
        this.name = err.name = "Error401";
        this.message = err.message;
        this.stack = err.stack;
        return this;
    },
    Error403: (msg) => { //prohibido
        let err = Error.apply(this, [msg]);
        this.name = err.name = "Error403";
        this.message = err.message;
        this.stack = err.stack;
        return this;
    },
    Error404: (msg) => { //no encontrado
        let err = Error.apply(this, [msg]);
        this.name = err.name = "Error404";
        this.message = err.message;
        this.stack = err.stack;
        return this;
    },
    InfoError: (msg) => { //todo ok, solo información
        let err = Error.apply(this, [msg]);
        this.name = err.name = "InfoError";
        this.message = err.message;
        this.stack = err.stack;
        return this;
    } 
};

module.exports = error_types;