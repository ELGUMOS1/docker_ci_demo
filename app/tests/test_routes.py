import unittest

import redis
import requests

from app import db
from app.models import Post


class TestRoutes(unittest.TestCase):

    def setUp(self):
        self.redis = redis.StrictRedis(host='redis', port=6379, db=0)

    def test_database(self):
        response = requests.get('/')
        self.assertEqual(response.status_code, 200)
        

    def test_eight_queen_puzzle(self):
        response = requests.post('/get/4') 
        self.assertIn('[[1, 3, 0, 2], [2, 0, 3, 1]]',response.data)

      