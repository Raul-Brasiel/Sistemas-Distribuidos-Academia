const API_AVAL = 'http://localhost:8080/api/gestor';
let mapaClientesAval = {};

function mostrarMsg(texto, tipo) {
    const cls = tipo === 'ok' ? 'alert-success' : 'alert-danger';
    $('#msg').attr('class', 'alert ' + cls).text(texto).show();
    setTimeout(() => $('#msg').fadeOut(), 3000);
}

function inicializar() {
    $.get(API_AVAL + '/clientes', function(data) {
        let opts = '<option value="">-- Selecione --</option>';
        data.forEach(function(c) {
            mapaClientesAval[c.id] = c.nome;
            opts += `<option value="${c.id}">${c.nome} — ${c.cpf}</option>`;
        });
        $('#clienteId').html(opts);
    });

    $.get(API_AVAL + '/funcionarios', function(data) {
        let opts = '<option value="">-- Selecione --</option>';
        data.forEach(function(f) {
            opts += `<option value="${f.id}">${f.nome} — ${f.cargo}</option>`;
        });
        $('#funcionarioId').html(opts);
    });

    setTimeout(carregarAvaliacoes, 500);
}

function calcularImc() {
    const peso   = parseFloat($('#peso').val());
    const altura = parseFloat($('#altura').val());
    if (peso > 0 && altura > 0) {
        const altM = altura / 100;
        const imc  = (peso / (altM * altM)).toFixed(1);
        $('#imc').val(imc);
    } else {
        $('#imc').val('');
    }
}

function carregarAvaliacoes() {
    $.get(API_AVAL + '/avaliacoes', function(data) {
        let html = '';
        if (!data || data.length === 0) {
            html = '<tr><td colspan="7" class="text-center text-muted">Nenhuma avaliação cadastrada.</td></tr>';
        } else {
            data.forEach(function(a) {
                const nomeCliente = mapaClientesAval[a.clienteId] || a.clienteId;
                const bio = a.dadosBiometricos || {};
                html += `<tr>
                    <td>${nomeCliente}</td>
                    <td>${a.dataAvaliacao || '-'}</td>
                    <td>${bio.peso_kg || '-'} kg</td>
                    <td>${bio.imc || '-'}</td>
                    <td>${bio.percentual_gordura || '-'}%</td>
                    <td>${a.proximaAvaliacao || '-'}</td>
                    <td>
                        <button class="btn btn-warning btn-acao me-1" onclick='editarAvaliacao(${JSON.stringify(a)})'>Editar</button>
                        <button class="btn btn-danger btn-acao" onclick="excluirAvaliacao('${a.id}')">Excluir</button>
                    </td>
                </tr>`;
            });
        }
        $('#tabela-avaliacoes').html(html);
    }).fail(function() {
        mostrarMsg('Erro ao carregar avaliações.', 'err');
    });
}

function salvarAvaliacao() {
    const id = $('#aval-id').val();
    const body = {
        clienteId: $('#clienteId').val(),
        funcionarioId: $('#funcionarioId').val() || null,
        dataAvaliacao: $('#dataAvaliacao').val(),
        dadosBiometricos: {
            peso_kg: parseFloat($('#peso').val()) || null,
            altura_cm: parseInt($('#altura').val()) || null,
            imc: parseFloat($('#imc').val()) || null,
            percentual_gordura: parseFloat($('#percentualGordura').val()) || null,
            massa_muscular_kg: parseFloat($('#massaMuscular').val()) || null
        },
        pressaoArterial: {
            sistolica: parseInt($('#sistolica').val()) || null,
            diastolica: parseInt($('#diastolica').val()) || null
        },
        objetivoDeclarado: $('#objetivoDeclarado').val(),
        observacoes: $('#observacoes').val(),
        proximaAvaliacao: $('#proximaAvaliacao').val() || null
    };
    if (!body.clienteId || !body.dataAvaliacao) {
        mostrarMsg('Cliente e Data da Avaliação são obrigatórios.', 'err');
        return;
    }
    $.ajax({
        url: id ? API_AVAL + '/avaliacoes/' + id : API_AVAL + '/avaliacoes',
        method: id ? 'PUT' : 'POST',
        contentType: 'application/json',
        data: JSON.stringify(body),
        success: function() {
            mostrarMsg(id ? 'Avaliação atualizada!' : 'Avaliação salva!', 'ok');
            limparForm();
            carregarAvaliacoes();
        },
        error: function() { mostrarMsg('Erro ao salvar avaliação.', 'err'); }
    });
}

function editarAvaliacao(a) {
    $('#aval-id').val(a.id);
    $('#clienteId').val(a.clienteId);
    $('#funcionarioId').val(a.funcionarioId || '');
    $('#dataAvaliacao').val(a.dataAvaliacao);
    $('#proximaAvaliacao').val(a.proximaAvaliacao || '');
    const bio = a.dadosBiometricos || {};
    $('#peso').val(bio.peso_kg || '');
    $('#altura').val(bio.altura_cm || '');
    $('#imc').val(bio.imc || '');
    $('#percentualGordura').val(bio.percentual_gordura || '');
    $('#massaMuscular').val(bio.massa_muscular_kg || '');
    const pa = a.pressaoArterial || {};
    $('#sistolica').val(pa.sistolica || '');
    $('#diastolica').val(pa.diastolica || '');
    $('#objetivoDeclarado').val(a.objetivoDeclarado || '');
    $('#observacoes').val(a.observacoes || '');
    $('#form-titulo').text('Editar Avaliação');
    window.scrollTo(0, 0);
}

function excluirAvaliacao(id) {
    if (!confirm('Excluir esta avaliação?')) return;
    $.ajax({
        url: API_AVAL + '/avaliacoes/' + id,
        method: 'DELETE',
        success: function() { mostrarMsg('Avaliação excluída.', 'ok'); carregarAvaliacoes(); },
        error: function() { mostrarMsg('Erro ao excluir avaliação.', 'err'); }
    });
}

function limparForm() {
    $('#aval-id, #dataAvaliacao, #proximaAvaliacao').val('');
    $('#clienteId, #funcionarioId').val('');
    $('#peso, #altura, #imc, #percentualGordura, #massaMuscular').val('');
    $('#sistolica, #diastolica, #objetivoDeclarado, #observacoes').val('');
    $('#form-titulo').text('Nova Avaliação');
}