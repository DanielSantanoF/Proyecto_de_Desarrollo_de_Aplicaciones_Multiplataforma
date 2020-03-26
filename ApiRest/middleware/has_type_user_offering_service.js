'use strict'

const { handleError, ErrorHandler } = require('../controllers/error');

let middlewares = {
    
    ensureTypeUserOfferingService: (req,res,next)=>{
        if(req.user.type_user == 'OFRECE_SERVICIO'){
            next();
            (req, res, next);
        } else if(req.user.role == 'ADMIN'){
            next();
            (req, res, next)
        } else {
            return next(new ErrorHandler(403, "You are not allowed to access, you arent offering service"));
        }
    }
}
    
module.exports = middlewares;