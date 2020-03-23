'use strict'

const { handleError, ErrorHandler } = require('./error');
const User = require('../models/user');
const LocationOffered = require('../models/location');

let controller = {
    getLocations: async (req, res, next) => {
        User.find({ active: true, location_offered: { $exists: true }}, { username: 1, avatar: 1, date_of_birth: 1, location_offered: 1 })
            .populate({ path: 'location_offered', model: 'LocationOffered' })
            .exec()
            .then(x => res.status(200).json(x))
            .catch(err => next(new ErrorHandler(500, err.message)));
    },
    postNewLocation: (req, res, next) => {
        if (req.body.conutry != "" && req.body.isoCode != "" && req.body.city != "" && req.body.street != "" && req.body.postalCode != "") {
            let location = new LocationOffered({
                country: req.body.country,
                iso_code: req.body.isoCode,
                city: req.body.city,
                street: req.body.street,
                postal_code: req.body.postalCode
            });
            location.save((err, user) => {
                if (err) {
                    next(new ErrorHandler(400, err.message));
                } else {
                    User.findByIdAndUpdate(req.user.id,
                        {
                            $set: {
                                location_offered: location.id
                            }
                        }, { new: true }, (err, userUpdated) => {
                            if(err) new ErrorHandler(500, err.message);
                            if (userUpdated == null) {
                                next(new ErrorHandler(404, "User not found"));
                            }
                            else {
                                User.find({ _id: userUpdated._id }, { username: 1, avatar: 1, date_of_birth: 1, location_offered: 1 })
                                    .populate({ path: 'location_offered', model: 'LocationOffered' })
                                    .exec()
                                    .then(x => res.status(200).json(x))
                                    .catch(err => next(new ErrorHandler(500, err.message)));
                            }
                        });
                }

            });
        } else {
            next(new ErrorHandler(400, "Complete all fields Bad Request"));
        }
    },
    updateLocationOffered: (req, res, next) => {
        LocationOffered.findByIdAndUpdate(req.params.id,
            {
                $set: {
                    country: req.body.country,
                    iso_code: req.body.isoCode,
                    city: req.body.city,
                    street: req.body.street,
                    postal_code: req.body.postalCode

                }
            }, { new: true }, (err, locationUpdated) => {
                if(err) new ErrorHandler(500, err.message);
                if (locationUpdated == null) {
                    next(new ErrorHandler(404, "Location not found"));
                }
                else {
                    User.find({ location_offered: req.params.id }, { username: 1, avatar: 1, date_of_birth: 1, location_offered: 1 })
                        .populate({ path: 'location_offered', model: 'LocationOffered' })
                        .exec()
                        .then(x => res.status(200).json(x))
                        .catch(err => next(new ErrorHandler(500, err.message)));
                }
            });
    },
    deleteLocation: (req, res, next) => {
        User.update({ location_offered: req.params.id },
            {
                $unset: {
                    location_offered: 1
                }
            }, { new: true }, (err, userUpdated) => {
                if(err) new ErrorHandler(500, err.message);
                if (userUpdated == null) {
                    next(new ErrorHandler(500, err.message));
                } else {
                    LocationOffered.findByIdAndDelete(req.params.id)
                        .exec()
                        .then(res.sendStatus(204))
                        .catch(err => next(new ErrorHandler(404, "Location not found")));
                }
            });
    },
    updateAvailableLocationOffered: (req, res, next) => {
        LocationOffered.findByIdAndUpdate(req.params.id,
            {
                $set: {
                    available: req.body.available
                }
            }, { new: true }, (err, locationUpdated) => {
                if(err) new ErrorHandler(500, err.message);
                if (locationUpdated == null) {
                    next(new ErrorHandler(404, "Location not found"));
                }
                else {
                    User.find({ location_offered: req.params.id }, { username: 1, avatar: 1, date_of_birth: 1, location_offered: 1 })
                        .populate({ path: 'location_offered', model: 'LocationOffered' })
                        .exec()
                        .then(x => res.status(200).json(x))
                        .catch(err => next(new ErrorHandler(500, err.message)));
                }
            });
    },
    getLocationById: async (req, res, next) => {
        User.find({ active: true, location_offered: { _id: req.params.id }}, { active: 0, register_date: 0, __v: 0 })
            .populate({ path: 'location_offered', model: 'LocationOffered' })
            .exec()
            .then(x => res.status(200).json(x))
            .catch(err => next(new ErrorHandler(500, err.message)));
    },
}

module.exports = controller;