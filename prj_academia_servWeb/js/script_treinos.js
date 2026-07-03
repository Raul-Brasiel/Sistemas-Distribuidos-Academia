const API_TREINO = 'http://localhost:8080/api/gestor';
let exercicioCount = 0;
let mapaClientesTreino = {};
let mapaFuncTreino = {};

function mostrarMsg(texto, tipo) {
    const cls = tipo === 'ok' ? 'alert-success' : 'alert-danger';
    $('#msg').attr('class', 'alert ' + cls).text(texto).show();
    setTimeout(() => $('#msg').fadeOut(), 3000);
}

function inicializar() {
    $.get(API_TREINO + '/clientes', function(data) {
        let opts = '<option value="">-- Selecione --</option>';
        data.forEach(function(c) {
            mapaClientesTreino[c.id] = c.nome;
            opts += `<option value="${c.id}">${c.nome} — ${c.cpf}</option>`;
        });
        $('#clienteId').html(opts);
    });

    $.get(API_TREINO + '/funcionarios', function(data) {
        let opts = '<option value="">-- Selecione --</option>';
        data.forEach(function(f) {
            mapaFuncTreino[f.id] = f.nome;
            opts += `<option value="${f.id}">${f.nome} — ${f.cargo}</option>`;
        });
        $('#funcionarioId').html(opts);
    });

    setTimeout(carregarTreinos, 500);
}

function adicionarExercicio(ex) {
    exercicioCount++;
    const idx = exercicioCount;
    const nome      = ex ? ex.nome : '';
    const series    = ex ? ex.series : '';
    const reps      = ex ? ex.repeticoes : '';
    const carga     = ex ? ex.carga_kg : '';
    const descanso  = ex ? ex.descanso_seg : '';
    const obs       = ex ? (ex.observacoes || '') : '';

    const html = `
    <div class="exercicio-row" id="ex-${idx}">
        <button class="btn btn-danger btn-sm btn-remover" onclick="removerExercicio(${idx})">✕</button>
        <div class="row g-2">
            <div class="col-md-4">
                <label class="form-label small">Exercício *</label>
                <input type="text" class="form-control form-control-sm ex-nome" placeholder="Ex: Supino Reto" value="${nome}">
            </div>
            <div class="col-md-1">
                <label class="form-label small">Séries</label>
                <input type="number" class="form-control form-control-sm ex-series" placeholder="4" value="${series}">
            </div>
            <div class="col-md-2">
                <label class="form-label small">Repetições</label>
                <input type="text" class="form-control form-control-sm ex-reps" placeholder="8-10" value="${reps}">
            </div>
            <div class="col-md-2">
                <label class="form-label small">Carga (kg)</label>
                <input type="number" class="form-control form-control-sm ex-carga" placeholder="60" value="${carga}">
            </div>
            <div class="col-md-1">
                <label class="form-label small">Descanso (s)</label>
                <input type="number" class="form-control form-control-sm ex-descanso" placeholder="90" value="${descanso}">
            </div>
            <div class="col-md-2">
                <label class="form-label small">Obs.</label>
                <input type="text" class="form-control form-control-sm ex-obs" placeholder="Opcional" value="${obs}">
            </div>
        </div>
    </div>`;
    $('#lista-exercicios').append(html);
}

function removerExercicio(idx) {
    $('#ex-' + idx).remove();
}

function coletarExercicios() {
    const exercicios = [];
    let ordem = 1;
    $('.exercicio-row').each(function() {
        const nome = $(this).find('.ex-nome').val();
        if (!nome) return;
        exercicios.push({
            ordem: ordem++,
            nome: nome,
            grupos_musculares: [],
            series: parseInt($(this).find('.ex-series').val()) || 0,
            repeticoes: $(this).find('.ex-reps').val(),
            carga_kg: parseFloat($(this).find('.ex-carga').val()) || 0,
            descanso_seg: parseInt($(this).find('.ex-descanso').val()) || 0,
            observacoes: $(this).find('.ex-obs').val() || null
        });
    });
    return exercicios;
}

function carregarTreinos() {
    $.get(API_TREINO + '/treinos', function(data) {
        let html = '';
        if (!data || data.length === 0) {
            html = '<tr><td colspan="6" class="text-center text-muted">Nenhum treino cadastrado.</td></tr>';
        } else {
            data.forEach(function(t) {
                const nomeCliente = mapaClientesTreino[t.clienteId] || t.clienteId;
                const qtdEx = t.exercicios ? t.exercicios.length : 0;
                html += `<tr>
                    <td>${t.nome}</td>
                    <td>${nomeCliente}</td>
                    <td>${t.objetivo || '-'}</td>
                    <td>${t.nivel || '-'}</td>
                    <td>${qtdEx} exercício(s)</td>
                    <td>
                        <button class="btn btn-warning btn-acao me-1" onclick='editarTreino(${JSON.stringify(t)})'>Editar</button>
                        <button class="btn btn-danger btn-acao" onclick="excluirTreino('${t.id}')">Excluir</button>
                    </td>
                </tr>`;
            });
        }
        $('#tabela-treinos').html(html);
    }).fail(function() {
        mostrarMsg('Erro ao carregar treinos.', 'err');
    });
}

function salvarTreino() {
    const id = $('#treino-id').val();
    const exercicios = coletarExercicios();
    const body = {
        clienteId: $('#clienteId').val(),
        funcionarioId: $('#funcionarioId').val() || null,
        nome: $('#nome').val(),
        objetivo: $('#objetivo').val(),
        nivel: $('#nivel').val(),
        exercicios: exercicios,
        ativo: true
    };
    if (!body.clienteId || !body.nome) {
        mostrarMsg('Cliente e Nome do treino são obrigatórios.', 'err');
        return;
    }
    $.ajax({
        url: id ? API_TREINO + '/treinos/' + id : API_TREINO + '/treinos',
        method: id ? 'PUT' : 'POST',
        contentType: 'application/json',
        data: JSON.stringify(body),
        success: function() {
            mostrarMsg(id ? 'Treino atualizado!' : 'Treino salvo!', 'ok');
            limparForm();
            carregarTreinos();
        },
        error: function() { mostrarMsg('Erro ao salvar treino.', 'err'); }
    });
}

function editarTreino(t) {
    $('#treino-id').val(t.id);
    $('#clienteId').val(t.clienteId);
    $('#funcionarioId').val(t.funcionarioId || '');
    $('#nome').val(t.nome);
    $('#objetivo').val(t.objetivo);
    $('#nivel').val(t.nivel);
    $('#lista-exercicios').empty();
    exercicioCount = 0;
    if (t.exercicios && t.exercicios.length > 0) {
        t.exercicios.forEach(function(ex) { adicionarExercicio(ex); });
    }
    $('#form-titulo').text('Editar Treino');
    window.scrollTo(0, 0);
}

function excluirTreino(id) {
    if (!confirm('Excluir este treino?')) return;
    $.ajax({
        url: API_TREINO + '/treinos/' + id,
        method: 'DELETE',
        success: function() { mostrarMsg('Treino excluído.', 'ok'); carregarTreinos(); },
        error: function() { mostrarMsg('Erro ao excluir treino.', 'err'); }
    });
}

function limparForm() {
    $('#treino-id, #nome, #objetivo').val('');
    $('#clienteId, #funcionarioId').val('');
    $('#nivel').val('Todos');
    $('#lista-exercicios').empty();
    exercicioCount = 0;
    $('#form-titulo').text('Novo Treino');
}