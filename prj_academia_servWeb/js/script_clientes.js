const API_CLIENTES = 'http://localhost:8080/api/gestor/clientes';

function mostrarMsg(texto, tipo) {
    const cls = tipo === 'ok' ? 'alert-success' : 'alert-danger';
    $('#msg').attr('class', 'alert ' + cls).text(texto).show();
    setTimeout(() => $('#msg').fadeOut(), 3000);
}

function carregarClientes() {
    $.get(API_CLIENTES, function(data) {
        let html = '';
        if (!data || data.length === 0) {
            html = '<tr><td colspan="6" class="text-center text-muted">Nenhum cliente cadastrado.</td></tr>';
        } else {
            data.forEach(function(c) {
                html += `<tr>
                    <td>${c.nome}</td>
                    <td>${c.cpf}</td>
                    <td>${c.email || '-'}</td>
                    <td>${c.telefone || '-'}</td>
                    <td>${c.ativo ? '<span class="badge bg-success">Sim</span>' : '<span class="badge bg-secondary">Não</span>'}</td>
                    <td>
                        <button class="btn btn-warning btn-acao me-1" onclick='editarCliente(${JSON.stringify(c)})'>Editar</button>
                        <button class="btn btn-danger btn-acao" onclick="excluirCliente('${c.id}')">Excluir</button>
                    </td>
                </tr>`;
            });
        }
        $('#tabela-clientes').html(html);
    }).fail(function() {
        mostrarMsg('Erro ao carregar clientes.', 'err');
    });
}

function salvarCliente() {
    const id = $('#cliente-id').val();
    const body = {
        id: id || null,
        nome: $('#nome').val(),
        cpf: $('#cpf').val(),
        email: $('#email').val(),
        telefone: $('#telefone').val(),
        dataNascimento: $('#dataNascimento').val() || null,
        sexo: $('#sexo').val() || null,
        endereco: $('#endereco').val(),
        ativo: $('#ativo').val() === 'true'
    };
    if (!body.nome || !body.cpf || !body.email) {
        mostrarMsg('Nome, CPF e E-mail são obrigatórios.', 'err');
        return;
    }
    $.ajax({
        url: id ? API_CLIENTES + '/' + id : API_CLIENTES,
        method: id ? 'PUT' : 'POST',
        contentType: 'application/json',
        data: JSON.stringify(body),
        success: function() {
            mostrarMsg(id ? 'Cliente atualizado!' : 'Cliente salvo!', 'ok');
            limparForm();
            carregarClientes();
        },
        error: function() { mostrarMsg('Erro ao salvar cliente.', 'err'); }
    });
}

function editarCliente(c) {
    $('#cliente-id').val(c.id);
    $('#nome').val(c.nome);
    $('#cpf').val(c.cpf);
    $('#email').val(c.email);
    $('#telefone').val(c.telefone);
    $('#dataNascimento').val(c.dataNascimento);
    $('#sexo').val(c.sexo);
    $('#endereco').val(c.endereco);
    $('#ativo').val(String(c.ativo));
    $('#form-titulo').text('Editar Cliente');
    window.scrollTo(0, 0);
}

function excluirCliente(id) {
    if (!confirm('Excluir este cliente?')) return;
    $.ajax({
        url: API_CLIENTES + '/' + id,
        method: 'DELETE',
        success: function() { mostrarMsg('Cliente excluído.', 'ok'); carregarClientes(); },
        error: function() { mostrarMsg('Erro ao excluir cliente.', 'err'); }
    });
}

function limparForm() {
    $('#cliente-id, #nome, #cpf, #email, #telefone, #dataNascimento, #endereco').val('');
    $('#sexo').val('');
    $('#ativo').val('true');
    $('#form-titulo').text('Novo Cliente');
}