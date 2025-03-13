// Funcție pentru a afișa secțiunea selectată
function showSection(sectionId) {
    const sections = document.querySelectorAll('.content-section');
    sections.forEach(section => {
        section.style.display = (section.id === sectionId) ? 'block' : 'none';
    });
}

// Funcție pentru a încărca stocul de piese din API
function loadStock() {
    fetch('/api/stock')
        .then(response => response.json())
        .then(data => {
            const tbody = document.querySelector('#stockTable tbody');
            tbody.innerHTML = '';
            data.forEach(entry => {
                const tr = document.createElement('tr');
                tr.innerHTML = `
                    <td>${entry.partName}</td>
                    <td>${entry.carModel}</td>
                    <td>${entry.quantity}</td>
                    <td>${entry.price} lei</td>
                    <td>${entry.euro}</td>
                    <td>${entry.code}</td>
                    <td><button class="btn btn-danger btn-sm" onclick="sellPiece(${entry.id})">Vinde</button></td>
                `;
                tbody.appendChild(tr);
            });
        })
        .catch(err => console.error('Eroare la încărcarea stocului:', err));
}

// Funcție de exemplu pentru vânzarea unei piese
function sellPiece(id) {
    // Apelăm un endpoint API pentru a vinde o piesă (acest endpoint trebuie definit în backend)
    fetch(`/api/stock/sell/${id}`, { method: 'PUT' })
        .then(response => response.json())
        .then(data => {
            console.log(data);
            loadStock();
        })
        .catch(err => console.error('Eroare la vânzare:', err));
}

// Funcție pentru a încărca modelele de mașini din API
function loadModels() {
    fetch('/api/carmodels')
        .then(response => response.json())
        .then(data => {
            const modelList = document.getElementById('modelList');
            modelList.innerHTML = '';
            data.forEach(model => {
                const li = document.createElement('li');
                li.className = 'list-group-item d-flex justify-content-between align-items-center';
                li.textContent = model.modelName;
                const deleteBtn = document.createElement('button');
                deleteBtn.className = 'btn btn-danger btn-sm';
                deleteBtn.textContent = 'Șterge';
                deleteBtn.onclick = () => deleteModel(model.id);
                li.appendChild(deleteBtn);
                modelList.appendChild(li);
            });
        })
        .catch(err => console.error('Eroare la încărcarea modelelor:', err));
}

function deleteModel(modelId) {
    fetch(`/api/carmodels/${modelId}`, { method: 'DELETE' })
        .then(response => {
            if (response.ok) {
                loadModels();
            }
        })
        .catch(err => console.error('Eroare la ștergerea modelului:', err));
}

// Eveniment pentru formularul de adăugare model
document.getElementById('modelForm').addEventListener('submit', function(e) {
    e.preventDefault();
    const modelName = document.getElementById('modelName').value.trim();
    if (modelName === '') return;
    fetch('/api/carmodels', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ modelName: modelName })
    })
    .then(response => response.json())
    .then(data => {
        console.log(data);
        loadModels();
        document.getElementById('modelName').value = '';
    })
    .catch(err => console.error('Eroare la adăugarea modelului:', err));
});

// Inițial, se încarcă stocul și modelele
document.addEventListener('DOMContentLoaded', function() {
    loadStock();
    loadModels();
});
