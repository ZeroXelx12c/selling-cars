// Hàm lọc và sắp xếp xe
function filterAndSortCars() {
    const filterBrand = document.getElementById('filterBrand').value;
    const filterYear = document.getElementById('filterYear').value;
    const filterPrice = document.getElementById('filterPrice').value;
    const sortOrder = document.getElementById('sortOrder').value;

    const cars = Array.from(document.getElementsByClassName('car-item'));

    // Lọc xe
    cars.forEach(car => {
        const brand = car.getAttribute('data-brand');
        const year = car.getAttribute('data-year');
        const price = parseInt(car.getAttribute('data-price'));

        const brandMatch = filterBrand === 'all' || brand === filterBrand;
        
        // Xử lý lọc theo năm
        let yearMatch = true;
        if (filterYear !== 'all') {
            if (filterYear === 'older') {
                yearMatch = parseInt(year) < 2021;
            } else {
                yearMatch = year === filterYear;
            }
        }

        let priceMatch = true;
        if (filterPrice === 'under5') priceMatch = price < 5000000000;
        else if (filterPrice === '5to10') priceMatch = price >= 5000000000 && price <= 10000000000;
        else if (filterPrice === 'over10') priceMatch = price > 10000000000;

        car.style.display = (brandMatch && yearMatch && priceMatch) ? '' : 'none';
    });

    // Sắp xếp xe
    const visibleCars = cars.filter(car => car.style.display !== 'none');
    visibleCars.sort((a, b) => {
        const priceA = parseInt(a.getAttribute('data-price'));
        const priceB = parseInt(b.getAttribute('data-price'));
        if (sortOrder === 'priceAsc') return priceA - priceB;
        if (sortOrder === 'priceDesc') return priceB - priceA;
        return 0; // Giữ nguyên thứ tự ban đầu cho "Mới nhất"
    });

    // Cập nhật lại vị trí trong DOM
    const carList = document.getElementById('carList');
    carList.innerHTML = '';
    visibleCars.forEach((car, index) => {
        car.setAttribute('data-aos', 'fade-up');
        car.setAttribute('data-aos-delay', index * 200);
        carList.appendChild(car);
    });

    // Khởi tạo lại AOS
    AOS.refresh();
}

// Gọi hàm khi tải trang
filterAndSortCars();