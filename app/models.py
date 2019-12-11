from app import db


class Post(db.Model):
    __tablename__ = 'solutions'
    n = db.Column(db.Integer, primary_key=True)
    title = db.Column(db.String)
    
    def __init__(self, n, sol):
        self.sol = sol
        self.n = n