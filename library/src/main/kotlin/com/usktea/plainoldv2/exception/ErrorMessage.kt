package com.usktea.plainoldv2.exception

enum class ErrorMessage(
    val value: String
) {
    RGB_VALUE_EXCEPTION("RGB값은 0-255 사이의 숫자만 허용됩니다"),
    PRICE_AMOUNT_EXCEPTION("Price는 마이너스 값이 될 수 없습니다"),
    PRODUCT_NOT_FOUND("해당 상품을 찾을 수 없습니다"),
    LOGIN_FAILED("아이디 또는 비밀번호가 맞지 않습니다"),
    UNIDENTIFIED_USER("사용자 정보가 일치하지 않습니다"),
    REQUEST_ATTRIBUTE_NOT_FOUND("Request Attribute를 가져오지 못 했습니다"),
    PARAMETER_NOT_FOUND("파라미터를 가져오지 못 했습니다"),
    REQUEST_BODY_NOT_FOUND("Request Body를 가져오지 못 했습니다"),
    USERNAME_ALREADY_IN_USE("이미 사용 중인 사용자이름입니다"),
    OAUTH_PROVIDER_NOT_FOUND("OAuth Provider를 가져오지 못 했습니다"),
    INVALID_EMAIL("정확하지 않은 이메일 주소입니다"),
    INVALID_USERNAME("정확하지 않은 사용자 이름입니다"),
    INVALID_NAME("정확하지 않은 이름입니다"),
    INVALID_PHONENUMBER("정확하지 않은 핸드폰 번호입니다"),
    INVALID_QUANTITY_AMOUNT("정확하지 않는 수량입니다"),
    INVALID_ZIPCODE("정확하지 않은 우편번호입니다"),
    INVALID_ADDRESS("정확하지 않은 주소입니다"),
    USER_NOT_EXISTS("사용자 정보를 찾을 수 없습니다"),
    INVALID_ORDER_ITEM("정확하지 않은 상품 정보입니다"),
    PAYMENT_PROVIDER_NOT_FOUND("Payment Provider를 가져오지 못 했습니다"),
    PREPAYMENT_NOT_EXISTS("PrePayment 정보를 찾을 수 없습니다"),
    KAKAOPAY_READY("카카오페이 서버에 결제 준비 요청 진행 중 에러가 발생했습니다"),
    KAKAOPAY_APPROVE("카카오페이 서버에서 결제 승인 요청 진행 중 에러가 발생했습니다"),
    CART_NOT_FOUND("사용자 장바구니 정보를 찾을 수 없습니다"),
    CART_ITEM_NOT_FOUND("사용자 장바구니에서 상품을 찾을 수 없습니다")
}
