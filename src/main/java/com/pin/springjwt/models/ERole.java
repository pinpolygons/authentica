package com.pin.springjwt.models;

/*Chúng ta đã có 3 bảng trong database: users, roles, user_roles cho mối quan hệ many-to-many
 * hẫy xác định 3 mô hình này*/
public enum ERole {
	ROLE_USER,
	ROLE_MODERATOR,
	ROLE_ADMIN
}
