package lk.ijse.Model;

import lk.ijse.DB.DbConnection;
import lk.ijse.dto.ItemDto;
import lk.ijse.dto.PlaceOrderDto;
import lk.ijse.dto.tm.CartTm;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

public class PlaceOrderModel {
    private OrderModel orderModel = new OrderModel();
    private ItemModel itemModel = new ItemModel();
    private OrderDetailModel orderDetailModel = new OrderDetailModel();

    public boolean placeOrder(PlaceOrderDto placeOrderDto) throws SQLException {
        System.out.println(placeOrderDto);

        String orderId = placeOrderDto.getOrderId();
        String customerId = placeOrderDto.getCustomerId();
        LocalDate date = placeOrderDto.getDate();

        Connection connection = null;
        try {
            connection = DbConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            boolean isOrderSaved = orderModel.saveOrder(orderId, customerId, date);
            if (isOrderSaved) {
                boolean isUpdated = itemModel.updateItem(placeOrderDto.getCartTmList());
                if (isUpdated) {
                    boolean isOrderDetailSaved = orderDetailModel.saveOrderDetails(placeOrderDto.getOrderId(), placeOrderDto.getCartTmList());
                    if (isOrderDetailSaved) {
                        connection.commit();
                    }
                }
            }
            connection.rollback();
        } finally {
            connection.setAutoCommit(true);
        }
        return true;
    }
}
