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
        backArrow.style.display = (pageName === 'dashboard' || pageName === 'login')
            ? 'none'
            : 'inline-block';
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
                API.setAuthToken(response.token);
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
        API.clearAuthToken();
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
            const user = await API.getUser(); // GET /user
            const el = document.getElementById('userName');
            if (el) el.textContent = user.nome || user.email;
        } catch (e) {
            console.warn('Não foi possível buscar dados do usuário', e);
        }
    }
    window.loadUserInfo = loadUserInfo;
});
