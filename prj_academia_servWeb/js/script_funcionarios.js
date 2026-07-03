const API_FUNC = 'http://localhost:8080/api/gestor/funcionarios';

function mostrarMsg(texto, tipo) {
    const cls = tipo === 'ok' ? 'alert-success' : 'alert-danger';
    $('#msg').attr('class', 'alert ' + cls).text(texto).show();
    setTimeout(() => $('#msg').fadeOut(), 3000);
}

function carregarFuncionarios() {
    $.get(API_FUNC, function(data) {
        let html = '';
        if (!data || data.length === 0) {
            html = '<tr><td colspan="7" class="text-center text-muted">Nenhum funcionário cadastrado.</td></tr>';
        } else {
            data.forEach(function(f) {
                html += `<tr>
                    <td>${f.nome}</td>
                    <td>${f.cpf}</td>
                    <td>${f.cargo}</td>
                    <td>${f.salario ? 'R$ ' + parseFloat(f.salario).toFixed(2) : '-'}</td>
                    <td>${f.dataAdmissao || '-'}</td>
                    <td>${f.ativo ? '<span class="badge bg-success">Sim</span>' : '<span class="badge bg-secondary">Não</span>'}</td>
                    <td>
                        <button class="btn btn-warning btn-acao me-1" onclick='editarFuncionario(${JSON.stringify(f)})'>Editar</button>
                        <button class="btn btn-danger btn-acao" onclick="excluirFuncionario('${f.id}')">Excluir</button>
                    </td>
                </tr>`;
            });
        }
        $('#tabela-funcionarios').html(html);
    }).fail(function() {
        mostrarMsg('Erro ao carregar funcionários.', 'err');
    });
}

function salvarFuncionario() {
    const id = $('#func-id').val();
    const body = {
        id: id || null,
        nome: $('#nome').val(),
        cpf: $('#cpf').val(),
        email: $('#email').val(),
        telefone: $('#telefone').val(),
        cargo: $('#cargo').val(),
        salario: $('#salario').val() ? parseFloat($('#salario').val()) : null,
        dataAdmissao: $('#dataAdmissao').val() || null,
        dataDemissao: $('#dataDemissao').val() || null,
        ativo: $('#ativo').val() === 'true'
    };
    if (!body.nome || !body.cpf || !body.email || !body.cargo) {
        mostrarMsg('Nome, CPF, E-mail e Cargo são obrigatórios.', 'err');
        return;
    }
    $.ajax({
        url: id ? API_FUNC + '/' + id : API_FUNC,
        method: id ? 'PUT' : 'POST',
        contentType: 'application/json',
        data: JSON.stringify(body),
        success: function() {
            mostrarMsg(id ? 'Funcionário atualizado!' : 'Funcionário salvo!', 'ok');
            limparForm();
            carregarFuncionarios();
        },
        error: function() { mostrarMsg('Erro ao salvar funcionário.', 'err'); }
    });
}

function editarFuncionario(f) {
    $('#func-id').val(f.id);
    $('#nome').val(f.nome);
    $('#cpf').val(f.cpf);
    $('#email').val(f.email);
    $('#telefone').val(f.telefone);
    $('#cargo').val(f.cargo);
    $('#salario').val(f.salario);
    $('#dataAdmissao').val(f.dataAdmissao);
    $('#dataDemissao').val(f.dataDemissao);
    $('#ativo').val(String(f.ativo));
    $('#form-titulo').text('Editar Funcionário');
    window.scrollTo(0, 0);
}

function excluirFuncionario(id) {
    if (!confirm('Excluir este funcionário?')) return;
    $.ajax({
        url: API_FUNC + '/' + id,
        method: 'DELETE',
        success: function() { mostrarMsg('Funcionário excluído.', 'ok'); carregarFuncionarios(); },
        error: function() { mostrarMsg('Erro ao excluir funcionário.', 'err'); }
    });
}

function limparForm() {
    $('#func-id, #nome, #cpf, #email, #telefone, #cargo, #salario, #dataAdmissao, #dataDemissao').val('');
    $('#ativo').val('true');
    $('#form-titulo').text('Novo Funcionário');
}