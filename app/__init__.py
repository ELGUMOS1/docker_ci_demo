# -*- encoding: utf-8 -*-
"""
Python Aplication Template
Licence: GPLv3
"""
import os

from flask import Flask
from flask import SQLAlchemy


from app.configuration import config

app = Flask(__name__)

env = os.environ.get("FLASK_ENV", "dev")
app.config.from_object(config[env])

db = SQLAlchemy(app)


from app import views, models
