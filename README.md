# QuanLyCuaHangFX
  
Hệ thống quản lý cửa hàng bán lẻ đơn giản sử dụng JavaFX và JDBC

![Demo Giao Diện](src/resources/preview.png)
  
## Môi Trường

Dự án yêu cầu
- Tải [JDK 21 hoặc mới hơn](https://jdk.java.net/) phù hợp với hệ điều hành.
> Đảm bảo rằng biến môi trường `JAVA_HOME` đã được thiết lập đúng đến thư mục cài đặt JDK.
> 
- JavaFX 24: Lấy từ trang chính chủ chính [JavaFX](https://openjfx.io/).
- JDBC: Tải xuống từ đây [Microsoft JDBC Driver for SQL Server](https://learn.microsoft.com/vi-vn/sql/connect/jdbc/download-microsoft-jdbc-driver-for-sql-server?view=sql-server-ver16).

## Cài Đặt Cho VSCode Desktop

Thực hiện theo từng bước:

- Tải JavaFX và JDBC giải nén vào vị trí mong muốn.

- Đảm bảo đã cài đặt [Extension Pack for Java](https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-java-pack) trong Visual Studio Code.

- Mở file .vscode/settings.json, thay thế <JavaFX_lib_path> bằng đường dẫn thư mục lib của JavaFX và JDBC trên máy bạn.

- Mở file .vscode/launch.json, thay thế <JavaFX_lib_path> bằng đường dẫn thư mục lib của JavaFX và JDBC trên máy bạn.

## Cấu Trúc Thư Mục

Không gian làm việc mặc định chứa hai thư mục, trong đó:

- `src`: thư mục dùng để lưu trữ mã nguồn

Trong khi đó, các tệp kết quả biên dịch sẽ được tạo trong thư mục `bin` theo mặc định.
