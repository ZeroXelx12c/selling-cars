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




    // Load danh sách khóa học
    async function loadCourses() {
        try {
            const response = await fetch('/api/courses/top3');
            if (response.ok) {
                const courses = await response.json();
                const courseList = document.getElementById('courseList');
                courseList.innerHTML = courses.map((course, index) => `
                    <div class="col-md-4" data-aos="fade-up" data-aos-delay="${index * 200}">
                        <div class="course-card">
                            <div class="course-image">
                                <img src="https://images.unsplash.com/photo-1517248135467-2c7ed3ab7229" alt="Course Thumbnail">
                            </div>
                            <div class="course-details">
                                <h3 class="course-title">${course.title}</h3>
                                <div class="course-price">${course.price === 0 ? 'Miễn phí' : course.price.toLocaleString('vi-VN') + ' VND'}</div>
                                <a href="/dskhoahoc.html?id=${course.id}" class="btn btn-custom">Xem chi tiết</a>
                            </div>
                        </div>
                    </div>
                `).join('');
            } else {
                console.error('Lỗi khi gọi API /api/courses:', response.status);
            }
        } catch (error) {
            console.error('Lỗi khi load khóa học:', error);
        }
    }