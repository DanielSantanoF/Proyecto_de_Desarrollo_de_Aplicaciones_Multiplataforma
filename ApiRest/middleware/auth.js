'use strict'

const passport = require('passport');
const { handleError, ErrorHandler } = require('../controllers/error');

let middlewares = {
    
    ensureAuthenticated: (req,res,next)=>{
        passport.authenticate('jwt', {session: false}, (err, user, info)=>{
            if(info){ return next(new ErrorHandler(401, info.message)); }

            if (err) { return next(err); }

            if (!user) { return  next(new ErrorHandler(403, "You are not allowed to access.")); }
            
            req.user = user;
            next();
        })(req, res, next);
    },
    notFoundHandler: (req, res, next) => {
        res.status(404).json({error: "endpoint not found"});
    }
}
    

module.exports = middlewares;