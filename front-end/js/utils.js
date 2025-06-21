

// Utility Functions
        function showError(message, elementId = 'errorMessage') {
            const errorElement = document.getElementById(elementId);
            if (errorElement) {
                errorElement.textContent = message;
                errorElement.style.display = 'block';
            }
        }

        function hideError(elementId = 'errorMessage') {
            const errorElement = document.getElementById(elementId);
            if (errorElement) {
                errorElement.style.display = 'none';
            }
        }

        function showSuccess(message, elementId = 'successMessage') {
            const successElement = document.getElementById(elementId);
            if (successElement) {
                successElement.textContent = message;
                successElement.style.display = 'block';
            }
        }

        function redirectToLogin() {
            window.location.href = 'login.html';
        }

        function redirectToDashboard() {
            window.location.href = 'dashboard.html';
        }

        async function checkAuth() {
            const token = API.getAuthToken();
            if (!token) {
                redirectToLogin();
                return null;
            }

            try {
                const user = await API.getUser();
                return user;
            } catch (error) {
                API.clearAuthToken();
                redirectToLogin();
                return null;
            }
        }

        function logout() {
            API.clearAuthToken();
            redirectToLogin();
        }

        // Modal Functions
        function showModal(modalId) {
            const modal = document.getElementById(modalId);
            if (modal) {
                modal.style.display = 'block';
            }
        }

        function hideModal(modalId) {
            const modal = document.getElementById(modalId);
            if (modal) {
                modal.style.display = 'none';
            }
        }

        // Format functions
        function formatDate(dateString) {
            return new Date(dateString).toLocaleDateString('pt-BR');
        }

        function formatCurrency(value) {
            return new Intl.NumberFormat('pt-BR', {
                style: 'currency',
                currency: 'BRL'
            }).format(value);
        }

        function formatCPF(cpf) {
            return cpf.replace(/(\d{3})(\d{3})(\d{3})(\d{2})/, '$1.$2.$3-$4');
        }
