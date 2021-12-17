window.addEventListener('load', async() => {
    //console.log('EXECUTED');

    let res = await fetch('http://localhost:8080/checkloginstatus', {
        method: 'GET',
        credentials: 'include'
    });

    if(res.status === 200) {
        let userObj = await res.json();

        if(userObj.userRole === 'employee') {
            window.location.href = 'employee-homepage.html';
        } else if(userObj.userRole === 'manager') {
            window.location.href = 'manager-homepage.html';
        }
    }
});

let loginButton = document.querySelector('#login-btn');

loginButton.addEventListener('click', loginButtonClick);

function loginButtonClick() {
    //console.log('Click');
    login();
}

async function login() {
    let usernameInput = document.querySelector('#username');
    let passwordInput = document.querySelector('#password');
    
    try {
        let res = await fetch('http://localhost:8080/login', {
            method: "POST",
            credentials: 'include',
            body: JSON.stringify({
                username: usernameInput.value,
                password: passwordInput.value
            })
        });

        let data = await res.json();

        if(res.status === 400) {
            let loginErrorMessage = document.createElement('p');
            let loginDiv = document.querySelector('#login-info');

            loginErrorMessage.innerHTML = data.message;
            loginErrorMessage.style.color = 'red';
            loginDiv.appendChild(loginErrorMessage);
        }

        if(res.status === 200) {
            console.log(data.userRole);
            if(data.userRole === 'employee') {
                window.location.href = 'employee-homepage.html';
            } else if(data.userRole === 'manager') {
                window.location.href = 'manager-homepage.html';
            }
        }

        //console.log(userObject);
    } catch(err){
        console.log(err);
    }
}