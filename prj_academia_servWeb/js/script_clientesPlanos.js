const API_CP = 'http://localhost:8080/api/gestor';

function mostrarMsg(texto, tipo) {
    const cls = tipo === 'ok' ? 'alert-success' : 'alert-danger';
    $('#msg').attr('class', 'alert ' + cls).text(texto).show();
    setTimeout(() => $('#msg').fadeOut(), 3000);
}

function carregarRelatorio() {
    $('#resultado').empty();
    $('#loading').show().text('Carregando clientes e matrículas...');

    // Clientes do Serviço 1 (UUID direto)
    $.get(API_CP + '/clientes', function(clientes) {
        if (!clientes || clientes.length === 0) {
            $('#loading').hide();
            $('#resultado').html('<p class="text-muted">Nenhum cliente cadastrado.</p>');
            return;
        }

        let pendentes = clientes.length;
        let html = '';

        clientes.forEach(function(cliente) {
            // Matrículas do Serviço 2 usando UUID do cliente (Serviço 1)
            $.get(API_CP + '/matriculas/cliente/' + cliente.id, function(matriculas) {

                let linhas = '';
                if (!matriculas || matriculas.length === 0) {
                    linhas = '<tr><td colspan="4" class="text-muted fst-italic">Nenhuma matrícula vinculada.</td></tr>';
                } else {
                    matriculas.forEach(function(m) {
                        const badge = `<span class="badge badge-${m.status}">${m.status}</span>`;
                        linhas += `<tr>
                            <td>${m.planoId ? m.planoId.substring(0,8) + '...' : '-'}</td>
                            <td>${m.dataInicio} → ${m.dataFim}</td>
                            <td>${badge}</td>
                        </tr>`;
                    });
                }

                html += `<div class="card mb-3">
                    <div class="card-header">
                        <strong>${cliente.nome}</strong>
                        &nbsp;|&nbsp; CPF: ${cliente.cpf}
                        ${cliente.email ? ' &nbsp;|&nbsp; ' + cliente.email : ''}
                    </div>
                    <div class="card-body p-0">
                        <table class="table table-sm table-striped mb-0">
                            <thead class="table-secondary">
                                <tr><th>Plano (ID)</th><th>Período</th><th>Status</th></tr>
                            </thead>
                            <tbody>${linhas}</tbody>
                        </table>
                    </div>
                </div>`;

                pendentes--;
                if (pendentes === 0) { $('#loading').hide(); $('#resultado').html(html); }

            }).fail(function() {
                html += `<div class="card mb-3">
                    <div class="card-header"><strong>${cliente.nome}</strong></div>
                    <div class="card-body"><p class="text-muted fst-italic">Erro ao carregar matrículas.</p></div>
                </div>`;
                pendentes--;
                if (pendentes === 0) { $('#loading').hide(); $('#resultado').html(html); }
            });
        });

    }).fail(function() {
        $('#loading').hide();
        mostrarMsg('Erro ao carregar clientes.', 'err');
    });
}