package com.example.selling_cars.service;

import com.example.selling_cars.dto.OrderDetailDTO;
import com.example.selling_cars.entity.OrderDetails;
import com.example.selling_cars.entity.Orders;
import com.example.selling_cars.entity.ProductOptions;
import com.example.selling_cars.entity.Products;
import com.example.selling_cars.repository.OrderDetailsRepository;
import com.example.selling_cars.repository.OrdersRepository;
import com.example.selling_cars.repository.ProductOptionsRepository;
import com.example.selling_cars.repository.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderDetailsService {

    @Autowired
    private OrderDetailsRepository orderDetailsRepository;

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private ProductsRepository productsRepository;

    @Autowired
    private ProductOptionsRepository productOptionsRepository;

    // Lấy tất cả chi tiết đơn hàng
    public List<OrderDetailDTO> getAllOrderDetails() {
        return orderDetailsRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    // Lấy chi tiết đơn hàng theo ID
    public Optional<OrderDetailDTO> getOrderDetailById(Integer id) {
        return orderDetailsRepository.findById(id).map(this::convertToDTO);
    }

    // Lấy chi tiết đơn hàng theo đơn hàng
    public List<OrderDetailDTO> getOrderDetailsByOrder(Integer orderId) {
        return orderDetailsRepository.findByOrderOrderId(orderId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Lấy chi tiết đơn hàng theo sản phẩm
    public List<OrderDetailDTO> getOrderDetailsByProduct(Integer productId) {
        return orderDetailsRepository.findByProductProductId(productId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Đếm số lượng sản phẩm đã bán
    public Long getSoldQuantityByProduct(Integer productId) {
        Long count = orderDetailsRepository.countSoldQuantityByProduct(productId);
        return count != null ? count : 0L;
    }

    // Thêm chi tiết đơn hàng mới
    public OrderDetailDTO createOrderDetail(OrderDetailDTO orderDetailDTO) {
        Orders order = ordersRepository.findById(orderDetailDTO.getOrderId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng!"));

        Products product = productsRepository.findById(orderDetailDTO.getProductId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm!"));

        ProductOptions exteriorOption = orderDetailDTO.getExteriorOptionId() != null
                ? productOptionsRepository.findById(orderDetailDTO.getExteriorOptionId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy tùy chọn ngoại thất!"))
                : null;

        ProductOptions interiorOption = orderDetailDTO.getInteriorOptionId() != null
                ? productOptionsRepository.findById(orderDetailDTO.getInteriorOptionId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy tùy chọn nội thất!"))
                : null;

        OrderDetails orderDetail = new OrderDetails();
        orderDetail.setOrder(order);
        orderDetail.setProduct(product);
        orderDetail.setQuantity(orderDetailDTO.getQuantity());
        orderDetail.setUnitPrice(orderDetailDTO.getUnitPrice());
        orderDetail.setExteriorOption(exteriorOption);
        orderDetail.setInteriorOption(interiorOption);

        OrderDetails savedOrderDetail = orderDetailsRepository.save(orderDetail);
        return convertToDTO(savedOrderDetail);
    }

    // Cập nhật chi tiết đơn hàng
    public OrderDetailDTO updateOrderDetail(Integer id, OrderDetailDTO orderDetailDTO) {
        OrderDetails orderDetail = orderDetailsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy chi tiết đơn hàng!"));

        Orders order = ordersRepository.findById(orderDetailDTO.getOrderId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng!"));

        Products product = productsRepository.findById(orderDetailDTO.getProductId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm!"));

        ProductOptions exteriorOption = orderDetailDTO.getExteriorOptionId() != null
                ? productOptionsRepository.findById(orderDetailDTO.getExteriorOptionId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy tùy chọn ngoại thất!"))
                : null;

        ProductOptions interiorOption = orderDetailDTO.getInteriorOptionId() != null
                ? productOptionsRepository.findById(orderDetailDTO.getInteriorOptionId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy tùy chọn nội thất!"))
                : null;

        orderDetail.setOrder(order);
        orderDetail.setProduct(product);
        orderDetail.setQuantity(orderDetailDTO.getQuantity());
        orderDetail.setUnitPrice(orderDetailDTO.getUnitPrice());
        orderDetail.setExteriorOption(exteriorOption);
        orderDetail.setInteriorOption(interiorOption);

        OrderDetails updatedOrderDetail = orderDetailsRepository.save(orderDetail);
        return convertToDTO(updatedOrderDetail);
    }

    // Xóa chi tiết đơn hàng
    public void deleteOrderDetail(Integer id) {
        OrderDetails orderDetail = orderDetailsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy chi tiết đơn hàng!"));
        orderDetailsRepository.delete(orderDetail);
    }

    // Chuyển từ Entity sang DTO
    private OrderDetailDTO convertToDTO(OrderDetails orderDetail) {
        OrderDetailDTO dto = new OrderDetailDTO();
        dto.setOrderDetailId(orderDetail.getOrderDetailId());
        dto.setOrderId(orderDetail.getOrder().getOrderId());
        dto.setProductId(orderDetail.getProduct().getProductId());
        dto.setProductName(orderDetail.getProduct().getProductName());
        dto.setQuantity(orderDetail.getQuantity());
        dto.setUnitPrice(orderDetail.getUnitPrice());
        dto.setExteriorOptionId(orderDetail.getExteriorOption() != null ? orderDetail.getExteriorOption().getOptionId() : null);
        dto.setExteriorOptionName(orderDetail.getExteriorOption() != null ? orderDetail.getExteriorOption().getOptionName() : null);
        dto.setInteriorOptionId(orderDetail.getInteriorOption() != null ? orderDetail.getInteriorOption().getOptionId() : null);
        dto.setInteriorOptionName(orderDetail.getInteriorOption() != null ? orderDetail.getInteriorOption().getOptionName() : null);
        return dto;
    }
}