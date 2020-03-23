'use strict'

const { handleError, ErrorHandler } = require('../controllers/error');

let middlewares = {
    
    ensureTypeUserOfferingAccomodation: (req,res,next)=>{
        if(req.user.type_user == 'OFRECE_ALOJAMIENTO'){
            next();
            (req, res, next);
        } else if(req.user.type_user == 'ADMIN'){
            next();
            (req, res, next)
        } else {
            return next(new ErrorHandler(403, "You are not allowed to access, you arent offering accomodation"));
        }
    }
}
    
module.exports = middlewares;