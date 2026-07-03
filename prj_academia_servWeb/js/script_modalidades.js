const API_MODAL = 'http://localhost:8080/api/gestor/modalidades';
const API_FUNC_M = 'http://localhost:8080/api/gestor/funcionarios';

function mostrarMsg(texto, tipo) {
    const cls = tipo === 'ok' ? 'alert-success' : 'alert-danger';
    $('#msg').attr('class', 'alert ' + cls).text(texto).show();
    setTimeout(() => $('#msg').fadeOut(), 3000);
}

function inicializar() {
    $.get(API_FUNC_M, function(data) {
        let opts = '<option value="">-- Sem instrutor --</option>';
        data.forEach(function(f) {
            opts += `<option value="${f.id}">${f.nome} — ${f.cargo}</option>`;
        });
        $('#instrutorId').html(opts);
    });
    carregarModalidades();
}

function carregarModalidades() {
    $.get(API_MODAL, function(data) {
        let html = '';
        if (!data || data.length === 0) {
            html = '<tr><td colspan="7" class="text-center text-muted">Nenhuma modalidade cadastrada.</td></tr>';
        } else {
            data.forEach(function(m) {
                const instrutor = m.instrutor ? m.instrutor.nome : '-';
                html += `<tr>
                    <td>${m.nome}</td>
                    <td>${m.nivel}</td>
                    <td>${m.duracaoMinutos} min</td>
                    <td>${m.capacidadeMax}</td>
                    <td>${instrutor}</td>
                    <td>${m.ativa ? '<span class="badge bg-success">Sim</span>' : '<span class="badge bg-secondary">Não</span>'}</td>
                    <td>
                        <button class="btn btn-warning btn-acao me-1" onclick='editarModalidade(${JSON.stringify(m)})'>Editar</button>
                        <button class="btn btn-danger btn-acao" onclick="excluirModalidade('${m.id}')">Excluir</button>
                    </td>
                </tr>`;
            });
        }
        $('#tabela-modalidades').html(html);
    }).fail(function() {
        mostrarMsg('Erro ao carregar modalidades.', 'err');
    });
}

function salvarModalidade() {
    const id = $('#modal-id').val();
    const body = {
        id: id || null,
        nome: $('#nome').val(),
        descricao: $('#descricao').val(),
        duracaoMinutos: parseInt($('#duracaoMinutos').val()),
        capacidadeMax: parseInt($('#capacidadeMax').val()),
        nivel: $('#nivel').val(),
        instrutorId: $('#instrutorId').val() || null,
        ativa: $('#ativa').val() === 'true'
    };
    if (!body.nome || !body.duracaoMinutos || !body.capacidadeMax) {
        mostrarMsg('Nome, Duração e Capacidade são obrigatórios.', 'err');
        return;
    }
    $.ajax({
        url: id ? API_MODAL + '/' + id : API_MODAL,
        method: id ? 'PUT' : 'POST',
        contentType: 'application/json',
        data: JSON.stringify(body),
        success: function() {
            mostrarMsg(id ? 'Modalidade atualizada!' : 'Modalidade salva!', 'ok');
            limparForm();
            carregarModalidades();
        },
        error: function() { mostrarMsg('Erro ao salvar modalidade.', 'err'); }
    });
}

function editarModalidade(m) {
    $('#modal-id').val(m.id);
    $('#nome').val(m.nome);
    $('#descricao').val(m.descricao);
    $('#duracaoMinutos').val(m.duracaoMinutos);
    $('#capacidadeMax').val(m.capacidadeMax);
    $('#nivel').val(m.nivel);
    $('#instrutorId').val(m.instrutor ? m.instrutor.id : '');
    $('#ativa').val(String(m.ativa));
    $('#form-titulo').text('Editar Modalidade');
    window.scrollTo(0, 0);
}

function excluirModalidade(id) {
    if (!confirm('Excluir esta modalidade?')) return;
    $.ajax({
        url: API_MODAL + '/' + id,
        method: 'DELETE',
        success: function() { mostrarMsg('Modalidade excluída.', 'ok'); carregarModalidades(); },
        error: function() { mostrarMsg('Erro ao excluir modalidade.', 'err'); }
    });
}

function limparForm() {
    $('#modal-id, #nome, #descricao, #duracaoMinutos, #capacidadeMax').val('');
    $('#nivel').val('Todos');
    $('#instrutorId').val('');
    $('#ativa').val('true');
    $('#form-titulo').text('Nova Modalidade');
}