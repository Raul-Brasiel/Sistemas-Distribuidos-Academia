const API_PAG = 'http://localhost:8080/api/gestor';
let mapaMatriculas = {};
let mapaClientes = {};

function mostrarMsg(texto, tipo) {
    const cls = tipo === 'ok' ? 'alert-success' : 'alert-danger';
    $('#msg').attr('class', 'alert ' + cls).text(texto).show();
    setTimeout(() => $('#msg').fadeOut(), 3000);
}

function inicializar() {
    $.get(API_PAG + '/clientes', function(clientesData) {
        clientesData.forEach(function(c) {
            mapaClientes[c.id] = c.nome;
        });

        $.get(API_PAG + '/matriculas', function(data) {
            let opts = '<option value="">-- Selecione uma matrícula --</option>';
            data.forEach(function(m) {
                mapaMatriculas[m.id] = 'Matrícula ' + m.id.substring(0, 8) + '...';
                const nomeCliente = mapaClientes[m.clienteId] || m.clienteId.substring(0,8) + '...';
                opts += `<option value="${m.id}">ID: ${m.id.substring(0,8)}... | Cliente: ${nomeCliente} | ${m.status}</option>`;
            });
            $('#matricula-id').html(opts);
        });
    });
    setTimeout(carregarPagamentos, 500);
}

function carregarPagamentos() {
    $.get(API_PAG + '/pagamentos', function(data) {
        let html = '';
        if (!data || data.length === 0) {
            html = '<tr><td colspan="7" class="text-center text-muted">Nenhum pagamento cadastrado.</td></tr>';
        } else {
            data.forEach(function(p) {
                const matId = p.matricula ? p.matricula.id.substring(0,8) + '...' : '-';
                const badge = `<span class="badge badge-${p.status}">${p.status}</span>`;
                html += `<tr>
                    <td><small>${matId}</small></td>
                    <td>R$ ${parseFloat(p.valor).toFixed(2)}</td>
                    <td>${p.dataVencimento}</td>
                    <td>${p.dataPagamento || '-'}</td>
                    <td>${p.metodo || '-'}</td>
                    <td>${badge}</td>
                    <td>
                        <button class="btn btn-warning btn-acao me-1" onclick='editarPagamento(${JSON.stringify(p)})'>Editar</button>
                        <button class="btn btn-danger btn-acao" onclick="excluirPagamento('${p.id}')">Excluir</button>
                    </td>
                </tr>`;
            });
        }
        $('#tabela-pagamentos').html(html);
    }).fail(function() {
        mostrarMsg('Erro ao carregar pagamentos.', 'err');
    });
}

function salvarPagamento() {
    const id = $('#pag-id').val();
    const body = {
        id: id || null,
        matriculaId: $('#matricula-id').val(),
        valor: parseFloat($('#valor').val()),
        dataVencimento: $('#dataVencimento').val(),
        dataPagamento: $('#dataPagamento').val() || null,
        metodo: $('#metodo').val(),
        status: $('#status').val(),
        comprovanteUrl: null
    };
    if (!body.matriculaId || isNaN(body.valor) || !body.dataVencimento) {
        mostrarMsg('Matrícula, Valor e Vencimento são obrigatórios.', 'err');
        return;
    }
    $.ajax({
        url: id ? API_PAG + '/pagamentos/' + id : API_PAG + '/pagamentos',
        method: id ? 'PUT' : 'POST',
        contentType: 'application/json',
        data: JSON.stringify(body),
        success: function() {
            mostrarMsg(id ? 'Pagamento atualizado!' : 'Pagamento salvo!', 'ok');
            limparForm();
            carregarPagamentos();
        },
        error: function() { mostrarMsg('Erro ao salvar pagamento.', 'err'); }
    });
}

function editarPagamento(p) {
    $('#pag-id').val(p.id);
    $('#matricula-id').val(p.matricula ? p.matricula.id : '');
    $('#valor').val(p.valor);
    $('#dataVencimento').val(p.dataVencimento);
    $('#dataPagamento').val(p.dataPagamento);
    $('#metodo').val(p.metodo);
    $('#status').val(p.status);
    $('#form-titulo').text('Editar Pagamento');
    window.scrollTo(0, 0);
}

function excluirPagamento(id) {
    if (!confirm('Excluir este pagamento?')) return;
    $.ajax({
        url: API_PAG + '/pagamentos/' + id,
        method: 'DELETE',
        success: function() { mostrarMsg('Pagamento excluído.', 'ok'); carregarPagamentos(); },
        error: function() { mostrarMsg('Erro ao excluir pagamento.', 'err'); }
    });
}

function limparForm() {
    $('#pag-id, #valor, #dataVencimento, #dataPagamento').val('');
    $('#matricula-id').val('');
    $('#metodo').val('pix');
    $('#status').val('pendente');
    $('#form-titulo').text('Novo Pagamento');
}