'use strict'

const express = require('express')
const router = express.Router()

const authMiddleware = require('../middleware/auth');
const typeUserOfferingAccomodationMiddleware = require('../middleware/has_type_user_offering_accommodation');
const LocationController = require('../controllers/location');

router.post('/locations', authMiddleware.ensureAuthenticated, typeUserOfferingAccomodationMiddleware.ensureTypeUserOfferingAccomodation, LocationController.postNewLocation);
router.get('/locations', authMiddleware.ensureAuthenticated, LocationController.getLocations);
router.get('/locations/:id', authMiddleware.ensureAuthenticated, LocationController.getLocationById);
router.put('/locations/:id', authMiddleware.ensureAuthenticated, typeUserOfferingAccomodationMiddleware.ensureTypeUserOfferingAccomodation, LocationController.updateLocationOffered);
router.delete('/locations/:id', authMiddleware.ensureAuthenticated, typeUserOfferingAccomodationMiddleware.ensureTypeUserOfferingAccomodation, LocationController.deleteLocation);
router.put('/locations/available/:id', authMiddleware.ensureAuthenticated, typeUserOfferingAccomodationMiddleware.ensureTypeUserOfferingAccomodation, LocationController.updateAvailableLocationOffered);

module.exports = router