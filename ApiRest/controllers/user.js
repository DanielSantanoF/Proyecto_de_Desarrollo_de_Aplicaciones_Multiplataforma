'use strict'

const bcrypt    = require('bcryptjs');
const passport  = require('passport');
const jwt       = require('jsonwebtoken');
const { handleError, ErrorHandler } = require('./error');
const User = require('../models/user');

let controller = {
    register: (req, res, next) => {
        User.find({username: req.username}, (err, result) => {
           if (result.username != null) { 
                next(new ErrorHandler(400, "User already exists"));
            } else {
                if(req.body.password == req.body.confirmPassword && req.body.password.length >= 5 && req.body.confirmPassword.length >= 5){
                let hash = bcrypt.hashSync(req.body.password, parseInt(process.env.BCRYPT_ROUNDS));
                let date = new Date(req.body.dateOfBirth);
                let user = new User({
                    username: req.body.username,
                    email: req.body.email,
                    phone: req.body.phone,
                    name: req.body.name,
                    role: 'USER',
                    type_user: req.body.typeUser,
                    password: hash,
                    date_of_birth: date
                });
                if (req.file != undefined) {
                    user.avatar = {
                      data: req.file.buffer.toString('base64'),
                      contentType: req.file.mimetype
                    }
                  }

                user.save((err, user) => {                    
                    if (err) next(new ErrorHandler(400, err.message));
                    res.status(201).json({  
                        id : user.id,
                        name : user.name,
                        username : user.username,
                        email : user.email
                    });
                });
                } else {
                    next(new ErrorHandler(400, "Password length less than 5 or passwords not matching Bad Request"));
                }
            
            }
        })
    },
    login: (req, res, next) => {
        passport.authenticate("local", {session: false}, (error, user) => {
            if (error || !user) {
                next(new ErrorHandler(404, "username or password not correct."));
            } else {
                const payload = {
                    sub: user.id,
                    exp: Date.now() + parseInt(process.env.JWT_LIFETIME),
                    username: user.username
                };
                const token = jwt.sign(JSON.stringify(payload), process.env.JWT_SECRET, {algorithm: process.env.JWT_ALGORITHM});
                res.json({ 
                    username: user.username,
                    role: user.role,
                    token: token
                });
            }
        })(req, res)
    },
    getUsers : async (req, res, next) => {
        try {
            let result = null;
            result = await User.find({active: true}, {active: 0, register_date: 0, __v: 0}).exec();
            res.status(200).json(result);
        } catch (error) {
            res.send(500, error.message);
        }
    },
    getUserById : async (req, res, next) => {
        try {
            let result = null;
            result = await User.find({_id: req.params.id}, {active: 0, register_date: 0, __v: 0}).exec();
            res.status(200).json(result);
        } catch (error) {
            res.send(500, error.message);
        }
    },
    getMe : async (req, res, next) => {
        try {
            let result = null;
            result = await User.find({_id: req.user.id}, {active: 0, register_date: 0, __v: 0}).exec();
            res.status(200).json(result);
        } catch (error) {
            res.send(500, error.message);
        }
    },
    deleteUser: (req, res, next) => {
        User.findByIdAndDelete(req.params.id)
            .exec()
            .then(res.send(204))
            .catch(err => {
                next(new ErrorHandler(404, "User not found"));
            });
    },

}

module.exports = controller;
