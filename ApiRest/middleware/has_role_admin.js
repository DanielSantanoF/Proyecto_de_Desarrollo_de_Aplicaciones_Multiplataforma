'use strict'

const { handleError, ErrorHandler } = require('../controllers/error');

let middlewares = {
    
    ensureRolAdmin: (req,res,next)=>{
        if(req.user.role == 'ADMIN'){
            next();
            (req, res, next);
        } else {
            //return res.send(403,"You are not allowed to access.");
            return next(new ErrorHandler(403, "You are not allowed to access."));
        }
    }
}
    
module.exports = middlewares;