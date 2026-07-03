from datetime import datetime

from bson.objectid import ObjectId
from flask import Blueprint, jsonify, request

from app.database import mongo
from app.models import AvaliacaoFisica

avaliacoes_bp = Blueprint("avaliacoes", __name__)


@avaliacoes_bp.route("/avaliacoes", methods=["GET"])
def get_avaliacoes():
    avaliacoes = mongo.db.avaliacoes_fisicas.find()
    return jsonify([AvaliacaoFisica.to_dict(a) for a in avaliacoes])


@avaliacoes_bp.route("/avaliacoes/<string:avaliacao_id>", methods=["GET"])
def get_avaliacao(avaliacao_id):
    avaliacao = mongo.db.avaliacoes_fisicas.find_one({"_id": ObjectId(avaliacao_id)})
    if avaliacao:
        return jsonify(AvaliacaoFisica.to_dict(avaliacao))
    return jsonify({"error": "Avaliação não encontrada"}), 404


@avaliacoes_bp.route("/avaliacoes/cliente/<string:cliente_id>", methods=["GET"])
def get_avaliacoes_por_cliente(cliente_id):
    avaliacoes = mongo.db.avaliacoes_fisicas.find({"clienteId": cliente_id})
    return jsonify([AvaliacaoFisica.to_dict(a) for a in avaliacoes])


@avaliacoes_bp.route("/avaliacoes/funcionario/<string:funcionario_id>", methods=["GET"])
def get_avaliacoes_por_funcionario(funcionario_id):
    avaliacoes = mongo.db.avaliacoes_fisicas.find({"funcionarioId": funcionario_id})
    return jsonify([AvaliacaoFisica.to_dict(a) for a in avaliacoes])


@avaliacoes_bp.route("/avaliacoes", methods=["POST"])
def create_avaliacao():
    data = request.json
    now = datetime.utcnow().isoformat()
    data["criadoEm"] = now
    data["atualizadoEm"] = now
    avaliacao = AvaliacaoFisica.to_dict(data)
    result = mongo.db.avaliacoes_fisicas.insert_one(avaliacao)
    return jsonify({"id": str(result.inserted_id), **avaliacao}), 201


@avaliacoes_bp.route("/avaliacoes/<string:avaliacao_id>", methods=["PUT"])
def update_avaliacao(avaliacao_id):
    data = request.json
    data["atualizadoEm"] = datetime.utcnow().isoformat()
    update_data = {"$set": AvaliacaoFisica.to_dict(data)}
    result = mongo.db.avaliacoes_fisicas.update_one(
        {"_id": ObjectId(avaliacao_id)}, update_data
    )
    if result.matched_count:
        updated = mongo.db.avaliacoes_fisicas.find_one({"_id": ObjectId(avaliacao_id)})
        return jsonify(AvaliacaoFisica.to_dict(updated))
    return jsonify({"error": "Avaliação não encontrada"}), 404


@avaliacoes_bp.route("/avaliacoes/<string:avaliacao_id>", methods=["DELETE"])
def delete_avaliacao(avaliacao_id):
    result = mongo.db.avaliacoes_fisicas.delete_one({"_id": ObjectId(avaliacao_id)})
    if result.deleted_count:
        return jsonify({"message": "Avaliação excluída com sucesso"})
    return jsonify({"error": "Avaliação não encontrada"}), 404