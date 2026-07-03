from datetime import datetime

class Treino:
    @staticmethod
    def to_dict(treino):
        if treino.get("_id", "") == "":
            return {
                "clienteId": treino.get("clienteId", ""),
                "funcionarioId": treino.get("funcionarioId", ""),
                "nome": treino.get("nome", ""),
                "objetivo": treino.get("objetivo", ""),
                "nivel": treino.get("nivel", "Todos"),
                "exercicios": treino.get("exercicios", []),
                "ativo": treino.get("ativo", True),
                "criadoEm": treino.get("criadoEm", datetime.utcnow().isoformat()),
                "atualizadoEm": treino.get("atualizadoEm", datetime.utcnow().isoformat()),
            }
        else:
            return {
                "id": str(treino["_id"]),
                "clienteId": treino.get("clienteId", ""),
                "funcionarioId": treino.get("funcionarioId", ""),
                "nome": treino.get("nome", ""),
                "objetivo": treino.get("objetivo", ""),
                "nivel": treino.get("nivel", "Todos"),
                "exercicios": treino.get("exercicios", []),
                "ativo": treino.get("ativo", True),
                "criadoEm": treino.get("criadoEm", ""),
                "atualizadoEm": treino.get("atualizadoEm", ""),
            }


class AvaliacaoFisica:

    @staticmethod
    def to_dict(avaliacao):
        if avaliacao.get("_id", "") == "":
            return {
                "clienteId": avaliacao.get("clienteId", ""),
                "funcionarioId": avaliacao.get("funcionarioId", ""),
                "dataAvaliacao": avaliacao.get("dataAvaliacao", ""),
                "dadosBiometricos": avaliacao.get("dadosBiometricos", {}),
                "medidasCm": avaliacao.get("medidasCm", {}),
                "pressaoArterial": avaliacao.get("pressaoArterial", {}),
                "testesFisicos": avaliacao.get("testesFisicos", {}),
                "objetivoDeclarado": avaliacao.get("objetivoDeclarado", ""),
                "observacoes": avaliacao.get("observacoes", ""),
                "proximaAvaliacao": avaliacao.get("proximaAvaliacao", ""),
                "criadoEm": avaliacao.get("criadoEm", datetime.utcnow().isoformat()),
                "atualizadoEm": avaliacao.get("atualizadoEm", datetime.utcnow().isoformat()),
            }
        else:
            return {
                "id": str(avaliacao["_id"]),
                "clienteId": avaliacao.get("clienteId", ""),
                "funcionarioId": avaliacao.get("funcionarioId", ""),
                "dataAvaliacao": avaliacao.get("dataAvaliacao", ""),
                "dadosBiometricos": avaliacao.get("dadosBiometricos", {}),
                "medidasCm": avaliacao.get("medidasCm", {}),
                "pressaoArterial": avaliacao.get("pressaoArterial", {}),
                "testesFisicos": avaliacao.get("testesFisicos", {}),
                "objetivoDeclarado": avaliacao.get("objetivoDeclarado", ""),
                "observacoes": avaliacao.get("observacoes", ""),
                "proximaAvaliacao": avaliacao.get("proximaAvaliacao", ""),
                "criadoEm": avaliacao.get("criadoEm", ""),
                "atualizadoEm": avaliacao.get("atualizadoEm", ""),
            }