package controller;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;

import dao.DonHangDAO;
import util.ThongBaoUtil;
import view.trangchu.TrangChuView;

public class TrangChuController {
    private TrangChuView trangChuView;
    private DonHangDAO donHangDAO;

    public TrangChuController(TrangChuView trangChuView) {
        this.trangChuView = trangChuView;

        donHangDAO = new DonHangDAO();

        initListeners();
    }

    private void initListeners() {
        trangChuView.getBtnApplyFilter().setOnAction(_ -> {
            try {
                String status = trangChuView.getCbxStatusFilter().getValue();
                Date startDate = null;
                Date endDate = null;

                if (status.equals("Tất cả"))
                    status = null;

                LocalDate startLocalDate = trangChuView.getDpStartDate().getValue();
                if (startLocalDate != null) {
                    startDate = java.sql.Date.valueOf(startLocalDate);
                }

                LocalDate endLocalDate = trangChuView.getDpEndDate().getValue();
                if (endLocalDate != null) {
                    endDate = java.sql.Date.valueOf(endLocalDate);
                }

                trangChuView.getTblRecentOrders().getItems().clear();
                trangChuView.getTblRecentOrders().getItems()
                        .addAll(donHangDAO.getDonHangByFilter(status, startDate, endDate));
            } catch (SQLException e) {
                ThongBaoUtil.baoLoi("Lỗi", "Lỗi kết nối cơ sở dữ liệu: " + e.getMessage());
                e.printStackTrace();
            }
        });

    }
}