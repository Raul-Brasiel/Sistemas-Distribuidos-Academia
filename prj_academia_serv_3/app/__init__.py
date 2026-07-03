from flask import Flask
from flask_cors import CORS

from app.config import Config
from app.database import mongo


def create_app():
    app = Flask(__name__)
    app.config.from_object(Config)

    mongo.init_app(app)
    CORS(app)

    from app.routes_treinos import treinos_bp
    from app.routes_avaliacoes import avaliacoes_bp

    app.register_blueprint(treinos_bp, url_prefix="/api")
    app.register_blueprint(avaliacoes_bp, url_prefix="/api")

    return app