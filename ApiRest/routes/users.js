'use strict'

const express = require('express')
const router = express.Router()

const multer = require('multer')
const storage = multer.memoryStorage()
const upload = multer({ storage })

const authMiddleware = require('../middleware/auth');
const adminMiddleware = require('../middleware/has_role_admin');
const UserController = require('../controllers/user')

//Auth
router.post('/login', UserController.login);
router.post('/register', upload.single('avatar'), UserController.register);

//Password
router.put('/users/me/password', authMiddleware.ensureAuthenticated, UserController.updatePassword);
router.put('/users/password/:id', authMiddleware.ensureAuthenticated, adminMiddleware.ensureRolAdmin, UserController.updatePasswordOfUserById);

//Users Me
router.get('/users/me', authMiddleware.ensureAuthenticated, UserController.getMe);
router.delete('/users/me', authMiddleware.ensureAuthenticated, UserController.deleteMe);
router.put('/users/me', authMiddleware.ensureAuthenticated, UserController.updateMe);
router.put('/users/me/avatar', authMiddleware.ensureAuthenticated, upload.single('avatar'), UserController.updateAvatar);
router.delete('/users/me/avatar', authMiddleware.ensureAuthenticated, UserController.deleteAvatar);

//Users
router.get('/users', authMiddleware.ensureAuthenticated, UserController.getUsers);
router.get('/users/:id', authMiddleware.ensureAuthenticated, UserController.getUserById);
router.put('/users/:id', authMiddleware.ensureAuthenticated, adminMiddleware.ensureRolAdmin, UserController.updateUser);
router.delete('/users/:id', authMiddleware.ensureAuthenticated, adminMiddleware.ensureRolAdmin, UserController.deleteUser);

//Favorite users
router.post('/users/me/favorites', authMiddleware.ensureAuthenticated, UserController.postNewFavorite);
router.get('/users/me/favorites', authMiddleware.ensureAuthenticated, UserController.getMyFavorites);
router.delete('/users/me/favorites/:id', authMiddleware.ensureAuthenticated, UserController.deleteFavorite);

//Two users contact and start to living together old/young field living_with on User model
router.put('/users/me/livingWith', authMiddleware.ensureAuthenticated, UserController.updateLivingWith);
router.delete('/users/me/livingWith/:id', authMiddleware.ensureAuthenticated, UserController.deleteLivingWith);

//Admin validate users and active users
router.put('/users/admin/validate/:id', authMiddleware.ensureAuthenticated, adminMiddleware.ensureRolAdmin, UserController.updateValidated);
router.put('/users/admin/active/:id', authMiddleware.ensureAuthenticated, adminMiddleware.ensureRolAdmin, UserController.updateActive);

module.exports = router