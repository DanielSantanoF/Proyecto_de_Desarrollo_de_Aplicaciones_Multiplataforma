'use strict'

const bcrypt = require('bcryptjs');
const passport = require('passport');
const jwt = require('jsonwebtoken');
const { handleError, ErrorHandler } = require('./error');
const User = require('../models/user');

let controller = {
    register: (req, res, next) => {
        User.find({ username: req.username }, (err, result) => {
            if (result.username != null) {
                next(new ErrorHandler(400, "User already exists"));
            } else {
                if (req.body.password == req.body.confirmPassword && req.body.password.length >= 5 && req.body.confirmPassword.length >= 5) {
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
                            id: user.id,
                            name: user.name,
                            username: user.username,
                            email: user.email
                        });
                    });
                } else {
                    next(new ErrorHandler(400, "Password length less than 5 or passwords not matching Bad Request"));
                }

            }
        })
    },
    login: (req, res, next) => {
        passport.authenticate("local", { session: false }, (error, user) => {
            if (error || !user) {
                next(new ErrorHandler(404, "Error can be username or password not correct, not validated user or not active user"));
            } else {
                const payload = {
                    sub: user.id,
                    exp: Date.now() + parseInt(process.env.JWT_LIFETIME),
                    username: user.username
                };
                const token = jwt.sign(JSON.stringify(payload), process.env.JWT_SECRET, { algorithm: process.env.JWT_ALGORITHM });
                res.json({
                    username: user.username,
                    role: user.role,
                    type_user: user.type_user,
                    id: user.id,
                    token: token
                });
            }
        })(req, res)
    },
    updateAvatar: (req, res, next) => {
        if (req.file != undefined) {
            User.findByIdAndUpdate(req.user.id,
                {
                    $set: {
                        avatar: {
                            data: req.file.buffer.toString('base64'),
                            contentType: req.file.mimetype
                        }
                    }
                }, { new: true }, (err, userUpdated) => {
                    if (err) new ErrorHandler(500, err.message);
                    if (userUpdated == null) {
                        next(new ErrorHandler(404, "User not found"));
                    }
                    else {
                        User.findById(userUpdated._id)
                            .exec()
                            .then(x => res.status(200).json(x))
                            .catch(err => next(new ErrorHandler(500, err.message)))
                    }
                });
        } else {
            next(new ErrorHandler(400, "Not file uploaded Bad request"))
        }
    },
    deleteAvatar: (req, res, next) => {
        User.findByIdAndUpdate(req.user.id,
            {
                $unset: {
                    avatar: 1
                }
            }, { new: true }, (err, userUpdated) => {
                if (err) new ErrorHandler(500, err.message);
                if (userUpdated == null) {
                    next(new ErrorHandler(404, "User not found"));
                }
                return userUpdated;
            })
            .then((u) => res.status(200).json(u))
            .catch(err => next(new ErrorHandler(500, err.message)));
    },
    getUsers: async (req, res, next) => {
        try {
            let result = null;
            result = await User.find({}, { register_date: 0, __v: 0 }).exec();
            res.status(200).json(result);
        } catch (error) {
            next(new ErrorHandler(500, err.message));
        }
    },
    getUserById: async (req, res, next) => {
        User.findById(req.params.id)
            .exec()
            .then(x => res.status(200).json(x))
            .catch(err => next(new ErrorHandler(500, err.message)))
    },
    getMe: async (req, res, next) => {
        User.findById(req.user.id)
            .exec()
            .then(x => res.status(200).json(x))
            .catch(err => next(new ErrorHandler(500, err.message)))
    },
    deleteMe: (req, res, next) => {
        User.findByIdAndDelete(req.user.id)
            .exec()
            .then(res.send(204))
            .catch(err => next(new ErrorHandler(404, "User not found")));
    },
    updateMe: (req, res, next) => {
        let date = new Date(req.body.dateOfBirth);
        User.findByIdAndUpdate(req.user.id,
            {
                $set: {
                    username: req.body.username,
                    email: req.body.email,
                    phone: req.body.phone,
                    name: req.body.name,
                    type_user: req.body.typeUser,
                    date_of_birth: date
                }
            }, { new: true }, (err, userUpdated) => {
                if (err) new ErrorHandler(500, err.message);
                if (userUpdated == null) {
                    next(new ErrorHandler(404, "User not found"));
                }
                else {
                    User.findById(userUpdated._id)
                        .exec()
                        .then(x => res.status(200).json(x))
                        .catch(err => next(new ErrorHandler(500, err.message)))
                }
            });
    },
    getMyFavorites: (req, res, next) => {
        User.findById(req.user.id)
            .exec()
            .then(x => x.populate({
                path: 'favorite_users',
                model: 'User'
            }).execPopulate())
            .then(x => res.status(200).json(x))
            .catch(err => next(new ErrorHandler(500, err.message)))
    },
    postNewFavorite: (req, res, next) => {
        User.findByIdAndUpdate(req.user.id, { $push: { favorite_users: req.body.idUser } })
            .exec()
            .then(x => x.populate({
                path: 'favorite_users',
                model: 'User'
            }).execPopulate())
            .then(x => res.status(201).json(x))
            .catch(err => next(new ErrorHandler(500, err.message)))
    },
    deleteFavorite: (req, res, next) => {
        User.findByIdAndUpdate(req.user.id, {
            $pull: {
                favorite_users: req.params.id
            }
        }, (err, user) => {
            if (err) {
                next(new ErrorHandler(500, err.message))
            }
            return user;
        })
            .then((u) => res.status(200).json(u))
            .catch(err => next(new ErrorHandler(500, err.message)));
    },
    deleteUser: (req, res, next) => {
        User.findByIdAndDelete(req.params.id)
            .exec()
            .then(res.send(204))
            .catch(err => next(new ErrorHandler(404, "User not found")));
    },
    updateUser: (req, res, next) => {
        let date = new Date(req.body.dateOfBirth);
        User.findByIdAndUpdate(req.params.id,
            {
                $set: {
                    username: req.body.username,
                    email: req.body.email,
                    phone: req.body.phone,
                    name: req.body.name,
                    type_user: req.body.typeUser,
                    date_of_birth: date
                }
            }, { new: true }, (err, userUpdated) => {
                if (err) new ErrorHandler(500, err.message);
                if (userUpdated == null) {
                    next(new ErrorHandler(404, "User not found"));
                }
                else {
                    User.findById(userUpdated._id)
                        .exec()
                        .then(x => res.status(200).json(x))
                        .catch(err => next(new ErrorHandler(500, err.message)))
                }
            });
    },
    updateLivingWith: (req, res, next) => {
        User.findByIdAndUpdate(req.user.id,
            {
                $set: {
                    living_with: req.body.idUser
                }
            }, { new: true }, (err, userUpdated) => {
                if (err) new ErrorHandler(500, err.message);
                if (userUpdated == null) {
                    next(new ErrorHandler(404, "User not found"));
                }
                else {
                    User.findByIdAndUpdate(req.body.idUser,
                        {
                            $set: {
                                living_with: req.user.id
                            }
                        }, { new: true }, (err, secondUserUpdated) => {
                            if (secondUserUpdated == null) {
                                next(new ErrorHandler(404, "User to living with not found"));
                            }
                            else {
                                User.findById(userUpdated._id)
                                    .exec()
                                    .then(x => x.populate({
                                        path: 'living_with',
                                        model: 'User'
                                    }).execPopulate())
                                    .then(x => res.status(200).json(x))
                                    .catch(err => next(new ErrorHandler(500, err.message)))
                            }
                        });
                }
            });
    },
    deleteLivingWith: (req, res, next) => {
        User.findByIdAndUpdate(req.user.id,
            {
                $unset: {
                    living_with: 1
                }
            }, { new: true }, (err, userUpdated) => {
                if (err) new ErrorHandler(500, err.message);
                if (userUpdated == null) {
                    next(new ErrorHandler(404, "User not found"));
                }
                else {
                    User.findByIdAndUpdate(req.body.idUser,
                        {
                            $unset: {
                                living_with: 1
                            }
                        }, { new: true }, (err, secondUserUpdated) => {
                            if (secondUserUpdated == null) {
                                next(new ErrorHandler(404, "User to living with not found"));
                            }
                            else {
                                User.findById(userUpdated._id)
                                    .exec()
                                    .then(x => res.status(204))
                                    .catch(err => next(new ErrorHandler(500, err.message)))
                            }
                        });
                }
                return userUpdated;
            })
            .then((u) => res.status(200).json(u))
            .catch(err => next(new ErrorHandler(500, err.message)));
    },
    updatePassword: (req, res, next) => {
        if (req.body.password == req.body.confirmPassword && req.body.password.length >= 5 && req.body.confirmPassword.length >= 5) {
            let hash = bcrypt.hashSync(req.body.password, parseInt(process.env.BCRYPT_ROUNDS));
            User.findByIdAndUpdate(req.user.id,
                {
                    $set: {
                        password: hash
                    }
                }, { new: true }, (err, userUpdated) => {
                    if (err) new ErrorHandler(500, err.message);
                    if (userUpdated == null) {
                        next(new ErrorHandler(404, "User not found"));
                    }
                    else {
                        User.findById(userUpdated._id)
                            .exec()
                            .then(x => res.status(200).json(x))
                            .catch(err => next(new ErrorHandler(500, err.message)))
                    }
                });
        } else {
            next(new ErrorHandler(400, "Password length less than 5 or passwords not matching Bad Request"));
        }
    },
    updatePasswordOfUserById: (req, res, next) => {
        if (req.body.password == req.body.confirmPassword && req.body.password.length >= 5 && req.body.confirmPassword.length >= 5) {
            let hash = bcrypt.hashSync(req.body.password, parseInt(process.env.BCRYPT_ROUNDS));
            User.findByIdAndUpdate(req.params.id,
                {
                    $set: {
                        password: hash
                    }
                }, { new: true }, (err, userUpdated) => {
                    if (err) new ErrorHandler(500, err.message);
                    if (userUpdated == null) {
                        next(new ErrorHandler(404, "User not found"));
                    }
                    else {
                        User.findById(userUpdated._id)
                            .exec()
                            .then(x => res.status(200).json(x))
                            .catch(err => next(new ErrorHandler(500, err.message)))
                    }
                });
        } else {
            next(new ErrorHandler(400, "Password length less than 5 or passwords not matching Bad Request"));
        }
    },
    updateValidated: (req, res, next) => {
        User.findByIdAndUpdate(req.params.id,
            {
                $set: {
                    validated: req.body.validated
                }
            }, { new: true }, (err, userUpdated) => {
                if (err) new ErrorHandler(500, err.message);
                if (userUpdated == null) {
                    next(new ErrorHandler(404, "User not found"));
                }
                else {
                    User.findById(userUpdated._id)
                        .exec()
                        .then(x => res.status(200).json(x))
                        .catch(err => next(new ErrorHandler(500, err.message)))
                }
            });
    },
    updateActive: (req, res, next) => {
        User.findByIdAndUpdate(req.params.id,
            {
                $set: {
                    active: req.body.active
                }
            }, { new: true }, (err, userUpdated) => {
                if (err) new ErrorHandler(500, err.message);
                if (userUpdated == null) {
                    next(new ErrorHandler(404, "User not found"));
                }
                else {
                    User.findById(userUpdated._id)
                        .exec()
                        .then(x => res.status(200).json(x))
                        .catch(err => next(new ErrorHandler(500, err.message)))
                }
            });
    }

}

module.exports = controller;
