const API_MAT = 'http://localhost:8080/api/gestor';
let mapaClientes = {};
let mapaPlanos = {};

function mostrarMsg(texto, tipo) {
    const cls = tipo === 'ok' ? 'alert-success' : 'alert-danger';
    $('#msg').attr('class', 'alert ' + cls).text(texto).show();
    setTimeout(() => $('#msg').fadeOut(), 3000);
}

function inicializar() {
    // Clientes e planos vêm do Serviço 1 (UUIDs diretos)
    $.get(API_MAT + '/clientes', function(data) {
        let opts = '<option value="">-- Selecione um cliente --</option>';
        data.forEach(function(c) {
            mapaClientes[c.id] = c.nome;
            opts += `<option value="${c.id}">${c.nome} — ${c.cpf}</option>`;
        });
        $('#cliente-id').html(opts);
    });

    $.get(API_MAT + '/planos', function(data) {
        let opts = '<option value="">-- Selecione um plano --</option>';
        data.forEach(function(p) {
            mapaPlanos[p.id] = p.nome;
            opts += `<option value="${p.id}">${p.nome} — R$ ${parseFloat(p.preco).toFixed(2)}</option>`;
        });
        $('#plano-id').html(opts);
    });

    setTimeout(carregarMatriculas, 500);
}

function carregarMatriculas() {
    $.get(API_MAT + '/matriculas', function(data) {
        let html = '';
        if (!data || data.length === 0) {
            html = '<tr><td colspan="6" class="text-center text-muted">Nenhuma matrícula cadastrada.</td></tr>';
        } else {
            data.forEach(function(m) {
                const nomeCliente = mapaClientes[m.clienteId] || m.clienteId;
                const nomePlano   = mapaPlanos[m.planoId] || m.planoId;
                const badge = `<span class="badge badge-${m.status}">${m.status}</span>`;
                html += `<tr>
                    <td>${nomeCliente}</td>
                    <td>${nomePlano}</td>
                    <td>${m.dataInicio}</td>
                    <td>${m.dataFim}</td>
                    <td>${badge}</td>
                    <td>
                        <button class="btn btn-warning btn-acao me-1" onclick='editarMatricula(${JSON.stringify(m)})'>Editar</button>
                        <button class="btn btn-danger btn-acao" onclick="excluirMatricula('${m.id}')">Excluir</button>
                    </td>
                </tr>`;
            });
        }
        $('#tabela-matriculas').html(html);
    }).fail(function() {
        mostrarMsg('Erro ao carregar matrículas.', 'err');
    });
}

function salvarMatricula() {
    const id = $('#matricula-id').val();
    const body = {
        id: id || null,
        clienteId: $('#cliente-id').val(),
        planoId: $('#plano-id').val(),
        dataInicio: $('#data-inicio').val(),
        dataFim: $('#data-fim').val(),
        status: $('#status').val(),
        observacoes: $('#observacoes').val()
    };
    if (!body.clienteId || !body.planoId || !body.dataInicio || !body.dataFim) {
        mostrarMsg('Preencha todos os campos obrigatórios.', 'err');
        return;
    }
    $.ajax({
        url: id ? API_MAT + '/matriculas/' + id : API_MAT + '/matriculas',
        method: id ? 'PUT' : 'POST',
        contentType: 'application/json',
        data: JSON.stringify(body),
        success: function() {
            mostrarMsg(id ? 'Matrícula atualizada!' : 'Matrícula salva!', 'ok');
            limparForm();
            carregarMatriculas();
        },
        error: function() { mostrarMsg('Erro ao salvar matrícula.', 'err'); }
    });
}

function editarMatricula(m) {
    $('#matricula-id').val(m.id);
    $('#cliente-id').val(m.clienteId);
    $('#plano-id').val(m.planoId);
    $('#data-inicio').val(m.dataInicio);
    $('#data-fim').val(m.dataFim);
    $('#status').val(m.status);
    $('#observacoes').val(m.observacoes);
    $('#form-titulo').text('Editar Matrícula');
    window.scrollTo(0, 0);
}

function excluirMatricula(id) {
    if (!confirm('Excluir esta matrícula?')) return;
    $.ajax({
        url: API_MAT + '/matriculas/' + id,
        method: 'DELETE',
        success: function() { mostrarMsg('Matrícula excluída.', 'ok'); carregarMatriculas(); },
        error: function() { mostrarMsg('Erro ao excluir matrícula.', 'err'); }
    });
}

function limparForm() {
    $('#matricula-id, #observacoes').val('');
    $('#cliente-id, #plano-id').val('');
    $('#data-inicio, #data-fim').val('');
    $('#status').val('ativa');
    $('#form-titulo').text('Nova Matrícula');
}