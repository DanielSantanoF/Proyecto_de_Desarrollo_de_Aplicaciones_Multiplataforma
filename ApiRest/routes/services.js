'use strict'

const express = require('express')
const router = express.Router()

const authMiddleware = require('../middleware/auth');
const adminMiddleware = require('../middleware/has_role_admin');
const ServiceController = require('../controllers/services');

router.get('/servies', authMiddleware.ensureAuthenticated, ServiceController.getServices);
router.post('/servies', authMiddleware.ensureAuthenticated, ServiceController.postNewService);
router.delete('/servies/:id', authMiddleware.ensureAuthenticated, ServiceController.deleteService);
router.get('/servies/available', authMiddleware.ensureAuthenticated, ServiceController.getServicesAvailable);

module.exports = router