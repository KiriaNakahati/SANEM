// api.js

const API_CONFIG = {
    baseURL: 'http://localhost:8088'
};

class API {
    static getAuthToken() {
        return localStorage.getItem('sanem_token');
    }

    static setAuthToken(token) {
        localStorage.setItem('sanem_token', token);
    }

    static clearAuthToken() {
        localStorage.removeItem('sanem_token');
    }

    static async request(endpoint, options = {}) {
        const url = `${API_CONFIG.baseURL}${endpoint}`;
        const token = this.getAuthToken();
        const defaultOptions = {
            headers: {
                'Content-Type': 'application/json',
                ...(token && { 'Authorization': `Bearer ${token}` })
            }
        };
        const finalOptions = {
            ...defaultOptions,
            ...options,
            headers: {
                ...defaultOptions.headers,
                ...options.headers
            }
        };
        try {
            const response = await fetch(url, finalOptions);
            const data = await response.json();
            if (!response.ok) {
                throw new Error(data.message || 'Senha incorreta, tente novamente');
            }
            return data;
        } catch (error) {
            console.error('API Error:', error);
            throw error;
        }
    }

    // Authentication
    static async login(credentials) {
        return this.request('/auth/login', {
            method: 'POST',
            body: JSON.stringify({
                email: credentials.email,
                password: credentials.senha
            })
        });
    }

    // Busca os dados do usuário autenticado (UserController em /api/v1)
    static async getUser() {
        return this.request('/api/v1', { method: 'GET' });
    }

    // Beneficiários
    static async fetchBeneficiarios(filters = {}) {
        const params = new URLSearchParams(filters);
        return this.request(`/beneficiarios?${params}`);
    }

    static async createBeneficiario(data) {
        return this.request('/beneficiarios', {
            method: 'POST',
            body: JSON.stringify(data)
        });
    }

    static async approveBeneficiario(id) {
        return this.request(`/beneficiarios/${id}/approve`, {
            method: 'PUT'
        });
    }

    static async rejectBeneficiario(id) {
        return this.request(`/beneficiarios/${id}/reject`, {
            method: 'PUT'
        });
    }

    static async getBeneficiario(id) {
        return this.request(`/beneficiarios/${id}`);
    }

    static async getBeneficiarioCartao(id) {
        return this.request(`/beneficiarios/${id}/cartao`);
    }

    // Doações
    static async fetchDoacoes(filters = {}) {
        const params = new URLSearchParams(filters);
        return this.request(`/doacoes?${params}`);
    }

    static async createDoacao(data) {
        return this.request('/doacoes', {
            method: 'POST',
            body: JSON.stringify(data)
        });
    }

    // Itens
    static async fetchItens(filters = {}) {
        const params = new URLSearchParams(filters);
        return this.request(`/itens?${params}`);
    }

    static async classifyItem(id, data) {
        return this.request(`/itens/${id}/classify`, {
            method: 'PUT',
            body: JSON.stringify(data)
        });
    }

    // Relatórios
    static async getRelatorios(filters = {}) {
        const params = new URLSearchParams(filters);
        return this.request(`/relatorios/doacoes?${params}`);
    }

    static getRelatorioPdfUrl(filters = {}) {
        const params = new URLSearchParams(filters);
        return `${API_CONFIG.baseURL}/relatorios/doacoes/pdf?${params}`;
    }

    static getRelatorioExcelUrl(filters = {}) {
        const params = new URLSearchParams(filters);
        return `${API_CONFIG.baseURL}/relatorios/doacoes/excel?${params}`;
    }
}

// Expondo a API no escopo global para uso sem modules
window.API = API;