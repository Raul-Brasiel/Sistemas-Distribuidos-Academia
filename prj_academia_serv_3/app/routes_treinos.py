from datetime import datetime

from bson.objectid import ObjectId
from flask import Blueprint, jsonify, request

from app.database import mongo
from app.models import Treino

treinos_bp = Blueprint("treinos", __name__)


@treinos_bp.route("/treinos", methods=["GET"])
def get_treinos():
    treinos = mongo.db.treinos.find()
    return jsonify([Treino.to_dict(t) for t in treinos])


@treinos_bp.route("/treinos/<string:treino_id>", methods=["GET"])
def get_treino(treino_id):
    treino = mongo.db.treinos.find_one({"_id": ObjectId(treino_id)})
    if treino:
        return jsonify(Treino.to_dict(treino))
    return jsonify({"error": "Treino não encontrado"}), 404


@treinos_bp.route("/treinos/cliente/<string:cliente_id>", methods=["GET"])
def get_treinos_por_cliente(cliente_id):
    treinos = mongo.db.treinos.find({"clienteId": cliente_id})
    return jsonify([Treino.to_dict(t) for t in treinos])


@treinos_bp.route("/treinos/funcionario/<string:funcionario_id>", methods=["GET"])
def get_treinos_por_funcionario(funcionario_id):
    treinos = mongo.db.treinos.find({"funcionarioId": funcionario_id})
    return jsonify([Treino.to_dict(t) for t in treinos])


@treinos_bp.route("/treinos", methods=["POST"])
def create_treino():
    data = request.json
    now = datetime.utcnow().isoformat()
    data["criadoEm"] = now
    data["atualizadoEm"] = now
    treino = Treino.to_dict(data)
    result = mongo.db.treinos.insert_one(treino)
    return jsonify({"id": str(result.inserted_id), **treino}), 201


@treinos_bp.route("/treinos/<string:treino_id>", methods=["PUT"])
def update_treino(treino_id):
    data = request.json
    data["atualizadoEm"] = datetime.utcnow().isoformat()
    update_data = {"$set": Treino.to_dict(data)}
    result = mongo.db.treinos.update_one(
        {"_id": ObjectId(treino_id)}, update_data
    )
    if result.matched_count:
        updated = mongo.db.treinos.find_one({"_id": ObjectId(treino_id)})
        return jsonify(Treino.to_dict(updated))
    return jsonify({"error": "Treino não encontrado"}), 404


@treinos_bp.route("/treinos/<string:treino_id>", methods=["DELETE"])
def delete_treino(treino_id):
    result = mongo.db.treinos.delete_one({"_id": ObjectId(treino_id)})
    if result.deleted_count:
        return jsonify({"message": "Treino excluído com sucesso"})
    return jsonify({"error": "Treino não encontrado"}), 404