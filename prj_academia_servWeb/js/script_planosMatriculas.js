const API_PM = 'http://localhost:8080/api/gestor';

function mostrarMsg(texto, tipo) {
    const cls = tipo === 'ok' ? 'alert-success' : 'alert-danger';
    $('#msg').attr('class', 'alert ' + cls).text(texto).show();
    setTimeout(() => $('#msg').fadeOut(), 3000);
}

function carregarRelatorio() {
    $('#resultado').empty();
    $('#loading').show().text('Carregando planos e matrículas...');

    $.get(API_PM + '/planos', function(planos) {
        if (!planos || planos.length === 0) {
            $('#loading').hide();
            $('#resultado').html('<p class="text-muted">Nenhum plano cadastrado.</p>');
            return;
        }

        let pendentes = planos.length;
        let html = '';

        planos.forEach(function(plano) {
            $.get(API_PM + '/matriculas/plano/' + plano.id, function(matriculas) {

                let linhas = '';
                if (!matriculas || matriculas.length === 0) {
                    linhas = '<tr><td colspan="3" class="text-muted fst-italic">Nenhuma matrícula neste plano.</td></tr>';
                } else {
                    matriculas.forEach(function(m, i) {
                        const badge = `<span class="badge badge-${m.status}">${m.status}</span>`;
                        linhas += `<tr>
                            <td>${i + 1}</td>
                            <td>${m.dataInicio} → ${m.dataFim}</td>
                            <td>${badge}</td>
                        </tr>`;
                    });
                }

                const total = matriculas ? matriculas.length : 0;
                html += `<div class="card mb-3">
                    <div class="card-header" style="background-color:#1a6b3c; color:#fff;">
                        <strong>${plano.nome}</strong>
                        &nbsp;|&nbsp; ${plano.duracaoDias} dias
                        &nbsp;|&nbsp; R$ ${parseFloat(plano.preco).toFixed(2)}
                        &nbsp;|&nbsp; ${total} matrícula(s)
                        &nbsp;|&nbsp; ${plano.ativo ? 'Ativo' : 'Inativo'}
                    </div>
                    ${plano.descricao ? `<div class="px-3 pt-2 text-muted small">${plano.descricao}</div>` : ''}
                    <div class="card-body p-0">
                        <table class="table table-sm table-striped mb-0">
                            <thead class="table-secondary">
                                <tr><th>#</th><th>Período</th><th>Status</th></tr>
                            </thead>
                            <tbody>${linhas}</tbody>
                        </table>
                    </div>
                </div>`;

                pendentes--;
                if (pendentes === 0) { $('#loading').hide(); $('#resultado').html(html); }

            }).fail(function() {
                html += `<div class="card mb-3">
                    <div class="card-header" style="background-color:#1a6b3c; color:#fff;"><strong>${plano.nome}</strong></div>
                    <div class="card-body"><p class="text-muted fst-italic">Erro ao carregar matrículas.</p></div>
                </div>`;
                pendentes--;
                if (pendentes === 0) { $('#loading').hide(); $('#resultado').html(html); }
            });
        });

    }).fail(function() {
        $('#loading').hide();
        mostrarMsg('Erro ao carregar planos.', 'err');
    });
}