Controller này cung cấp APIs cho các hành động đăng ký và đăng nhập
- /api/auth/signup
	Kiểm tra username/email đã tồn tại
	Khởi tạo một User mới (với ROLE_USER nếu không xác định quyền)
	Lưu trữ User đến database sử dụng UserRespository
- /api/auth/signin 
	Chứng thực {username, password}
	cập nhật SecurityContext sử dụng đối tượng Authentication   
	Khởi tạo JWT
	lấy UserDetails từ đối tượng chứng thực
	phản hồi chứa JWT và dữ liệu UserDetails 