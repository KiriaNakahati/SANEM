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
                    <td>${u.role}</td>
                     <td>${new Date(u.createdAt || u.registeredAt || Date.now()).toLocaleDateString()}</td>
                    <td class="acoes-coluna">
                      <img src="assets/edit.webp" alt="Editar" title="Editar" class="icon-btn icon-edit">
                      <img src="assets/lixo.png" alt="Excluir" title="Excluir" class="icon-btn icon-delete">
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


