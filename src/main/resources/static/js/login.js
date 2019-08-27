const username = document.querySelector('.username');
const password = document.querySelector('.password');
const btnLoading = document.querySelector('.btn-loading');
const submitBtn = document.querySelector('.submit-btn');
const usernameError = document.querySelector('.username-error');
const passwordError = document.querySelector('.password-error');
window.onload = function () {
    if (usernameError.innerHTML !== '') {
        username.className = 'username username-input-error';
        usernameError.className = 'username-error opacity1';
    }
};

function handleSubmit(e) {
    if (username.value === '') {
        username.className = 'username username-input-error';
        usernameError.className = 'username-error opacity1';
        usernameError.innerHTML = '请输入用户名';
        return false;
    }
    if (password.value === '') {
        password.className = 'password password-input-error';
        passwordError.className = 'password-error opacity1';
        usernameError.innerHTML = '请输入密码';
        return false
    }
    btnLoading.style.display = 'block';
    submitBtn.style.backgroundColor = '#b3b3b3';
    submitBtn.style.cursor = 'default';
    return true;
}

function usernameInputFocus() {
    if (username.className === 'username username-input-error') {
        username.className = 'username username-input-default';
        usernameError.className = 'username-error opacity0';
    }
}

function passwordInputFocus() {
    if (password.className === 'password password-input-error') {
        password.className = 'password password-input-default';
        passwordError.className = 'password-error opacity0';
    }
}
