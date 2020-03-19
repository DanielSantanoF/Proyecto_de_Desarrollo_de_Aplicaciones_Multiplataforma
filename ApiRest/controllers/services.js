'use strict'

const { handleError, ErrorHandler } = require('./error');
const Service = require('../models/services');

let controller = {
    getServices: async (req, res, next) => {
        try {
            let result = null;
            result = await Service.find({}, { __v: 0 }).exec();
            res.status(200).json(result);
        } catch (error) {
            res.send(500, error.message);
        }
    },
    getServicesAvailable: async (req, res, next) => {
        try {
            let result = null;
            result = await Service.find({ available: true }, { __v: 0 }).exec();
            res.status(200).json(result);
        } catch (error) {
            res.send(500, error.message);
        }
    },
    postNewService: (req, res, next) => {
        if (req.body.title != "" && req.body.typeService != "" && req.body.description != "") {
            let service = new Service({
                type_service: req.body.typeService,
                title: req.body.title,
                description: req.body.description,
                user_offering_service: req.user.id
            });

            service.save((err, user) => {
                if (err) next(new ErrorHandler(400, err.message));
                res.status(201).json({
                    id: service.id,
                    type_service: service.type_service,
                    title: service.title,
                    description: service.description
                });
            });
        } else {
            next(new ErrorHandler(400, "Complete all fields Bad Request"));
        }
    },
    deleteService: (req, res, next) => {
        Service.findByIdAndDelete(req.params.id)
            .exec()
            .then(res.send(204))
            .catch(err => {
                next(new ErrorHandler(404, "Service not found"));
            });
    }
}

module.exports = controller;