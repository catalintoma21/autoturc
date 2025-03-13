document.addEventListener('DOMContentLoaded', () => {
  const apiUrl = '/api';

  // Funcție pentru popularea unui dropdown cu modelele existente
  function populateSelect(selectId, models) {
    const select = document.getElementById(selectId);
    if (select) {
      select.innerHTML = '';
      models.forEach(model => {
        const option = document.createElement('option');
        option.value = model.modelName;
        option.textContent = model.modelName;
        select.appendChild(option);
      });
    }
  }

  // Funcția loadModels preia toate modelele și actualizează dropdown-urile din secțiuni
  function loadModels(callback) {
    fetch(`${apiUrl}/models/all`)
      .then(response => response.json())
      .then(models => {
        // Actualizează dropdown-urile pentru secțiunile de configurare, adăugare mașină, piese manual
        populateSelect('selectConfigModel', models);
        populateSelect('selectCarForStock', models);
        populateSelect('selectManualModel', models);
        // Actualizează dropdown-ul din secțiunea de căutare pentru model
        const selectSearchModel = document.getElementById('selectSearchModel');
        if (selectSearchModel) {
          selectSearchModel.innerHTML = '<option value="Toate">Toate</option>';
          models.forEach(model => {
            const option = document.createElement('option');
            option.value = model.modelName;
            option.textContent = model.modelName;
            selectSearchModel.appendChild(option);
          });
        }
        if (callback) callback(models);
      })
      .catch(error => console.error('Error loading models:', error));
  }

  // Inițial se încarcă modelele
  loadModels();

  // -------------------- Secțiunea "Adaugă modele" --------------------
  function loadModelList() {
    fetch(`${apiUrl}/models/all`)
      .then(response => response.json())
      .then(models => {
        const modelList = document.getElementById('modelList');
        modelList.innerHTML = '';
        models.forEach(model => {
          const li = document.createElement('li');
          li.textContent = model.modelName;
          modelList.appendChild(li);
        });
      })
      .catch(error => console.error('Error loading model list:', error));
  }

  document.getElementById('addModelBtn').addEventListener('click', () => {
    const modelName = document.getElementById('modelName').value;
    fetch(`${apiUrl}/models/add?modelName=${encodeURIComponent(modelName)}`, {
      method: 'POST'
    })
      .then(response => response.json())
      .then(data => {
        alert("Model adăugat!");
        document.getElementById('modelName').value = '';
        // Actualizează toate dropdown-urile cu noile modele
        loadModels();
      })
      .catch(error => console.error('Error adding model:', error));
  });

  document.getElementById('openModelBtn').addEventListener('click', () => {
    loadModelList();
    document.getElementById('modelModal').style.display = 'block';
  });

  document.getElementById('closeModelModal').addEventListener('click', () => {
    document.getElementById('modelModal').style.display = 'none';
  });

  // -------------------- Secțiunea "Setează piese pentru model" --------------------
  document.getElementById('selectConfigModel').addEventListener('change', (e) => {
    const modelName = e.target.value;
    loadConfigParts(modelName);
  });

  function loadConfigParts(modelName) {
    fetch(`${apiUrl}/parts/default?carModel=${encodeURIComponent(modelName)}`)
      .then(response => response.json())
      .then(parts => {
        const ul = document.getElementById('configPartList');
        ul.innerHTML = '';
        if (parts.length === 0) {
          ul.innerHTML = '<li>Nu există piese configurate pentru acest model.</li>';
        } else {
          parts.forEach(part => {
            const li = document.createElement('li');
            li.innerHTML = `
              ${part.name} - ${part.price} lei, Euro: ${part.euro}, Cod: ${part.code}
              <button class="deletePartBtn" data-id="${part.id}">Șterge</button>
            `;
            ul.appendChild(li);
          });
          document.querySelectorAll('.deletePartBtn').forEach(button => {
            button.addEventListener('click', () => {
              const partId = button.getAttribute('data-id');
              if (confirm("Ești sigur că vrei să ștergi această piesă?")) {
                fetch(`${apiUrl}/parts/delete/${partId}`, {
                  method: 'DELETE'
                })
                  .then(response => response.text())
                  .then(msg => {
                    alert(msg);
                    loadConfigParts(document.getElementById('selectConfigModel').value);
                  })
                  .catch(error => console.error('Error deleting part:', error));
              }
            });
          });
        }
      })
      .catch(error => console.error('Error loading config parts:', error));
  }

  document.getElementById('addConfigPartBtn').addEventListener('click', () => {
    const modelName = document.getElementById('selectConfigModel').value;
    const requestData = {
      name: document.getElementById('configPartName').value,
      carModel: modelName,
      price: parseFloat(document.getElementById('configPartPrice').value),
      euro: parseInt(document.getElementById('configPartEuro').value),
      code: document.getElementById('configPartCode').value,
      standard: true
    };

    fetch(`${apiUrl}/parts/add`, {
      method: 'POST',
      headers: {'Content-Type': 'application/json'},
      body: JSON.stringify(requestData)
    })
      .then(response => response.json())
      .then(data => {
        alert("Piesă configurată adăugată!");
        loadConfigParts(modelName);
      })
      .catch(error => console.error('Error adding config part:', error));
  });

  // -------------------- Secțiunea "Adaugă mașină" --------------------
  document.getElementById('addCarBtn').addEventListener('click', () => {
    const carModel = document.getElementById('selectCarForStock').value;
    fetch(`${apiUrl}/inventory/addCar`, {
      method: 'POST',
      headers: {'Content-Type': 'application/json'},
      body: JSON.stringify({ carModel: carModel })
    })
      .then(response => response.text())
      .then(msg => {
        alert(msg);
        updateStockSummary();
      })
      .catch(error => console.error('Error adding car to stock:', error));
  });

  // -------------------- Secțiunea "Adaugă piese manual" --------------------
  document.getElementById('addManualPartBtn').addEventListener('click', () => {
    const requestData = {
      partName: document.getElementById('manualPartName').value,
      carModel: document.getElementById('selectManualModel').value,
      price: parseFloat(document.getElementById('manualPartPrice').value),
      euro: parseInt(document.getElementById('manualPartEuro').value),
      code: document.getElementById('manualPartCode').value,
      quantity: parseInt(document.getElementById('manualPartQuantity').value)
    };

    fetch(`${apiUrl}/inventory/addManual`, {
      method: 'POST',
      headers: {'Content-Type': 'application/json'},
      body: JSON.stringify(requestData)
    })
      .then(response => response.text())
      .then(msg => {
        alert(msg);
        document.getElementById('manualPartName').value = '';
        document.getElementById('manualPartPrice').value = '';
        document.getElementById('manualPartEuro').value = '';
        document.getElementById('manualPartCode').value = '';
        document.getElementById('manualPartQuantity').value = '';
        updateStockSummary();
      })
      .catch(error => console.error('Error adding manual part:', error));
  });

  // -------------------- Secțiunea "Stoc" --------------------
  function performSearch() {
    const partName = document.getElementById('searchPartName').value;
    const carModel = document.getElementById('selectSearchModel').value || "Toate";
    const euro = document.getElementById('selectSearchEuro').value || "Toate";
    const code = document.getElementById('searchCode').value || "Toate";

    fetch(`${apiUrl}/inventory/search?partName=${encodeURIComponent(partName)}&carModel=${encodeURIComponent(carModel)}&euro=${encodeURIComponent(euro)}&code=${encodeURIComponent(code)}`)
      .then(response => response.json())
      .then(data => {
        populateStockTable(data);
      })
      .catch(error => console.error('Error searching stock:', error));
  }

  // Apel automat când se schimbă dropdown-urile pentru model și euro din secțiunea de căutare
  document.getElementById('selectSearchModel').addEventListener('change', performSearch);
  document.getElementById('selectSearchEuro').addEventListener('change', performSearch);
  // Căutarea se declanșează manual pentru câmpurile text (nume și cod)
  document.getElementById('searchBtn').addEventListener('click', performSearch);

  function populateStockTable(entries) {
    const tbody = document.getElementById('stockTable').querySelector('tbody');
    tbody.innerHTML = '';
    let totalValue = 0;
    let totalPieces = 0;
    if (entries.length === 0) {
      const tr = document.createElement('tr');
      tr.innerHTML = `<td colspan="7" style="text-align:center;">Nu există piese în stoc</td>`;
      tbody.appendChild(tr);
    } else {
      entries.forEach(entry => {
        const tr = document.createElement('tr');
        tr.innerHTML = `
          <td>${entry.partName}</td>
          <td>${entry.carModel}</td>
          <td>${entry.euro}</td>
          <td>${entry.code}</td>
          <td>${entry.quantity}</td>
          <td>${entry.price}</td>
          <td><button class="sellBtn" data-part="${entry.partName}" data-model="${entry.carModel}" data-price="${entry.price}" data-euro="${entry.euro}" data-code="${entry.code}">Vinde</button></td>
        `;
        tbody.appendChild(tr);
        totalValue += entry.price * entry.quantity;
        totalPieces += entry.quantity;
      });
    }
    // Actualizează sumarul din sidebar (acesta este doar pentru filtrarea curentă)
    document.getElementById('totalValue').textContent = "Valoare totală piese (LEI): " + totalValue;
    document.getElementById('totalPieces').textContent = "Piese totale: " + totalPieces;
    attachSellListeners(); // Asigură-te că atașezi listener-ele pentru butonul "Vinde"
  }

  function attachSellListeners() {
    document.querySelectorAll('.sellBtn').forEach(button => {
      button.addEventListener('click', () => {
        const sellData = {
          partName: button.getAttribute('data-part'),
          carModel: button.getAttribute('data-model'),
          price: parseFloat(button.getAttribute('data-price')),
          euro: parseInt(button.getAttribute('data-euro')),
          code: button.getAttribute('data-code')
        };

        fetch(`${apiUrl}/inventory/sell`, {
          method: 'POST',
          headers: {'Content-Type': 'application/json'},
          body: JSON.stringify(sellData)
        })
        .then(response => response.text())
        .then(msg => {
          alert(msg);
          performSearch();
          updateStockSummary();
        })
        .catch(error => console.error('Error selling part:', error));
      });
    });
  }


  // Funcție pentru actualizarea sumarului stocului (calcul pentru întreg stocul)
  function updateStockSummary() {
    fetch(`${apiUrl}/inventory/search?partName=&carModel=Toate&euro=Toate&code=Toate`)
      .then(response => response.json())
      .then(entries => {
        let totalValue = 0;
        let totalPieces = 0;
        entries.forEach(entry => {
          totalValue += entry.price * entry.quantity;
          totalPieces += entry.quantity;
        });
        document.getElementById('totalValue').textContent = "Valoare totală piese (LEI): " + totalValue;
        document.getElementById('totalPieces').textContent = "Piese totale: " + totalPieces;
      })
      .catch(error => console.error('Error updating stock summary:', error));
  }

  // Actualizează sumarul la pornire
  updateStockSummary();

  // -------------------- Secțiunea "Stoc" - Dropdown-uri de căutare --------------------
  function loadSearchDropdowns(models) {
    const selectSearchModel = document.getElementById('selectSearchModel');
    if (selectSearchModel) {
      selectSearchModel.innerHTML = '<option value="Toate">Toate</option>';
      models.forEach(model => {
        const option = document.createElement('option');
        option.value = model.modelName;
        option.textContent = model.modelName;
        selectSearchModel.appendChild(option);
      });
    }
    // Dropdown-ul pentru euro rămâne fix, cu opțiuni predefinite.
  }

  // Încarcă și actualizează dropdown-urile pentru căutare după ce se încarcă modelele
  loadModels(loadSearchDropdowns);

  // -------------------- Secțiunea "Stoc" - Vânzarea unei piese --------------------
  // Atașează event listener pentru butoanele de vânzare atunci când se populează tabela
  function attachSellListeners() {
    document.querySelectorAll('.sellBtn').forEach(button => {
      button.addEventListener('click', () => {
        const sellData = {
          partName: button.getAttribute('data-part'),
          carModel: button.getAttribute('data-model'),
          price: parseFloat(button.getAttribute('data-price')),
          euro: parseInt(button.getAttribute('data-euro')),
          code: button.getAttribute('data-code')
        };

        fetch(`${apiUrl}/inventory/sell`, {
          method: 'POST',
          headers: {'Content-Type': 'application/json'},
          body: JSON.stringify(sellData)
        })
          .then(response => response.text())
          .then(msg => {
            alert(msg);
            performSearch();
            updateStockSummary();
          })
          .catch(error => console.error('Error selling part:', error));
      });
    });
  }

  // Modificăm populateStockTable pentru a atașa și event listener-ul pentru vânzare
  function populateStockTableWithSell(entries) {
    populateStockTable(entries);
    attachSellListeners();
  }

  // Actualizează evenimentul de căutare astfel încât să folosească noua funcție
  document.getElementById('searchBtn').addEventListener('click', () => {
    performSearch();
  });

  // -------------------- Navigare între secțiuni --------------------
  const sidebarLinks = document.querySelectorAll('.sidebar ul li a');
  sidebarLinks.forEach(link => {
    link.addEventListener('click', (e) => {
      e.preventDefault();
      sidebarLinks.forEach(l => l.classList.remove('active'));
      link.classList.add('active');
      const targetId = link.getAttribute('data-target');
      document.querySelectorAll('.section').forEach(section => {
        section.style.display = section.id === targetId ? 'block' : 'none';
      });
    });
  });
});
