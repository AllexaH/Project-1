window.addEventListener('load', async () => {

    let res = await fetch('http://localhost:8080/checkloginstatus', {
        credentials: 'include',
        method: 'GET'
    });

    if (res.status === 200) {
        let userObj = await res.json();

        if (userObj.userRole === 'manager') {
            window.location.href = 'manager-homepage.html';
        }
    } else if (res.status === 401) {
        window.location.href = 'index.html';
    }

    populateTableWithReimbursements();
});

let logoutBtn = document.querySelector('#logout');

logoutBtn.addEventListener('click', async () => {

    let res = await fetch('http://localhost:8080/logout', {
        method: 'POST',
        credentials: 'include'
    });

    if (res.status === 200) {
        window.location.href = 'index.html';
    }

})


async function populateTableWithReimbursements() {
    let res = await fetch('http://localhost:8080/reimbursements', {
        credentials: 'include',
        method: 'GET'
    });

    let tbodyElement = document.querySelector("#reimbursement-table tbody");
    tbodyElement.innerHTML = '';
    let reimbursementArray =  await res.json();

    for (let i = 0; i < reimbursementArray.length; i++) {
        let reimbursement = reimbursementArray[i];

        let tr = document.createElement('tr');

        let td1 = document.createElement('td');
        td1.innerHTML = reimbursement.id;

        let td2 = document.createElement('td');
        td2.innerHTML = reimbursement.amount;

        let td3 = document.createElement('td');
        td3.innerHTML = reimbursement.submittedTimeStamp;


        let td4 = document.createElement('td');
        if (reimbursement.managerID != undefined) {
            td4.innerHTML = reimbursement.resolvedTimeStamp;
        } else {
            td4.innerHTML = '-';
        }

        let td5 = document.createElement('td');
        td5.innerHTML = reimbursement.status;

        let td6 = document.createElement('td');
        td6.innerHTML = reimbursement.type;

        let td7 = document.createElement('td');
        td7.innerHTML = reimbursement.description;

        let td8 = document.createElement('td');
        let viewImageButton = document.createElement('button');
        viewImageButton.innerHTML = 'View Image';
        td8.appendChild(viewImageButton);

        viewImageButton.addEventListener('click', () => {
            let reimbursementImageModal = document.querySelector('#receipt-image-modal');

            let modalCloseElement = reimbursementImageModal.querySelector('button');
            modalCloseElement.addEventListener('click', () => {
                reimbursementImageModal.classList.remove('is-active');
            });

            let modalContentElement = reimbursementImageModal.querySelector('.modal-content');
            modalContentElement.innerHTML = '';

            let imageElement = document.createElement('img');
            imageElement.setAttribute('src', `http://localhost:8080/reimbursements/${reimbursement.id}/image`);
            modalContentElement.appendChild(imageElement);

            reimbursementImageModal.classList.add('is-active');
        });

        let td9 = document.createElement('td');
        if (reimbursement.managerID != undefined) {
            td9.innerHTML = reimbursement.managerID;
        } else {
            td9.innerHTML = '-';
        }

        let td10 = document.createElement('td');
        td10.innerHTML = reimbursement.employeeID;

        tr.appendChild(td1);
        tr.appendChild(td2);
        tr.appendChild(td3);
        tr.appendChild(td4);
        tr.appendChild(td5);
        tr.appendChild(td6);
        tr.appendChild(td7);
        tr.appendChild(td8);
        tr.appendChild(td9);
        tr.appendChild(td10);

        tbodyElement.appendChild(tr);
    }
}

let ReimbursementSubmitButton = document.querySelector('#submit-reimbursement-btn');

ReimbursementSubmitButton.addEventListener('click', submitReimbursement);

async function submitReimbursement() {

    let amountInput = document.querySelector('#amount');
    let reimbursementTypeInput = document.querySelector('#reimbursement_type');
    console.log("Reimbursement type: " + reimbursementTypeInput.value);
    let descriptionInput = document.querySelector('#description');
    let receiptInput = document.querySelector('#receipt');

    const file = receiptInput.files[0];

    let formData = new FormData();
    formData.append('amount', amountInput.value);
    formData.append('reimbursement_type', reimbursementTypeInput.value);
    formData.append('description', descriptionInput.value);
    formData.append('receipt', file);

    let res = await fetch('http://localhost:8080/reimbursements', {
        method: 'PUT',
        credentials: 'include',
        body: formData
    });

    if (res.status === 201) { 
        populateTableWithReimbursements(); 
    } else if (res.status === 400) {
        let reimbursementForm = document.querySelector('#reimbursement-submit-form');

        let data = await res.json();

        let pTag = document.createElement('p');
        pTag.innerHTML = data.message;
        pTag.style.color = 'red';

        reimbursementForm.appendChild(pTag);
    }
}