'use strict'

const express = require('express')
const router = express.Router()

const authMiddleware = require('../middleware/auth');
const ServiceController = require('../controllers/services');

router.get('/services/available', authMiddleware.ensureAuthenticated, ServiceController.getServicesAvailable);
router.get('/services', authMiddleware.ensureAuthenticated, ServiceController.getServices);
router.post('/services', authMiddleware.ensureAuthenticated, ServiceController.postNewService);
router.get('/services/:id', authMiddleware.ensureAuthenticated, ServiceController.getServiceById);
router.delete('/services/:id', authMiddleware.ensureAuthenticated, ServiceController.deleteService);
router.put('/services/:id', authMiddleware.ensureAuthenticated, ServiceController.updateService);

module.exports = router