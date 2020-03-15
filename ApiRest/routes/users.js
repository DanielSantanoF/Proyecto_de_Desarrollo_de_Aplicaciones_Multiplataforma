'use strict'

const express = require('express')
const router = express.Router()

const multer = require('multer')
const storage = multer.memoryStorage()
const upload = multer({ storage })

const authMiddleware = require('../middleware/auth');
const adminMiddleware = require('../middleware/has_role_admin');
const UserController = require('../controllers/user')

router.post('/login', UserController.login);
//router.post('/register', UserController.register);
router.post('/register', upload.single('avatar'), UserController.register);
router.get('/users', authMiddleware.ensureAuthenticated, /*,adminMiddleware.ensureRolAdmin,*/ UserController.getUsers);
router.get('/users/:id', authMiddleware.ensureAuthenticated, UserController.getUsers);
router.get('/users/me', authMiddleware.ensureAuthenticated, UserController.getMe);

module.exports = router