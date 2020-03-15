'use strict'

const bcrypt    = require('bcryptjs');
const passport  = require('passport');
const jwt       = require('jsonwebtoken');
const error_types = require('./error_types');
const User = require('../models/user');

let controller = {
    register: (req, res, next) => {
        User.find({username: req.username}, (err, result) => {
           if (result.username != null) { 
                next(new error_types.Error400("User already exists"));
            } else {
                if(req.body.password == req.body.confirmPassword && req.body.password.length >= 5 && req.body.confirmPassword.length >= 5){
                let hash = bcrypt.hashSync(req.body.password, parseInt(process.env.BCRYPT_ROUNDS));
                let user = new User({
                    username: req.body.username,
                    email: req.body.email,
                    name: req.body.name,
                    roles: 'USER',
                    password: hash
                });
                if (req.file != undefined) {
                    user.avatar = {
                      data: req.file.buffer.toString('base64'),
                      contentType: req.file.mimetype
                    }
                  }

                user.save((err, user) => {                    
                    if (err) next(new error_types.Error400(err.message));
                    res.status(201).json({  
                        id : user.id,
                        name : user.name,
                        username : user.username,
                        email : user.email
                    });
                });
                } else {
                    next(new error_types.Error400("Password length less than 5 or passwords not matching Bad Request"));
                }
            
            }
        })
    },
    login: (req, res, next) => {
        passport.authenticate("local", {session: false}, (error, user) => {
            if (error || !user) {
                next(new error_types.Error404("username or password not correct."))
            } else {
                const payload = {
                    sub: user.id,
                    exp: Date.now() + parseInt(process.env.JWT_LIFETIME),
                    username: user.username
                };
                const token = jwt.sign(JSON.stringify(payload), process.env.JWT_SECRET, {algorithm: process.env.JWT_ALGORITHM});
                res.json({ 
                    username: user.username,
                    role: user.roles,
                    token: token
                });
            }
        })(req, res)
    },
    getUsers : async (req, res, next) => {
        try {
            let result = null;
            result = await User.find({}, {_id: 1, username: 1, email:1, roles: 1, avatar: 1, register_date: 1}).exec();
            res.status(200).json(result);
        } catch (error) {
            res.send(500, error.message);
        }
    }

}

module.exports = controller;