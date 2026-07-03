const API_PLANOS = 'http://localhost:8080/api/gestor/planos';

function mostrarMsg(texto, tipo) {
    const cls = tipo === 'ok' ? 'alert-success' : 'alert-danger';
    $('#msg').attr('class', 'alert ' + cls).text(texto).show();
    setTimeout(() => $('#msg').fadeOut(), 3000);
}

function carregarPlanos() {
    $.get(API_PLANOS, function(data) {
        let html = '';
        if (!data || data.length === 0) {
            html = '<tr><td colspan="5" class="text-center text-muted">Nenhum plano cadastrado.</td></tr>';
        } else {
            data.forEach(function(p) {
                html += `<tr>
                    <td>${p.nome}</td>
                    <td>${p.duracaoDias} dias</td>
                    <td>R$ ${parseFloat(p.preco).toFixed(2)}</td>
                    <td>${p.ativo ? '<span class="badge bg-success">Sim</span>' : '<span class="badge bg-secondary">Não</span>'}</td>
                    <td>
                        <button class="btn btn-warning btn-acao me-1" onclick='editarPlano(${JSON.stringify(p)})'>Editar</button>
                        <button class="btn btn-danger btn-acao" onclick="excluirPlano('${p.id}')">Excluir</button>
                    </td>
                </tr>`;
            });
        }
        $('#tabela-planos').html(html);
    }).fail(function() {
        mostrarMsg('Erro ao carregar planos. Verifique se o Gestor está rodando.', 'err');
    });
}

function salvarPlano() {
    const id = $('#plano-id').val();
    const body = {
        id: id || null,
        nome: $('#nome').val(),
        descricao: $('#descricao').val(),
        duracaoDias: parseInt($('#duracao').val()),
        preco: parseFloat($('#preco').val()),
        ativo: $('#ativo').val() === 'true'
    };
    if (!body.nome || !body.duracaoDias || isNaN(body.preco)) {
        mostrarMsg('Preencha os campos obrigatórios: Nome, Duração e Preço.', 'err');
        return;
    }
    $.ajax({
        url: id ? API_PLANOS + '/' + id : API_PLANOS,
        method: id ? 'PUT' : 'POST',
        contentType: 'application/json',
        data: JSON.stringify(body),
        success: function() {
            mostrarMsg(id ? 'Plano atualizado!' : 'Plano salvo!', 'ok');
            limparForm();
            carregarPlanos();
        },
        error: function() { mostrarMsg('Erro ao salvar plano.', 'err'); }
    });
}

function editarPlano(p) {
    $('#plano-id').val(p.id);
    $('#nome').val(p.nome);
    $('#descricao').val(p.descricao);
    $('#duracao').val(p.duracaoDias);
    $('#preco').val(p.preco);
    $('#ativo').val(String(p.ativo));
    $('#form-titulo').text('Editar Plano');
    window.scrollTo(0, 0);
}

function excluirPlano(id) {
    if (!confirm('Excluir este plano?')) return;
    $.ajax({
        url: API_PLANOS + '/' + id,
        method: 'DELETE',
        success: function() { mostrarMsg('Plano excluído.', 'ok'); carregarPlanos(); },
        error: function() { mostrarMsg('Erro ao excluir plano.', 'err'); }
    });
}

function limparForm() {
    $('#plano-id, #nome, #descricao, #duracao, #preco').val('');
    $('#ativo').val('true');
    $('#form-titulo').text('Novo Plano');
}