const API_STATUS  = 'http://localhost:8080/api/gestor/status';
const API_SERV2   = 'http://localhost:8083/api/matriculas';
const API_SERV3   = 'http://localhost:5000/api/treinos';

function mostrarMsg(texto, tipo) {
    const cls = tipo === 'ok' ? 'alert-success' : 'alert-danger';
    $('#msg').attr('class', 'alert ' + cls).text(texto).show();
    setTimeout(() => $('#msg').fadeOut(), 3000);
}

function carregarStatus() {
    $('#instancias-container').html('<p class="text-muted">Verificando...</p>');
    $('#outros-servicos').html('<p class="text-muted">Verificando...</p>');

    // Status do load balancer (via Gestor)
    $.get(API_STATUS, function(data) {
        let html = '';
        data.todasInstancias.forEach(function(inst) {
            const ativa = data.instanciasAtivas.includes(inst);
            html += `<div class="instancia-card ${ativa ? 'instancia-ativa' : 'instancia-inativa'}">
                <strong>${ativa ? '🟢' : '🔴'} ${inst}</strong>
                <span class="float-end badge ${ativa ? 'bg-success' : 'bg-danger'}">
                    ${ativa ? 'Online' : 'Offline'}
                </span>
            </div>`;
        });
        $('#instancias-container').html(html);
    }).fail(function() {
        $('#instancias-container').html('<p class="text-danger">❌ Gestor indisponível na porta 8080.</p>');
    });

    // Verifica Serviço 2
    let htmlOutros = '';
    $.get(API_SERV2)
        .done(function() {
            htmlOutros += pingCard('Serviço 2 — Spring Boot :8083', true);
            renderOutros(htmlOutros);
        })
        .fail(function() {
            htmlOutros += pingCard('Serviço 2 — Spring Boot :8083', false);
            renderOutros(htmlOutros);
        });

    // Verifica Serviço 3
    $.get(API_SERV3)
        .done(function() {
            htmlOutros += pingCard('Serviço 3 — Flask :5000', true);
            renderOutros(htmlOutros);
        })
        .fail(function() {
            htmlOutros += pingCard('Serviço 3 — Flask :5000', false);
            renderOutros(htmlOutros);
        });
}

function pingCard(nome, online) {
    return `<div class="instancia-card ${online ? 'instancia-ativa' : 'instancia-inativa'}">
        <strong>${online ? '🟢' : '🔴'} ${nome}</strong>
        <span class="float-end badge ${online ? 'bg-success' : 'bg-danger'}">
            ${online ? 'Online' : 'Offline'}
        </span>
    </div>`;
}

function renderOutros(html) {
    $('#outros-servicos').html(html);
}