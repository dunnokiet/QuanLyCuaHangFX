package controller.TrangChu;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.Date;

import dao.DonHangDAO;
import util.ThongBaoUtil;
import view.TrangChu.TrangChuView;

public class TrangChuController {
    private TrangChuView trangChuView;
    private DonHangDAO donHangDAO;

    public TrangChuController(TrangChuView trangChuView) {
        this.trangChuView = trangChuView;

        donHangDAO = new DonHangDAO();

        initListeners();
        loadTableData();
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
                        .addAll(donHangDAO.layDonTheoLoc(status, startDate, endDate));
            } catch (SQLException e) {
                ThongBaoUtil.baoLoi("Lỗi", "Lỗi kết nối cơ sở dữ liệu: " + e.getMessage());
                e.printStackTrace();
            }
        });

    }

    private void loadTableData() {
        try {
            trangChuView.getTblRecentOrders().getItems().addAll(donHangDAO.layTatCaDonGanNhat());

            DecimalFormat df = new DecimalFormat("#,### VNĐ");
            trangChuView.getLblRevenueValue().setText(df.format(donHangDAO.layTongDoanhThu()));
            trangChuView.getLblOrdersValue().setText(String.valueOf(donHangDAO.laySoLuongDonHang()));
            trangChuView.getLblCustomersValue().setText(String.valueOf(donHangDAO.laySoLuongKhachHang()));
        } catch (SQLException e) {
            e.printStackTrace();
            ThongBaoUtil.baoLoi("Lỗi", e.getMessage());
        }
    }

}