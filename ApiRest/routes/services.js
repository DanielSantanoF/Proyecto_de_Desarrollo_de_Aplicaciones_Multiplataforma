'use strict'

const express = require('express')
const router = express.Router()

const authMiddleware = require('../middleware/auth');
const ServiceController = require('../controllers/services');
const typeUserOfferingServiceMiddleware = require('../middleware/has_type_user_offering_service');

router.get('/services/available', authMiddleware.ensureAuthenticated, ServiceController.getServicesAvailable);
router.get('/services', authMiddleware.ensureAuthenticated, ServiceController.getServices);
router.post('/services', authMiddleware.ensureAuthenticated, typeUserOfferingServiceMiddleware.ensureTypeUserOfferingService, ServiceController.postNewService);
router.get('/services/:id', authMiddleware.ensureAuthenticated, ServiceController.getServiceById);
router.delete('/services/:id', authMiddleware.ensureAuthenticated, typeUserOfferingServiceMiddleware.ensureTypeUserOfferingService, ServiceController.deleteService);
router.put('/services/:id', authMiddleware.ensureAuthenticated, typeUserOfferingServiceMiddleware.ensureTypeUserOfferingService, ServiceController.updateService);

module.exports = router