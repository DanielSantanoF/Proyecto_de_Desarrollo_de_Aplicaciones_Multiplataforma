'use strict'

const express = require('express')
const router = express.Router()

const authMiddleware = require('../middleware/auth');
const ServiceController = require('../controllers/services');

router.get('/servies/available', authMiddleware.ensureAuthenticated, ServiceController.getServicesAvailable);
router.get('/servies', authMiddleware.ensureAuthenticated, ServiceController.getServices);
router.post('/servies', authMiddleware.ensureAuthenticated, ServiceController.postNewService);
router.get('/servies/:id', authMiddleware.ensureAuthenticated, ServiceController.getServiceById);
router.delete('/servies/:id', authMiddleware.ensureAuthenticated, ServiceController.deleteService);
router.put('/servies/:id', authMiddleware.ensureAuthenticated, ServiceController.updateService);

module.exports = router