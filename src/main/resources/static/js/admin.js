// Chuyển đổi giữa các tab
document.querySelectorAll('.sidebar-nav a').forEach(link => {
    link.addEventListener('click', function(e) {
        e.preventDefault();
        document.querySelectorAll('.sidebar-nav a').forEach(a => a.classList.remove('active'));
        this.classList.add('active');
        const target = this.getAttribute('href').substring(1);
        document.querySelectorAll('.overview-container, .table-container').forEach(section => {
            section.style.display = section.id === target ? 'block' : 'none';
        });
    });
});

// Ẩn tất cả section trừ section đầu tiên
document.querySelectorAll('.overview-container, .table-container').forEach((section, index) => {
    section.style.display = index === 0 ? 'block' : 'none';
});

// Biểu đồ doanh thu
const revenueChart = new Chart(document.getElementById('revenueChart'), {
    type: 'line',
    data: {
        labels: ['Tháng 1', 'Tháng 2'],
        datasets: [{
            label: 'Doanh thu (tỷ VNĐ)',
            data: [16.498, 8.999],
            borderColor: 'rgba(197, 164, 126, 1)',
            backgroundColor: 'rgba(197, 164, 126, 0.2)',
            fill: true,
            tension: 0.4
        }]
    },
    options: {
        responsive: true,
        scales: {
            y: {
                beginAtZero: true
            }
        }
    }
});

// Biểu đồ khách hàng
const customerChart = new Chart(document.getElementById('customerChart'), {
    type: 'bar',
    data: {
        labels: ['Tháng 1', 'Tháng 2'],
        datasets: [{
            label: 'Số lượng khách hàng',
            data: [8, 10],
            backgroundColor: 'rgba(197, 164, 126, 0.8)',
            borderColor: 'rgba(197, 164, 126, 1)',
            borderWidth: 1
        }]
    },
    options: {
        responsive: true,
        scales: {
            y: {
                beginAtZero: true
            }
        }
    }
});

// Xem trước hình ảnh khi upload
function previewImage(input, previewId) {
    const file = input.files[0];
    if (file) {
        const reader = new FileReader();
        reader.onload = function(e) {
            const preview = document.getElementById(previewId);
            preview.src = e.target.result;
            preview.style.display = 'block';
        };
        reader.readAsDataURL(file);
    }
}

// Thêm Sản Phẩm
function addProduct() {
    const name = document.getElementById('productName').value;
    const price = document.getElementById('productPrice').value;
    const imageInput = document.getElementById('productImage');
    const file = imageInput.files[0];

    if (name && price && file) {
        const reader = new FileReader();
        reader.onload = function(e) {
            const imageData = e.target.result;
            const tbody = document.getElementById('productTable');
            const id = '00' + (tbody.children.length + 1);
            const row = `
                <tr>
                    <td>${id}</td>
                    <td>${name}</td>
                    <td>${price}</td>
                    <td><img src="${imageData}" alt="${name}"></td>
                    <td>Có sẵn</td>
                    <td>
                        <button class="btn-action btn-edit" onclick="editProduct(this)">Sửa</button>
                        <button class="btn-action btn-delete" onclick="deleteProduct(this)">Xóa</button>
                    </td>
                </tr>
            `;
            tbody.insertAdjacentHTML('beforeend', row);
            bootstrap.Modal.getInstance(document.getElementById('addProductModal')).hide();
            document.getElementById('addProductForm').reset();
            document.getElementById('addImagePreview').style.display = 'none';
        };
        reader.readAsDataURL(file);
    }
}

// Sửa Sản Phẩm
function editProduct(button) {
    const row = button.closest('tr');
    const id = row.cells[0].innerText;
    const name = row.cells[1].innerText;
    const price = row.cells[2].innerText;
    const image = row.cells[3].querySelector('img').src;

    document.getElementById('editProductId').value = id;
    document.getElementById('editProductName').value = name;
    document.getElementById('editProductPrice').value = price;
    document.getElementById('editProductImage').value = '';
    document.getElementById('editImagePreview').src = image;

    new bootstrap.Modal(document.getElementById('editProductModal')).show();
}

function saveProduct() {
    const id = document.getElementById('editProductId').value;
    const name = document.getElementById('editProductName').value;
    const price = document.getElementById('editProductPrice').value;
    const imageInput = document.getElementById('editProductImage');
    const file = imageInput.files[0];
    const currentImage = document.getElementById('editImagePreview').src;

    const rows = document.getElementById('productTable').rows;
    if (file) {
        const reader = new FileReader();
        reader.onload = function(e) {
            const imageData = e.target.result;
            for (let row of rows) {
                if (row.cells[0].innerText === id) {
                    row.cells[1].innerText = name;
                    row.cells[2].innerText = price;
                    row.cells[3].innerHTML = `<img src="${imageData}" alt="${name}">`;
                    row.cells[4].innerText = 'Có sẵn';
                    break;
                }
            }
            bootstrap.Modal.getInstance(document.getElementById('editProductModal')).hide();
        };
        reader.readAsDataURL(file);
    } else {
        for (let row of rows) {
            if (row.cells[0].innerText === id) {
                row.cells[1].innerText = name;
                row.cells[2].innerText = price;
                row.cells[3].innerHTML = `<img src="${currentImage}" alt="${name}">`;
                row.cells[4].innerText = 'Có sẵn';
                break;
            }
        }
        bootstrap.Modal.getInstance(document.getElementById('editProductModal')).hide();
    }
}

// Xóa Sản Phẩm
function deleteProduct(button) {
    if (confirm('Bạn có chắc muốn xóa Sản Phẩm này?')) {
        button.closest('tr').remove();
    }
}

// Tìm kiếm trong bảng
function searchTable(tableId, query) {
    const table = document.getElementById(tableId);
    const rows = table.getElementsByTagName('tr');
    query = query.toLowerCase();

    for (let i = 0; i < rows.length; i++) {
        const cells = rows[i].getElementsByTagName('td');
        let match = false;
        for (let j = 0; j < cells.length; j++) {
            if (cells[j] && cells[j].innerText.toLowerCase().includes(query)) {
                match = true;
                break;
            }
        }
        rows[i].style.display = match ? '' : 'none';
    }
}