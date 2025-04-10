package com.example.selling_cars.controller;

import com.example.selling_cars.dto.OrderRequest;
import com.example.selling_cars.entity.OrderDetail;
import com.example.selling_cars.entity.Product;
import com.example.selling_cars.entity.ProductOption;
import com.example.selling_cars.service.OrderService;
import com.example.selling_cars.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Collectors;

import com.example.selling_cars.entity.Order;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ProductWebController {

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderService orderService;

    @GetMapping("/product-list")
    public String getProductList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "9") int size,
            @RequestParam(defaultValue = "all") String brand,
            @RequestParam(defaultValue = "all") String year,
            @RequestParam(defaultValue = "all") String price,
            @RequestParam(defaultValue = "latest") String sort,
            @RequestParam(required = false) String keyword,
            Model model) {

        // Xử lý sắp xếp
        Sort sortOption;
        switch (sort) {
            case "priceAsc":
                sortOption = Sort.by("price").ascending();
                break;
            case "priceDesc":
                sortOption = Sort.by("price").descending();
                break;
            default:
                sortOption = Sort.by("createdAt").descending();
        }
        PageRequest pageRequest = PageRequest.of(page, size, sortOption);

        // Xử lý bộ lọc
        Integer minYear = null, maxYear = null;
        BigDecimal minPrice = null, maxPrice = null;
        String status = "in_stock"; // Chỉ hiển thị Sản Phẩm còn hàng

        if (!year.equals("all")) {
            if (year.equals("older")) {
                maxYear = 2020;
            } else {
                minYear = maxYear = Integer.parseInt(year);
            }
        }

        if (!price.equals("all")) {
            switch (price) {
                case "under5":
                    maxPrice = new BigDecimal("5000000000");
                    break;
                case "5to10":
                    minPrice = new BigDecimal("5000000000");
                    maxPrice = new BigDecimal("10000000000");
                    break;
                case "over10":
                    minPrice = new BigDecimal("10000000000");
                    break;
            }
        }

        // Lấy dữ liệu từ service
        Page<Product> productPage;
        if (keyword != null && !keyword.trim().isEmpty()) {
            productPage = productService.searchProducts(keyword, pageRequest);
        } else {
            productPage = productService.findProductsByFilters(null, minPrice, maxPrice, minYear, maxYear, status,
                    !brand.equals("all") ? brand : null, pageRequest);
        }

        // Truyền dữ liệu sang view
        model.addAttribute("products", productPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", productPage.getTotalPages());
        model.addAttribute("param", new java.util.HashMap<String, String>() {
            {
                put("brand", brand);
                put("year", year);
                put("price", price);
                put("sort", sort);
            }
        });

        return "product-list";
    }

    @GetMapping("/product-detail")
    public String getProductDetail(@RequestParam("id") Integer id, Model model) {
        Optional<Product> productOpt = productService.getProductById(id);
        if (productOpt.isEmpty()) {
            return "redirect:/product-list";
        }
        Product product = productOpt.get();
        model.addAttribute("product", product);
        return "product-detail";
    }

    @GetMapping("/payment")
    public String getPaymentPage(@RequestParam("id") Integer productId, Model model) {
        if (productId == null) {
            return "redirect:/product-list"; // Hoặc trang lỗi
        }
        Optional<Product> productOpt = productService.getProductById(productId);
        if (productOpt.isEmpty()) {
            return "redirect:/product-list";
        }
        Product product = productOpt.get();

        List<ProductOption> exteriorOptions = product.getOptions().stream()
                .filter(opt -> "Exterior".equals(opt.getOptionType()))
                .collect(Collectors.toList());
        List<ProductOption> interiorOptions = product.getOptions().stream()
                .filter(opt -> "Interior".equals(opt.getOptionType()))
                .collect(Collectors.toList());

        model.addAttribute("product", product);
        model.addAttribute("exteriorOptions", exteriorOptions);
        model.addAttribute("interiorOptions", interiorOptions);
        model.addAttribute("order", new OrderRequest());
        return "payment";
    }

    @PostMapping("/payment/process")
    public String processPayment(@ModelAttribute("order") OrderRequest orderRequest) {
        Optional<Product> productOpt = productService.getProductById(orderRequest.getProductId());
        if (productOpt.isEmpty()) {
            return "redirect:/product-list";
        }
        Product product = productOpt.get();

        Order order = new Order();
        order.setUser(null); // Cần lấy từ session/auth
        order.setOrderDate(LocalDateTime.now());
        order.setTotalAmount(product.getPrice()
                .add(getOptionPrice(orderRequest.getExteriorOptionId()))
                .add(getOptionPrice(orderRequest.getInteriorOptionId())));
        order.setDepositAmount(order.getTotalAmount()
                .multiply(BigDecimal.valueOf(orderRequest.getDepositPercent()))
                .divide(BigDecimal.valueOf(100)));
        order.setDeliveryArea(orderRequest.getDeliveryArea());
        order.setOrderStatus("pending");
        order.setNotes(orderRequest.getCustomerName() + " - " + orderRequest.getPhoneNumber() + " - "
                + orderRequest.getEmail());
        order.setDeliveryAddress(""); // Cần thêm vào OrderRequest nếu muốn
        order.setDeliveryPhone(orderRequest.getPhoneNumber());

        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrder(order);
        orderDetail.setProduct(product);
        orderDetail.setQuantity(1);
        orderDetail.setUnitPrice(product.getPrice());
        orderDetail.setExteriorOption(orderRequest.getExteriorOptionId() != null ? product.getOptions().stream()
                .filter(opt -> opt.getOptionId().equals(orderRequest.getExteriorOptionId())).findFirst().orElse(null)
                : null);
        orderDetail.setInteriorOption(orderRequest.getInteriorOptionId() != null ? product.getOptions().stream()
                .filter(opt -> opt.getOptionId().equals(orderRequest.getInteriorOptionId())).findFirst().orElse(null)
                : null);

        orderService.saveOrder(order, orderDetail);

        return "redirect:/order-confirmation";
    }

    private BigDecimal getOptionPrice(Integer optionId) {
        if (optionId == null)
            return BigDecimal.ZERO;
        Optional<ProductOption> option = productService.getProductById(optionId)
                .map(Product::getOptions)
                .flatMap(options -> options.stream().filter(opt -> opt.getOptionId().equals(optionId)).findFirst());
        return option.map(ProductOption::getAdditionalPrice).orElse(BigDecimal.ZERO);
    }
}