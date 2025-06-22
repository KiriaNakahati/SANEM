// app.js

// Histórico de navegação da SPA
let spaHistory = ['dashboard'];

/**
 * Exibe a página solicitada e atualiza o histórico.
 * @param {string} pageName - Nome da página sem o sufixo "Page" (ex.: 'login', 'dashboard', 'beneficiarios').
 */
function showPage(pageName) {
    document.querySelectorAll('.page').forEach(page => page.style.display = 'none');
    const id = pageName.endsWith('Page') ? pageName : pageName + 'Page';
    const el = document.getElementById(id);
    if (el) {
        el.style.display = (id === 'loginPage') ? 'flex' : 'block';
    }

    // Atualiza histórico SPA
    if (spaHistory[spaHistory.length - 1] !== pageName) {
        spaHistory.push(pageName);
    }

    // Controla exibição do botão de voltar
    const backArrow = document.getElementById('backArrow');
    if (backArrow) {
        // só esconde quando for a tela de login
        backArrow.style.display = (
            spaHistory.length > 1
            && pageName !== 'login'
            && pageName !== 'dashboard'
        ) ? 'inline-flex' : 'none';
    }
}

/** Retorna true se existir um token salvo */
function isAuthenticated() {
    return !!API.getAuthToken();
}

document.addEventListener('DOMContentLoaded', async function () {
    // Ao carregar, decide se vai pro login ou pro dashboard
    if (isAuthenticated()) {
        await loadUserInfo();
        showPage('dashboard');
    } else {
        showPage('login');
    }

    // Listener do formulário de login
    const loginForm = document.getElementById('loginForm');
    if (loginForm) {
        loginForm.addEventListener('submit', async function (e) {
            e.preventDefault();
            hideError();

            const formData = new FormData(loginForm);
            const credentials = {
                email: formData.get('email'),
                senha: formData.get('senha')
            };

            try {
                // Chama o back-end
                const response = await API.login(credentials);
                // Salva token e email para depois identificar o usuário
                API.setAuthToken(response.token);
                localStorage.setItem('sanem_email', credentials.email);

                // Carrega dados do usuário e vai pro dashboard
                await loadUserInfo();
                showPage('dashboard');
            } catch (error) {
                showError(error.message || 'Erro ao fazer login');
            }
        });
    }

    // Função de logout
    window.logout = function () {
        console.log('logout() disparado');
        API.clearAuthToken();
        localStorage.removeItem('sanem_email');
        // Limpa o nome exibido na barra
        const el = document.getElementById('userName');
        console.log('antes de limpar, #userName:', el && el.textContent);
        if (el) el.textContent = '';
        console.log('depois de limpar, #userName:', el && el.textContent);
        showPage('login');
    };


    // Expondo showPage globalmente (se necessário em outros scripts)
    window.showPage = showPage;

    // Função de “voltar” na SPA
    function goBack() {
        if (spaHistory.length > 1) {
            spaHistory.pop(); // Remove a página atual
            const prevPage = spaHistory[spaHistory.length - 1];
            showPage(prevPage);
        } else {
            showPage('dashboard');
        }
    }
    window.goBack = goBack;

    // --- Carrega dados do usuário autenticado ---
    async function loadUserInfo() {
        try {
            const users = await API.getUser();               // array de usuários
            const storedEmail = localStorage.getItem('sanem_email');
            console.log('>>> loadUserInfo:', { users, storedEmail });

            // busca pelo email exato
            let me = Array.isArray(users)
                ? users.find(u => u.email === storedEmail)
                : (users.email === storedEmail ? users : null);

            console.log('encontrou me:', me);

            // fallback para primeiro ou último
            if (!me && Array.isArray(users)) {
                me = users[users.length - 1];  // pega último cadastrado
                console.log('fallback para último:', me);
            }

            const fullName = [me.firstName, me.lastName].filter(Boolean).join(' ');
            const el = document.getElementById('userName');
            if (el) el.textContent = fullName || me.email;
        } catch (e) {
            console.warn('Não foi possível buscar dados do usuário', e);
        }
    }

    window.loadUserInfo = loadUserInfo;
});

// 1) Amarra a navegação: sempre que a gente for pra 'funcionariosPage', dispara o loader
const originalShowPage = showPage;
showPage = function (pageName) {
    originalShowPage(pageName);
    if (pageName === 'funcionarios') {
        loadFuncionarios();
    }
};

// 2) Busca e renderiza
async function loadFuncionarios() {
    const tbody = document.getElementById('funcionariosTableBody');
    tbody.innerHTML = '<tr><td colspan="5" class="loading">Carregando funcionários…</td></tr>';

    try {
        // puxar todos
        const lista = await API.getUser(); // retorna array de UserDTO
        // atualiza o nome logado
        const me = lista.find(u => u.email === localStorage.getItem('sanem_email'))
            || lista[0];
        document.getElementById('userName8').textContent =
            [me.firstName, me.lastName].filter(Boolean).join(' ') || me.email;

        // renderiza todas as linhas
        if (Array.isArray(lista) && lista.length) {
            tbody.innerHTML = ''; // limpa loading
            lista.forEach(u => {
                const tr = document.createElement('tr');
                tr.innerHTML = `
  <td>${u.firstName} ${u.lastName}</td>
  <td>${u.email}</td>
  <td>${new Date(u.createdAt || u.registeredAt || Date.now()).toLocaleDateString()}</td>
  <td class="acoes-coluna">
   <img
      src="assets/edit.webp"
     alt="Editar"
      title="Editar"
     class="icon-btn icon-edit"
     onclick='promptEdit(${JSON.stringify({
                    uuid: u.uuid,
                    firstName: u.firstName,
                    lastName: u.lastName,
                    email: u.email
                })})'
   >
    <img
      src="assets/lixo.png"
      alt="Excluir"
      title="Excluir"
      class="icon-btn icon-delete"
      onclick='promptDelete(${JSON.stringify({
                    uuid: u.uuid,
                    firstName: u.firstName,
                    lastName: u.lastName
                })})'
    >
  </td>
`;


                tbody.appendChild(tr);
            });
        } else {
            tbody.innerHTML = '<tr><td colspan="5">Nenhum funcionário cadastrado.</td></tr>';
        }

    } catch (err) {
        tbody.innerHTML = `<tr><td colspan="5" class="error-message">Erro ao carregar: ${err.message}</td></tr>`;
    }
}

let funcionarioToDelete = null;

function promptDelete(func) {
    funcionarioToDelete = func;
    const txt = document.getElementById('confirmDeleteText');
    txt.textContent = `Tem certeza que deseja excluir o funcionário “${func.firstName} ${func.lastName}”?`;
    showModal('confirmDeleteModal');
}

let funcionarioToEdit = null;  // armazena o usuário a ser editado

/**
 * Abre o modal de edição preenchido com os campos permitidos.
 */
function promptEdit(func) {
    funcionarioToEdit = func;

    document.getElementById('editFuncionarioUuid').value = func.uuid;
    document.getElementById('editFuncionarioFirstName').value = func.firstName;
    document.getElementById('editFuncionarioLastName').value = func.lastName;
    document.getElementById('editFuncionarioEmail').value = func.email;
    document.getElementById('editFuncionarioSenha').value = '';

    // limpa mensagens antigas
    document.getElementById('editFuncionarioMessage').style.display = 'none';
    document.getElementById('editFuncionarioError').style.display = 'none';

    showModal('editFuncionarioModal');
}


// Amarra o botão "Excluir" do modal
document.getElementById('btnConfirmDelete').addEventListener('click', async () => {
    if (!funcionarioToDelete) return;
    try {
        await API.request(`/auth/delete?id=${funcionarioToDelete.uuid}`, { method: 'DELETE' });
        hideModal('confirmDeleteModal');
        loadFuncionarios();
    } catch (err) {
        alert('Erro ao excluir: ' + err.message);
    }
});


const novoFuncForm = document.getElementById('novoFuncionarioForm');
const msgSucesso = document.getElementById('funcionarioMessage');
const msgErro = document.getElementById('funcionarioError');

novoFuncForm.addEventListener('submit', async e => {
    e.preventDefault();
    // Esconde mensagens antigas
    msgSucesso.style.display = 'none';
    msgErro.style.display = 'none';

    // Pega valores
    const cpf = document.getElementById('funcionarioCpf').value.trim();
    const firstName = document.getElementById('funcionarioFirstName').value.trim();
    const lastName = document.getElementById('funcionarioLastName').value.trim();
    const email = document.getElementById('funcionarioEmail').value.trim();
    const password = document.getElementById('funcionarioSenha').value;

    try {
        // Dispara o POST /auth/register
        await API.request('/auth/register', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({
                first_name: firstName,
                last_name: lastName,
                email,
                cpf,
                password
            })
        });

        novoFuncForm.reset();
        msgSucesso.textContent = 'Funcionário cadastrado com sucesso!';
        msgSucesso.style.display = 'block';

        // Recarrega a listagem imediatamente
        loadFuncionarios();

        // Fecha o modal após 2 segundos, para o usuário ver a mensagem
        setTimeout(() => {
            hideModal('novoFuncionarioModal');
            // limpa mensagem para a próxima abertura
            msgSucesso.style.display = 'none';
        }, 3000);

    } catch (err) {
        // Exibe a mensagem de erro real, se houver
        msgErro.textContent = err.message || 'Falha ao cadastrar funcionário.';
        msgErro.style.display = 'block';
    }
});

const editForm = document.getElementById('editFuncionarioForm');
const editMsg = document.getElementById('editFuncionarioMessage');
const editErr = document.getElementById('editFuncionarioError');

editForm.addEventListener('submit', async e => {
    e.preventDefault();
    editMsg.style.display = 'none';
    editErr.style.display = 'none';

    const id = document.getElementById('editFuncionarioUuid').value;
    const firstName = document.getElementById('editFuncionarioFirstName').value.trim();
    const lastName = document.getElementById('editFuncionarioLastName').value.trim();
    const email = document.getElementById('editFuncionarioEmail').value.trim();
    const password = document.getElementById('editFuncionarioSenha').value;

    try {
        await API.request(`/auth/update?id=${id}`, {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({
                first_name: firstName,
                last_name: lastName,
                email,
                password
            })
        });

        editMsg.textContent = 'Funcionário atualizado com sucesso!';
        editMsg.style.display = 'block';
        loadFuncionarios();

        setTimeout(() => {
            hideModal('editFuncionarioModal');
            editMsg.style.display = 'none';
        }, 2000);

    } catch (err) {
        editErr.textContent = err.message || 'Erro ao atualizar funcionário.';
        editErr.style.display = 'block';
    }
});



